package it.prova.gestionepermessi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionepermessi.model.Messaggio;

public interface MessaggioRepository extends CrudRepository<Messaggio, Long>{
	
	public Messaggio findByRichiestaPermesso_id(Long idRichiesta);

	public Page<Messaggio> findAll(Specification<Messaggio> specificationCriteria, Pageable paging);
	
	@Query("FROM Messaggio m WHERE m.letto = false")
	public List<Messaggio> findAllMessaggiNonLetti();

}
