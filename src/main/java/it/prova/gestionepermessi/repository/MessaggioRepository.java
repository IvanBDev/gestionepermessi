package it.prova.gestionepermessi.repository;

import org.springframework.data.repository.CrudRepository;

import it.prova.gestionepermessi.model.Messaggio;

public interface MessaggioRepository extends CrudRepository<Messaggio, Long>{
	
	public Messaggio findByRichiestaPermesso_id(Long idRichiesta);

}
