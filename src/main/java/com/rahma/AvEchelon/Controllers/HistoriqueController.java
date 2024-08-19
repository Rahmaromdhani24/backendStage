package com.rahma.AvEchelon.Controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import com.rahma.AvEchelon.Entity.HistoriqueAv;
import com.rahma.AvEchelon.Repository.HistoriqueRepository;
import com.rahma.AvEchelon.Services.Historique.ServiceHistorique;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import com.rahma.AvEchelon.Services.Files.ExcelMensuelService;
import com.rahma.AvEchelon.Services.Files.ExcelExceptionnelHoraireService;
import com.rahma.AvEchelon.Services.Files.ExcelExceptionnelMensuelService;
import com.rahma.AvEchelon.Services.Files.ExcelHoraireService;
import org.springframework.core.io.ByteArrayResource;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/historiques")
public class HistoriqueController {
	
	 @Autowired private HistoriqueRepository repository;
	 @Autowired private ServiceHistorique service;
	 @Autowired private ExcelMensuelService excelMensuelService;	    	 
	 @Autowired private ExcelHoraireService excelHoraireService;
	 @Autowired private ServiceHistorique serviceHistorique;	    	 
	 @Autowired private ExcelExceptionnelMensuelService serviceMensuel57ans ;	
	 @Autowired private ExcelExceptionnelHoraireService serviceHoraire57ans ;	    	 



	 @GetMapping("/{id}")
	 public HistoriqueAv getHistoriqueById(@PathVariable int id)  {
     return repository.findById(id).get() ;  }
		 
	 
	 @PostMapping("/addHistorique")
	 public void addHistorique(
			    @RequestParam int idAvancement ,
	            @RequestParam float note,
	            @RequestParam(required = false) String san1,
	            @RequestParam(required = false) String san2,
	            @RequestParam(required = false) String observation) throws ParseException {
	        
	        service.sauvgardeAvancement(idAvancement, note, san1, san2, observation);}
	    
	    
	 @GetMapping("/downloadExcelMensuel")
	 public ResponseEntity<ByteArrayResource> downloadMensuelExcel(@RequestParam String dateEffet) throws IOException, ParseException {
	 return excelMensuelService.generateExcelMensuel(dateEffet);}

     @GetMapping("/downloadExcelHoraire")
     public ResponseEntity<ByteArrayResource> downloadHoraireExcel(@RequestParam String dateEffet) throws IOException, ParseException {
     return excelHoraireService.generateExcelHoraire(dateEffet);}

	 @GetMapping("/historiquesHoraire")
	 public List<HistoriqueAv> getHistoriquesHorairePersonnels(@RequestParam String dateEffet) throws ParseException {
	 return service.recupereLesHistoriquePersonnelsHoraire(dateEffet);}
	 

	@GetMapping("/historiquesMensuel")
	public List<HistoriqueAv> getHistoriquesMensuelPersonnels(@RequestParam String dateEffet) throws ParseException {
	return service.recupereLesHistoriquePersonnelsMensuel(dateEffet); }
	 
	 @GetMapping("/nbrr")
	 public int getHistoriquesMensuelPersonnelsnbr(@RequestParam String dateEffet) throws ParseException {
	 return service.recupereLesHistoriquePersonnelsMensuel(dateEffet).size(); }
		 
	 @GetMapping("/telechargements_Mensuel")
	 public Map<String, List<HistoriqueAv>> getHistoriquesAvMensuel() {
	 return service.getHistoriquesAvMensuel(); }
	   
	 @GetMapping("/telechargements_Horaire")
	 public Map<String, List<HistoriqueAv>> getHistoriquesAvHoraire() {
	 return service.getHistoriquesAvHoraire();}
	    
	 @GetMapping("/telechargements_Mensuel_57ans")
	 public Map<String, List<HistoriqueAv>> getHistoriquesAvMensuel57ans() throws ParseException {
	 return service.getHistoriquesAvMensuel57ans(); }
	   
	 @GetMapping("/telechargements_Horaire_57ans")
	 public Map<String, List<HistoriqueAv>> getHistoriquesAvHoraire57ans() throws ParseException {
	 return service.getHistoriquesAvHoraire57ans();}
	 
	 @GetMapping("/downloadMensule57ans")
     public ResponseEntity<ByteArrayResource> downloadMensuel57ans(@RequestParam String dateEffet) throws IOException, ParseException {
     return serviceMensuel57ans.generateExcelMensuel57ans(dateEffet);}
	
	 @GetMapping("/downloadHoraire57ans")
     public ResponseEntity<ByteArrayResource> downloadHoraire57ans(@RequestParam String dateEffet) throws IOException, ParseException {
     return serviceHoraire57ans.generateExcelHoraire57ans(dateEffet);}

	 @GetMapping("/supprimerAvMensuel")
	 public void  supprimerAvancementsMensuel(@RequestParam String deffet) throws ParseException {
	 serviceHistorique.supprimerAvancementsEnregistresMensuel(deffet); }

	 
	 @GetMapping("/supprimerAvHoraire")
	 public void  supprimerAvancementsHoraire(@RequestParam String deffet) throws ParseException {
	 serviceHistorique.supprimerAvancementsEnregistresHoraire(deffet);}
	    
		 
}
