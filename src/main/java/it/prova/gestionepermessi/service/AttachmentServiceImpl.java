package it.prova.gestionepermessi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionepermessi.model.Attachment;
import it.prova.gestionepermessi.repository.AttachmentRepository;

@Service
public class AttachmentServiceImpl implements AttachmentService{
	
	@Autowired
	private AttachmentRepository attachmentrepository;

	@Override
	public Attachment caricaSingoloAttachment(Long id) {
		// TODO Auto-generated method stub
		return attachmentrepository.findById(id).orElse(null);
	}

	@Override
	public void delete(Attachment attachment) {
		// TODO Auto-generated method stub
		attachmentrepository.delete(attachment);
	}

}
