package com.rahma.AvEchelon.Entity;

import jakarta.persistence.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



/**
 * The persistent class for the t_historique_av database table.
 * 
 */
@Entity
@Table(name="t_historique_av")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.ALWAYS)
public class HistoriqueAv  {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String mle;

	private String nom;

	private String qualification; 
	
	@Column(name="CAT_A")
	private String catA;

	@Column(name="CAT_N")
	private String catN;
	
	@Column(name="S_CAT_A")
	private String sCatA;

	@Column(name="S_CAT_N")
	private String sCatN;
	
	@Column(name="ECH_A")
	private String echA;

	@Column(name="ECH_N")
	private String echN;
	
	@Column(name="SBASE_A")
	private String sbaseA;

	@Column(name="SBASE_N")
	private String sbaseN;

	@Column(name="TH_A")
	private String thA;

	@Column(name="TH_N")
	private String thN;
	
	@Column(name="IND_DIFF_A")
	private String indDiffA;

	@Column(name="IND_DIFF_N")
	private String indDiffN;

	@Temporal(TemporalType.DATE)
	@Column(name="D_EFFET_A")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dEffetA;

	@Temporal(TemporalType.DATE)
	@Column(name="D_EFFET_N")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dEffetN;

	@Temporal(TemporalType.DATE)
	@Column(name="D_P_AV_A")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dPAvA;

	@Temporal(TemporalType.DATE)
	@Column(name="D_P_AV_N")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dPAvN;
	
	private String observation;
	
	private float note;

	private String san1;

	private String san2;

	public HistoriqueAv() {}
	
	public HistoriqueAv(String mle, String nom, String qualification, String catA, String catN, String sCatA,
			String sCatN, String echA, String echN, String sbaseA, String sbaseN, String thA, String thN,
			String indDiffA, String indDiffN, Date dEffetA, Date dEffetN, Date dPAvA, Date dPAvN,
			String observation, float note, String san1, String san2) {
		super();
		this.mle = mle;
		this.nom = nom;
		this.qualification = qualification;
		this.catA = catA;
		this.catN = catN;
		this.sCatA = sCatA;
		this.sCatN = sCatN;
		this.echA = echA;
		this.echN = echN;
		this.sbaseA = sbaseA;
		this.sbaseN = sbaseN;
		this.thA = thA;
		this.thN = thN;
		this.indDiffA = indDiffA;
		this.indDiffN = indDiffN;
		this.dEffetA = dEffetA;
		this.dEffetN = dEffetN;
		this.dPAvA = dPAvA;
		this.dPAvN = dPAvN;
		this.observation = observation;
		this.note = note;
		this.san1 = san1;
		this.san2 = san2;
	}


	public int getId() {	return this.id;	}

	public void setId(int id) { 	this.id = id;	}

	public String getCatA() { return this.catA;}

	public void setCatA(String catA) {	this.catA = catA; }

	public String getCatN() { 	return this.catN;}

	public void setCatN(String catN) {	this.catN = catN;}

	public Date getDEffetA() {	return this.dEffetA;}

	public void setDEffetA(Date dEffetA) {	this.dEffetA = dEffetA;}

	public Date getDEffetN() {return this.dEffetN;}

	public void setDEffetN(Date dEffetN) {this.dEffetN = dEffetN; }

	public Date getDPAvA() { return this.dPAvA; }

	public void setDPAvA(Date dPAvA) { 		this.dPAvA = dPAvA; }

	public Date getDPAvN() { return this.dPAvN; }
	
	public void setDPAvN(Date dPAvN) {	this.dPAvN = dPAvN; }
	
	public String getEchA() {	return this.echA;}

	public void setEchA(String echA) {	this.echA = echA;}

	public String getEchN() {	return this.echN; }

	public void setEchN(String echN) {	this.echN = echN; }

	public String getIndDiffA() { 	return this.indDiffA;}

	public void setIndDiffA(String indDiffA) {	this.indDiffA = indDiffA; }

	public String getIndDiffN() { return this.indDiffN; }

	public void setIndDiffN(String indDiffN) {	this.indDiffN = indDiffN;}

	public String getMle() {	return this.mle; }

	public void setMle(String mle) { this.mle = mle; 	}

	public String getNom() {	return this.nom; }

	public void setNom(String nom) { 	this.nom = nom; }

	public float getNote() { 	return this.note; }

	public void setNote(float note) { this.note = note; }

	public String getObservation() { 	return this.observation; }

	public void setObservation(String observation) { 	this.observation = observation; }

	public String getQualification() { 	return this.qualification; }

	public void setQualification(String qualification) { this.qualification = qualification; }

	public String getSCatA() { 	return this.sCatA; }

	public void setSCatA(String sCatA) { 	this.sCatA = sCatA; }

	public String getSCatN() { 	return this.sCatN; }

	public void setSCatN(String sCatN) {	this.sCatN = sCatN; }

	public String getSan1() { 	return this.san1; }

	public void setSan1(String san1) { 	this.san1 = san1;	}

	public String getSan2() { 	return this.san2; }

	public void setSan2(String san2) { 	this.san2 = san2; }

	public String getSbaseA() { 	return this.sbaseA; }

	public void setSbaseA(String sbaseA) {		this.sbaseA = sbaseA; }

	public String getSbaseN() { 	return this.sbaseN; }

	public void setSbaseN(String sbaseN) { 	this.sbaseN = sbaseN;}

	public String getThA() { 	return this.thA; }

	public void setThA(String thA) { 	this.thA = thA; }

	public String getThN() { 	return this.thN; }

	public void setThN(String thN) { this.thN = thN;}

	public String getsCatA() { 	return sCatA; }

	public String getsCatN() {	return sCatN;	}

	public Date getdEffetA() {	return dEffetA;	}

	public Date getdEffetN() {	return dEffetN;}

	public Date getdPAvA() {		return dPAvA;	}

	public Date getdPAvN() {	return dPAvN;	}

	public void setsCatA(String sCatA) {this.sCatA = sCatA;	}

    public void setsCatN(String sCatN) {	this.sCatN = sCatN;}

    public void setdEffetA(Date dEffetA) {	this.dEffetA = dEffetA;	}

	public void setdEffetN(Date dEffetN) {	this.dEffetN = dEffetN;}

	public void setdPAvA(Date dPAvA) {		this.dPAvA = dPAvA;	}

	public void setdPAvN(Date dPAvN) {		this.dPAvN = dPAvN;	}
}