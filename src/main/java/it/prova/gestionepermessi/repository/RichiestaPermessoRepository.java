package it.prova.gestionepermessi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface RichiestaPermessoRepository extends CrudRepository<RichiestaPermesso, Long>{
	
	List<RichiestaPermesso> findAllByDipendente_Id(Long id);

	Page<RichiestaPermesso> findAll(Specification<RichiestaPermesso> specificationCriteria, Pageable paging);
	
	@Query("SELECT r FROM RichiestaPermesso r JOIN FETCH r.dipendente d JOIN FETCH d.richiestaPermessi JOIN FETCH r.attachment WHERE r.id = ?1")
	public RichiestaPermesso findByIdEager(Long id);
	
}
