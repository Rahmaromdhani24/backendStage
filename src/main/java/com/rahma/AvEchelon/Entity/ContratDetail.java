package com.rahma.AvEchelon.Entity;

import jakarta.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * The persistent class for the t_contrat_details database table.
 * 
 */
@Entity
@Table(name="t_contrat_details")
public class ContratDetail  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	//bi-directional many-to-one association to ContratParent
	@ManyToOne
	@JoinColumn(name="ID_Contrat_parent")
	private ContratParent detailsContratParent;

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date_D;

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date_F;
	
	private String jours;

	private String mois;
	
	private String annee;
	
	private String qualification;

	private String type;

	private String ref;

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date_Ref;
	
	private String CDD_File;

	@Column(name="ID_AV")
	private int idAv;


	public ContratDetail() {
	}

	public ContratDetail(int id, ContratParent detailsContratParent) {
		super();
		this.id = id;
		this.detailsContratParent = detailsContratParent;
	}

	public int getId() { return this.id; }

	public void setId(int id) { this.id = id; }
		
	public String getAnnee() { 	return this.annee; }

	public void setAnnee(String annee) { this.annee = annee; }
		
	public String getCDD_File() {  return this.CDD_File; }

	public void setCDD_File(String CDD_File) { 		this.CDD_File = CDD_File; }

	public Date getDate_D() { return this.date_D; }

	public void setDate_D(Date date_D) { this.date_D = date_D; }

	public Date getDate_F() {	return this.date_F;	}

	public void setDate_F(Date date_F) {	this.date_F = date_F;}

	public Date getDate_Ref() {		return this.date_Ref;	}

	public void setDate_Ref(Date date_Ref) {	this.date_Ref = date_Ref;	}

	public int getIdAv() {	return this.idAv;}

	public void setIdAv(int idAv) { 	this.idAv = idAv; }

	public String getJours() { 		return this.jours; }

	public void setJours(String jours) { 	this.jours = jours; }
	
	public String getMois() {	return this.mois; }

	public void setMois(String mois) {	this.mois = mois;}

	public String getQualification() {	return this.qualification;	}

	public void setQualification(String qualification) { this.qualification = qualification; }

	public String getRef() {	return this.ref;}

	public void setRef(String ref) { this.ref = ref;}

	public String getType() {	return this.type; }

	public void setType(String type) { this.type = type; }

	public ContratParent getDetailsContratParent() {return detailsContratParent;}

	public void setDetailsContratParent(ContratParent detailsContratParent) {this.detailsContratParent = detailsContratParent;}

}