package it.prova.gestionepermessi.service;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Utente;

public interface UtenteService {
	
	public Utente caricaSingoloUtente(Long id);
	
	public Utente findByUsername(String username);
	
	public void inserisciECollegaDipendenteAUtente(Utente utenteInstance, Dipendente instance);
	
	public void inserisciNuovo(Utente utenteInstance);
	
	public void changeUserAbilitation(Long utenteInstanceId);
	
}
