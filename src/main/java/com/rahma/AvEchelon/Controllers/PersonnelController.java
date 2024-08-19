package com.rahma.AvEchelon.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.rahma.AvEchelon.Entity.Personnel;
import com.rahma.AvEchelon.Entity.Sanction;
import com.rahma.AvEchelon.Repository.PersonnelRepository;
import com.rahma.AvEchelon.Services.Personnel.MesPersonnels;
import com.rahma.AvEchelon.Services.Personnel.ServicePersonnel;
import java.text.ParseException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/personnel")
public class PersonnelController {

    @Autowired private ServicePersonnel service;
    @Autowired  PersonnelRepository repository;


    @GetMapping("/idClasseTotal/{mle}")
    public Personnel getPersonnel(@PathVariable String mle) throws ParseException {
    return repository.findById(mle).get(); }
   
   
    @GetMapping("/all")
    public List<MesPersonnels> getAllPersonnel() throws ParseException {
    return service.getMesPersonnels(); }
    
    @GetMapping("/qualification/{mle}")
    public String getQualificationPersonnel(@PathVariable String mle) throws ParseException {
    return service.recuperePersonnelParMle(mle).getQualification(); }
    
    @GetMapping("/nbrAll")
    public int getNombre() throws ParseException {
    return service.getMesPersonnels().size(); } 
    
    @GetMapping("/{mle}")
    public MesPersonnels getPersonnelByMle(@PathVariable String mle) throws ParseException {
    return service.recuperePersonnelParMle(mle) ;}
    
    @GetMapping("/mensuel/{mle}")
    public MesPersonnels getPersonnelMensuelByMle(@PathVariable String mle) throws ParseException {
    return service.recuperePersonnelMensuleParMle(mle) ;}
    
    
    @GetMapping("/horaire")
    public List<MesPersonnels> getHorairePersonnels() throws ParseException {
    return service.getHorairePersonnels(); }
    
    @GetMapping("/nbrHoraire")
    public int  getNbrHorairePersonnels() throws ParseException {
    return service.getHorairePersonnels().size(); }
    
    @GetMapping("/mensuel")
    public List<MesPersonnels> getMensuelPersonnels() throws ParseException {
    return service.getMensuelPersonnels(); }
    
    @GetMapping("/nbrMensuel")
    public int  getNbrMensuelPersonnels() throws ParseException {
    return service.getMensuelPersonnels().size(); } 
    
    @GetMapping("/57ansCeMois")
    public List<MesPersonnels> getExceptionnel57AnsCetteAnnee () throws ParseException {
    return service.getExceptionnel57AnsCetteAnnee(); }
    
    @GetMapping("/plus57ans")
    public List<MesPersonnels> getExceptionnel57Ans() throws ParseException {
    return service.getExceptionnel57Ans(); }
    
    @GetMapping("/nbrplus57ans")
    public int getNbrExceptionnel57Ans() throws ParseException {
    return service.getExceptionnel57Ans().size(); }
      
    
    @GetMapping("/sanctions/{mle}")
    public List<Sanction> getSanctionsPersonnel(@PathVariable String mle) {
    return service.recupererSanctionsPersonnel18Mois(mle) ;}
    
    @GetMapping("/nbrsanctions/{mle}")
    public int getNbrSanctionsPersonnel(@PathVariable String mle) {
    List<Sanction> sanctions = service.recupererSanctionsPersonnel18Mois(mle);
    return (sanctions != null) ? sanctions.size() : 0;   }
 
}
