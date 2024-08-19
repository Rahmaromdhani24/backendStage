package com.rahma.AvEchelon.Services.Historique;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Historiques_Supprimes {
	
	 private String mle;
	 private Date dateEffetA;
	 private String ech;
	 
	 
	 public String getFormattedDateEffetA() {
	        if (dateEffetA != null) {
	            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	            return formatter.format(dateEffetA);
	        }
	        return null;
	    } 
	 
	    // Getters and setters for mle, dateEffetA, and ech
	    public String getMle() {     return mle;    }

	    public void setMle(String mle) {        this.mle = mle;    }

	    public Date getDateEffetA() {      return dateEffetA;   }

	    public void setDateEffetA(Date dateEffetA) {        this.dateEffetA = dateEffetA;    }

	    public String getEch() {        return ech;    }

	    public void setEch(String ech) {       this.ech = ech;   }

		public Historiques_Supprimes(String mle, Date dateEffetA, String ech) {
			super();
			this.mle = mle;
			this.dateEffetA = dateEffetA;
			this.ech = ech;
		}
		 public Historiques_Supprimes() {}

	 
}
