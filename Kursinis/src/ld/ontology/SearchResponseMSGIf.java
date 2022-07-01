package ld.ontology;


import jade.util.leap.*;

/**
* Protege name: SearchResponseMSG
* @author ontology bean generator
* @version 2022/05/28, 17:17:02
*/
public interface SearchResponseMSGIf extends jade.content.Predicate {

   /**
   * Protege name: msgID
   */
   public void setMsgID(String value);
   public String getMsgID();

   /**
   * Protege name: apartments
   */
   public void addApartments(Apartment elem);
   public boolean removeApartments(Apartment elem);
   public void clearAllApartments();
   public Iterator getAllApartments();
   public List getApartments();
   public void setApartments(List l);

}
