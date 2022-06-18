package it.prova.gestionepermessi.service;

import java.util.ArrayList;
import java.util.Date;
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
import it.prova.gestionepermessi.model.StatoUtente;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.repository.DipendenteRepository;
import it.prova.gestionepermessi.repository.UtenteRepository;

@Service
public class DipendenteServiceimpl implements DipendenteService{
	
	@Autowired
	public DipendenteRepository dipendenteRepository;
	
	@Autowired
	public UtenteRepository utenteRepository;
	
	@Autowired
	public RuoloService ruoloService;

	@Override
	@Transactional(readOnly = true)
	public List<Dipendente> listAllDipendente() {
		// TODO Auto-generated method stub
		return (List<Dipendente>) dipendenteRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = true)
	public Dipendente caricaSingoloDipendente(Long id) {
		// TODO Auto-generated method stub
		return dipendenteRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void inserisciNuovo(Dipendente dipendenteInstance) {
		// TODO Auto-generated method stub
		dipendenteRepository.save(dipendenteInstance);
	}

	@Override
	@Transactional
	public void costruzioneEInserimentoDiDipendenteEUtente(Dipendente dipendenteinstance) {
		// TODO Auto-generated method stub
		dipendenteinstance.setUtente(creazioneUtenza(dipendenteinstance));
		
		utenteRepository.save(dipendenteinstance.getUtente());
		dipendenteRepository.save(dipendenteinstance);
	}
	
	@Transactional
	private Utente creazioneUtenza(Dipendente dipendenteInstance) {
		
		Utente nuovaUtenza = new Utente(dipendenteInstance);
		
		nuovaUtenza.setUsername(dipendenteInstance.getNome().substring(0, 1) +"."+ dipendenteInstance.getCognome());
		nuovaUtenza.setPassword("Password@01");
		nuovaUtenza.setStato(StatoUtente.CREATO);
		nuovaUtenza.getRuoli().add(ruoloService.cercaPerDescrizioneECodice("Dipendente", "ROLE_DIPENDENTE_USER"));
		nuovaUtenza.setDateCreated(new Date());
		
		return nuovaUtenza;
		
	}

	@Override
	@Transactional
	public void aggiornaDipendente(Dipendente dipendenteInstance) {
		// TODO Auto-generated method stub
		Dipendente dipendenteDb = caricaSingoloDipendenteEagerConUtente(dipendenteInstance.getId());
		
		dipendenteInstance.setUtente(dipendenteDb.getUtente());
		dipendenteInstance.getUtente().setUsername(dipendenteInstance.getNome().substring(0, 1) +"."+ dipendenteInstance.getCognome());
		
		utenteRepository.save(dipendenteInstance.getUtente());
		dipendenteRepository.save(dipendenteInstance);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Dipendente caricaSingoloDipendenteEagerConUtente(Long id) {
		// TODO Auto-generated method stub
		return dipendenteRepository.findDipendenteEagerWithUtente(id);
	}

}
