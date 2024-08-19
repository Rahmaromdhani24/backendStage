package com.rahma.AvEchelon.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.rahma.AvEchelon.Entity.HistoriqueAv;

import jakarta.transaction.Transactional;


@Repository
	
	public interface HistoriqueRepository extends JpaRepository<HistoriqueAv , Integer> {
	
	
	  List<HistoriqueAv> findBydEffetN(Date dEffetN);
	  @Transactional
	  void deleteBydEffetN(Date dateEffet);	
}
