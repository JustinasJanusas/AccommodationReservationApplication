package ld.ontology;


import ld.ontology.*;

/**
* Protege name: SearchMSG
* @author ontology bean generator
* @version 2022/05/28, 17:17:02
*/
public class SearchMSG implements SearchMSGIf {

  private static final long serialVersionUID = -4228915551888134581L;

  private String _internalInstanceName = null;

  public SearchMSG() {
    this._internalInstanceName = "";
  }

  public SearchMSG(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  public String toString() {
    return _internalInstanceName;
  }

   /**
   * Protege name: countryName
   */
   private String countryName;
   public void setCountryName(String value) { 
    this.countryName=value;
   }
   public String getCountryName() {
     return this.countryName;
   }

   /**
   * Protege name: cityName
   */
   private String cityName;
   public void setCityName(String value) { 
    this.cityName=value;
   }
   public String getCityName() {
     return this.cityName;
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
