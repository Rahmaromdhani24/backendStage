package com.rahma.AvEchelon.Entity;
// Generated Jun 13, 2024, 2:26:21 AM by Hibernate Tools 5.6.7.Final

import java.util.HashSet;
import java.util.Set;

/**
 * TDepartements generated by hbm2java
 */
public class TDepartements implements java.io.Serializable {

	private Integer id;
	private String nomDep;
	private String mleChefDep;
	private String idDepSup;
	private String type;
	private Set TPosteses = new HashSet(0);

	public TDepartements() {
	}

	public TDepartements(String nomDep, String type) {
		this.nomDep = nomDep;
		this.type = type;
	}

	public TDepartements(String nomDep, String mleChefDep, String idDepSup, String type, Set TPosteses) {
		this.nomDep = nomDep;
		this.mleChefDep = mleChefDep;
		this.idDepSup = idDepSup;
		this.type = type;
		this.TPosteses = TPosteses;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomDep() {
		return this.nomDep;
	}

	public void setNomDep(String nomDep) {
		this.nomDep = nomDep;
	}

	public String getMleChefDep() {
		return this.mleChefDep;
	}

	public void setMleChefDep(String mleChefDep) {
		this.mleChefDep = mleChefDep;
	}

	public String getIdDepSup() {
		return this.idDepSup;
	}

	public void setIdDepSup(String idDepSup) {
		this.idDepSup = idDepSup;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set getTPosteses() {
		return this.TPosteses;
	}

	public void setTPosteses(Set TPosteses) {
		this.TPosteses = TPosteses;
	}

}
