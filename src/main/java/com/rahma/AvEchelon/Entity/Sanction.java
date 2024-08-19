package com.rahma.AvEchelon.Entity;

import jakarta.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;



/**
 * The persistent class for the t_sanctions database table.
 * 
 */
@Entity
@Table(name="t_sanctions")
public class Sanction  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	//bi-directional many-to-one association to Personnel
	@ManyToOne
    @JoinColumn(name="Mle", referencedColumnName="mle")
	private Personnel sanctionPersonnel;
	
	private String sanction;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date d_Debut;

	private int duree;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date d_Fin;

	@Lob
	private String motif;
	
	private String ref;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date_ref;

	public Sanction() {	}

	public int getId() {		return this.id;}

	public void setId(int id) {		this.id = id;	}

	public Date getD_Debut() {		return this.d_Debut;	}

	public void setD_Debut(Date d_Debut) {	this.d_Debut = d_Debut;}

	public Date getD_Fin() {		return this.d_Fin;	}

	public void setD_Fin(Date d_Fin) {		this.d_Fin = d_Fin;	}
	
	public Date getDate_ref() {		return this.date_ref;	}

	public void setDate_ref(Date date_ref) {		this.date_ref = date_ref;	}

	public int getDuree() {		return this.duree;	}

	public void setDuree(int duree) {		this.duree = duree;	}

	public String getMotif() {		return this.motif;	}
	
	public void setMotif(String motif) {		this.motif = motif;	}

	public String getRef() {		return this.ref;	}

	public void setRef(String ref) {		this.ref = ref;	}

	public String getSanction() {		return this.sanction;	}

	public void setSanction(String sanction) {		this.sanction = sanction;	}

	public Personnel getSanctionPersonnel() {		return sanctionPersonnel;	}

	public void setSanctionPersonnel(Personnel sanctionPersonnel) {		this.sanctionPersonnel = sanctionPersonnel;	}

}