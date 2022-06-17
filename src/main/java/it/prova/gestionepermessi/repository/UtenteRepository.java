package it.prova.gestionepermessi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionepermessi.model.Utente;

public interface UtenteRepository extends CrudRepository<Utente, Long>{
	
	@EntityGraph(attributePaths = { "ruoli" })
	Optional<Utente> findByUsername(String username);
	
	@Query("SELECT u FROM Utente u LEFT JOIN FETCH u.ruoli r LEFT JOIN FETCH u.dipendente d WHERE u.id = ?1")
	Optional<Utente> findByUsernameAndDipendente(Long id);

	Page<Utente> findAll(Specification<Utente> specificationCriteria, Pageable paging);
	
}
