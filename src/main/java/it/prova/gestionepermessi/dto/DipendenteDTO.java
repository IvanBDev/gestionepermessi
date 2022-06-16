package it.prova.gestionepermessi.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.model.Sesso;
import it.prova.gestionepermessi.model.Utente;

public class DipendenteDTO {

	private Long id;

	@NotBlank(message = "{nome.notBlank}")
	@Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String nome;

	@NotBlank(message = "{cognome.notBlank}")
	@Size(min = 3, max = 25, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String cognome;

	@NotBlank(message = "{codiceFiscale.notBlank}")
	@Size(min = 16, max = 16, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String codiceFiscale;

	private String email;
	private Date dataNascita;
	private Date dataAssunzione;
	private Date dataDimissione;
	private Sesso sesso;
	private UtenteDTO utenteDTO;
	private Long[] richiestaPermessiIds;

	public DipendenteDTO() {
		super();
	}

	public DipendenteDTO(Long id,
			@NotBlank(message = "{nome.notBlank}") @Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri") String nome,
			@NotBlank(message = "{cognome.notBlank}") @Size(min = 3, max = 25, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri") String cognome,
			@NotBlank(message = "{codiceFiscale.notBlank}") @Size(min = 1, max = 16, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri") String codiceFiscale,
			@NotNull String email, @NotNull Date dataNascita, @NotNull Date dataAssunzione) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.email = email;
		this.dataNascita = dataNascita;
		this.dataAssunzione = dataAssunzione;
	}

	public DipendenteDTO(Long id,
			@NotBlank(message = "{nome.notBlank}") @Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri") String nome,
			@NotBlank(message = "{cognome.notBlank}") @Size(min = 3, max = 25, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri") String cognome,
			@NotBlank(message = "{codiceFiscale.notBlank}") @Size(min = 1, max = 16, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri") String codiceFiscale,
			@NotNull String email, @NotNull Date dataNascita, @NotNull Date dataAssunzione, @NotNull Sesso sesso,
			@NotNull UtenteDTO utenteDTO) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.email = email;
		this.dataNascita = dataNascita;
		this.dataAssunzione = dataAssunzione;
		this.sesso = sesso;
		this.utenteDTO = utenteDTO;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Date getDataAssunzione() {
		return dataAssunzione;
	}

	public void setDataAssunzione(Date dataAssunzione) {
		this.dataAssunzione = dataAssunzione;
	}

	public Date getDataDimissione() {
		return dataDimissione;
	}

	public void setDataDimissione(Date dataDimissione) {
		this.dataDimissione = dataDimissione;
	}

	public Sesso getSesso() {
		return sesso;
	}

	public void setSesso(Sesso sesso) {
		this.sesso = sesso;
	}

	public UtenteDTO getUtenteDTO() {
		return utenteDTO;
	}

	public void setUtenteDTO(UtenteDTO utenteDTO) {
		this.utenteDTO = utenteDTO;
	}

	public Long[] getRichiestaPermessiIds() {
		return richiestaPermessiIds;
	}

	public void setRichiestaPermessiIds(Long[] richiestaPermessiIds) {
		this.richiestaPermessiIds = richiestaPermessiIds;
	}

//	public Dipendente buildDipendenteModel(boolean includeRichiestaPermesso) {
//
//		Dipendente result = new Dipendente(this.id, this.nome, this.cognome, this.codiceFiscale, this.email,
//				this.dataNascita, this.dataAssunzione, this.sesso, this.utente);
//
//		if (includeRichiestaPermesso && richiestaPermessiIds != null)
//			result.setRichiestaPermessi(Arrays.asList(richiestaPermessiIds).stream()
//					.map(id -> new RichiestaPermesso(id)).collect(Collectors.toSet()));
//
//		return result;
//
//	}
//
//	public static DipendenteDTO buildDipendenteDTOFromModel(Dipendente dipendenteModel) {
//
//		DipendenteDTO result = new DipendenteDTO(dipendenteModel.getId(), dipendenteModel.getNome(),
//				dipendenteModel.getCognome(), dipendenteModel.getCodiceFiscale(), dipendenteModel.getEmail(),
//				dipendenteModel.getDataNascita(), dipendenteModel.getDataAssunzione(), dipendenteModel.getSesso(),
//				dipendenteModel.getUtente());
//
//		if (!dipendenteModel.getRichiestaPermessi().isEmpty())
//			result.richiestaPermessiIds = dipendenteModel.getRichiestaPermessi().stream().map(r -> r.getId())
//					.collect(Collectors.toList()).toArray(new Long[] {});
//
//		return result;
//		
//	}
//	
//	public static List<DipendenteDTO> createDipendenteDDTOListFromModelList(List<Dipendente> modelListInput) {
//		return modelListInput.stream().map(utenteEntity -> {
//			return DipendenteDTO.buildDipendenteDTOFromModel(utenteEntity);
//		}).collect(Collectors.toList());
//	}

}