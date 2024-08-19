package com.rahma.AvEchelon.Repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.rahma.AvEchelon.Entity.*;

@Repository
	
	public interface TriggerRepository extends JpaRepository<Trigger_Avancement , Integer> {

	@Query("SELECT a FROM Trigger_Avancement a WHERE a.mle = :mle  AND a.dPAv = :dPAv AND action='INSERT'")
    Trigger_Avancement findByMleAndDeffet(
            @Param("mle") String mle,
            @Param("dPAv") Date dPAv);
}
