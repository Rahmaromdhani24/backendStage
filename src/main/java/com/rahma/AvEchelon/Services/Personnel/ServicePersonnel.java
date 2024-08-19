package com.rahma.AvEchelon.Services.Personnel;

import java.util.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.rahma.AvEchelon.Entity.Avancement;
import com.rahma.AvEchelon.Entity.Sanction;
import com.rahma.AvEchelon.Repository.PersonnelRepository;
import com.rahma.AvEchelon.Repository.PostRepository;
import com.rahma.AvEchelon.Repository.SanctionRepository;
import com.rahma.AvEchelon.Services.Avancement.ServiceAvancement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.stream.Collectors;
@Service

public class ServicePersonnel implements IServicePersonnel {

	@Autowired PostRepository repoPoste ; 
	@Lazy @Autowired ServiceAvancement serviceAvancement ; 
	@Autowired PersonnelRepository repository ; 
	@Autowired SanctionRepository sanctionRepository  ; 
	

	@Override
	public List<MesPersonnels> getMesPersonnels() throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date currentDate = sdf.parse("2024-06-07");
    return repository.mesPersonnels(currentDate);}
	
	
	@Override
	public MesPersonnels recuperePersonnelParMle(String mle) throws ParseException {
    List<MesPersonnels> liste=  getMesPersonnels();
	MesPersonnels personnel = null ; 
    for(MesPersonnels p : liste) {
	if(p.getMle().equals(mle))
	 personnel= p ; }
     return personnel ;  }

	public MesPersonnels recuperePersonnelMensuleParMle(String mle) throws ParseException {
    List<MesPersonnels> liste=  getMensuelPersonnels();
	MesPersonnels personnel = null ; 
    for(MesPersonnels p : liste) {
    if(p.getMle().equals(mle))
	personnel= p ; }
    return personnel ; }
	
	@Override
	public List<Sanction> recupererSanctionsPersonnel18Mois(String mle) {
    List< Avancement> dernierAvancement = serviceAvancement.derniereAvancementPersonnelParMemeDateDEFFET(mle);//peut contenir un avancement ou plusieurs avancements 
    if (dernierAvancement.size()== 1) {
    for(Avancement avancement : dernierAvancement ) {
    Date dateProchainAvancement = avancement.getDPAv();
   // Calcul de la date de prochain avancement moins 18 mois
     Calendar cal = Calendar.getInstance();
	 cal.setTime(dateProchainAvancement);
     cal.add(Calendar.MONTH, -18);
     Date dateProchainAvancementMoins18Mois = cal.getTime();
     return sanctionRepository.findSanctionsSinceDateProchainAvancement(dateProchainAvancementMoins18Mois, mle); } }

     else if (dernierAvancement.size() > 1 && dernierAvancement.size() <=2  ) {
     Avancement avantdernierObjet = dernierAvancement.get(dernierAvancement.size() - 2); // avancement avant dernier 
     Avancement dernierObjet = dernierAvancement.get(dernierAvancement.size() - 1);      // dernier avancement    	  
	 Date datePAvAvantDeDerniereObjet = avantdernierObjet.getDPAv();
	 Date dateAvDeDerniereObjet = dernierObjet.getDPAv();
	 return  sanctionRepository.findSanctionsBetweenDates(mle , datePAvAvantDeDerniereObjet , dateAvDeDerniereObjet) ; }
     return null ; }
	

	@Override
	public Date calculerDatePAv(int decalageDesSanctions, Date dpav) {
	Calendar calendar = Calendar.getInstance();
    calendar.setTime(dpav);
	calendar.add(Calendar.MONTH, decalageDesSanctions);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    if (day > 13) {
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    calendar.add(Calendar.MONTH, 1);}
	return calendar.getTime();}
	
	@Override
	public List<MesPersonnels> getHorairePersonnels() throws ParseException {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date currentDate = sdf.parse("2024-06-07");
    List<MesPersonnels> allPersonnels = repository.mesPersonnelsAvecCategorieEtSousCategorie(currentDate);
    List<MesPersonnels> listeDesPersonnelsHoraire = new ArrayList<>();
    for (MesPersonnels personnel : allPersonnels) {
    String categorieStr = personnel.getCategorie();   
    // Vérifiez si la chaîne de catégorie n'est pas vide avant de la convertir en entier
    if (categorieStr != null && !categorieStr.isEmpty()) {
    try {
        int categorie = Integer.parseInt(categorieStr);
        if (categorie >= 1 && categorie <=5  && personnel.getSousCategorie()!= null) {
            listeDesPersonnelsHoraire.add(personnel);} }   
    catch (NumberFormatException e) {
        e.printStackTrace(); } }} // ou loggez un message d'erreur 
	    return listeDesPersonnelsHoraire; }

	@Override
	public List<MesPersonnels> getMensuelPersonnels() throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date currentDate = sdf.parse("2024-06-07");
    List<MesPersonnels> allPersonnels= repository.mesPersonnelsAvecCategorieEtSousCategorie(currentDate);
    List<MesPersonnels>  listeDesPersonnlesMensuel= new ArrayList<MesPersonnels>() ; 
    for (MesPersonnels personnel : allPersonnels) {   String categorieStr = personnel.getCategorie();  
	 // Vérifiez si la chaîne de catégorie n'est pas vide avant de la convertir en entier
    if (categorieStr != null && !categorieStr.isEmpty()) {
        try {
            int categorie = Integer.parseInt(categorieStr);
            if (categorie >= 1 && categorie <=12  && personnel.getSousCategorie()== null) {
            	listeDesPersonnlesMensuel.add(personnel);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();     }   }  }
   return listeDesPersonnlesMensuel ;	}

	@Override
	public List<MesPersonnels> getExceptionnel57Ans() throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date currentDate = sdf.parse("2024-06-07");
    List<MesPersonnels>allPersonnels= repository.mesPersonnels(currentDate);
    LocalDate currentLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    return allPersonnels.stream()
            .filter(personnel -> {
                LocalDate birthDate = new Date(personnel.getDate_N().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int age = Period.between(birthDate, currentLocalDate).getYears();
                return age >= 57;  }) 
            .collect(Collectors.toList());
}

	@Override
	public List<MesPersonnels> getExceptionnel57AnsCetteAnnee() throws ParseException {
 //   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date currentDate = new Date(); // Date du système
    List<MesPersonnels> allPersonnels = repository.mesPersonnels(currentDate);
    LocalDate currentLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    return allPersonnels.stream()
            .filter(personnel -> {
                LocalDate birthDate = new Date(personnel.getDate_N().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate nextBirthday = birthDate.plusYears(57);

                // Check if the person will be exactly 57 years old this month
                return nextBirthday.getMonth() == currentLocalDate.getMonth() &&
                       nextBirthday.getYear() == currentLocalDate.getYear();
            })
            .collect(Collectors.toList());
}
}