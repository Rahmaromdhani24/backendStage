package com.rahma.AvEchelon.Services.Personnel;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import com.rahma.AvEchelon.Entity.Sanction;

public interface IServicePersonnel {
	
	public List<MesPersonnels> getMesPersonnels() throws ParseException ; 
	public List<MesPersonnels> getHorairePersonnels() throws ParseException ; 	
	public List<MesPersonnels> getMensuelPersonnels() throws ParseException ; 
	public List<MesPersonnels> getExceptionnel57AnsCetteAnnee() throws ParseException ; 
	public List<MesPersonnels> getExceptionnel57Ans() throws ParseException ; 
	public MesPersonnels recuperePersonnelParMle(String mle) throws ParseException ; 
	public List<Sanction> recupererSanctionsPersonnel18Mois(String mle) ; 
	public Date calculerDatePAv( int decalageDesSanctions , Date dpav) ; 
	
}
