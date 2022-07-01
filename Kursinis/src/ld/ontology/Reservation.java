package ld.ontology;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import ld.ontology.*;

/**
* Protege name: Reservation
* @author ontology bean generator
* @version 2022/05/28, 17:17:02
*/
public class Reservation implements ReservationIf, Cloneable {

  private static final long serialVersionUID = -4228915551888134581L;

  private String _internalInstanceName = null;
  
  
  public Reservation() {
    this._internalInstanceName = "";
  }
  public Reservation(String start, String end) {
    this._internalInstanceName = "";
    this.startDate = start;
    this.endDate = end;
    this.agentName = "";
  }
  public Reservation(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  public String toString() {
    return _internalInstanceName;
  }

   /**
   * Protege name: agentName
   */
   private String agentName;
   public void setAgentName(String value) { 
    this.agentName=value;
   }
   public String getAgentName() {
     return this.agentName;
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
   * Protege name: endDate
   */
   private String endDate;
   public void setEndDate(String value) { 
    this.endDate=value;
   }
   public String getEndDate() {
     return this.endDate;
   }
   @Override
    public Object clone()
        throws CloneNotSupportedException
    {
        return super.clone();
    }
}
