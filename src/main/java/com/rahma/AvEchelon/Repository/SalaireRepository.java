package com.rahma.AvEchelon.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.rahma.AvEchelon.Entity.Salaire;


@Repository
	
	public interface SalaireRepository extends JpaRepository<Salaire , Integer> {
	

    Salaire findByCatAndSCat(String cat, String sCat);
    
    Salaire findByCat(String cat);
}