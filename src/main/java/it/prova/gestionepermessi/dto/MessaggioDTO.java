package it.prova.gestionepermessi.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;

public class MessaggioDTO {

	private Long id;
	private String testo;
	private String oggetto;
	private boolean letto;
	private Date dataInserimento;
	private Date dataLettura;
	private RichiestaPermesso richiestaPermesso;

	public MessaggioDTO(Long id, String testo, String oggetto, boolean letto, Date dataInserimento, Date dataLettura,
			RichiestaPermesso richiestaPermesso) {
		super();
		this.id = id;
		this.testo = testo;
		this.oggetto = oggetto;
		this.letto = letto;
		this.dataInserimento = dataInserimento;
		this.dataLettura = dataLettura;
		this.richiestaPermesso = richiestaPermesso;
	}

	public MessaggioDTO(String testo, String oggetto, boolean letto, Date dataInserimento, Date dataLettura) {
		super();
		this.testo = testo;
		this.oggetto = oggetto;
		this.letto = letto;
		this.dataInserimento = dataInserimento;
		this.dataLettura = dataLettura;
	}

	public MessaggioDTO(Long id, String testo, String oggetto, Date dataInserimento, Date dataLettura) {
		super();
		this.id = id;
		this.testo = testo;
		this.oggetto = oggetto;
		this.dataInserimento = dataInserimento;
		this.dataLettura = dataLettura;
	}

	public MessaggioDTO(String testo, String oggetto, boolean letto, Date dataInserimento, Date dataLettura,
			RichiestaPermesso richiestaPermesso) {
		super();
		this.testo = testo;
		this.oggetto = oggetto;
		this.letto = letto;
		this.dataInserimento = dataInserimento;
		this.dataLettura = dataLettura;
		this.richiestaPermesso = richiestaPermesso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public boolean isLetto() {
		return letto;
	}

	public void setLetto(boolean letto) {
		this.letto = letto;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Date getDataLettura() {
		return dataLettura;
	}

	public void setDataLettura(Date dataLettura) {
		this.dataLettura = dataLettura;
	}

	public RichiestaPermesso getRichiestaPermesso() {
		return richiestaPermesso;
	}

	public void setRichiestaPermesso(RichiestaPermesso richiestaPermesso) {
		this.richiestaPermesso = richiestaPermesso;
	}

	public Messaggio buildModelFromDTO() {

		Messaggio model = new Messaggio(this.oggetto, this.testo, this.dataInserimento, this.dataLettura);

		model.setId(this.id);

		return model;
	}

	public static MessaggioDTO buildMessaggioDTOFromModel(Messaggio messaggiomodel) {

		MessaggioDTO result = new MessaggioDTO(messaggiomodel.getId(), messaggiomodel.getOggetto(),
				messaggiomodel.getTesto(), messaggiomodel.getDataInserimento(), messaggiomodel.getDataLettura());
		
		return result;
		
	}
	
	public static List<MessaggioDTO> createMessaggioDTOListFromModelList(List<Messaggio> modelListInput) {
		
		return modelListInput.stream().map(messaggioEntity -> {
			return MessaggioDTO.buildMessaggioDTOFromModel(messaggioEntity);
		}).collect(Collectors.toList());
		
	}

}
