package com.rahma.AvEchelon.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.rahma.AvEchelon.Entity.Avancement;
import com.rahma.AvEchelon.Entity.Salaire;
import com.rahma.AvEchelon.Repository.AvancementRepository;
import com.rahma.AvEchelon.Services.Avancement.AvancementProjection;
import com.rahma.AvEchelon.Services.Avancement.ServiceAvancement;
import com.rahma.AvEchelon.Services.Salaire.ServiceSalaire;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/avancement")
public class AvancementController {
	
	 @Autowired private AvancementRepository repository;
	 @Lazy @Autowired private ServiceAvancement service;
	 @Autowired ServiceSalaire  serviceSalaire ; 
		   	    	 
 
	 @GetMapping("/{id}")
	 public Avancement getAvancementById(@PathVariable int id)  {
     return repository.findById(id).get() ;  }
	 

	 @GetMapping("/avancementMensuel")
	 public List<Avancement> getAvancementsPersonnelsMensuel() throws ParseException  {
     return service.getAvancementPourPersonnelsMensuel() ;  }
	 
	 @GetMapping("/avancementHoraire")
	 public List<Avancement> getAvancementsPersonnelsHoraire() throws ParseException  {
     return service.getAvancementPourPersonnelsHorraire() ;  }
	 
	 
	@GetMapping("/avancementsCeMoisHoraire")
	public List<Avancement> getAvancementsCeMoisHoraire(@RequestParam String dateString) throws ParseException {
	List<Avancement> liste = service.getAvancementCeMoisPourPersonnelsHorraire(dateString);
	List<Avancement> finale = new ArrayList<Avancement>();
	for(Avancement av : liste)
	    		finale.add(av) ; 
    return finale ;  }
    
	    
    @GetMapping("/nbrAvancementsCeMoisHoraire")
    public int getNbrAvancementsCeMoisHoraire(@RequestParam String dateString) throws ParseException {
	List<Avancement> liste = service.getAvancementCeMoisPourPersonnelsHorraire(dateString);
	List<Avancement> finale = new ArrayList<Avancement>();
    for(Avancement av : liste)
	    		finale.add(av) ; 
	return finale.size() ;  }
	
	    
    @GetMapping("/avancementsCeMoisMensuel")
	public List<Avancement> getAvancementsCeMoisMensuel(@RequestParam String dateString) throws ParseException {
	List<Avancement> liste = service.getAvancementCeMoisPourPersonnelsMensuel(dateString);
	List<Avancement> finale = new ArrayList<Avancement>();
	for(Avancement av : liste)
	    		finale.add(av) ; 
	return finale ;   }
	
	    
	@GetMapping("/nbrAvancementsCeMoisMensuel")
	public int getNbrAvancementsCeMoisMensuel(@RequestParam String dateString) throws ParseException {
	List<Avancement> liste = service.getAvancementCeMoisPourPersonnelsMensuel(dateString);
	List<Avancement> finale = new ArrayList<Avancement>();
	for(Avancement av : liste)
	    		finale.add(av) ; 
	return finale.size() ;  }
	
	  
	@GetMapping("/AvCeMoisGlobale")
    public List<Avancement> getAvGlobale(@RequestParam String dateString) throws ParseException {
    List<Avancement> listeConcaténée = new ArrayList<>(getAvancementsCeMoisMensuel(dateString));
 	listeConcaténée.addAll(getAvancementsCeMoisHoraire(dateString));
	return  listeConcaténée  ;}
	
	    
	    
    @GetMapping("/nbrAvCeMoisGlobale")
	public int getNbrAv(@RequestParam String dateString) throws ParseException {
    return (service.getNombreAvancementCeMoisPourPersonnelsHorraire(dateString))+service.getNombreAvancementCeMoisPourPersonnelsMensuel(dateString) ;  }
	        		    
	    
    @GetMapping("/personnel/{mle}")
	public List<Avancement> getAvancementsByPersonnel(@PathVariable String mle) {
	return repository.findAvancementsByPersonnelMle(mle);}
	   
	    // fonctionne sur date d'effet  avancement (Max date d'effet) 
    @GetMapping("/lastAvancement2/{mle}")
    public Avancement getDerniererAvancementsByPersonnel2(@PathVariable String mle) {
	return service.derniereAvancementPersonnel(mle);}
	    
	    
	    // fonctionne sur date d'effet  avancement (Max date d'effet) 
	@GetMapping("/listeAvMemeDeffet/{mle}")
    public List<Avancement> getListesDerniererAvancementsByPersonnel(@PathVariable String mle) {
	return service.derniereAvancementPersonnelParMemeDateDEFFET(mle);}
	       
	@GetMapping("/nbrAvMemeDateEffet/{mle}")
	public  int getNbrElementsListesDerniererAvancementsByPersonnel(@PathVariable String mle) {
	return service.derniereAvancementPersonnelParMemeDateDEFFET(mle).size();}
	       
	    
    @GetMapping("/avancementsRecentsPourChaquePersonnel")
    public List<AvancementProjection> getLatestAvancementsForEachPersonnel() throws ParseException {
    return service.lesDernieresAvancementProjectionsPersonnels(); }
	    
    @GetMapping("/nbrAvRecent")
    public int getNbrLatestAvancementsForEachPersonnel() throws ParseException {
    return service.lesDernieresAvancementPersonnels().size(); }
   
	    
    @GetMapping("/anomalie")
    public List<Avancement> getRecentAvancementsWithProchainAvancementBeforeCurrentDate() throws ParseException {
    return service.getAnomalieAvancemets();}
    
	
    @GetMapping("/nbrAnomalie")
    public int getNombreRecentAvancementsWithProchainAvancementBeforeCurrentDate() throws ParseException {
    return service.getAnomalieAvancemets().size();}
    
	
	@PostMapping(value="/modifierSituation", consumes={"application/json"})
	public Avancement addAvancement(@RequestBody Avancement avancementN , @RequestParam("mle") String mle) throws ParseException {
    Avancement avancement = service.updateSituationPersonnel(avancementN, mle);	    	  
	return avancement;}
	    	    
    @GetMapping("/getTh")
    public String getTh(@RequestParam int echelon ,@RequestParam  String cat ,@RequestParam String scat) throws ParseException {
    Salaire salaire = serviceSalaire.getSalaire(cat, scat) ; 
	String thValue  = serviceSalaire.getThValueForEchelon(salaire,echelon ) ; 
    return thValue ;}
	    
    @GetMapping("/getIndDiff")
    public String getIndDiff(@RequestParam int echelon , @RequestParam  String cat , @RequestParam String scat) throws ParseException {
    Salaire salaire = serviceSalaire.getSalaire(cat, scat) ; 
	String inddiff  = serviceSalaire.getIndDiffValueForEchelon(salaire,echelon ) ; 
    return inddiff ;}
    
	    
    @GetMapping("/changementMensuel")
    public AvancementProjection getChangementsAvancementsMensuel(@RequestParam String  mle , @RequestParam  String dpav) throws ParseException {
    return service.recupereLesAvancementsPersonnelMensuelByPeriode(mle , dpav);}
	    
    @GetMapping("/changementHoraire")
    public AvancementProjection getChangementsAvancementsHoraire(@RequestParam String  mle , @RequestParam  String dpav) throws ParseException {
    return service.recupereLesAvancementsPersonnelHoraireByPeriode(mle , dpav);}
     
}
