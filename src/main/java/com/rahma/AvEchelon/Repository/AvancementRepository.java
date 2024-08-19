package com.rahma.AvEchelon.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.rahma.AvEchelon.Entity.*;
import com.rahma.AvEchelon.Services.Avancement.AvancementProjection;
import java.util.Date;
import java.util.List;
@Repository
	
	public interface AvancementRepository extends JpaRepository<Avancement , Integer> {

    List<Avancement> findBydPAv(Date dateProchainAvancement);
                                  /**************************************/
    // recuperer liste des avancement pour un personnel 
    @Query("SELECT a FROM Avancement a WHERE a.TPersonnel.mle = :mle")
    List<Avancement> findAvancementsByPersonnelMle(@Param("mle") String mle);
    
                                 /**************************************/
    // recupere derniere avancement pour un personnel bien determiner 
    @Query("SELECT a FROM Avancement a WHERE a.dEffet = (SELECT MAX(a2.dEffet) FROM Avancement a2 WHERE a2.TPersonnel = a.TPersonnel) AND a.dPAv = (SELECT MAX(a2.dPAv) FROM Avancement a2 WHERE a2.TPersonnel = a.TPersonnel AND a2.dEffet = a.dEffet) AND a.TPersonnel.mle = :mle")
    Avancement findRecentAvancement(@Param("mle") String mle);

                                 /**************************************/
	// recupere les derniere avancement pour un personnel bien determiner avec meme date d'effet ===> perseonnel admet des sanctions 
    @Query("SELECT a FROM Avancement a WHERE a.dEffet = (SELECT MAX(a2.dEffet) FROM Avancement a2 WHERE a2.TPersonnel = a.TPersonnel) AND a.TPersonnel.mle = :mle")
    List<Avancement> findRecentAvancementPourMemeDateEffet(@Param("mle") String mle);

                                  /**************************************/
 // recupere les  avancements pour un personnel bien determiner avec meme date PV 
    @Query("SELECT a.id AS id, " +
    	       "a.TPersonnel.mle AS personnelMle, " +
    	       "a.nom AS nom, " +
    	       "a.cat AS cat, " +  // Make sure this matches the getter in AvancementProjection
    	       "a.sCat AS sCat, " +
    	       "a.ech AS ech, " +
    	       "a.sbase AS sbase, " +
    	       "a.th AS th, " +
    	       "a.indDiff AS indDiff, " +
    	       "a.dEffet AS dEffet, " +
    	       "a.dPAv AS dPAv " +
    	       "FROM Avancement a " +
    	       "WHERE a.dPAv = :datePv AND a.TPersonnel.mle = :mle")
    	List<AvancementProjection> findAvancementByDatePVAndMle(@Param("datePv") Date datePv, @Param("mle") String mle);


                              /***************************************************/
    // recuperer derniere avancement pour chaque personnel  ==========> Globale(Horaire + Mensuel)
    @Query("SELECT a FROM Avancement a " +
    	       "WHERE a.TPersonnel.mle IN :personnelMles " +
    	       "AND a.dEffet = (SELECT MAX(a2.dEffet) FROM Avancement a2 WHERE a2.TPersonnel.mle = a.TPersonnel.mle)")
    	List<Avancement> findLatestAvancementsForEachPersonnel(@Param("personnelMles") List<String> personnelMles);
                            /****************************************************/
    
    @Query("SELECT a.id as id, a.nom as nom, a.cat as cat, a.sCat as sCat, a.ech as ech, a.sbase as sbase, a.th as th, a.indDiff as indDiff, a.dEffet as dEffet, a.dPAv as dPAv, p.mle as personnelMle " +
    	       "FROM Avancement a JOIN a.TPersonnel p " +
    	       "WHERE p.mle IN :personnelMles " +
    	       "ORDER BY a.dEffet DESC")
    	List<AvancementProjection> findLatestAvancementsProjectionForEachPersonnel(@Param("personnelMles") List<String> personnelMles);
    // recuperer derniere avancement pour chaque personnel ======> Personnels Mensuel
    @Query("SELECT a FROM Avancement a " +
    	       "WHERE a.TPersonnel.mle IN :personnelMles " +
    	       "AND a.dEffet = (SELECT MAX(a2.dEffet) FROM Avancement a2 WHERE a2.TPersonnel.mle = a.TPersonnel.mle) " +
    	       "AND a.cat IS NOT NULL " +
    	       "AND a.sCat IS NULL")
    	List<Avancement> findLatestAvancementsForEachPersonnelMensuel(@Param("personnelMles") List<String> personnelMles);

    
    // recuperer derniere avancement pour chaque personnel  ==================> Personnels Horaire
    @Query("SELECT a FROM Avancement a " +
    	       "WHERE a.TPersonnel.mle IN :personnelMles " +
    	       "AND a.dEffet = (SELECT MAX(a2.dEffet) FROM Avancement a2 WHERE a2.TPersonnel.mle = a.TPersonnel.mle) " +
    	       "AND a.cat IS NOT NULL " +
    	       "AND a.sCat IS NOT NULL")
    	List<Avancement> findLatestAvancementsForEachPersonnelHoraire(@Param("personnelMles") List<String> personnelMles);

    /************************************************************************************************/
    @Query("SELECT a FROM Avancement a WHERE a.TPersonnel.mle = :mle AND a.dEffet = :deffetN")
    List<Avancement> findByMleAndDeffetN(@Param("mle") String mle, @Param("deffetN") Date deffetN);
}
