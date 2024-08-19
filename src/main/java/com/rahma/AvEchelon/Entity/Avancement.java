package com.rahma.AvEchelon.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the t_avancement database table.
 * 
 */
@Getter
@Setter
@Entity
@Table(name="t_avancement")
public class Avancement  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	//bi-directional many-to-one association to Personnel
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JsonIgnore
	 @JoinColumn(name="Mle", referencedColumnName="mle")
	 @JsonBackReference
    private Personnel TPersonnel ;
	 
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
	public Avancement() {
	}
	 
	public int getId() { return this.id; }

	public String getCat() { return this.cat; }

	public String getEch() { return this.ech; }

	public String getIndDiff() { return this.indDiff; }
		
	public String getNom() { return this.nom; }
		
	public String getObservation() { return this.observation;  }

    public String getQualification() { return this.qualification; } 

	public String getSbase() { return this.sbase; }

	public String getTh() { return this.th;}

	public Date getDPAv() { return dPAv; } 

	public Date getdEffet() { return dEffet; }
	
	public Personnel getTPersonnel() { return TPersonnel;}

	public String getsCat() { return sCat;}


	public Avancement(Personnel tPersonnel, String nom, String qualification, String cat, String sCat, String ech,
		String sbase, String th, String indDiff, Date dEffet, Date dPAv, String observation) {
		super();
		this.TPersonnel = tPersonnel;
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

	public void setCat(String cat) { this.cat = cat; }

	public void setsCat(String sCat) { this.sCat = sCat; }

	public void setEch(String ech) { this.ech = ech; }

	public void setSbase(String sbase) {  this.sbase = sbase; 	}

	public void setTh(String th) { 	this.th = th; 	} 

	public void setIndDiff(String indDiff) { this.indDiff = indDiff; }

	public void setdEffet(Date dEffet) { this.dEffet = dEffet; }

	public void setdPAv(Date dPAv) { this.dPAv = dPAv;}

	public void setObservation(String observation) { this.observation = observation; }

	public Date getdPAv() { return dPAv; }

	public void setId(int id) {  this.id = id; }

	public void setTPersonnel(Personnel tPersonnel) { TPersonnel = tPersonnel; }

	public void setNom(String nom) { this.nom = nom; }

	public void setQualification(String qualification) { this.qualification = qualification; }

	
	
	
}