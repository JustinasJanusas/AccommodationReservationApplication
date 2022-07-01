package ld.ontology;


import jade.util.leap.*;

/**
* Protege name: Apartment
* @author ontology bean generator
* @version 2022/05/28, 17:17:02
*/
public interface ApartmentIf extends jade.content.Concept {

   /**
   * Protege name: company
   */
   public void setCompany(String value);
   public String getCompany();

   /**
   * Protege name: reservations
   */
   public void addReservations(Reservation elem);
   public boolean removeReservations(Reservation elem);
   public void clearAllReservations();
   public Iterator getAllReservations();
   public List getReservations();
   public void setReservations(List l);

   /**
   * Protege name: description
   */
   public void setDescription(String value);
   public String getDescription();

   /**
   * Protege name: name
   */
   public void setName(String value);
   public String getName();

   /**
   * Protege name: country
   */
   public void setCountry(String value);
   public String getCountry();

   /**
   * Protege name: amountOfPeople
   */
   public void setAmountOfPeople(int value);
   public int getAmountOfPeople();

   /**
   * Protege name: address
   */
   public void setAddress(String value);
   public String getAddress();

   /**
   * Protege name: apID
   */
   public void setApID(String value);
   public String getApID();

   /**
   * Protege name: city
   */
   public void setCity(String value);
   public String getCity();

   /**
   * Protege name: price
   */
   public void setPrice(float value);
   public float getPrice();

}
