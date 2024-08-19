package com.rahma.AvEchelon.Services.Avancement;

import java.text.ParseException;
import java.util.List;
import com.rahma.AvEchelon.Entity.Avancement;
import com.rahma.AvEchelon.Services.Historique.Historiques_Supprimes;

public interface IServiceAvancement {
	
	public List<Avancement> getAllAvancementPersonnel(String mle) ; 
	public List<Avancement> getAvancementCeMoisPourPersonnelsHorraire(String dateString) throws ParseException ;
	public List<Avancement> getAvancementPourPersonnelsHorraire() throws ParseException ;
	public List<Avancement> getAvancementPourPersonnelsMensuel() throws ParseException ;
	public List<Avancement> getAvancementCeMoisPourPersonnelsMensuel(String dateString) throws ParseException ; 
	public int getNombreAvancementCeMoisPourPersonnelsHorraire(String dateString) throws ParseException ; 
	public int getNombreAvancementCeMoisPourPersonnelsMensuel(String dateString) throws ParseException ; 
    public Avancement derniereAvancementPersonnel(String mle ); 	
    public List<Avancement> derniereAvancementPersonnelParMemeDateDEFFET (String mle ); 	
    public List<Avancement> lesDernieresAvancementPersonnels ()throws ParseException; 
    public List<AvancementProjection> lesDernieresAvancementProjectionsPersonnels ()throws ParseException; 
    public List<Avancement> getAnomalieAvancemets() throws ParseException; 
    public Avancement updateSituationPersonnel(Avancement avancementN , String mle)  throws ParseException ;   
    public AvancementProjection recupereLesAvancementsPersonnelMensuelByPeriode( String mle , String dateProchainAvancement) throws ParseException; 
    public AvancementProjection recupereLesAvancementsPersonnelHoraireByPeriode( String mle , String dateProchainAvancement) throws ParseException;   
    public void deleteAvancementsByIds(int id) ; 
    public 	List<Historiques_Supprimes> supprimerAvancementsEtRemplacerParAvancemenetsPersonnelManuelGobale(String dateEffet)throws ParseException  ; 


}
