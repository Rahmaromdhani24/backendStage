package com.rahma.AvEchelon.Services.Avancement;

import java.text.ParseException;
import java.text.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.rahma.AvEchelon.Entity.Avancement;
import com.rahma.AvEchelon.Entity.Personnel;
import com.rahma.AvEchelon.Entity.Salaire;
import com.rahma.AvEchelon.Repository.AvancementRepository;
import com.rahma.AvEchelon.Repository.PersonnelRepository;
import com.rahma.AvEchelon.Services.Historique.Historiques_Supprimes;
import com.rahma.AvEchelon.Services.Historique.ServiceHistorique;
import com.rahma.AvEchelon.Services.Personnel.MesPersonnels;
import com.rahma.AvEchelon.Services.Personnel.ServicePersonnel;
import com.rahma.AvEchelon.Services.Salaire.ServiceSalaire;
import com.rahma.AvEchelon.Services.Trigger.TriggerService;




@Service
public class ServiceAvancement implements IServiceAvancement {

	
	@Autowired AvancementRepository repository ; 
	@Autowired PersonnelRepository repositoryPersonnel ; 
	@Lazy @Autowired ServicePersonnel servicePersonnel ; 
    @Autowired ServiceSalaire serviceSalaire ;
    @Autowired ServiceHistorique serviceHistorique ;
    @Autowired  TriggerService serviceAvancementAudit ; 

	
	@Override
	public List<Avancement> getAllAvancementPersonnel(String mle) {
        List <Avancement> all = repository.findAll() ; 
		List<Avancement> listeFinal = 	new ArrayList<Avancement>() ; 

		for (Avancement  av : all) {
			if(av.getTPersonnel().getMle().equals(mle))
				listeFinal.add(av) ; 	}
	
          return listeFinal ; }
		               
	                    /**************************************************************/

	public List<Avancement> getAvancementCeMoisPourPersonnelsHorraire(String dateString) throws ParseException {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date convertedDate = dateFormat.parse(dateString);
	 // Step 2: Retrieve the list of "horaire" personnels
	    List<MesPersonnels> mesPersonnels = servicePersonnel.getHorairePersonnels();

	    // Step 3: Retrieve the list of advancements for the specified date
	    List<Avancement> avancements = repository.findBydPAv(convertedDate);

	    // Step 4: Use a Map to ensure unique advancements per personnel
	    Map<String, Avancement> uniqueAvancementsMap = new HashMap<>();
	    for (Avancement avancement : avancements) {
	        for (MesPersonnels personnel : mesPersonnels) {
	            if (avancement.getTPersonnel().getMle().equals(personnel.getMle())) {
	                uniqueAvancementsMap.put(personnel.getMle(), avancement);
	                break; // Break the inner loop if a match is found
	            }
	        }
	    }

	    // Step 5: Return the values of the map as a list
	    return new ArrayList<>(uniqueAvancementsMap.values());
        }

	         
                    	/**************************************************************/
	
	public List<Avancement> getAvancementCeMoisPourPersonnelsMensuel(String dateString) throws ParseException {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date convertedDate = dateFormat.parse(dateString);

	    // Step 2: Retrieve the list of "horaire" personnels
	    List<MesPersonnels> mesPersonnels = servicePersonnel.getMensuelPersonnels();

	    // Step 3: Retrieve the list of advancements for the specified date
	    List<Avancement> avancements = repository.findBydPAv(convertedDate);
	    
	    // Step 4: Use a Map to ensure unique advancements per personnel
	    Map<String, Avancement> uniqueAvancementsMap = new HashMap<>();
	    for (Avancement avancement : avancements) {
	        for (MesPersonnels personnel : mesPersonnels) {
	            if (avancement.getTPersonnel().getMle().equals(personnel.getMle())) {
	                uniqueAvancementsMap.put(personnel.getMle(), avancement);
	                break; // Break the inner loop if a match is found
	            }
	        }
	    }
	    // Step 5: Return the values of the map as a list
	    return new ArrayList<>(uniqueAvancementsMap.values());
        }

	         /************************************************************************************/
	

	@Override
	public int getNombreAvancementCeMoisPourPersonnelsHorraire(String dateString) throws ParseException {
		// TODO Auto-generated method stub
		List<Avancement> liste = getAvancementCeMoisPourPersonnelsHorraire(dateString) ; 
		return liste.size();}
	  
	                   /**************************************************************/
	@Override
	public int getNombreAvancementCeMoisPourPersonnelsMensuel (String dateString) throws ParseException {
		// TODO Auto-generated method stub
		List<Avancement> liste = getAvancementCeMoisPourPersonnelsMensuel(dateString) ; 
		return liste.size();}
	
	                     /*****************************************************************/
	@Override
	public Avancement derniereAvancementPersonnel(String mle) {
		Avancement avancementFinale = repository.findRecentAvancement(mle);
		
			if(avancementFinale.getTPersonnel().getMle().equals(mle)) {
				return avancementFinale;
			
		}
		return null;}
                    	/*************************************************************/
	@Override
	public List<Avancement> derniereAvancementPersonnelParMemeDateDEFFET(String mle) {
		List<Avancement> listRecentHistoriqueAvancement = repository.findRecentAvancementPourMemeDateEffet(mle);
		List<Avancement> avancementFinale = new ArrayList<Avancement>()  ; 
		
		for (Avancement avancement :listRecentHistoriqueAvancement ) {
			if(avancement.getTPersonnel().getMle().equals(mle)) {
				avancementFinale.add(avancement) ;
			}
		}
		return avancementFinale;}
	                   /**************************************************************/
	public List<MesPersonnels> getMesPersonnels() throws ParseException {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Date currentDate = sdf.parse("2024-06-07");
	    return repositoryPersonnel.mesPersonnels(currentDate);
	}
	
	/********************************************************/
	
	@Override
    public List<Avancement> lesDernieresAvancementPersonnels() throws ParseException {
        // Étape 1 : Récupérer les personnels spécifiques
        List<MesPersonnels> mesPersonnels = getMesPersonnels();
        
        // Étape 2 : Extraire les 'mle' des objets 'MesPersonnels'
        List<String> personnelMles = mesPersonnels.stream()
                                                  .map(MesPersonnels::getMle)
                                                  .collect(Collectors.toList());
        
        // Étape 3 : Récupérer les derniers avancements pour ces personnels
        List<Avancement> avancements = repository.findLatestAvancementsForEachPersonnel(personnelMles);
        
        // Étape 4 : Filtrer pour obtenir les avancements uniques pour chaque personnel
        Map<String, Avancement> dernierAvancementParPersonnel = avancements.stream()
                                                                           .collect(Collectors.toMap(
                                                                               avancement -> avancement.getTPersonnel().getMle(),
                                                                               avancement -> avancement,
                                                                               (existing, replacement) -> existing // En cas de doublon, gardez le premier
                                                                           ));
        
        return dernierAvancementParPersonnel.values().stream()
                                              .collect(Collectors.toList());
    }
	
                    	/**************************************************************/
	
	@Override
	public List<AvancementProjection> lesDernieresAvancementProjectionsPersonnels() throws ParseException {
	    // Étape 1 : Récupérer les personnels spécifiques
	    List<MesPersonnels> mesPersonnels = getMesPersonnels();
	    
	    // Étape 2 : Extraire les 'mle' des objets 'MesPersonnels'
	    List<String> personnelMles = mesPersonnels.stream()
	                                              .map(MesPersonnels::getMle)
	                                              .collect(Collectors.toList());
	    
	    // Étape 3 : Récupérer les derniers avancements pour ces personnels
	    List<AvancementProjection> avancements = repository.findLatestAvancementsProjectionForEachPersonnel(personnelMles);
	    
	    // Étape 4 : Filtrer pour obtenir les avancements uniques pour chaque personnel
	    Map<String, AvancementProjection> dernierAvancementParPersonnel = avancements.stream()
	                                                                                 .collect(Collectors.toMap(
	                                                                                     AvancementProjection::getPersonnelMle,
	                                                                                     avancement -> avancement,
	                                                                                     (existing, replacement) -> existing // En cas de doublon, gardez le premier
	                                                                                 ));
	    
	    return new ArrayList<>(dernierAvancementParPersonnel.values());
	}


	
	/******************************************************************/
	@Override
	public List<Avancement> getAnomalieAvancemets() throws ParseException {
		// TODO Auto-generated method stub
		
		List<Avancement> liste  = lesDernieresAvancementPersonnels();
		List<Avancement> listeFinale  = new ArrayList<Avancement>() ; 
		Date dateCurrent = new Date(); // Supposons que vous avez déjà la date actuelle
		  
		  for (Avancement avancement : liste) {
	            if (avancement.getDPAv() != null && avancement.getDPAv().before(dateCurrent)) {
	                listeFinale.add(avancement);
	            }
	        }

	        return listeFinale;
	}

	@Override
	public Avancement updateSituationPersonnel(Avancement avancementN , String mle ) throws ParseException {
       
		Avancement test = derniereAvancementPersonnel(mle); 
		Personnel personnel = repositoryPersonnel.findById(mle).get() ; 
		String qualificationPersonnel = servicePersonnel.recuperePersonnelParMle(mle).getQualification() ; 
		String categorie = avancementN.getCat() ; 
		String sousCategorie = avancementN.getsCat() ;
		int echelon = Integer.parseInt(avancementN.getEch()) ; 
		Salaire salaireSelonCatEtScat = serviceSalaire.getSalaire(categorie , sousCategorie) ; 
		String thValue = serviceSalaire.getThValueForEchelon(salaireSelonCatEtScat,echelon ) ; 
	    String indDiffValue = serviceSalaire.getIndDiffValueForEchelon(salaireSelonCatEtScat,echelon ) ; 
	    
		Avancement avancement = new Avancement() ; 
	    if(test.getSbase() == null) { //==> personnel horaire
	    	if(test.getIndDiff() == null) {
	    	avancement.setTPersonnel(personnel);
			avancement.setNom(personnel.getNom() +" " +personnel.getPrenom());
			avancement.setQualification(qualificationPersonnel);
			avancement.setCat(avancementN.getCat());
			avancement.setsCat(avancementN.getsCat());
			avancement.setEch(avancementN.getEch());
			avancement.setSbase(null);
			avancement.setTh(thValue);
			avancement.setIndDiff(null);
			avancement.setdEffet(avancementN.getdEffet());
			avancement.setdPAv(avancementN.getDPAv());
			avancement.setObservation(avancementN.getObservation());
	    	}  
	    	else if(test.getIndDiff() != null) {
    		avancement.setTPersonnel(personnel);
			avancement.setNom(personnel.getNom() +" " +personnel.getPrenom());
			avancement.setQualification(qualificationPersonnel);
			avancement.setCat(avancementN.getCat());
			avancement.setsCat(avancementN.getsCat());
			avancement.setEch(avancementN.getEch());
			avancement.setSbase(null);
			avancement.setTh(thValue);
			avancement.setIndDiff(indDiffValue);
			avancement.setdEffet(avancementN.getdEffet());
			avancement.setdPAv(avancementN.getDPAv());
			avancement.setObservation(avancementN.getObservation());
	    	}
	    }
	    else if(test.getSbase() != null) { //==> personnel mensuel
	    	if(test.getIndDiff() == null) {
		avancement.setTPersonnel(personnel);
		avancement.setNom(personnel.getNom() +" " +personnel.getPrenom());
		avancement.setQualification(qualificationPersonnel);
		avancement.setCat(avancementN.getCat());
		avancement.setsCat(null);
		avancement.setEch(avancementN.getEch());
		avancement.setSbase(thValue);
		avancement.setTh(null);
		avancement.setIndDiff(null);
		avancement.setdEffet(avancementN.getdEffet());
		avancement.setdPAv(avancementN.getDPAv());
		avancement.setObservation(avancementN.getObservation());
	    	}
	    	else if(test.getIndDiff() != null) {
		avancement.setTPersonnel(personnel);
		avancement.setNom(personnel.getNom() +" " +personnel.getPrenom());
		avancement.setQualification(qualificationPersonnel);
		avancement.setCat(avancementN.getCat());
		avancement.setsCat(null);
		avancement.setEch(avancementN.getEch());
		avancement.setSbase(thValue);
		avancement.setTh(null);
		avancement.setIndDiff(indDiffValue);
		avancement.setdEffet(avancementN.getdEffet());
		avancement.setdPAv(avancementN.getDPAv());
		avancement.setObservation(avancementN.getObservation());
	    	}
	    }
		repository.save(avancement) ; 
		
		return avancement ; 
		
	}

	@Override
	public List<Avancement> getAvancementPourPersonnelsHorraire() throws ParseException {
		 // Étape 1 : Récupérer les personnels spécifiques
        List<MesPersonnels> mesPersonnels = servicePersonnel.getHorairePersonnels();
        
        // Étape 2 : Extraire les 'mle' des objets 'MesPersonnels'
        List<String> personnelMles = mesPersonnels.stream()
                                                  .map(MesPersonnels::getMle)
                                                  .collect(Collectors.toList());
        
        // Étape 3 : Récupérer les derniers avancements pour ces personnels
        List<Avancement> avancements = repository.findLatestAvancementsForEachPersonnelHoraire(personnelMles);
        
        // Étape 4 : Filtrer pour obtenir les avancements uniques pour chaque personnel
        Map<String, Avancement> dernierAvancementParPersonnel = avancements.stream()
                                                                           .collect(Collectors.toMap(
                                                                               avancement -> avancement.getTPersonnel().getMle(),
                                                                               avancement -> avancement,
                                                                               (existing, replacement) -> existing // En cas de doublon, gardez le premier
                                                                           ));
        
        return dernierAvancementParPersonnel.values().stream()
                                              .collect(Collectors.toList());
    }

	@Override
	public List<Avancement> getAvancementPourPersonnelsMensuel() throws ParseException {
		 // Étape 1 : Récupérer les personnels spécifiques
        List<MesPersonnels> mesPersonnels = servicePersonnel.getMensuelPersonnels();
        
        // Étape 2 : Extraire les 'mle' des objets 'MesPersonnels'
        List<String> personnelMles = mesPersonnels.stream()
                                                  .map(MesPersonnels::getMle)
                                                  .collect(Collectors.toList());
        
        // Étape 3 : Récupérer les derniers avancements pour ces personnels
        List<Avancement> avancements = repository.findLatestAvancementsForEachPersonnelMensuel(personnelMles);
        
        // Étape 4 : Filtrer pour obtenir les avancements uniques pour chaque personnel
        Map<String, Avancement> dernierAvancementParPersonnel = avancements.stream()
                                                                           .collect(Collectors.toMap(
                                                                               avancement -> avancement.getTPersonnel().getMle(),
                                                                               avancement -> avancement,
                                                                               (existing, replacement) -> existing // En cas de doublon, gardez le premier
                                                                           ));
        
        return dernierAvancementParPersonnel.values().stream()
                                              .collect(Collectors.toList());  }
  
	@Override
	public AvancementProjection recupereLesAvancementsPersonnelMensuelByPeriode(String mle, String dateProchainAvancement)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    java.sql.Date datePv = new java.sql.Date(sdf.parse(dateProchainAvancement).getTime());
	    List<AvancementProjection> listRecentHistoriqueAvancement = repository.findAvancementByDatePVAndMle(datePv, mle);
	    AvancementProjection elementARecuperer = null ; 
	    List<AvancementProjection> resultats = new ArrayList<>();
	    if (listRecentHistoriqueAvancement.size() >= 2) {
	        AvancementProjection premier = listRecentHistoriqueAvancement.get(0);
	        AvancementProjection deuxieme = listRecentHistoriqueAvancement.get(1);
	        int categoriePremiere= Integer.parseInt(premier.getCat()) ; 
	        int categorieDeuxieme= Integer.parseInt(premier.getCat()) ; 
	        String sousCatPremiere = premier.getsCat() ; 
	        String sousCatDeuxieme=deuxieme.getsCat() ; 
	        int echPremiere = Integer.parseInt(premier.getEch()) ; 
	        int echDeuxieme = Integer.parseInt(deuxieme.getEch()) ; 

	  if((categoriePremiere >= 1 && categorieDeuxieme >= 1 ) && (categoriePremiere <=12 && categorieDeuxieme <=12 ) &&
			  (sousCatPremiere == null && sousCatDeuxieme == null)  && 
				 ((echPremiere != echDeuxieme) ||(echPremiere == echDeuxieme) )) {
		  resultats.add(premier); 
		  resultats.add(deuxieme); 

	  }
	    }
	    if (resultats.size() == 2) {
	    	AvancementProjection premier = resultats.get(0);
	    	AvancementProjection deuxieme = resultats.get(1);
            
            // Trouver l'élément avec l'ID le plus petit
	    	 elementARecuperer = (premier.getId() < deuxieme.getId()) ? premier : deuxieme;
	    }
	    

	    if (elementARecuperer != null) {
	        String indDiff = elementARecuperer.getIndDiff();
	        if (indDiff == null) {
	            indDiff = "";
	        }
	        // Create a wrapper to handle the null case for indDiff
	        AvancementProjection finalElement = elementARecuperer;
	        String finalIndDiff = indDiff;
	        elementARecuperer = new AvancementProjection() {
	            @Override
	            public int getId() { return finalElement.getId(); }
	            @Override
	            public String getPersonnelMle() { return finalElement.getPersonnelMle(); }
	            @Override
	            public String getNom() { return finalElement.getNom(); }
	            @Override
	            public String getCat() { return finalElement.getCat(); }
	            @Override
	            public String getsCat() { return finalElement.getsCat(); }
	            @Override
	            public String getEch() { return finalElement.getEch(); }
	            @Override
	            public String getSbase() { return finalElement.getSbase(); }
	            @Override
	            public String getTh() { return finalElement.getTh(); }
	            @Override
	            public String getIndDiff() { return finalIndDiff; }  // Return the non-null indDiff
	            @Override
	            public Date getdEffet() { return finalElement.getdEffet(); }
	            @Override
	            public Date getdPAv() { return finalElement.getdPAv(); }
	        };
	    }

	    return elementARecuperer;
	}

	@Override
	public AvancementProjection recupereLesAvancementsPersonnelHoraireByPeriode(String mle, String dateProchainAvancement)
	        throws ParseException {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    java.sql.Date datePv = new java.sql.Date(sdf.parse(dateProchainAvancement).getTime());
	    List<AvancementProjection> listRecentHistoriqueAvancement = repository.findAvancementByDatePVAndMle(datePv, mle);
	    List<AvancementProjection> resultats = new ArrayList<>();
	    AvancementProjection elementARecuperer = null; 

	    if (listRecentHistoriqueAvancement.size() >= 2) {
	        AvancementProjection premier = listRecentHistoriqueAvancement.get(0);
	        AvancementProjection deuxieme = listRecentHistoriqueAvancement.get(1);
	        int categoriePremiere = Integer.parseInt(premier.getCat()); 
	        int categorieDeuxieme = Integer.parseInt(deuxieme.getCat()); 
	        String sousCatPremiere = premier.getsCat(); 
	        String sousCatDeuxieme = deuxieme.getsCat(); 
	        int echPremiere = Integer.parseInt(premier.getEch()); 
	        int echDeuxieme = Integer.parseInt(deuxieme.getEch()); 

	        if ((categoriePremiere >= 1 && categorieDeuxieme >= 1) && (categoriePremiere <= 5 && categorieDeuxieme <= 5) &&
	            ((categoriePremiere == categorieDeuxieme) || (categoriePremiere != categorieDeuxieme)) &&
	            (sousCatPremiere != null && sousCatDeuxieme != null) && (!(sousCatPremiere.equals(sousCatDeuxieme))) &&
	            ((echPremiere != echDeuxieme) || (echPremiere == echDeuxieme))) {
	            resultats.add(premier); 
	            resultats.add(deuxieme); 
	        }
	    }

	    if (resultats.size() == 2) {
	        AvancementProjection premier = resultats.get(0);
	        AvancementProjection deuxieme = resultats.get(1);           
	        elementARecuperer = (premier.getId() < deuxieme.getId()) ? premier : deuxieme;
	    }

	    if (elementARecuperer != null) {
	        String indDiff = elementARecuperer.getIndDiff();
	        if (indDiff == null) {
	            indDiff = "";
	        }
	        // Create a wrapper to handle the null case for indDiff
	        AvancementProjection finalElement = elementARecuperer;
	        String finalIndDiff = indDiff;
	        elementARecuperer = new AvancementProjection() {
	            @Override
	            public int getId() { return finalElement.getId(); }
	            @Override
	            public String getPersonnelMle() { return finalElement.getPersonnelMle(); }
	            @Override
	            public String getNom() { return finalElement.getNom(); }
	            @Override
	            public String getCat() { return finalElement.getCat(); }
	            @Override
	            public String getsCat() { return finalElement.getsCat(); }
	            @Override
	            public String getEch() { return finalElement.getEch(); }
	            @Override
	            public String getSbase() { return finalElement.getSbase(); }
	            @Override
	            public String getTh() { return finalElement.getTh(); }
	            @Override
	            public String getIndDiff() { return finalIndDiff; }  // Return the non-null indDiff
	            @Override
	            public Date getdEffet() { return finalElement.getdEffet(); }
	            @Override
	            public Date getdPAv() { return finalElement.getdPAv(); }
	        };
	    }

	    return elementARecuperer;
	}


	@Override
	public void deleteAvancementsByIds(int id) {
        	repository.deleteById(id);
        
	}

	@Override
	public List<Historiques_Supprimes> supprimerAvancementsEtRemplacerParAvancemenetsPersonnelManuelGobale(
			String dateEffet) throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}
	
}

