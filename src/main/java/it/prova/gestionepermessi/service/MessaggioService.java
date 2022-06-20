package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface MessaggioService {
	
	public void nuovoMessaggio(Messaggio messaggioInstance, RichiestaPermesso richiestaInstance);
	
	public Messaggio findByRichiestaPermesso_Id(Long idRichiesta);
	
	public void rimuoviMessaggio(Messaggio messaggio);
	
	public Page<Messaggio> findByExample(Messaggio messaggioExample, Integer pageNo, Integer pageSize,
			String sortBy);
	
	public Messaggio caricaSingoloMessaggio(Long idMessaggio);
	
	public Messaggio aggiorna(Messaggio messaggio);
	
	public List<Messaggio> findAllMessaggi();

	public List<Messaggio> findAllMessaggiNonLetti();
	
}
