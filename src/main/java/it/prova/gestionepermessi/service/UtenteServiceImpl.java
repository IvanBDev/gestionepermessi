package it.prova.gestionepermessi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.dto.RicercaUtenteDTO;
import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Ruolo;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.repository.DipendenteRepository;
import it.prova.gestionepermessi.repository.UtenteRepository;
import it.prova.gestionepermessi.model.StatoUtente;

@Service
public class UtenteServiceImpl implements UtenteService {

	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private DipendenteRepository dipendenteRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Utente findByUsername(String username) {
		// TODO Auto-generated method stub
		return utenteRepository.findByUsername(username).orElse(null);
	}

	@Override
	@Transactional
	public void inserisciECollegaDipendenteAUtente(Utente utenteInstance, Dipendente dipendenteInstance) {
		// TODO Auto-generated method stub
		utenteInstance.setStato(StatoUtente.CREATO);
		utenteInstance.setPassword(passwordEncoder.encode(utenteInstance.getPassword()));
		utenteInstance.setDateCreated(new Date());

		utenteRepository.save(utenteInstance);
		dipendenteRepository.save(dipendenteInstance);

	}

	@Override
	@Transactional
	public void inserisciNuovo(Utente utenteInstance) {
		// TODO Auto-generated method stub
		utenteRepository.save(utenteInstance);
	}

	@Override
	@Transactional
	public void changeUserAbilitation(Long utenteInstanceId) {
		// TODO Auto-generated method stub
		Utente utenteInstance = caricaSingoloUtente(utenteInstanceId);

		if (utenteInstance == null)
			throw new RuntimeException("Elemento non trovato.");

		if (utenteInstance.getStato() == null || utenteInstance.getStato().equals(StatoUtente.CREATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
		else if (utenteInstance.getStato().equals(StatoUtente.ATTIVO))
			utenteInstance.setStato(StatoUtente.DISABILITATO);
		else if (utenteInstance.getStato().equals(StatoUtente.DISABILITATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);

	}

	@Override
	@Transactional
	public Utente caricaSingoloUtente(Long id) {
		// TODO Auto-generated method stub
		return utenteRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Utente> listAllUtenti() {
		// TODO Auto-generated method stub
		return (List<Utente>) utenteRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Utente caricaSingoloUtenteConRuoli(Long id) {
		// TODO Auto-generated method stub
		return utenteRepository.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public Page<Utente> findByExample(RicercaUtenteDTO example, Integer pageNo, Integer pageSize, String sortBy) {
		Specification<Utente> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();

			// faccio fetch del dipendente e ruoli a prescindere
			root.fetch("dipendente", JoinType.INNER);
			root.fetch("ruoli", JoinType.LEFT);

			if (StringUtils.isNotEmpty(example.getUsername()))
				predicates
						.add(cb.like(cb.upper(root.get("username")), "%" + example.getUsername().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getNome()))
				predicates.add(cb.like(cb.upper(root.join("dipendente", JoinType.INNER).get("nome")),
						"%" + example.getNome().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getCognome()))
				predicates.add(cb.like(cb.upper(root.join("dipendente", JoinType.INNER).get("cognome")),
						"%" + example.getCognome().toUpperCase() + "%"));
			
			if (example.getStato() != null)
				predicates.add(cb.equal(root.get("stato"), example.getStato()));

			if (example.getRuoliIds() != null && example.getRuoliIds().length > 0)
				predicates.add(root.join("ruoli").in(Arrays.asList(example.getRuoliIds()).stream().map(id -> new Ruolo(id)).collect(Collectors.toSet())));

			query.distinct(true);
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		Pageable paging = null;
		// se non passo parametri di paginazione non ne tengo conto
		if (pageSize == null || pageSize < 10)
			paging = Pageable.unpaged();
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		return utenteRepository.findAll(specificationCriteria, paging);

	}

	@Override
	@Transactional
	public void aggiorna(Utente utenteInstance) {
		// deve aggiornare solo nome, cognome, username, ruoli
		Utente utenteReloaded = utenteRepository.findById(utenteInstance.getId()).orElse(null);
		if (utenteReloaded == null)
			throw new RuntimeException("Elemento non trovato");
		utenteReloaded.setRuoli(utenteInstance.getRuoli());
		utenteRepository.save(utenteReloaded);
	}

	@Override
	public Utente caricaSingoloUtenteConRuoliEDipendente(Long id) {
		// TODO Auto-generated method stub
		return utenteRepository.findByUsernameAndDipendente(id).orElse(null);
	}
}
