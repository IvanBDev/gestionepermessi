package it.prova.gestionepermessi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.prova.gestionepermessi.model.Attachment;
import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.repository.AttachmentRepository;
import it.prova.gestionepermessi.repository.RichiestaPermessoRepository;

@Service
public class RichiestaPermessoServiceImpl implements RichiestaPermessoService {

	@Autowired
	private RichiestaPermessoRepository richiestaPermessoRepository;

	@Autowired
	private MessaggioService messaggioService;

	@Autowired
	private AttachmentRepository attachmentRepository;

	@Override
	public List<RichiestaPermesso> listAllRichiestePerIdDipendente(Long idDipendente) {
		// TODO Auto-generated method stub
		return richiestaPermessoRepository.findAllByDipendente_Id(idDipendente);
	}

	@Override
	@Transactional
	public void inserisciNuovo(RichiestaPermesso richiestaPermessoInstance, boolean giornoSingolo, MultipartFile file) {
		// TODO Auto-generated method stub
		if (giornoSingolo) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(richiestaPermessoInstance.getDataInizio());
			calendar.add(Calendar.HOUR, 24);
			richiestaPermessoInstance.setDataFine(calendar.getTime());
		}
		if (file != null) {
			Attachment newfile = new Attachment();
			newfile.setNomeFile(file.getOriginalFilename());
			newfile.setContentType(file.getContentType());
			try {
				newfile.setPayload(file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			richiestaPermessoInstance.setAttachment(newfile);
			attachmentRepository.save(newfile);
			richiestaPermessoRepository.save(richiestaPermessoInstance);
			messaggioService.nuovoMessaggio(new Messaggio(), richiestaPermessoInstance);
			return;
		}
		richiestaPermessoRepository.save(richiestaPermessoInstance);
		messaggioService.nuovoMessaggio(new Messaggio(), richiestaPermessoInstance);
	}

	@Override
	@Transactional
	public Page<RichiestaPermesso> findByExample(RichiestaPermesso example, Integer pageNo, Integer pageSize,
			String sortBy) {
		// TODO Auto-generated method stub
		Specification<RichiestaPermesso> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (StringUtils.isNotEmpty(example.getCodiceCertificato()))
				predicates.add(cb.like(cb.upper(root.get("codiceCertificato")),
						"%" + example.getCodiceCertificato().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getNote()))
				predicates.add(cb.like(cb.upper(root.get("note")), "%" + example.getNote().toUpperCase() + "%"));

			if (example.getTipoPermesso() != null)
				predicates.add(cb.equal(root.get("tipoPermesso"), example.getTipoPermesso()));

			if (example.getDipendente() != null)
				predicates.add(root.join("dipendente").in(example.getDipendente()));

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		Pageable paging = null;
		// se non passo parametri di paginazione non ne tengo conto
		if (pageSize == null || pageSize < 10)
			paging = Pageable.unpaged();
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		return richiestaPermessoRepository.findAll(specificationCriteria, paging);
	}

	@Override
	@Transactional(readOnly = true)
	public RichiestaPermesso caricaSingoloTramiteId(Long idRichiesta) {
		// TODO Auto-generated method stub
		return richiestaPermessoRepository.findById(idRichiesta).orElse(null);
	}

	@Override
	@Transactional
	public void rimuoviTramiteId(Long idRichiesta) {
		// TODO Auto-generated method stub

		RichiestaPermesso daEliminare = richiestaPermessoRepository.findByIdEager(idRichiesta);

		System.out.println(daEliminare.getDipendente().getRichiestaPermessi());
//		for (RichiestaPermesso richiestaItem : daEliminare.getDipendente().getRichiestaPermessi()) {
//			if (richiestaItem.getId() == idRichiesta)
//				daEliminare.getDipendente().getRichiestaPermessi().remove(richiestaItem);
//		}

		attachmentRepository.delete(daEliminare.getAttachment());

		Messaggio messaggio = messaggioService.findByRichiestaPermesso_Id(idRichiesta);

		messaggioService.rimuoviMessaggio(messaggio);

		richiestaPermessoRepository.delete(daEliminare);
	}

	@Override
	@Transactional(readOnly = true)
	public RichiestaPermesso findByIdEager(Long id) {
		return richiestaPermessoRepository.findByIdEager(id);
	}

}
