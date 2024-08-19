package com.rahma.AvEchelon.Repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import com.rahma.AvEchelon.Entity.Sanction;

public interface SanctionRepository extends JpaRepository<Sanction , Integer> {
	
	
	@Query("SELECT s FROM Sanction s WHERE s.d_Debut >= :dateProchainAvancementMoins18Mois AND s.sanctionPersonnel.mle = :mle")
    List<Sanction> findSanctionsSinceDateProchainAvancement(
            @Param("dateProchainAvancementMoins18Mois") Date dateProchainAvancementMoins18Mois,
            @Param("mle") String mle
    );
	
	
	
	// recuperer les sanctions d'un personnel entre date d'effet et date de prochain avancement d'un avancement bien determiner 
	 @Query("SELECT s FROM Sanction s WHERE s.sanctionPersonnel.mle = :mle AND s.d_Debut >= :dEffet AND s.d_Fin <= :dPAv")
	    List<Sanction> findSanctionsBetweenDates(@Param("mle") String mle, 
	                                             @Param("dEffet") Date dEffet, 
	                                             @Param("dPAv") Date dPAv);
	
}