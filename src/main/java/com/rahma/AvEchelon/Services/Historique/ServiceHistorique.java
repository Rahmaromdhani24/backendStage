package com.rahma.AvEchelon.Services.Historique;

import java.text.ParseException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.rahma.AvEchelon.Entity.*;
import com.rahma.AvEchelon.Repository.*;
import com.rahma.AvEchelon.Services.Personnel.ServicePersonnel;
import com.rahma.AvEchelon.Services.Salaire.ServiceSalaire;
import com.rahma.AvEchelon.Services.Trigger.TriggerService;
import com.rahma.AvEchelon.Services.Avancement.ServiceAvancement;
import com.rahma.AvEchelon.Services.Personnel.MesPersonnels;

@Service
public class ServiceHistorique implements IHistorique {

	@Autowired HistoriqueRepository repository ; 
	@Autowired SalaireRepository repositorySalaire ; 
	@Autowired ServiceSalaire serviceSalaire ; 
	@Autowired ServicePersonnel servicePersonnel ; 
	@Autowired AvancementRepository repositoryAvancement ; 
	@Autowired PersonnelRepository repositoryPersonnel ; 
    @Autowired private HistoriqueRepository historiqueRepository;
    @Lazy@Autowired TriggerService serviceAvancementAudit ; 
    @Lazy@Autowired ServiceAvancement serviceAvancement ; 


	@Override
	public void sauvgardeAvancement( int idavancement , float note ,String san1 , String san2 , String observation) throws ParseException {
		      Avancement avancement = repositoryAvancement.findById(idavancement).get() ;   
			  Personnel personnel = repositoryPersonnel.findById(avancement.getTPersonnel().getMle()).get();  
			  MesPersonnels p = servicePersonnel.recuperePersonnelParMle(avancement.getTPersonnel().getMle()); 
			  String mle = personnel.getMle() ; 
			  String categorie = avancement.getCat();
			  String sousCategorie = avancement.getsCat(); 
			  String echelon = avancement.getEch();
			  Salaire salaire = serviceSalaire.getSalaire(categorie, sousCategorie) ; 
			  String th_ancienne = serviceSalaire.getThValueForEchelon(salaire, Integer.parseInt(echelon)) ; 
			  String th_nouveau = serviceSalaire.getThValueForEchelon(salaire, Integer.parseInt(echelon)+1) ; 
			  String ind_ancienne = serviceSalaire.getIndDiffValueForEchelon(salaire, Integer.parseInt(echelon)) ; 
			  String ind_nouveau = serviceSalaire.getIndDiffValueForEchelon(salaire, Integer.parseInt(echelon)+1) ;
              List<Sanction>  listeSanctions  = servicePersonnel.recupererSanctionsPersonnel18Mois(avancement.getTPersonnel().getMle()) ; 
              List<SanctionDetails> detailsSanctions = calculateDelays(listeSanctions ,note ) ; 
              if(note < 17) {
				  SanctionDetails detail1 = new SanctionDetails();            	  
            	  detail1.setDelai(3) ;
            	  detail1.setObservation("note < 17") ; 
            	  detailsSanctions.add(detail1) ; 
              }
              int declageDatePAV = calculateTotalDelay(detailsSanctions) ;
              Date nouvelDatePAv = (Date) servicePersonnel.calculerDatePAv(declageDatePAV, avancement.getDPAv()) ; 
              String obser = concatenateObservations(detailsSanctions) ;

              String qualificationPersonnel = null;
              if (p != null) {
                  if (p.getQualification() != null) {
                      qualificationPersonnel = p.getQualification();
                  } else  if (p.getQualification() == null){
                      qualificationPersonnel = null;
                  }
              }
      // personnel  Horaire (possede categorie et sous categorie) & ne possede pas des  sanctions ==> declageDatePAV = 0  && note ==0
              
			  if(serviceSalaire.isCatInRange(categorie) == true && declageDatePAV == 0 && note == 0) { // if                                                                        

				  HistoriqueAv historique = new HistoriqueAv() ; 
historique.setMle(mle) ;  historique.setNom(avancement.getNom()) ; historique.setQualification(qualificationPersonnel);
historique.setCatA(categorie); historique.setCatN(categorie);
historique.setSCatA(sousCategorie) ; historique.setSCatN(sousCategorie) ; 
historique.setEchA(echelon) ; historique.setEchN(Integer.parseInt(echelon) + 1+"") ;
historique.setSbaseA(null); historique.setSbaseN(null) ; 
historique.setThA(th_ancienne) ;  historique.setThN(th_nouveau) ; 
if(avancement.getIndDiff() == null) { historique.setIndDiffA(null); historique.setIndDiffN(null);  }
else { historique.setIndDiffA(ind_ancienne); historique.setIndDiffN(ind_nouveau); }
historique.setDEffetA(avancement.getdEffet()); 			 historique.setDEffetN(avancement.getDPAv());
historique.setDPAvA(avancement.getDPAv());               historique.setDPAvN(serviceSalaire.ajouter18Mois((Date) avancement.getDPAv()));
historique.setObservation(observation) ;                 historique.setNote(note) ; 
historique.setSan1(san1) ;                               historique.setSan2(san2) ; 

 repository.save(historique);
                           			  
			             
 Avancement av = new Avancement();
 av.setTPersonnel(personnel) ; 
 av.setNom(personnel.getNom()+" "+personnel.getPrenom());
 av.setQualification(qualificationPersonnel);  
 av.setCat(categorie) ; 
 av.setsCat(sousCategorie);
 av.setEch(echelon) ;
 av.setSbase(null) ; 
 av.setTh(th_ancienne); 
 if(avancement.getIndDiff() == null) { av.setIndDiff(null);  }
 else { av.setIndDiff(ind_ancienne);}
 av.setdEffet(avancement.getdEffet()) ; 
 av.setdPAv(serviceSalaire.ajouter18Mois((Date) avancement.getDPAv())) ; 
 av.setObservation(observation) ;

						  			             repositoryAvancement.save(av);           
			             
			             
			             
			  }	
  // personnel  Horaire (possede categorie et sous categorie) & ne possede pas des  sanctions ==> declageDatePAV = 0  & note != 0
              
			  if(serviceSalaire.isCatInRange(categorie) == true && declageDatePAV == 0 && note != 0) { // if                                                                        

				  HistoriqueAv historique = new HistoriqueAv() ; 
historique.setMle(mle) ;  historique.setNom(avancement.getNom()) ; historique.setQualification(qualificationPersonnel);
historique.setCatA(categorie); historique.setCatN(categorie);
historique.setSCatA(sousCategorie) ; historique.setSCatN(sousCategorie) ; 
historique.setEchA(echelon) ; historique.setEchN(Integer.parseInt(echelon) + 1+"") ;
historique.setSbaseA(null); historique.setSbaseN(null) ; 
historique.setThA(th_ancienne) ;  historique.setThN(th_nouveau) ; 
if(avancement.getIndDiff() == null) { historique.setIndDiffA(null); historique.setIndDiffN(null);  }
else { historique.setIndDiffA(ind_ancienne); historique.setIndDiffN(ind_nouveau); }
historique.setDEffetA(avancement.getdEffet()); 			 historique.setDEffetN(avancement.getDPAv());
historique.setDPAvA(avancement.getDPAv());               historique.setDPAvN(serviceSalaire.ajouter18Mois((Date) avancement.getDPAv()));
historique.setObservation(observation) ;                 historique.setNote(note) ; 
historique.setSan1(san1) ;                               historique.setSan2(san2) ; 

 repository.save(historique);
                           			  
			             
 Avancement av = new Avancement();
 av.setTPersonnel(personnel) ; 
 av.setNom(personnel.getNom()+" "+personnel.getPrenom());
 av.setQualification(qualificationPersonnel);  
 av.setCat(categorie) ; 
 av.setsCat(sousCategorie);
 av.setEch(Integer.parseInt(echelon) + 1+"") ;
 av.setSbase(null) ; 
 av.setTh(th_nouveau); 
 if(avancement.getIndDiff() == null) { av.setIndDiff(null);  }
 else { av.setIndDiff(ind_nouveau);}
 av.setdEffet(avancement.getDPAv()) ; 
 av.setdPAv(serviceSalaire.ajouter18Mois((Date) avancement.getDPAv())) ; 
 av.setObservation(observation) ;

						  			             repositoryAvancement.save(av);                    
			  }	 
   // personnel  Horaire (possede categorie et sous categorie) &  possede  des  sanctions ==> declageDatePAV <> 0  && note ==0
			  
			  else  if(serviceSalaire.isCatInRange(categorie) == true && declageDatePAV != 0 && note ==0) {
				
				  HistoriqueAv historique = new HistoriqueAv() ; 
historique.setMle(mle) ;  historique.setNom(avancement.getNom()) ;
historique.setQualification(qualificationPersonnel);
historique.setCatA(categorie); historique.setCatN(categorie);
historique.setSCatA(sousCategorie) ; historique.setSCatN(sousCategorie) ; 
historique.setEchA(echelon) ; historique.setEchN(Integer.parseInt(echelon) + 1+"") ; historique.setSbaseA(null); historique.setSbaseN(null) ; 
historique.setThA(th_ancienne) ;  historique.setThN(th_nouveau) ; 
if(avancement.getIndDiff() == null) { historique.setIndDiffA(null); historique.setIndDiffN(null);  }
else { historique.setIndDiffA(ind_ancienne); historique.setIndDiffN(ind_nouveau); }
historique.setDEffetA(avancement.getdEffet()); 			 historique.setDEffetN(avancement.getDPAv());
historique.setDPAvA(avancement.getDPAv());               historique.setDPAvN(nouvelDatePAv);
 historique.setObservation(obser ) ;                
historique.setNote(note) ; 
historique.setSan1(san1) ;                               historique.setSan2(san2) ; 

 repository.save(historique);
 
 Avancement av = new Avancement();
 av.setTPersonnel(personnel) ; 
 av.setNom(personnel.getNom()+" "+personnel.getPrenom());
 av.setQualification(qualificationPersonnel);  
 av.setCat(categorie) ; 
 av.setsCat(sousCategorie);
 av.setEch(echelon) ;
 av.setSbase(null) ; 
 av.setTh(th_ancienne); 
 if(avancement.getIndDiff() == null) { av.setIndDiff(null);  }
 else { av.setIndDiff(ind_ancienne);}
 av.setdEffet(avancement.getdEffet()) ; 
 av.setdPAv(serviceSalaire.ajouter18Mois((Date) avancement.getDPAv())) ; 
 if(observation == null) { av.setObservation(null);  }
 else { av.setObservation(observation) ;}
 


						  			             repositoryAvancement.save(av);
						  			  }	

			  
   // personnel  Horaire (possede categorie et sous categorie) &  possede  des  sanctions ==> declageDatePAV <> 0 && note !=0
			  
			  else  if(serviceSalaire.isCatInRange(categorie) == true && declageDatePAV != 0 && note != 0) {
				
				  HistoriqueAv historique = new HistoriqueAv() ; 
historique.setMle(mle) ;  historique.setNom(avancement.getNom()) ;
historique.setQualification(qualificationPersonnel);
historique.setCatA(categorie); historique.setCatN(categorie);
historique.setSCatA(sousCategorie) ; historique.setSCatN(sousCategorie) ; 
historique.setEchA(echelon) ; historique.setEchN(Integer.parseInt(echelon) + 1+"") ; historique.setSbaseA(null); historique.setSbaseN(null) ; 
historique.setThA(th_ancienne) ;  historique.setThN(th_nouveau) ; 
if(avancement.getIndDiff() == null) { historique.setIndDiffA(null); historique.setIndDiffN(null);  }
else { historique.setIndDiffA(ind_ancienne); historique.setIndDiffN(ind_nouveau); }
historique.setDEffetA(avancement.getdEffet()); 			 historique.setDEffetN(avancement.getDPAv());
historique.setDPAvA(avancement.getDPAv());               historique.setDPAvN(nouvelDatePAv);
 historique.setObservation(obser ) ;                
historique.setNote(note) ; 
historique.setSan1(san1) ;                               historique.setSan2(san2) ; 

 repository.save(historique);
 
 Avancement av = new Avancement();
 av.setTPersonnel(personnel) ; 
 av.setNom(personnel.getNom()+" "+personnel.getPrenom());
 av.setQualification(qualificationPersonnel);  
 av.setCat(categorie) ; 
 av.setsCat(sousCategorie);
 av.setEch(echelon) ;
 av.setSbase(null) ; 
 av.setTh(th_ancienne); 
 if(avancement.getIndDiff() == null) { av.setIndDiff(null);  }
 else { av.setIndDiff(ind_ancienne);}
 av.setdEffet(avancement.getdEffet()) ; 
 av.setdPAv(nouvelDatePAv) ; 
 if(observation == null) { av.setObservation(null);  }
 else { av.setObservation(observation) ;}
 


						  			             repositoryAvancement.save(av);
						  			  }	
			  // personnel  Mensuel  (possede categorie seulement )	 & ne possede pas des  sanctions ==> declageDatePAV = 0  & note == 0

			  else if( (Integer.parseInt(categorie)>5) && declageDatePAV == 0 && note == 0) {
			 HistoriqueAv historique = new HistoriqueAv();
			 historique.setMle(mle); 
			 historique.setNom(avancement.getNom());
			 historique.setQualification(qualificationPersonnel) ; 
			 historique.setCatA(categorie) ;      historique.setCatN(categorie) ; 
			 historique.setSCatA(null) ;          historique.setSCatN(null) ;				 
			 historique.setEchA(echelon) ;        historique.setEchN(Integer.parseInt(echelon) + 1+"") ;
		     historique.setSbaseA(th_ancienne) ;  historique.setSbaseN(th_nouveau) ;
			 historique.setThA(null) ;            historique.setThN(null) ; 
			 if(avancement.getIndDiff() == null) { historique.setIndDiffA(null); historique.setIndDiffN(null);  }
			 else { historique.setIndDiffA(ind_ancienne); historique.setIndDiffN(ind_nouveau); }
			 historique.setDEffetA(avancement.getdEffet()); 			 historique.setDEffetN(avancement.getDPAv());
			 historique.setDPAvA(avancement.getDPAv());
			 historique.setDPAvN(serviceSalaire.ajouter18Mois( (Date) avancement.getDPAv()));
			 historique.setObservation(observation) ;
			 historique.setNote(note) ; 
			 historique.setSan1(san1) ; 
			 historique.setSan2(san2) ; 

			      repository.save(historique);
			      
  Avancement av = new Avancement();
  av.setTPersonnel(personnel) ; 
  av.setNom(personnel.getNom()+" "+personnel.getPrenom());
  av.setQualification(qualificationPersonnel);  
  av.setCat(categorie) ; 
  av.setsCat(null);
  av.setEch(echelon) ;
  av.setSbase(th_ancienne) ; 
  av.setTh(null); 
  if(avancement.getIndDiff() == null) { av.setIndDiff(null);  }
  else { av.setIndDiff(ind_ancienne);}
  av.setdEffet(avancement.getdEffet()) ; 
  av.setdPAv(serviceSalaire.ajouter18Mois( (Date) avancement.getDPAv())) ; 
  av.setObservation(observation) ;

    repositoryAvancement.save(av);}	
			  
			  // personnel  Mensuel  (possede categorie seulement )	 & ne possede pas des  sanctions ==> declageDatePAV = 0  & note != 0

			  else if( (Integer.parseInt(categorie)>5) && declageDatePAV == 0 && note != 0) {
			 HistoriqueAv historique = new HistoriqueAv();
			 historique.setMle(mle); 
			 historique.setNom(avancement.getNom());
			 historique.setQualification(qualificationPersonnel) ; 
			 historique.setCatA(categorie) ;      historique.setCatN(categorie) ; 
			 historique.setSCatA(null) ;          historique.setSCatN(null) ;				 
			 historique.setEchA(echelon) ;        historique.setEchN(Integer.parseInt(echelon) + 1+"") ;
		     historique.setSbaseA(th_ancienne) ;  historique.setSbaseN(th_nouveau) ;
			 historique.setThA(null) ;            historique.setThN(null) ; 
			 if(avancement.getIndDiff() == null) { historique.setIndDiffA(null); historique.setIndDiffN(null);  }
			 else { historique.setIndDiffA(ind_ancienne); historique.setIndDiffN(ind_nouveau); }
			 historique.setDEffetA(avancement.getdEffet()); 			 historique.setDEffetN(avancement.getDPAv());
			 historique.setDPAvA(avancement.getDPAv());
			 historique.setDPAvN(serviceSalaire.ajouter18Mois( (Date) avancement.getDPAv()));
			 historique.setObservation(observation) ;
			 historique.setNote(note) ; 
			 historique.setSan1(san1) ; 
			 historique.setSan2(san2) ; 

			      repository.save(historique);
			      
  Avancement av = new Avancement();
  av.setTPersonnel(personnel) ; 
  av.setNom(personnel.getNom()+" "+personnel.getPrenom());
  av.setQualification(qualificationPersonnel);  
  av.setCat(categorie) ; 
  av.setsCat(null);
  av.setEch(Integer.parseInt(echelon) + 1+"") ;
  av.setSbase(th_nouveau) ; 
  av.setTh(null); 
  if(avancement.getIndDiff() == null) { av.setIndDiff(null);  }
  else { av.setIndDiff(ind_nouveau);}
  av.setdEffet(avancement.getDPAv()) ; 
  av.setdPAv(serviceSalaire.ajouter18Mois( (Date) avancement.getDPAv())) ; 
  av.setObservation(observation) ;

    repositoryAvancement.save(av);}	
              // personnel  Mensuel  (possede categorie seulement )	 &  possede  des  sanctions ==> declageDatePAV <> 0  & note ==0 
			  else if( (Integer.parseInt(categorie)>5) && declageDatePAV != 0 && note == 0) {
					 HistoriqueAv historique = new HistoriqueAv();
					 historique.setMle(mle); 
					 historique.setNom(avancement.getNom());
					 historique.setQualification(qualificationPersonnel) ; 
					 historique.setCatA(categorie) ;      historique.setCatN(categorie) ; 
					 historique.setSCatA(null) ;          historique.setSCatN(null) ;				 
					 historique.setEchA(echelon) ;        historique.setEchN(Integer.parseInt(echelon) + 1+"") ;
				     historique.setSbaseA(th_ancienne) ;  historique.setSbaseN(th_nouveau) ;
					 historique.setThA(null) ;            historique.setThN(null) ; 
					 if(avancement.getIndDiff() == null) { historique.setIndDiffA(null); historique.setIndDiffN(null);  }
					 else { historique.setIndDiffA(ind_ancienne); historique.setIndDiffN(ind_nouveau); }					
					 historique.setDEffetA(avancement.getdEffet()); 	historique.setDEffetN(avancement.getDPAv());
					 historique.setDPAvA(avancement.getDPAv());
					 historique.setDPAvN(serviceSalaire.ajouter18Mois((Date) avancement.getDPAv()));
				     historique.setObservation(obser) ;                 
					 historique.setNote(note) ; 
					 historique.setSan1(san1) ; 
					 historique.setSan2(san2) ; 

					      repository.save(historique);
					      
		  Avancement av = new Avancement();
		  av.setTPersonnel(personnel) ; 
		  av.setNom(personnel.getNom()+" "+personnel.getPrenom());
		  av.setQualification(qualificationPersonnel);  
		  av.setCat(categorie) ; 
		  av.setsCat(null);
		  av.setEch(echelon) ;
		  av.setSbase(th_ancienne) ; 
		  av.setTh(null); 
		  if(avancement.getIndDiff() == null) { av.setIndDiff(null);  }
		  else { av.setIndDiff(ind_ancienne);}
		  av.setdEffet(avancement.getdEffet()) ; 
		  av.setdPAv(serviceSalaire.ajouter18Mois((Date) avancement.getDPAv())) ; 
		  if(observation == null) { av.setObservation(null);  }
		  else { av.setObservation(observation) ;}
		  
		  repositoryAvancement.save(av); }	

			  	
         // personnel  Mensuel  (possede categorie seulement )	 &  possede  des  sanctions ==> declageDatePAV <> 0  & note != 0
			  else if( (Integer.parseInt(categorie)>5) && declageDatePAV != 0 && note != 0) {
					 HistoriqueAv historique = new HistoriqueAv();
					 historique.setMle(mle); 
					 historique.setNom(avancement.getNom());
					 historique.setQualification(qualificationPersonnel) ; 
					 historique.setCatA(categorie) ;      historique.setCatN(categorie) ; 
					 historique.setSCatA(null) ;          historique.setSCatN(null) ;				 
					 historique.setEchA(echelon) ;        historique.setEchN(Integer.parseInt(echelon) + 1+"") ;
				     historique.setSbaseA(th_ancienne) ;  historique.setSbaseN(th_nouveau) ;
					 historique.setThA(null) ;            historique.setThN(null) ; 
					 if(avancement.getIndDiff() == null) { historique.setIndDiffA(null); historique.setIndDiffN(null);  }
					 else { historique.setIndDiffA(ind_ancienne); historique.setIndDiffN(ind_nouveau); }					
					 historique.setDEffetA(avancement.getdEffet()); 	historique.setDEffetN(avancement.getDPAv());
					 historique.setDPAvA(avancement.getDPAv());
					 historique.setDPAvN(nouvelDatePAv);
				     historique.setObservation(obser) ;                 
					 historique.setNote(note) ; 
					 historique.setSan1(san1) ; 
					 historique.setSan2(san2) ; 

					      repository.save(historique);
					      
		  Avancement av = new Avancement();
		  av.setTPersonnel(personnel) ; 
		  av.setNom(personnel.getNom()+" "+personnel.getPrenom());
		  av.setQualification(qualificationPersonnel);  
		  av.setCat(categorie) ; 
		  av.setsCat(null);
		  av.setEch(echelon) ;
		  av.setSbase(th_ancienne) ; 
		  av.setTh(null); 
		  if(avancement.getIndDiff() == null) { av.setIndDiff(null);  }
		  else { av.setIndDiff(ind_ancienne);}
		  av.setdEffet(avancement.getdEffet()) ; 
		  av.setdPAv(nouvelDatePAv) ; 
		  if(observation == null) { av.setObservation(obser);  }
		  else { av.setObservation(observation) ;}
		  
		  repositoryAvancement.save(av);
					  }					 	
		  }

	

	@Override
	public List<SanctionDetails> calculateDelays(List<Sanction> sanctions, float note) {
	    List<SanctionDetails> sanctionDetails = new ArrayList<>();

	    for (Sanction sanction : sanctions) {
	        SanctionDetails detail = new SanctionDetails();

	        if (note < 17) {
	            if (sanction.getDuree() <= 3) {
	                detail.setObservation("Sanction 1");
	                detail.setDelai(3);
	            } else if (sanction.getDuree() > 3) {
	                detail.setObservation("Sanction 2");
	                detail.setDelai(6);
	            }
	        } else {
	            if (sanction.getDuree() <= 3) {
	                detail.setObservation("Sanction 1");
	                detail.setDelai(3);
	            } else if (sanction.getDuree() > 3) {
	                detail.setObservation("Sanction 2");
	                detail.setDelai(6);
	            }
	        }

	        sanctionDetails.add(detail);
	    }

	    return sanctionDetails;
	}


	// Méthode pour calculer la somme des délais
    public static int calculateTotalDelay(List<SanctionDetails> sanctionDetails) {

        int totalDelay = 0;
        for (SanctionDetails detail : sanctionDetails) {
            totalDelay += detail.getDelai();
        }
        return totalDelay;}
    
    // Méthode pour concaténer les observations avec "&" comme séparateur
    public static String concatenateObservations(List<SanctionDetails> sanctionDetails) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sanctionDetails.size(); i++) {
            sb.append(sanctionDetails.get(i).getObservation());
            if (i < sanctionDetails.size() - 1) {
                sb.append(" & ");
            }
        }
        return sb.toString();
    }




/************************************************************************************************************************/


	@Override
	public List<HistoriqueAv> recupereLesHistoriquePersonnelsMensuel(String dateEffet) {
	
	List<HistoriqueAv> allHistoriques = historiqueRepository.findAll(); 
	List<HistoriqueAv> resultat = new ArrayList<HistoriqueAv>();  

    for (HistoriqueAv his :allHistoriques ) {
    	Date dateEffetNouveau = his.getDEffetN();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String dateEffetNouveauStr = sdf.format(dateEffetNouveau);
    	/********************************/
    	 String catA_string = his.getCatA();
    	 String catN_string = his.getCatN();
    	 String scatA_string= his.getSCatA();
    	 String scatN_string= his.getSCatN();
    	 String san1 = his.getSan1();
         String san2 = his.getSan2();
	        
    // Vérifiez si la chaîne de catégorie n'est pas vide avant de la convertir en entier
    if ((catA_string != null && !catA_string.isEmpty()) && (catN_string != null && !catN_string.isEmpty()) ) {
        try {
            int catA = Integer.parseInt(catA_string);
            int catN = Integer.parseInt(catN_string);

            if ((catA >= 1 && catA <=12  && scatA_string == null) && (catN >= 1 && catN <=12  && scatN_string == null) 
            		&& dateEffetNouveauStr.equals(dateEffet)) {
            	 if (san1 == null || san1.isEmpty()) {
                     his.setSan1(null);
                 }
                 if (san2 == null || san2.isEmpty()) {
                     his.setSan2(null);
                 }
            	resultat.add(his);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace(); // ou loggez un message d'erreur
        }
    } 
    }
		
		return resultat;
	}



	@Override
	public List<HistoriqueAv> recupereLesHistoriquePersonnelsHoraire(String dateEffet) {

		List<HistoriqueAv> allHistoriques = historiqueRepository.findAll(); 
		List<HistoriqueAv> resultat = new ArrayList<HistoriqueAv>();  

	    for (HistoriqueAv his :allHistoriques ) {    
	    Date dateEffetNouveau = his.getDEffetN();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String dateEffetNouveauStr = sdf.format(dateEffetNouveau);
    	/********************************/
	    	 String catA_string = his.getCatA();
	    	 String catN_string = his.getCatN();
	    	 String scatA_string= his.getSCatA();
	    	 String scatN_string= his.getSCatN();
	    	 String san1 = his.getSan1();
	         String san2 = his.getSan2();
		        
	    // Vérifiez si la chaîne de catégorie n'est pas vide avant de la convertir en entier
	    if ((catA_string != null && !catA_string.isEmpty()) && (catN_string != null && !catN_string.isEmpty()) ) {
	        try {
	            int catA = Integer.parseInt(catA_string);
	            int catN = Integer.parseInt(catN_string);

                if ((catA >= 1 && catA <=5 && catN >= 1 && catN <=5 ) && (scatA_string!= null && scatN_string!= null)&&
                		dateEffetNouveauStr.equals(dateEffet)) {
                	 if (san1 == null || san1.isEmpty()) {
                         his.setSan1(null);
                     }
                     if (san2 == null || san2.isEmpty()) {
                         his.setSan2(null);
                     }
                	resultat.add(his);
	            }
	        } catch (NumberFormatException e) {
	            e.printStackTrace(); // ou loggez un message d'erreur
	        }
	    } 
	    }
			
			return resultat;
		}



	@Override
	public Map<String, List<HistoriqueAv>> getHistoriquesAvMensuel() {
	    List<HistoriqueAv> allHistoriquesAv = repository.findAll();
	    Map<String, List<HistoriqueAv>> groupedByDEffetN = new HashMap<>();

	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	    for (HistoriqueAv historiqueAv : allHistoriquesAv) {
	        int categorie_ancienne = Integer.parseInt(historiqueAv.getCatA());
	        int categorie_nouveau = Integer.parseInt(historiqueAv.getCatN());

	        if ((categorie_ancienne >= 1 && categorie_nouveau >= 1) &&
	            (categorie_ancienne <= 12 && categorie_nouveau <= 12) &&
	            (historiqueAv.getsCatA() == null && historiqueAv.getsCatN() == null)) {
	            
	            String formattedDEffetN = dateFormat.format(historiqueAv.getDEffetN());
	            groupedByDEffetN
	                .computeIfAbsent(formattedDEffetN, k -> new ArrayList<>())
	                .add(historiqueAv);
	        }
	    }

	    return groupedByDEffetN;
	}



	@Override
	public Map<String, List<HistoriqueAv>> getHistoriquesAvHoraire() {
		   List<HistoriqueAv> allHistoriquesAv = repository.findAll();
		    Map<String, List<HistoriqueAv>> groupedByDEffetN = new HashMap<>();

		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		    for (HistoriqueAv historiqueAv : allHistoriquesAv) {
		        int categorie_ancienne = Integer.parseInt(historiqueAv.getCatA());
		        int categorie_nouveau = Integer.parseInt(historiqueAv.getCatN());

		        if ((categorie_ancienne >= 1 && categorie_nouveau >= 1) &&
		            (categorie_ancienne <= 5 && categorie_nouveau <= 5) &&
		            (historiqueAv.getsCatA() != null && historiqueAv.getsCatN() != null)) {
		            
		            String formattedDEffetN = dateFormat.format(historiqueAv.getDEffetN());
		            groupedByDEffetN
		                .computeIfAbsent(formattedDEffetN, k -> new ArrayList<>())
		                .add(historiqueAv);
		        }
		    }

		    return groupedByDEffetN;
	}
	/**************************************  Historique Horaire Exceptionnel 57 ans ********************************************/       
	    
	    public int calculerDifferenceDateEffetEtDateNaissance(String mle, String dateEffetStr) throws ParseException {
	        MesPersonnels personnel = servicePersonnel.recuperePersonnelParMle(mle);
	        if (personnel == null) {
	            throw new IllegalArgumentException("Personnel not found with mle: " + mle);
	        }

	        Date dateNaissance = personnel.getDate_N();
	        LocalDate dateNaissanceLocal = convertToLocalDate(dateNaissance);

	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        LocalDate dateEffet = LocalDate.parse(dateEffetStr, formatter);

	        return Period.between(dateNaissanceLocal, dateEffet).getYears();
	    }

	    private LocalDate convertToLocalDate(Date date) {
	        if (date instanceof java.sql.Date) {
	            return ((java.sql.Date) date).toLocalDate();
	        } else {
	            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	        }
	    }
	    
	                         /************************************/
	public Map<String, List<HistoriqueAv>> getHistoriquesAvHoraire57ans() throws ParseException {
		   List<HistoriqueAv> allHistoriquesAv = repository.findAll();
		    Map<String, List<HistoriqueAv>> groupedByDEffetN = new HashMap<>();

		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		    for (HistoriqueAv historiqueAv : allHistoriquesAv) {
		        int categorie_ancienne = Integer.parseInt(historiqueAv.getCatA());
		        int categorie_nouveau = Integer.parseInt(historiqueAv.getCatN());

		        if ((categorie_ancienne >= 1 && categorie_nouveau >= 1) &&
		            (categorie_ancienne <= 5 && categorie_nouveau <= 5) &&
		            (historiqueAv.getsCatA() != null && historiqueAv.getsCatN() != null)) {
		           
		            String formattedDEffetN = dateFormat.format(historiqueAv.getDEffetN());
		          
			        	int age = calculerDifferenceDateEffetEtDateNaissance(historiqueAv.getMle() , formattedDEffetN) ; 
                        if(age == 57) {
		            	groupedByDEffetN
		                .computeIfAbsent(formattedDEffetN, k -> new ArrayList<>())
		                .add(historiqueAv);
		        }
		        }
		    }
		    return groupedByDEffetN;
	}
	                       /**
	                     * @throws ParseException **********************************/
	
	public Map<String, List<HistoriqueAv>> getHistoriquesAvMensuel57ans() throws ParseException {
	    List<HistoriqueAv> allHistoriquesAv = repository.findAll();
	    Map<String, List<HistoriqueAv>> groupedByDEffetN = new HashMap<>();

	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	    for (HistoriqueAv historiqueAv : allHistoriquesAv) {
	        int categorie_ancienne = Integer.parseInt(historiqueAv.getCatA());
	        int categorie_nouveau = Integer.parseInt(historiqueAv.getCatN());

	        if ((categorie_ancienne >= 1 && categorie_nouveau >= 1) &&
	            (categorie_ancienne <= 12 && categorie_nouveau <= 12) &&
	            (historiqueAv.getsCatA() == null && historiqueAv.getsCatN() == null)) {
	            
	            String formattedDEffetN = dateFormat.format(historiqueAv.getDEffetN());
	            int age = calculerDifferenceDateEffetEtDateNaissance(historiqueAv.getMle() , formattedDEffetN) ; 
                if(age == 57) {
	            groupedByDEffetN
	                .computeIfAbsent(formattedDEffetN, k -> new ArrayList<>())
	                .add(historiqueAv);
	        }
	        }
	    }
	    return groupedByDEffetN;
	}

/*****************************************************************************************/
	@Override
	public void  supprimerAvancementsEnregistresMensuel(String dateEffet) throws ParseException {

		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 Date deffetN =dateFormat.parse(dateEffet) ; 
         List<HistoriqueAv> avancementsToDelete= repository.findBydEffetN(deffetN); 
         
for(HistoriqueAv his :avancementsToDelete ) {
	    if(Integer.parseInt(his.getCatA()) >=1  && Integer.parseInt(his.getCatA()) <= 12 && his.getSCatA() == null ) {
	   HistoriqueAv reserverHistorique = his ; 
	   repository.deleteById(his.getId()) ; 
	   serviceAvancement.deleteAvancementsByIds(serviceAvancementAudit.deleteByMleAndDateEffet(reserverHistorique) ) ; 	    	
	    }
}
		        
		      
	}
	
	@Override
	public void supprimerAvancementsEnregistresHoraire(String dateEffet) throws ParseException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 Date deffetN =dateFormat.parse(dateEffet) ; 
         List<HistoriqueAv> avancementsToDelete= repository.findBydEffetN(deffetN); 
         
         for(HistoriqueAv his :avancementsToDelete ) {
     	   if(Integer.parseInt(his.getCatA()) < 6 && his.getSCatA() != null ) {
     	   HistoriqueAv reserverHistorique = his ; 
     	   repository.deleteById(his.getId()) ; 
     	   serviceAvancement.deleteAvancementsByIds(serviceAvancementAudit.deleteByMleAndDateEffet(reserverHistorique) ) ; 	    	
     	    }
     }
		    
	}

}







