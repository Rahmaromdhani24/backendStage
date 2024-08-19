package com.rahma.AvEchelon.Entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * The persistent class for the t_contrat_parent database table.
 * 
 */
@Entity
@Table(name="t_contrat_parent")
public class ContratParent  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	//bi-directional one-to-one association to Personnel
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Mle" , referencedColumnName = "mle")
	private Personnel personnelContratParent;

	private String nom;	
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date_Entree_Etab;
	
	private String type;
	
	private int active;
	
	//bi-directional many-to-one association to ContratDetail
	@OneToMany(mappedBy="detailsContratParent")
	private List<ContratDetail> TContratDetails;

	public ContratParent() {}


	public ContratParent(int id, Personnel personnelContratParent, String nom) {
		super();
		this.id = id;
		this.personnelContratParent = personnelContratParent;
		this.nom = nom;
	}

	public int getId() {	return this.id;}

	public void setId(int id) { this.id = id;}

	public int getActive() {	return this.active;	}

	public void setActive(int active) { 	this.active = active; }

	public Date getDate_Entree_Etab() { return this.date_Entree_Etab; }

	public void setDate_Entree_Etab(Date date_Entree_Etab) { this.date_Entree_Etab = date_Entree_Etab; }

	public String getNom() { return this.nom; }

	public void setNom(String nom) { this.nom = nom; }

	public String getType() { 	return this.type; 	}

	public void setType(String type) { 	this.type = type; }

	public List<ContratDetail> getTContratDetails() { 	return this.TContratDetails; }

	public void setTContratDetails(List<ContratDetail> TContratDetails) { this.TContratDetails = TContratDetails; }

	public Personnel getPersonnelContratParent() { return personnelContratParent; }

	public void setPersonnelContratParent(Personnel personnelContratParent) { this.personnelContratParent = personnelContratParent; }


}