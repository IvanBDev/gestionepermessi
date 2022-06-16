package it.prova.gestionepermessi.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.repository.DipendenteRepository;
import it.prova.gestionepermessi.repository.UtenteRepository;
import it.prova.gestionepermessi.model.StatoUtente;

@Service
public class UtenteServiceImpl implements UtenteService{
	
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

}
