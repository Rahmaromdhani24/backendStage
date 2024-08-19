package com.rahma.AvEchelon.Entity;

import jakarta.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * The persistent class for the t_postes database table.
 * 
 */
@Entity
@Table(name="t_postes")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Poste  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	//bi-directional many-to-one association to Personnel
	@ManyToOne
	@JoinColumn(name="Mle", referencedColumnName="mle")
	@JsonBackReference
	private Personnel PostePersonnel;
		
	private String fonction;

	private String position;

	private String qualification;

	private String college;
	
	private String active;

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date_Poste;

	private String nom_Dep ; 	
	
	private String 	Id_Dep ; 	
	
	private String id_Service;

	private String nom_Service;

	private String ref;

	public Poste() {}
	
	public Poste(int id, Personnel postePersonnel, String fonction, String position, String qualification,
			String college, String active, Date date_Poste, String nom_Dep, String id_Dep, String id_Service,
			String nom_Service, String ref) {
		super();
		this.id = id;
		PostePersonnel = postePersonnel;
		this.fonction = fonction;
		this.position = position;
		this.qualification = qualification;
		this.college = college;
		this.active = active;
		this.date_Poste = date_Poste;
		this.nom_Dep = nom_Dep;
		Id_Dep = id_Dep;
		this.id_Service = id_Service;
		this.nom_Service = nom_Service;
		this.ref = ref;
	}


	public int getId() {		return this.id;	}

	public void setId(int id) {		this.id = id;	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {		this.active = active;	}

	public String getCollege() {		return this.college;	}

	public void setCollege(String college) {	this.college = college;	}

	public Date getDate_Poste() {		return this.date_Poste;}

	public void setDate_Poste(Date date_Poste) {	this.date_Poste = date_Poste;}

	public String getFonction() {	return this.fonction;}
	
	public void setFonction(String fonction) {this.fonction = fonction;	}

	public String getId_Service() {	return this.id_Service;	}

	public void setId_Service(String id_Service) {	this.id_Service = id_Service;}

	public String getNom_Service() {	return this.nom_Service;}

	public void setNom_Service(String nom_Service) {	this.nom_Service = nom_Service;}

	public String getPosition() {		return this.position;	}

	public void setPosition(String position) {		this.position = position;	}

	public String getQualification() {		return this.qualification;	}

	public void setQualification(String qualification) {		this.qualification = qualification;	}

	public String getRef() {		return this.ref;	}

	public void setRef(String ref) {		this.ref = ref;	}

	public Personnel getPostePersonnel() {		return PostePersonnel;	}

	public void setPostePersonnel(Personnel postePersonnel) {		PostePersonnel = postePersonnel;	}

	public String getNom_Dep() {	return nom_Dep;}

	public void setNom_Dep(String nom_Dep) {		this.nom_Dep = nom_Dep;}

	public String getId_Dep() {		return Id_Dep;	}

	public void setId_Dep(String id_Dep) {	Id_Dep = id_Dep;}

}