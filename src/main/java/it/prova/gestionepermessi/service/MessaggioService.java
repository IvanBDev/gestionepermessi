package it.prova.gestionepermessi.service;

import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface MessaggioService {
	
	public void nuovoMessaggio(Messaggio messaggioInstance, RichiestaPermesso richiestaInstance);
	
	public Messaggio findByRichiestaPermesso_Id(Long idRichiesta);
	
	public void rimuoviMessaggio(Messaggio messaggio);
	
}
