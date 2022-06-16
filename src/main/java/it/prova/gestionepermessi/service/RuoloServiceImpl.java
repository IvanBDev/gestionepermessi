package it.prova.gestionepermessi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.model.Ruolo;
import it.prova.gestionepermessi.repository.RuoloRepository;

@Service
public class RuoloServiceImpl implements RuoloService{

	@Autowired
	private RuoloRepository repository;
	
	@Override
	@Transactional(readOnly = true)
	public Ruolo cercaPerDescrizioneECodice(String descrizione, String codice) {
		// TODO Auto-generated method stub
		return repository.findByDescrizioneAndCodice(descrizione, codice);
	}

	@Override
	public void inserisciNuovo(Ruolo ruoloInstance) {
		// TODO Auto-generated method stub
		repository.save(ruoloInstance);
	}

}
