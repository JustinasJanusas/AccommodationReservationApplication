package ld.ontology;


import ld.ontology.*;

/**
* Protege name: BookResponseMSG
* @author ontology bean generator
* @version 2022/05/28, 17:17:02
*/
public class BookResponseMSG implements BookResponseMSGIf {

  private static final long serialVersionUID = -4228915551888134581L;

  private String _internalInstanceName = null;

  public BookResponseMSG() {
    this._internalInstanceName = "";
  }

  public BookResponseMSG(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  public String toString() {
    return _internalInstanceName;
  }

   /**
   * Protege name: reservationSuccessful
   */
   private boolean reservationSuccessful;
   public void setReservationSuccessful(boolean value) { 
    this.reservationSuccessful=value;
   }
   public boolean getReservationSuccessful() {
     return this.reservationSuccessful;
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

}
