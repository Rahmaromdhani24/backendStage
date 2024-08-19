package com.rahma.AvEchelon.Entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@Entity
@Table(name = "avancement_audit")
public class Trigger_Avancement {

	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    private String mle;

	    private String action;

		private String nom;
		
		private String qualification;

		private String cat;
		
		@Column(name="S_CAT")
		private String sCat;
		
		private String ech;

		private String sbase;
		
		private String th;

		@Column(name="IND_DIFF")
		private String indDiff;
		
		@Temporal(TemporalType.DATE)
		@Column(name="D_EFFET")
		private Date dEffet;

		@Temporal(TemporalType.DATE)
		@Column(name="D_P_AV")
		private Date dPAv;

		private String observation;

		public int getId() {		return id;	}

		public String getMle() {			return mle;		}

		public String getAction() {			return action;	}

		public String getNom() {			return nom;		}

		public String getQualification() {			return qualification;	}

		public String getCat() {			return cat;	}

		public String getsCat() {			return sCat;	}

		public String getEch() {			return ech;		}

		public String getSbase() {		return sbase;	}

		public String getTh() {		return th;	}

		public String getIndDiff() {		return indDiff;	}

		public Date getdEffet() {		return dEffet;	}

		public Date getdPAv() {			return dPAv;	}

		public String getObservation() {		return observation;	}

		public Trigger_Avancement(int id, String mle, String action, String nom, String qualification, String cat,
				String sCat, String ech, String sbase, String th, String indDiff, Date dEffet, Date dPAv,
				String observation) {
			super();
			this.id = id;
			this.mle = mle;
			this.action = action;
			this.nom = nom;
			this.qualification = qualification;
			this.cat = cat;
			this.sCat = sCat;
			this.ech = ech;
			this.sbase = sbase;
			this.th = th;
			this.indDiff = indDiff;
			this.dEffet = dEffet;
			this.dPAv = dPAv;
			this.observation = observation;
		}

		public Trigger_Avancement() {
			super();
		}
		
		
		

}
