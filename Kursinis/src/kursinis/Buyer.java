/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kursinis;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Result;
import jade.core.AID;
import jade.core.Agent;
import jade.core.Location;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.Iterator;
import jade.wrapper.ControllerException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import ld.ontology.Apartment;
import ld.ontology.BookMSG;
import ld.ontology.BookResponseMSG;
import ld.ontology.BookingOntology;
import ld.ontology.CancelReservationMSG;
import ld.ontology.Reservation;
import ld.ontology.SearchMSG;
import ld.ontology.SearchResponseMSG;

/**
 *
 * @author Justinas
 */
public class Buyer extends GuiAgent {
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private ArrayList<AID> providerList = new ArrayList<>();
    private jade.util.leap.List offerList = new jade.util.leap.ArrayList();
    private int messageCount = 0;
    private int receiverCount = 0;
    private int responseCount = 0;
    private int bookingCount = 0;
    private int bookingResponseCount = 0;
    private String lastSearchMessageID = "";
    private String lastBookMessageID = "";
    private String reservationStartDate;
    private String reservationEndDate;
    public final static int SEARCH = 0;
    public final static int BOOK = 1;
    public final static int CANCEL = 2;
    Comparator<Apartment> byPrice = new Comparator<Apartment>() {
		@Override
		public int compare(Apartment o1, Apartment o2) {
			return ((Float)o1.getPrice()).compareTo(o2.getPrice());
		}
	};
    private jade.util.leap.List apartments = new jade.util.leap.ArrayList();
    Gui gui = null;
    @Override
    protected void setup(){
        gui = new Gui(this);
        gui.setVisible(true);
        addBehaviour(new ReceiveNotification());
        addBehaviour(new SubscribeServiceProviders());
        addBehaviour(new ListenBehaviour(this));
    }
    
    private class ReceiveNotification extends CyclicBehaviour 
    {
        @Override
        public void action() 
        {
            ACLMessage msg = receive(MessageTemplate.MatchSender(getDefaultDF()));
                
            if (msg != null)
            {
                try 
                {
                    DFAgentDescription[] dfds = DFService.decodeNotification(msg.getContent());
                    for (int i=0; i<dfds.length; i++)                       
                    {                        
                        if (!dfds[i].getAllServices().hasNext())
                        {   
                            for(AID a : providerList){
                                if(dfds[i].getName().equals(a)){
                                    providerList.remove(a);
                                    break;
                                }
                            }
                            
                        }
                        else
                        {
                            providerList.add(dfds[i].getName());
                        }
                        
                    }
               }
               catch (Exception ex) { ex.printStackTrace();}
            }           
            block();   
         }  
    }
    @Override
    protected void onGuiEvent(GuiEvent ge) {
        Ontology onto = BookingOntology.getInstance();
        Codec codec = new SLCodec();
        ContentManager cm = getContentManager();
        cm.registerLanguage(codec);
        cm.registerOntology(onto);
        int cmd = ge.getType();
        if (cmd == SEARCH) 
        {
            if (ge.getParameter(0) instanceof String && ge.getParameter(1) instanceof Integer
                    && ge.getParameter(2) instanceof Integer && ge.getParameter(3) instanceof String
                    && ge.getParameter(4) instanceof String)
            {
                offerList.clear();
                gui.showTable(offerList);
                String startDate = (String) ge.getParameter(0);
                reservationStartDate = startDate;

                int durationTime = (int) ge.getParameter(1);
                int peopleCount = (int) ge.getParameter(2);
                String city = ((String) ge.getParameter(3)).trim();
                String country = ((String) ge.getParameter(4)).trim();
                ACLMessage req = new ACLMessage(ACLMessage.REQUEST);
                req.setLanguage(codec.getName()); 
                req.setOntology(onto.getName());
                req.clearAllReceiver();
                receiverCount = 0;
                for(AID aid : providerList){
                    req.addReceiver(aid);
                    receiverCount++;
                }
                SearchMSG msg = new SearchMSG();
                msg.setStartDate(startDate);
                Calendar cal = Calendar.getInstance();
                try {
                    cal.setTime(format.parse(startDate));
                } catch (ParseException ex) {
                    Logger.getLogger(Buyer.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
                cal.add(Calendar.DAY_OF_YEAR, durationTime);
                String endDate = format.format(cal.getTime());
                reservationEndDate = endDate;
                msg.setEndDate(endDate);
                msg.setAmountOfPeople(peopleCount);
                msg.setCityName(city);
                msg.setCountryName(country);
                lastSearchMessageID = getLocalName()+messageCount;
                responseCount = 0;
                apartments.clear();
                messageCount++;
                msg.setMsgID(lastSearchMessageID);
                try
                {
                    cm.fillContent(req, msg);  
                    send(req);
                    addBehaviour(new SearchResponseTimeOut(this, 10000, lastSearchMessageID));
                } 
                catch (Exception ex) 
                { 
                    System.out.println("A["+getLocalName()+"] Error while building message: " +ex.getMessage());
                }
                
                
            }
        }
        else if(cmd == BOOK){
            if(offerList != null){
                bookingCount = 0;
                bookingResponseCount = 0;
                lastBookMessageID = getLocalName()+messageCount;
                messageCount++;
                for(int i = 0; i< offerList.size(); i++){
                    ACLMessage req = new ACLMessage(ACLMessage.REQUEST);
                    req.setLanguage(codec.getName()); 
                    req.setOntology(onto.getName());
                    req.clearAllReceiver();
                    BookMSG msg = new BookMSG();
                    Apartment ap = (Apartment) offerList.get(i);
                    Reservation res = (Reservation) ap.getReservations().get(0);
                    req.addReceiver(ap.getAID());
                    msg.setApID(ap.getApID());
                    msg.setStartDate(res.getStartDate());
                    msg.setEndDate(res.getEndDate());
                    
                    bookingCount++;
                    
                    msg.setMsgID(lastBookMessageID);
                    try
                    {
                        cm.fillContent(req, msg);  
                        send(req);
                        
                    } 
                    catch (Exception ex) 
                    { 
                        System.out.println("A["+getLocalName()+"] Error while building message: " +ex.getMessage());
                    }
                }
                addBehaviour(new BookResponseTimeOut(this, 10000, lastBookMessageID));
            }
        }
        else if (cmd == CANCEL){
            if(offerList != null){
                cancelReservations();
                gui.setError("Užsakymai atšaukti");
            }
        }
    }
    private void cancelReservations(){
        Ontology onto = BookingOntology.getInstance();
        Codec codec = new SLCodec();
        ContentManager cm = getContentManager();
        cm.registerLanguage(codec);
        cm.registerOntology(onto);
        
        for(int i = 0; i< offerList.size(); i++){
            ACLMessage req = new ACLMessage(ACLMessage.REQUEST);
            req.setLanguage(codec.getName()); 
            req.setOntology(onto.getName());
            req.clearAllReceiver();
            CancelReservationMSG msg = new CancelReservationMSG();
            Apartment ap = (Apartment) offerList.get(i);
            Reservation res = (Reservation) ap.getReservations().get(0);
            req.addReceiver(ap.getAID());
            msg.setApID(ap.getApID());
            msg.setStartDate(res.getStartDate());
            msg.setEndDate(res.getEndDate());
            try
            {
                cm.fillContent(req, msg);  
                send(req);

            } 
            catch (Exception ex) 
            { 
                System.out.println("A["+getLocalName()+"] Error while building message: " +ex.getMessage());
            }
        }
        
    }

    private class ListenBehaviour extends CyclicBehaviour{
        public ListenBehaviour(Agent a){
            super(a);
        }
        @Override
        public void action(){
            Ontology onto = BookingOntology.getInstance();
            Codec codec = new SLCodec();
            ContentManager cm = getContentManager();
            cm.registerLanguage(codec);
            cm.registerOntology(onto);
            ACLMessage msg = myAgent.receive(MessageTemplate.and(MessageTemplate.not(MessageTemplate.MatchSender(getDefaultDF())), 
                                                                MessageTemplate.MatchPerformative(ACLMessage.INFORM)));
            
            if (msg != null) 
            {                                  
                try{
                    ContentElement c = cm.extractContent(msg);
                    if(c instanceof SearchResponseMSG){
                        SearchResponseMSG response = (SearchResponseMSG) c;
                        if(response.getMsgID().equals(lastSearchMessageID)){
                            responseCount++;
                            if(response.getApartments() != null){
                                for(Object ob : response.getApartments().toArray()){
                                    Apartment a = (Apartment) ob;
                                    a.setAID(msg.getSender());
                                    apartments.add(a);
                                    
                                }
                            }
                            if(responseCount == receiverCount && !apartments.isEmpty()){
                                myAgent.addBehaviour(new SortApartments(myAgent, apartments));
                            }
                            else if(responseCount == receiverCount && apartments.isEmpty()){
                                gui.setError("Apgyvendinimo vietų nerasta");
                            }
                        }
                    }
                    else if(c instanceof BookResponseMSG){
                        BookResponseMSG response = (BookResponseMSG) c;
                        if(response.getMsgID().equals(lastBookMessageID)){
                            bookingResponseCount++;
                            if(response.getReservationSuccessful()){
                                if(bookingResponseCount == bookingCount){
                                    gui.setError("Sėkmingai rezervuota");
                                    lastBookMessageID = "";
                                    gui.setCancel(true);
                                }
                            }
                            else{
                                cancelReservations();
                                gui.setError("Įvyko klaida užsakinėjant");
                            }
                        }
                    }
                }
                catch(Exception e){
                    
                }
            }
            else block();
        }
    }
    private class SubscribeServiceProviders extends OneShotBehaviour
    {       
        @Override
        public void action()
        {
            DFAgentDescription dfd = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType("Booking");
            dfd.addServices(sd);
            SearchConstraints sc = new SearchConstraints();
            sc.setMaxResults(Long.MAX_VALUE);
            send(DFService.createSubscriptionMessage(this.myAgent, getDefaultDF(), dfd, sc));
        }
    }
    private class SearchResponseTimeOut extends WakerBehaviour{
        String msgID;
        public SearchResponseTimeOut(Agent a, int time, String id){
            super(a, time);
            msgID = id;
        }
        @Override
        public void onWake(){
            if(msgID.equals(lastSearchMessageID) && responseCount < receiverCount && !apartments.isEmpty()){
                myAgent.addBehaviour(new SortApartments(myAgent, apartments));
                lastSearchMessageID = "";
            }
        }
    }
    private class BookResponseTimeOut extends WakerBehaviour{
        String msgID;
        public BookResponseTimeOut(Agent a, int time, String id){
            super(a, time);
            msgID = id;
        }
        @Override
        public void onWake(){
            if(msgID.equals(lastBookMessageID) && bookingCount > bookingResponseCount){
                gui.setError("Įvyko klaida rezervuojant");
                cancelReservations();
            }
        }
    }
    private class FindCheapest extends SimpleBehaviour
    {
        private int i = 0;
        private jade.util.leap.List filteredApartments = null;
        private jade.util.leap.List offerApartments = null;
        ArrayList<Reservation> holes = new ArrayList();
        public FindCheapest(Agent a, jade.util.leap.List array) 
        { 
            super(a);
            filteredApartments = array;
            offerApartments = new jade.util.leap.ArrayList();
            holes.add(new Reservation(reservationStartDate, reservationEndDate)) ;
        }

        @Override
        public void action()
        {
            Apartment ap = (Apartment) filteredApartments.get(i);
            int j = 0;
            for(int h = 0; h < holes.size(); h++){
                
                Reservation hole = holes.get(h);
                try {
                    Date hStart = format.parse(hole.getStartDate());
                    Date hEnd = format.parse(hole.getEndDate());
                    for(; j < ap.getReservations().size(); j++){
                        Reservation res = (Reservation) ap.getReservations().get(j);
                        Date rStart = format.parse(res.getStartDate());
                        Date rEnd = format.parse(res.getEndDate());
                        if(!(rEnd.compareTo(hStart) > 0)){
                            
                            
                        }
                        else if(!(rStart.compareTo(hEnd) < 0)){
                            break;
                        }
                        else{
                            Apartment newAp = ap.clone();
                            newAp.clearAllReservations();
                            if(!(rStart.compareTo(hStart) > 0)){
                                
                                if(!(rEnd.compareTo(hEnd) < 0)){
                                    newAp.addReservations(new Reservation(hole.getStartDate(), hole.getEndDate()));
                                    addApartment(newAp);
                                    holes.remove(hole);
                                    h--;
                                }
                                else{
                                    newAp.addReservations(new Reservation(hole.getStartDate(), res.getEndDate()));
                                    addApartment(newAp);
                                    holes.add(h, new Reservation(res.getEndDate(), hole.getEndDate()));
                                    holes.remove(hole);
                                    h--;
                                }
                            }
                            else if(!(rEnd.compareTo(hEnd) < 0)){
                                newAp.addReservations(new Reservation(res.getStartDate(), hole.getEndDate()));
                                addApartment(newAp);
                                holes.add(h, new Reservation(hole.getStartDate(), res.getStartDate()));
                                holes.remove(hole);
                            }
                            else{
                                newAp.addReservations(new Reservation(res.getStartDate(), res.getEndDate()));
                                addApartment(newAp);
                                holes.add(h, new Reservation(hole.getStartDate(), res.getStartDate()));
                                holes.add(h+1, new Reservation(res.getEndDate(), hole.getEndDate()));
                                holes.remove(hole);
                            }
                            break;
                        }
                    }
                } 
                catch (ParseException ex) {
                    Logger.getLogger(Buyer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Buyer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            i++;
            block(1);
        }
        
        @Override
        public boolean done() 
        {
            return i >= filteredApartments.size() || holes.size() == 0;
        }
        @Override
        public int onEnd(){
            offerList = offerApartments;
            gui.showTable(offerList);
            calculatePrice(offerList);
            if(!holes.isEmpty()){
                gui.setError("Sąrašas nėra pilnai užpildytas");
            }
            return 0;
        }
        private void addApartment(Apartment ap) throws ParseException{
            Date apEnd = format.parse(((Reservation)ap.getReservations().get(0)).getEndDate());
            for(int k = 0; k < offerApartments.size(); k++){
                Apartment listAp = (Apartment) offerApartments.get(k);
                Date liStart = format.parse(((Reservation)listAp.getReservations().get(0)).getEndDate());
                if(!(apEnd.compareTo(liStart) > 0)){
                    offerApartments.add(k, ap);
                    return;
                }
            }
            offerApartments.add(ap);
        }
    }
    private void calculatePrice(jade.util.leap.List list){
        float sum = 0;
        for(Object obj : list.toArray()){
            Apartment ap = (Apartment) obj;
            Reservation re = (Reservation) ap.getReservations().get(0);
            long daysBetween;
            //sum +=
            daysBetween = ChronoUnit.DAYS.between(LocalDate.parse(re.getStartDate()), LocalDate.parse(re.getEndDate()));
            sum += daysBetween*ap.getPrice();
        }
        gui.setPrice(sum);
    }
    private class SortApartments extends SimpleBehaviour
    {
        private int i = 0;
        private jade.util.leap.List unsortedApartments = null;
        private jade.util.leap.List sortedApartments = null;
        public SortApartments(Agent a, jade.util.leap.List array) 
        { 
            super(a);
            unsortedApartments = array;
            sortedApartments = new jade.util.leap.ArrayList();
        }

        @Override
        public void action()
        {
            boolean added = false;
            Apartment ap1 = (Apartment) unsortedApartments.get(i);
            Iterator it =sortedApartments.iterator();
            int place = 0;
            while(it.hasNext()){
                Apartment ap2 = (Apartment) it.next();
                if(ap2.getPrice() >= ap1.getPrice()){
                    sortedApartments.add(place, ap1);
                    added = true;
                    break;
                }
                place++;
            }
            if(!added){
                sortedApartments.add(ap1);
            }
            i++;
            block(1);
        }
        
        @Override
        public boolean done() 
        {
            return i >= unsortedApartments.size();
        }
        @Override
        public int onEnd(){
            apartments = sortedApartments;
            myAgent.addBehaviour(new FindCheapest(myAgent, apartments));
            return 0;
        }
    }
    @Override
   public void takeDown(){
       if(gui != null){
            gui.setVisible(false);
            gui.dispose();
       }
   }
   
}
