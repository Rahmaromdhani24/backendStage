package com.rahma.AvEchelon.Services.Historique;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import com.rahma.AvEchelon.Entity.HistoriqueAv;
import com.rahma.AvEchelon.Entity.Sanction;
import com.rahma.AvEchelon.Entity.SanctionDetails;

public interface IHistorique {

   public List<SanctionDetails> calculateDelays(List<Sanction> sanctions, float note) ; 
   public void  sauvgardeAvancement ( int idavancement , float note , String san1 , String san2 , String observation)throws ParseException  ; 
   public List<HistoriqueAv> recupereLesHistoriquePersonnelsMensuel(String dateEffet) ; 
   public List<HistoriqueAv> recupereLesHistoriquePersonnelsHoraire(String dateEffet) ; 
   public Map<String, List<HistoriqueAv>> getHistoriquesAvMensuel() ;
   public Map<String, List<HistoriqueAv>> getHistoriquesAvHoraire() ;  
   public void supprimerAvancementsEnregistresMensuel(String dateEffet) throws ParseException ;
   public void supprimerAvancementsEnregistresHoraire(String dateEffet) throws ParseException ;

 

}
