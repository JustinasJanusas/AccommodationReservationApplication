package ld.ontology;


import ld.ontology.*;

/**
* Protege name: BookMSG
* @author ontology bean generator
* @version 2022/05/28, 17:17:02
*/
public class BookMSG implements BookMSGIf {

  private static final long serialVersionUID = -4228915551888134581L;

  private String _internalInstanceName = null;

  public BookMSG() {
    this._internalInstanceName = "";
  }

  public BookMSG(String instance_name) {
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
   * Protege name: startDate
   */
   private String startDate;
   public void setStartDate(String value) { 
    this.startDate=value;
   }
   public String getStartDate() {
     return this.startDate;
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
   * Protege name: endDate
   */
   private String endDate;
   public void setEndDate(String value) { 
    this.endDate=value;
   }
   public String getEndDate() {
     return this.endDate;
   }

}
