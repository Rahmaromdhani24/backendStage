package com.rahma.AvEchelon.Entity;

import jakarta.persistence.*;
import java.util.List;



/**
 * The persistent class for the t_departements database table.
 * 
 */
@Entity
@Table(name="t_departements")
public class Departement  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String nom_Dep ; 
	
	private String mle_Chef_Dep;
	
	private String ID_dep_Sup;

	private String type;
 
	public Departement() {}

	public Departement(int id, String nom_Dep) {
		super();
		this.id = id;
		this.nom_Dep = nom_Dep;
	}
	public Departement(int id, String nom_Dep, List<Poste> postes) {
		super();
		this.id = id;
		this.nom_Dep = nom_Dep;
	}
	
	public int getId() {	return this.id; }

	public void setId(int id) { this.id = id;}

	public String getID_dep_Sup() { 	return this.ID_dep_Sup;}

	public void setID_dep_Sup(String ID_dep_Sup) { 	this.ID_dep_Sup = ID_dep_Sup; 	}

	public String getMle_Chef_Dep() { 	return this.mle_Chef_Dep; 	}

	public void setMle_Chef_Dep(String mle_Chef_Dep) { this.mle_Chef_Dep = mle_Chef_Dep; }

	public String getType() { 	return this.type; }

	public void setType(String type) { 	this.type = type; 	}
	
	public String getNom_Dep() { 	return nom_Dep; }

	public void setNom_Dep(String nom_Dep) { 	this.nom_Dep = nom_Dep; 	}

}