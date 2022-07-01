package ld.ontology;


import jade.core.AID;
import jade.util.leap.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import ld.ontology.*;

/**
* Protege name: Apartment
* @author ontology bean generator
* @version 2022/05/28, 17:17:02
*/
public class Apartment implements ApartmentIf, Cloneable {

  private static final long serialVersionUID = -4228915551888134581L;

  private String _internalInstanceName = null;
  private AID receivedFrom = null;
  public AID getAID(){
      return receivedFrom;
  }
  public void setAID(AID aid){
      receivedFrom = aid;
  }
  public Apartment() {
    this._internalInstanceName = "";
  }

  public Apartment(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  public String toString() {
    return _internalInstanceName;
  }
  public jade.util.leap.List getReservationTime(Date start, Date end){
      jade.util.leap.List list; 
      list = new  jade.util.leap.ArrayList();
      DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      Date lastEnd = null;
      for(int i = 0; i < reservations.size(); i++){
        Reservation  r = (Reservation)reservations.get(i);
        try {
            
            Date start1 = format.parse(r.getStartDate());
            Date end1 = format.parse(r.getEndDate());
            if(!(end.compareTo(start1) < 0)){
                
                if(start.compareTo(start1) < 0){
                    if(lastEnd == null || !(start.compareTo(lastEnd) < 0)){
                        
                        list.add(new Reservation(format.format(start), format.format(start1)));
                        lastEnd = end1;
                    }
                    else if(lastEnd.compareTo(start1) < 0){
                        list.add(new Reservation(format.format(lastEnd), format.format(start1)));
                        lastEnd = end1;
                    }
                }
                   
                else if(!(end.compareTo(end1) > 0)){
                    break;
                }
                 lastEnd = end1;
                if(i == reservations.size()-1){
                    
                    if(lastEnd == null || !(start.compareTo(lastEnd) < 0)){
                        list.add(new Reservation(format.format(start), format.format(end)));
                        break;
                    }
                    else if(lastEnd.compareTo(end) < 0){{
                        list.add(new Reservation(format.format(lastEnd), format.format(end)));
                        break;
                    }
                }
            }
            }
            else{
                if(lastEnd == null || !(start.compareTo(lastEnd) < 0)){
                    list.add(new Reservation(format.format(start), format.format(end)));
                    break;
                }
                else{
                    list.add(new Reservation(format.format(lastEnd), format.format(end)));
                    break;
                }
            }
             lastEnd = end1;
        } 
        catch (ParseException ex) {
            Logger.getLogger(Reservation.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
      }
      if(reservations.isEmpty())
          list.add(new Reservation(format.format(start), format.format(end)));
      return list;
      
  }
   /**
   * Protege name: company
   */
   private String company;
   public void setCompany(String value) { 
    this.company=value;
   }
   public String getCompany() {
     return this.company;
   }

   /**
   * Protege name: reservations
   */
   private List reservations = new ArrayList();
   public void addReservations(Reservation elem) {
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      try {
          Date start = format.parse(elem.getStartDate());
          for(int i = 0; i < reservations.size(); i++){
              
            if(start.before(format.parse(((Reservation)reservations.get(i)).getStartDate())) || i == reservations.size()){
                reservations.add(i, elem);
                return;
            }
        }  
          reservations.add(elem);
      } catch (ParseException ex) {
          Logger.getLogger(Apartment.class.getName()).log(Level.SEVERE, null, ex);
      }

   }
   public boolean addReservationBool(Reservation elem) {
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
        Date e1 = format.parse(elem.getStartDate());
        Date e2 = format.parse(elem.getEndDate());
        if(!(e1.compareTo(e2) < 0))
            return false;
          for(int i = 0; i < reservations.size(); i++){
              
            Date d1 =   format.parse(((Reservation)reservations.get(i)).getStartDate());
            Date d2 = format.parse(((Reservation)reservations.get(i)).getEndDate());
            if(e1.compareTo(d1) == 0 || e2.compareTo(d2) == 0){
                return false;
            }
            else if((e1.before(d2) && !(e1.before(d1))) || (e2.after(d1) && !(e2.after(d2)))){
                return false;
            }
            else if(e1.before(d1) || i == reservations.size()){
                reservations.add(i, elem);
                return true;
            }
        }  
          reservations.add(elem);
          return true;
      } catch (ParseException ex) {
          Logger.getLogger(Apartment.class.getName()).log(Level.SEVERE, null, ex);
      }
      return false;
   }
   public void removeReservation(String start, String end, AID aid){
       for(Object o : reservations.toArray()){
           Reservation r = (Reservation) o;
           if(r.getStartDate().equals(start)){
               if(r.getEndDate().equals(end) && r.getAgentName().equals(aid.getName())){
                   reservations.remove(o);
                   
               }
               return;
           }
       }
   }
   public boolean removeReservations(Reservation elem) {
     boolean result = reservations.remove(elem);
     return result;
   }
   public void clearAllReservations() {
     reservations.clear();
   }
   public Iterator getAllReservations() {return reservations.iterator(); }
   public List getReservations() {return reservations; }
   public void setReservations(List l) {reservations = l; }

   /**
   * Protege name: description
   */
   private String description;
   public void setDescription(String value) { 
    this.description=value;
   }
   public String getDescription() {
     return this.description;
   }

   /**
   * Protege name: name
   */
   private String name;
   public void setName(String value) { 
    this.name=value;
   }
   public String getName() {
     return this.name;
   }

   /**
   * Protege name: country
   */
   private String country;
   public void setCountry(String value) { 
    this.country=value;
   }
   public String getCountry() {
     return this.country;
   }

   /**
   * Protege name: amountOfPeople
   */
   private int amountOfPeople;
   public void setAmountOfPeople(int value) { 
    this.amountOfPeople=value;
   }
   public int getAmountOfPeople() {
     return this.amountOfPeople;
   }

   /**
   * Protege name: address
   */
   private String address;
   public void setAddress(String value) { 
    this.address=value;
   }
   public String getAddress() {
     return this.address;
   }

   /**
   * Protege name: apID
   */
   private String apID;
   public void setApID(String value) { 
    this.apID=value;
   }
   public String getApID() {
     return this.apID;
   }

   /**
   * Protege name: city
   */
   private String city;
   public void setCity(String value) { 
    this.city=value;
   }
   public String getCity() {
     return this.city;
   }

   /**
   * Protege name: price
   */
   private float price;
   public void setPrice(float value) { 
    this.price=value;
   }
   public float getPrice() {
     return this.price;
   }
   @Override
    public Apartment clone()
        throws CloneNotSupportedException
    {
        Apartment clone = (Apartment)super.clone();
        clone.setReservations(new ArrayList());
        for (Object item :  reservations.toArray()) 
            clone.addReservations((Reservation) ((Reservation) item).clone());
        return clone;
    }
}
