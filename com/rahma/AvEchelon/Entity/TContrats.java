package com.rahma.AvEchelon.Entity;
// Generated Jun 13, 2024, 2:26:21 AM by Hibernate Tools 5.6.7.Final

import java.util.Date;

/**
 * TContrats generated by hbm2java
 */
public class TContrats implements java.io.Serializable {

	private Integer id;
	private String mle;
	private String typeContrat;
	private String ref;
	private Date dateDebut;
	private Date dateFin;
	private String dureeContrat;

	public TContrats() {
	}

	public TContrats(String mle, String typeContrat, String ref, Date dateDebut, Date dateFin, String dureeContrat) {
		this.mle = mle;
		this.typeContrat = typeContrat;
		this.ref = ref;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.dureeContrat = dureeContrat;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMle() {
		return this.mle;
	}

	public void setMle(String mle) {
		this.mle = mle;
	}

	public String getTypeContrat() {
		return this.typeContrat;
	}

	public void setTypeContrat(String typeContrat) {
		this.typeContrat = typeContrat;
	}

	public String getRef() {
		return this.ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public Date getDateDebut() {
		return this.dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public String getDureeContrat() {
		return this.dureeContrat;
	}

	public void setDureeContrat(String dureeContrat) {
		this.dureeContrat = dureeContrat;
	}

}
