package it.prova.gestionepermessi.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.repository.DipendenteRepository;

@Service
public class DipendenteServiceimpl implements DipendenteService{
	
	@Autowired
	public DipendenteRepository dipendenteRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Dipendente> listAllDipendente() {
		// TODO Auto-generated method stub
		return (List<Dipendente>) dipendenteRepository.findAll();
	}

	@Override
	public Page<Dipendente> findByExample(Dipendente example, Integer pageNo, Integer pageSize, String sortBy) {
		// TODO Auto-generated method stub
		Specification<Dipendente> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();

			// faccio fetch del dipendente e ruoli a prescindere
			//root.fetch("dipendente", JoinType.INNER);
			//root.fetch("ruoli", JoinType.LEFT);

			if (StringUtils.isNotEmpty(example.getNome()))
				predicates
						.add(cb.like(cb.upper(root.get("nome")), "%" + example.getNome().toUpperCase() + "%"));
			
			if (StringUtils.isNotEmpty(example.getCognome()))
				predicates
						.add(cb.like(cb.upper(root.get("cognome")), "%" + example.getCognome().toUpperCase() + "%"));
			
			if (StringUtils.isNotEmpty(example.getCodiceFiscale()))
				predicates
						.add(cb.like(cb.upper(root.get("codiceFiscale")), "%" + example.getCodiceFiscale().toUpperCase() + "%"));
			
			if (StringUtils.isNotEmpty(example.getEmail()))
				predicates
						.add(cb.like(cb.upper(root.get("email")), "%" + example.getEmail().toUpperCase() + "%"));
			
			if (example.getSesso() != null)
				predicates.add(cb.equal(root.get("sesso"), example.getSesso()));
			
			if (example.getDataNascita() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataNascita"), example.getDataNascita()));
			
			if (example.getDataAssunzione() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataAssunzione"), example.getDataAssunzione()));

			query.distinct(true);
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		Pageable paging = null;
		// se non passo parametri di paginazione non ne tengo conto
		if (pageSize == null || pageSize < 10)
			paging = Pageable.unpaged();
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		return dipendenteRepository.findAll(specificationCriteria, paging);
	}

	@Override
	public Dipendente caricaSingoloDipendente(Long id) {
		// TODO Auto-generated method stub
		return dipendenteRepository.findById(id).orElse(null);
	}

}
