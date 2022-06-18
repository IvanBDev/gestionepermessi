package it.prova.gestionepermessi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import it.prova.gestionepermessi.model.Dipendente;

public interface DipendenteRepository extends CrudRepository<Dipendente, Long>, QueryByExampleExecutor<Dipendente>{

	Page<Dipendente> findAll(Specification<Dipendente> specificationCriteria, Pageable paging);
	
	@Query("FROM Dipendente d LEFT JOIN FETCH d.utente u WHERE d.id = ?1")
	public Dipendente findDipendenteEagerWithUtente(Long id);
	
}
