package com.rahma.AvEchelon.Entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;



/**
 * The persistent class for the t_personnels database table.
 * 
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="t_personnels")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "mle")
public class Personnel{

	
	@Id
	private String  mle;
	
	private String nom;

	private String prenom;

	private String nom_P;

	private String nom_M;
	
	@Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
	private Date date_N;
	
	private String lieu_N;

	private String cin;

	private String cpostal;

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date_CIN;
	
	private String num_CNSS;
	
	private String adresse;
	
	private String ville;
	
	@Column(name="S_F")
	private String sF;
	
	private String sexe;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date_Entre_Etab;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date_Anc;

	@Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
	private Date date_Sortie_Etab;


	private String motif_Sortie;

	private String tel;


	//bi-directional many-to-one association to Avancement
	@OneToMany(mappedBy="TPersonnel", cascade = CascadeType.ALL ,  fetch=FetchType.LAZY)
	//@JsonManagedReference
	private List<Avancement> TAvancements;
	
	//bi-directional many-to-one association to Sanction
	 @OneToMany(mappedBy="sanctionPersonnel", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY)
	 private List<Sanction> sanctions;
	 
	//bi-directional many-to-one association to Poste
	@OneToMany(mappedBy="PostePersonnel")
	@JsonManagedReference
	private List<Poste> TPostes;
	
	//bi-directional one-to-one association to ContratParent
	@OneToMany(mappedBy ="personnelContratParent" , cascade = CascadeType.ALL)
	private List<ContratParent>  listeContratParent ;
	
	public Personnel() {}


	public String getAdresse() {	return this.adresse;	}

	public void setAdresse(String adresse) {		this.adresse = adresse;	}

	public String getCin() {		return this.cin;	}

	public void setCin(String cin) {		this.cin = cin;	}

	public String getCpostal() {		return this.cpostal;	}

	public void setCpostal(String cpostal) {	this.cpostal = cpostal;}

	public String getsF() {	return sF;}

	public void setsF(String sF) {		this.sF = sF;}

	public Date getDate_Anc() {	return this.date_Anc;}

	public void setDate_Anc(Date date_Anc) {	this.date_Anc = date_Anc;}

	public Date getDate_CIN() {		return this.date_CIN;	}

	public void setDate_CIN(Date date_CIN) {	this.date_CIN = date_CIN;}

	public Date getDate_Entre_Etab() {		return this.date_Entre_Etab;	}

	public void setDate_Entre_Etab(Date date_Entre_Etab) {	this.date_Entre_Etab = date_Entre_Etab;	}

	public Date getDate_N() {	return this.date_N;}

	public void setDate_N(Date date_N) {this.date_N = date_N;}

	public Date getDate_Sortie_Etab() {	return this.date_Sortie_Etab;}

	public void setDate_Sortie_Etab(Date date_Sortie_Etab) {	this.date_Sortie_Etab = date_Sortie_Etab;}

	public String getLieu_N() {	return this.lieu_N;}

	public void setLieu_N(String lieu_N) {	this.lieu_N = lieu_N;}

	public String getMotif_Sortie() {		return this.motif_Sortie;	}

	public void setMotif_Sortie(String motif_Sortie) {		this.motif_Sortie = motif_Sortie;	}

	public String getNom() {		return this.nom;	}

	public void setNom(String nom) {		this.nom = nom;	}

	public String getNom_M() {		return this.nom_M;}

	public void setNom_M(String nom_M) {		this.nom_M = nom_M;	}

	public String getNom_P() {		return this.nom_P;}

	public void setNom_P(String nom_P) {		this.nom_P = nom_P;	}

	public String getNum_CNSS() {	return this.num_CNSS;	}

	public void setNum_CNSS(String num_CNSS) {	this.num_CNSS = num_CNSS;	}

	public String getPrenom() {		return this.prenom;	}

	public void setPrenom(String prenom) {		this.prenom = prenom;	}

	public String getSF() {		return this.sF;	}

	public void setSF(String sF) {		this.sF = sF;	}

	public String getSexe() {		return this.sexe;	}

	public void setSexe(String sexe) {		this.sexe = sexe;	}

	public String getTel() {		return this.tel;	}

	public void setTel(String tel) {		this.tel = tel;	}

	public String getVille() {		return this.ville;	}
	
	public void setVille(String ville) {	this.ville = ville;}

	public String getMle() {		return mle;	}
	
	public void setMle(String mle) {		this.mle = mle;	}

	public List<Poste> getTPostes() {		return TPostes;	}

	public List<Avancement> getTAvancements() {	return TAvancements;}

	public void setTAvancements(List<Avancement> tAvancements) {		TAvancements = tAvancements;}

	public List<Sanction> getSanctions() {		return sanctions;	}

	public void setSanctions(List<Sanction> sanctions) {		this.sanctions = sanctions;	}

	public void setTPostes(List<Poste> tPostes) {		TPostes = tPostes;	}


}