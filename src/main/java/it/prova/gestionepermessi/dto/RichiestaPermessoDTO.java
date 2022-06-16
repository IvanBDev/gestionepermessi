package it.prova.gestionepermessi.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import it.prova.gestionepermessi.model.Attachment;

public class RichiestaPermessoDTO {
	
	private Long id;
	
	@NotNull
	private Date dataInizio;
	
	@NotNull
	private Date dataFine;
	
	@NotNull
	private boolean approvato;
	
	private String codiceCertificato;
	private String note;
	private Long[] dipendentiIds;
	private Attachment attachment;
	
	public RichiestaPermessoDTO() {
		super();
	}

	public RichiestaPermessoDTO(Long id, @NotNull Date dataInizio, @NotNull Date dataFine, @NotNull boolean approvato,
			String codiceCertificato, String note, Attachment attachment) {
		super();
		this.id = id;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.approvato = approvato;
		this.codiceCertificato = codiceCertificato;
		this.note = note;

		this.attachment = attachment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public boolean isApprovato() {
		return approvato;
	}

	public void setApprovato(boolean approvato) {
		this.approvato = approvato;
	}

	public String getCodiceCertificato() {
		return codiceCertificato;
	}

	public void setCodiceCertificato(String codiceCertificato) {
		this.codiceCertificato = codiceCertificato;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long[] getDipendentiIds() {
		return dipendentiIds;
	}

	public void setDipendentiIds(Long[] dipendentiIds) {
		this.dipendentiIds = dipendentiIds;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}
	
	
}
