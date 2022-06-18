package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import it.prova.gestionepermessi.model.Dipendente;

public interface DipendenteService {
	
	public List<Dipendente> listAllDipendente();
	
	public Dipendente caricaSingoloDipendente(Long id);
	
	public Dipendente caricaSingoloDipendenteEagerConUtente(Long id);
	
	public Page<Dipendente> findByExample(Dipendente example, Integer pageNo, Integer pageSize, String sortBy);
	
	public void inserisciNuovo(Dipendente dipendente);
	
	public void aggiornaDipendente(Dipendente dipendente);
	
	public void costruzioneEInserimentoDiDipendenteEUtente(Dipendente dipendenteinstance);
	
}
