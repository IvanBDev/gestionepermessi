package it.prova.gestionepermessi.dto;


import java.util.Date;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import it.prova.gestionepermessi.model.StatoUtente;

public class RicercaUtenteDTO {
	
	private Long id;
	
	@NotBlank(message = "{username.notBlank}")
	@Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String username;
	
	@NotBlank(message = "{nome.notBlank}")
	@Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String nome;

	@NotBlank(message = "{cognome.notBlank}")
	@Size(min = 3, max = 25, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String cognome;
	
	private Date dateCreated;
	private StatoUtente stato;

	private Long[] ruoliIds;
	
	private DipendenteDTO dipendenteDTO;

	public RicercaUtenteDTO() {
		super();
	}

	public RicercaUtenteDTO(Long id,
			@NotBlank(message = "{username.notBlank}") @Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri") String username,
			@NotBlank(message = "{nome.notBlank}") @Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri") String nome,
			@NotBlank(message = "{cognome.notBlank}") @Size(min = 3, max = 25, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri") String cognome,
			Date dateCreated, StatoUtente stato, Long[] ruoliIds) {
		super();
		this.id = id;
		this.username = username;
		this.nome = nome;
		this.cognome = cognome;
		this.dateCreated = dateCreated;
		this.stato = stato;
		this.ruoliIds = ruoliIds;
	}

	public RicercaUtenteDTO(Long id,
			@NotBlank(message = "{username.notBlank}") @Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri") String username,
			@NotBlank(message = "{nome.notBlank}") @Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri") String nome,
			@NotBlank(message = "{cognome.notBlank}") @Size(min = 3, max = 25, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri") String cognome,
			Date dateCreated, StatoUtente stato, Long[] ruoliIds, DipendenteDTO dipendenteDTO) {
		super();
		this.id = id;
		this.username = username;
		this.nome = nome;
		this.cognome = cognome;
		this.dateCreated = dateCreated;
		this.stato = stato;
		this.ruoliIds = ruoliIds;
		this.dipendenteDTO = dipendenteDTO;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public StatoUtente getStato() {
		return stato;
	}

	public void setStato(StatoUtente stato) {
		this.stato = stato;
	}

	public Long[] getRuoliIds() {
		return ruoliIds;
	}

	public void setRuoliIds(Long[] ruoliIds) {
		this.ruoliIds = ruoliIds;
	}

	public DipendenteDTO getDipendenteDTO() {
		return dipendenteDTO;
	}

	public void setDipendenteDTO(DipendenteDTO dipendenteDTO) {
		this.dipendenteDTO = dipendenteDTO;
	}
	
	
	
}
