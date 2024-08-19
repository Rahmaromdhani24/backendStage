package com.rahma.AvEchelon.Services.Salaire;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rahma.AvEchelon.Entity.Salaire;
import com.rahma.AvEchelon.Repository.SalaireRepository;

@Service
public class ServiceSalaire implements ISalaire {

        @Autowired SalaireRepository repository ; 
	   
	   	   
	    @Override
	    public Salaire getSalaire(String cat, String scat) {
        if (isCatInRange(cat)) {    return repository.findByCatAndSCat(cat, scat) ; }
        else if (isCatGreaterThanFive(cat)) {    return repository.findByCat(cat);   }
        return null;  }	   
  
	     @Override
	     public Date ajouter18Mois(Date dateEffet) {
	     if (dateEffet instanceof java.sql.Date) { dateEffet = new Date(dateEffet.getTime());  }
         LocalDate localDateEffet = dateEffet.toInstant()
	                                           .atZone(ZoneId.systemDefault())
	                                           .toLocalDate();
	     LocalDate localDateResult = localDateEffet.plusMonths(18);

	     if (localDateResult.getDayOfMonth() > 13) { localDateResult = localDateResult.withDayOfMonth(1).plusMonths(1);}	          
	     return Date.from(localDateResult.atStartOfDay(ZoneId.systemDefault()).toInstant()); }
	   

	// return true si categorie de 1 a 5  , false sinon
	public boolean isCatInRange(String cat) {
	    try {
	        int catInt = Integer.parseInt(cat);
	        return catInt >= 1 && catInt <= 5;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}    
  // return true si categorie strictement supereur a 5 , false sinon 
	public boolean isCatGreaterThanFive(String cat) {
	        try {
	            int catInt = Integer.parseInt(cat);
	            return catInt > 5;
	        } catch (NumberFormatException e) {
	            return false; 
	        }
	    }

	    @Override
	    public String getThValueForEchelon(Salaire salaire, int echelon) {
	        switch (echelon) {
	            case 1:
	                return salaire.getTh1();
	            case 2:
	                return salaire.getTh2();
	            case 3:
	                return salaire.getTh3();
	            case 4:
	                return salaire.getTh4();
	            case 5:
	                return salaire.getTh5();
	            case 6:
	                return salaire.getTh6();
	            case 7:
	                return salaire.getTh7();
	            case 8:
	                return salaire.getTh8();
	            case 9:
	                return salaire.getTh9();
	            case 10:
	                return salaire.getTh10();
	            case 11:
	                return salaire.getTh11();
	            case 12:
	                return salaire.getTh12();
	            case 13:
	                return salaire.getTh13();
	            case 14:
	                return salaire.getTh14();
	            case 15:
	                return salaire.getTh15();
	            case 16:
	                return salaire.getTh16();
	            case 17:
	                return salaire.getTh17();
	            case 18:
	                return salaire.getTh18();
	            case 19:
	                return salaire.getTh19();
	            case 20:
	                return salaire.getTh20();
	            case 21:
	                return salaire.getTh21();
	            case 22:
	                return salaire.getTh22();
	            case 23:
	                return salaire.getTh23();
	            case 24:
	                return salaire.getTh24();
	            case 25:
	                return salaire.getTh25();
	            case 26:
	                return salaire.getTh26();
	            case 27:
	                return salaire.getTh27();
	            case 28:
	                return salaire.getTh28();
	            case 29:
	                return salaire.getTh29();
	            case 30:
	                return salaire.getTh30();
	            case 31:
	                return salaire.getTh31();
	            case 32:
	                return salaire.getTh32();
	            case 33:
	                return salaire.getTh33();
	            case 34:
	                return salaire.getTh34();
	            case 35:
	                return salaire.getTh35();
	            case 36:
	                return salaire.getTh36();
	            case 37:
	                return salaire.getTh37();
	            case 38:
	                return salaire.getTh38();
	            case 39:
	                return salaire.getTh39();
	            case 40:
	                return salaire.getTh40();
	            default:
	                throw new IllegalArgumentException("Échelon non géré: " + echelon);
	        }
	    }

		@Override
		public String getIndDiffValueForEchelon(Salaire salaire, int echelon) {
			  switch (echelon) {
		        case 1:
		            return salaire.getInddiff1();
		        case 2:
		            return salaire.getInddiff2();
		        case 3:
		            return salaire.getInddiff3();
		        case 4:
		            return salaire.getInddiff4();
		        case 5:
		            return salaire.getInddiff5();
		        case 6:
		            return salaire.getInddiff6();
		        case 7:
		            return salaire.getInddiff7();
		        case 8:
		            return salaire.getInddiff8();
		        case 9:
		            return salaire.getInddiff9();
		        case 10:
		            return salaire.getInddiff10();
		        case 11:
		            return salaire.getInddiff11();
		        case 12:
		            return salaire.getInddiff12();
		        case 13:
		            return salaire.getInddiff13();
		        case 14:
		            return salaire.getInddiff14();
		        case 15:
		            return salaire.getInddiff15();
		        case 16:
		            return salaire.getInddiff16();
		        case 17:
		            return salaire.getInddiff17();
		        case 18:
		            return salaire.getInddiff18();
		        case 19:
		            return salaire.getInddiff19();
		        case 20:
		            return salaire.getInddiff20();
		        case 21:
		            return salaire.getInddiff21();
		        case 22:
		            return salaire.getInddiff22();
		        case 23:
		            return salaire.getInddiff23();
		        case 24:
		            return salaire.getInddiff24();
		        case 25:
		            return salaire.getInddiff25();
		        case 26:
		            return salaire.getInddiff26();
		        case 27:
		            return salaire.getInddiff27();
		        case 28:
		            return salaire.getInddiff28();
		        case 29:
		            return salaire.getInddiff29();
		        case 30:
		            return salaire.getInddiff30();
		        case 31:
		            return salaire.getInddiff31();
		        case 32:
		            return salaire.getInddiff32();
		        case 33:
		            return salaire.getInddiff33();
		        case 34:
		            return salaire.getInddiff34();
		        case 35:
		            return salaire.getInddiff35();
		        case 36:
		            return salaire.getInddiff36();
		        case 37:
		            return salaire.getInddiff37();
		        case 38:
		            return salaire.getInddiff38();
		        case 39:
		            return salaire.getInddiff39();
		        case 40:
		            return salaire.getInddiff40();
		        default:
		            throw new IllegalArgumentException("Échelon non géré: " + echelon);
		    }
	}

		
		
}

