package com.rahma.AvEchelon.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.rahma.AvEchelon.Entity.Personnel;
import com.rahma.AvEchelon.Services.Personnel.MesPersonnels;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
	
	public interface PersonnelRepository extends JpaRepository<Personnel , String> {
	
    Optional<Personnel>  findById(String mle) ; 

	 @Query("SELECT per.mle AS mle, per.nom AS nom, per.prenom AS prenom, per.date_N AS date_N, " +
	           "per.num_CNSS AS num_CNSS, per.tel AS tel, per.date_Anc AS date_Anc, per.cin AS cin, " +
	           "per.adresse AS adresse, per.ville AS ville, pos.college AS college, pos.qualification AS qualification, " +
	           "pos.nom_Dep AS nom_Dep  , pos.nom_Service AS nom_Service  , pos.ref AS ref "+
	           "FROM ContratParent cp " +
	           "JOIN cp.personnelContratParent per " +
	           "JOIN cp.TContratDetails cd " +
	           "JOIN Poste pos ON pos.PostePersonnel.mle = per.mle " +
	           "WHERE cp.id = cd.detailsContratParent.id " +
	           "AND pos.PostePersonnel.mle = cp.personnelContratParent.mle " +
	           "AND cd.date_D <= :currentDate " +
	           "AND pos.active = '1' " +
	           "AND cd.type = 'Confirmation' " +
	           "AND per.date_Entre_Etab <= :currentDate " +
	           "AND (per.date_Sortie_Etab IS NULL OR per.date_Sortie_Etab >= :currentDate)") 
	    List<MesPersonnels> mesPersonnels(@Param("currentDate") Date currentDate);
	 
	  @Query("SELECT per.mle AS mle, per.nom AS nom, per.prenom AS prenom, pos.college AS college,per.date_N AS date_N, "+
			  "per.num_CNSS AS num_CNSS, per.tel AS tel, per.date_Anc AS date_Anc, per.cin AS cin, " +
			  "per.adresse AS adresse, per.ville AS ville, pos.qualification AS qualification, " +
			  "pos.nom_Dep AS nom_Dep  , pos.nom_Service AS nom_Service  , pos.ref AS ref ,"+
	           "COALESCE(av.cat, '') AS categorie, NULLIF(COALESCE(av.sCat, ''), '') AS sousCategorie " +
	           "FROM ContratParent cp " +
	           "JOIN cp.personnelContratParent per " +
	           "JOIN cp.TContratDetails cd " +
	           "JOIN Poste pos ON pos.PostePersonnel.mle = per.mle " +
	           "LEFT JOIN Avancement av ON av.TPersonnel.mle = per.mle AND av.dEffet = (" +
	           "    SELECT MAX(sub_av.dEffet) FROM Avancement sub_av WHERE sub_av.TPersonnel.mle = per.mle" +
	           ") " +
	           "WHERE cd.type = 'Confirmation' " +
	           "AND pos.active = '1' " +
	           "AND per.date_Entre_Etab <= :currentDate " +
	           "AND (per.date_Sortie_Etab IS NULL OR per.date_Sortie_Etab >= :currentDate) " +
	           "AND cd.date_D <= :currentDate " +
	           "GROUP BY per.mle, per.nom, per.prenom, pos.college " +
	           "HAVING COUNT(DISTINCT per.mle) = 1")
	     List<MesPersonnels> mesPersonnelsAvecCategorieEtSousCategorie(@Param("currentDate") Date currentDate);
}
