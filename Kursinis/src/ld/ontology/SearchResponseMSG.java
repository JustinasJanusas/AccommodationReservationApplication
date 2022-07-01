package ld.ontology;


import jade.util.leap.*;
import ld.ontology.*;

/**
* Protege name: SearchResponseMSG
* @author ontology bean generator
* @version 2022/05/28, 17:17:02
*/
public class SearchResponseMSG implements SearchResponseMSGIf {

  private static final long serialVersionUID = -4228915551888134581L;

  private String _internalInstanceName = null;

  public SearchResponseMSG() {
    this._internalInstanceName = "";
  }

  public SearchResponseMSG(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  public String toString() {
    return _internalInstanceName;
  }

   /**
   * Protege name: msgID
   */
   private String msgID;
   public void setMsgID(String value) { 
    this.msgID=value;
   }
   public String getMsgID() {
     return this.msgID;
   }

   /**
   * Protege name: apartments
   */
   private List apartments = new ArrayList();
   public void addApartments(Apartment elem) { 
     apartments.add(elem);
   }
   public boolean removeApartments(Apartment elem) {
     boolean result = apartments.remove(elem);
     return result;
   }
   public void clearAllApartments() {
     apartments.clear();
   }
   public Iterator getAllApartments() {return apartments.iterator(); }
   public List getApartments() {return apartments; }
   public void setApartments(List l) {apartments = l; }

}
