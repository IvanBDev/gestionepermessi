package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import it.prova.gestionepermessi.dto.RicercaUtenteDTO;
import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Utente;

public interface UtenteService {
	
	public List<Utente> listAllUtenti();
	
	public Utente caricaSingoloUtenteConRuoli(Long id);
	
	public Utente caricaSingoloUtente(Long id);
	
	public Utente caricaSingoloUtenteConRuoliEDipendente(Long id);
	
	public void aggiorna(Utente utenteInstance);
	
	public Utente findByUsername(String username);
	
	public Page<Utente> findByExample(RicercaUtenteDTO example, Integer pageNo, Integer pageSize, String sortBy);
	
	public void inserisciECollegaDipendenteAUtente(Utente utenteInstance, Dipendente instance);
	
	public void inserisciNuovo(Utente utenteInstance);
	
	public void changeUserAbilitation(Long utenteInstanceId);
	
}
