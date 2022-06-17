package it.prova.gestionepermessi.service;

import java.util.List;

import it.prova.gestionepermessi.model.Ruolo;

public interface RuoloService {
	
	public List<Ruolo> listAll();
	
	public Ruolo cercaPerDescrizioneECodice(String descrizione, String codice);
	
	public void inserisciNuovo(Ruolo ruoloInstance);
	
}
