package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface RichiestaPermessoService {
	
	public List<RichiestaPermesso> listAllRichiestePerIdDipendente(Long idDipendente);
	
	public void inserisciNuovo(RichiestaPermesso richiestaPermessoInstance, boolean giornoUnico,  MultipartFile file);
	
	public Page<RichiestaPermesso> findByExample(RichiestaPermesso example, Integer pageNo, Integer pageSize,
			String sortBy);
	
	public RichiestaPermesso caricaSingoloTramiteId(Long idRichiesta);
	
	public void rimuoviTramiteId(Long idRichiesta);
	
	public RichiestaPermesso findByIdEager(Long idRichiesta);
	
}
