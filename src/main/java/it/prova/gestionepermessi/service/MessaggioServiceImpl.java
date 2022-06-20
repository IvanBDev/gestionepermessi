package it.prova.gestionepermessi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.repository.MessaggioRepository;

@Service
public class MessaggioServiceImpl implements MessaggioService {

	@Autowired
	private MessaggioRepository messaggioRepository;

	@Override
	@Transactional
	public void nuovoMessaggio(Messaggio messaggioInstance, RichiestaPermesso richiestaInstance) {
		// TODO Auto-generated method stub
		String note = richiestaInstance.getNote().isBlank() ? ""
				: " , le note del dipendente " + richiestaInstance.getNote();
		String codiceCertificato = richiestaInstance.getCodiceCertificato().isBlank() ? ""
				: " , il Codice del Certificato: " + richiestaInstance.getCodiceCertificato();
		String attachment = richiestaInstance.getAttachment() == null ? "" : " , il file allegato";
		String parteFinaleMessaggio = ".";

		if (!note.isBlank() || !codiceCertificato.isBlank() || !attachment.isBlank()) {
			parteFinaleMessaggio += " In allegato :";
			parteFinaleMessaggio += note.isBlank() ? "" : " " + note;
			parteFinaleMessaggio += codiceCertificato.isBlank() ? "" : " " + codiceCertificato;
			parteFinaleMessaggio += attachment.isBlank() ? "" : " " + attachment;
			parteFinaleMessaggio += ".";
		}

		messaggioInstance.setOggetto("Richiesta Permesso di " + richiestaInstance.getDipendente().getNome() + " "
				+ richiestaInstance.getDipendente().getCognome());

		messaggioInstance.setTesto("Il dipendente " + richiestaInstance.getDipendente().getNome() + " "
				+ richiestaInstance.getDipendente().getCognome() + " ha richiesto un permesso per "
				+ richiestaInstance.getTipoPermesso() + " a partire dal giorno " + richiestaInstance.getDataInizio()
				+ " al giorno " + richiestaInstance.getDataFine() + parteFinaleMessaggio);

		messaggioInstance.setDataInserimento(new Date());
		messaggioInstance.setLetto(false);
		messaggioInstance.setRichiestaPermesso(richiestaInstance);

		messaggioRepository.save(messaggioInstance);
	}

	@Override
	@Transactional
	public Messaggio findByRichiestaPermesso_Id(Long idRichiesta) {
		// TODO Auto-generated method stub
		return messaggioRepository.findByRichiestaPermesso_id(idRichiesta);
	}

	@Override
	@Transactional
	public void rimuoviMessaggio(Messaggio messaggio) {
		// TODO Auto-generated method stub
		messaggioRepository.delete(messaggio);
	}

	@Override
	public Page<Messaggio> findByExample(Messaggio example, Integer pageNo, Integer pageSize, String sortBy) {
		// TODO Auto-generated method stub
		Specification<Messaggio> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();

			// faccio fetch del dipendente e ruoli a prescindere
			//root.fetch("dipendente", JoinType.INNER);
			//root.fetch("richiestaPermesso", JoinType.LEFT);

			if (StringUtils.isNotEmpty(example.getOggetto()))
				predicates
						.add(cb.like(cb.upper(root.get("oggetto")), "%" + example.getOggetto().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getTesto()))
				predicates.add(cb.like(cb.upper(root.get("testo")), "%" + example.getTesto().toUpperCase() + "%"));
			
			if (example.getDataInserimento() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataInserimento"), example.getDataInserimento()));
			
			if (example.getDataLettura() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataLettura"), example.getDataLettura()));

//			if (example.getRichiestaPermesso() != null && example.getRuoliIds().length > 0)
//				predicates.add(root.join("ruoli").in(Arrays.asList(example.getRuoliIds()).stream()
//						.map(id -> new Ruolo(id)).collect(Collectors.toSet())));

			query.distinct(true);
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		Pageable paging = null;
		// se non passo parametri di paginazione non ne tengo conto
		if (pageSize == null || pageSize < 10)
			paging = Pageable.unpaged();
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		return messaggioRepository.findAll(specificationCriteria, paging);
	}

	@Override
	public Messaggio caricaSingoloMessaggio(Long idMessaggio) {
		// TODO Auto-generated method stub
		return messaggioRepository.findById(idMessaggio).orElse(null);
	}

	@Override
	public Messaggio aggiorna(Messaggio messaggio) {
		// TODO Auto-generated method stub
		return messaggioRepository.save(messaggio);
	}

	@Override
	public List<Messaggio> findAllMessaggi() {
		// TODO Auto-generated method stub
		return (List<Messaggio>) messaggioRepository.findAll();
	}

	@Override
	public List<Messaggio> findAllMessaggiNonLetti() {
		// TODO Auto-generated method stub
		return (List<Messaggio>) messaggioRepository.findAllMessaggiNonLetti();
	}

}
