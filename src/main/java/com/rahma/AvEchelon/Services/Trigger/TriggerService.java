package com.rahma.AvEchelon.Services.Trigger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rahma.AvEchelon.Entity.HistoriqueAv;
import com.rahma.AvEchelon.Entity.Trigger_Avancement;
import com.rahma.AvEchelon.Repository.TriggerRepository;

@Service
public class TriggerService {

	@Autowired TriggerRepository repository ; 

	public int deleteByMleAndDateEffet(HistoriqueAv AvancementSupprimes) {
 
    Trigger_Avancement elementsToDelete = repository.findByMleAndDeffet(AvancementSupprimes.getMle(), AvancementSupprimes.getdPAvN() );
    int deletedIds= (elementsToDelete.getId());
    repository.deleteById(deletedIds);
	return deletedIds;
	}

}