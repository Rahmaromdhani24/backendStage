package com.rahma.AvEchelon.Services.Salaire;

import java.util.Date;
import com.rahma.AvEchelon.Entity.Salaire;

public interface ISalaire {

	    public  Salaire getSalaire(String cat, String scat);
	    public  String getThValueForEchelon(Salaire salaire, int echelon); 
	    public  String getIndDiffValueForEchelon(Salaire salaire, int echelon) ; 
	    public  Date ajouter18Mois(Date dateEffet);
	    
}
