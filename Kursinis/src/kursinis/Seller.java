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
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.Scanner;
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
public class Seller extends Agent {
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    int entry = 0;
    ArrayList<Apartment> apartments = new ArrayList<>();
    String fileName = "";
    @Override
    public void setup(){
        Object[] args = getArguments();
        if((args != null) && (args.length > 0)){
            addBehaviour(new ReadData((String)args[0]));
        }
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName( getAID() );
        ServiceDescription sd  = new ServiceDescription();
        sd.setType("Booking");
        sd.setName("Booking"+"-"+getLocalName() );
        dfd.addServices(sd);
        try 
        {
            DFService.register( this, dfd );  
        }
        catch(Exception e){}
        addBehaviour(new ListenBehaviour(this));
        
    }
    //Realizuoti
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
            ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
            
            if (msg != null) 
            {                                  
                try{
                    AID sender = msg.getSender();
                    ContentElement c = cm.extractContent(msg);
                    if(c instanceof SearchMSG){
                        SearchMSG search = (SearchMSG) c;
                        if(!apartments.isEmpty())
                            myAgent.addBehaviour(new FilterByLocation(myAgent, search.getCityName(), search.getCountryName(), search.getAmountOfPeople(),
                                    search.getMsgID(), search.getStartDate(), search.getEndDate(), sender));
                        else{
                            sendSearchResponseMSG(search.getMsgID(), sender, null);
                        }
                        
                    }
                    else if(c instanceof BookMSG){
                        BookMSG book = (BookMSG) cm.extractContent(msg);
                        boolean added = false;
                        for(Apartment ap : apartments){
                            if(ap.getApID().equals(book.getApID())){
                                Reservation res = new Reservation(book.getStartDate(), book.getEndDate());
                                res.setAgentName(sender.getName());
                                added = ap.addReservationBool(res);
                                break;
                            }
                        }
                        ACLMessage req = new ACLMessage(ACLMessage.INFORM);
                        req.setLanguage(codec.getName()); 
                        req.setOntology(onto.getName());
                        req.clearAllReceiver();
                        req.addReceiver(sender);
                        BookResponseMSG  resMsg = new BookResponseMSG();
                        resMsg.setMsgID(book.getMsgID());
                        resMsg.setReservationSuccessful(added);
                        try
                        {
                            cm.fillContent(req, resMsg);  
                            send(req);
                        } 
                        catch (Exception ex) 
                        { 
                            System.out.println("A["+getLocalName()+"] Error while building message: " +ex.getMessage());
                        }
                    }
                    else if(c instanceof CancelReservationMSG){
                        CancelReservationMSG cancel = (CancelReservationMSG) c;
                        for(Apartment ap : apartments){
                            if(ap.getApID().equals(cancel.getApID())){
                                ap.removeReservation(cancel.getStartDate(), cancel.getEndDate(), sender);
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
    //Realizuoti
    private class ReadData extends OneShotBehaviour{
        
        public ReadData(String file)
        {
            fileName = file;
        }
                
        @Override
        public void action(){
            try {
                File myObj = new File(fileName);
                Scanner reader = new Scanner(myObj);
                reader.nextLine();
                while (reader.hasNextLine()) {
                  String line = reader.nextLine();
                  String[] fields = line.split(":");
                  Apartment ap = new Apartment();
                  ap.setName(fields[0]);
                  ap.setApID(entry+"");
                  entry++;
                  ap.setCompany(fields[1]);
                  ap.setDescription(fields[2]);
                  ap.setAmountOfPeople(Integer.parseInt(fields[3]));
                  ap.setAddress(fields[4]);
                  ap.setCity(fields[5]);
                  ap.setCountry(fields[6]);
                  ap.setPrice(Float.parseFloat(fields[7]));
                  if(fields.length > 8){
                    String[] r = fields[8].split(";");
                    for(String res : r){
                        String[] dates = res.split("\\|");
                        if(dates.length > 2){
                            Reservation reservation = new Reservation();
                            reservation.setStartDate(dates[0]);
                            reservation.setEndDate(dates[1]);
                            reservation.setAgentName(dates[2]);
                            ap.addReservations(reservation);
                        }
                    }
                  }
                  apartments.add(ap);
                }
                reader.close();
              } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
             }
        }
    }
    @Override
    public void takeDown(){
        try { DFService.deregister(this);}
        catch (Exception e) {e.printStackTrace();}
    }
    private class FilterByLocation extends SimpleBehaviour
    {
        private int i = 0;
        private String city;
        private String country;
        private jade.util.leap.List filteredApartments;
        private String msgID;
        private String start;
        private String end;
        private AID sendTo;
        private int peopleCount;
        public FilterByLocation(Agent a, String cityName, String countryName, int count, String id, String startDate, String endDate, AID sender) 
        { 
            super(a);
            city = cityName;
            country = countryName;
            filteredApartments = new jade.util.leap.ArrayList();
            msgID = id;
            start = startDate;
            end = endDate;
            sendTo = sender;
            peopleCount = count;
        }
        
        @Override
        public void action()
        {
            Apartment apartment = apartments.get(i);
            if(apartment.getCity().toLowerCase().equals(city.toLowerCase()) && 
                    apartment.getCountry().toLowerCase().equals(country.toLowerCase()) && 
                    apartment.getAmountOfPeople() >= peopleCount){
                try {
                    filteredApartments.add((Apartment)apartment.clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Seller.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            i++;
            block(1);
        }
        
        @Override
        public boolean done() 
        {
            return i >= apartments.size();
        }
        @Override
        public int onEnd(){
            if(!filteredApartments.isEmpty()){
                myAgent.addBehaviour(new FilterByDate(myAgent, filteredApartments, start, end, msgID, sendTo));
            }
            else{
                sendSearchResponseMSG(msgID, sendTo, filteredApartments);
            }
            return 0;
        }
    }
    private class FilterByDate extends SimpleBehaviour
    {
        private int i = 0;
        private Date start;
        private Date end;
        private String msgID;
        private jade.util.leap.List unfilteredApartments;
        private jade.util.leap.List filteredApartments;
        private AID sendTo;
        public FilterByDate(Agent a, jade.util.leap.List array, String startDate, String endDate, String id, AID sender) 
        { 
            super(a);
            unfilteredApartments = array;
            filteredApartments = new jade.util.leap.ArrayList();
            try {
                start = format.parse(startDate);
                end = format.parse(endDate);
            } catch (ParseException ex) {
                Logger.getLogger(Seller.class.getName()).log(Level.SEVERE, null, ex);
            }
            msgID = id;
            sendTo = sender;
        }
        
        @Override
        public void action()
        {
            Apartment apartment = (Apartment) unfilteredApartments.get(i);
            jade.util.leap.List list =  apartment.getReservationTime(start, end);
            if(list != null && !list.isEmpty()){
                apartment.setReservations((List) list);
                filteredApartments.add(apartment);
            }
            i++;
            block(1);
        }
        
        @Override
        public boolean done() 
        {
            return (i >= unfilteredApartments.size());
        }
        @Override
        public int onEnd(){
            sendSearchResponseMSG(msgID, sendTo, filteredApartments);
            
            return 0;
        }
    }
    private void sendSearchResponseMSG(String msgID, AID sendTo, jade.util.leap.List filteredApartments){
        Ontology onto = BookingOntology.getInstance();
            Codec codec = new SLCodec();
            ContentManager cm = getContentManager();
            cm.registerLanguage(codec);
            cm.registerOntology(onto); 
            ACLMessage req = new ACLMessage(ACLMessage.INFORM);
            req.setLanguage(codec.getName()); 
            req.setOntology(onto.getName());
            req.clearAllReceiver();
            req.addReceiver(sendTo);
            SearchResponseMSG  msg = new SearchResponseMSG();
            msg.setApartments((List) filteredApartments);
            msg.setMsgID(msgID);
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
