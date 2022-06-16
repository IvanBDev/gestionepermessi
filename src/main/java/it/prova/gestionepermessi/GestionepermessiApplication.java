package it.prova.gestionepermessi;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Ruolo;
import it.prova.gestionepermessi.model.Sesso;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.service.RuoloService;
import it.prova.gestionepermessi.service.UtenteService;

@SpringBootApplication
public class GestionepermessiApplication implements CommandLineRunner {

	@Autowired
	private RuoloService ruoloServiceInstance;

	@Autowired
	private UtenteService utenteService;

	public static void main(String[] args) {
		SpringApplication.run(GestionepermessiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", "ROLE_ADMIN"));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("BackOffice", "ROLE_BO_USER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("BackOffice", "ROLE_BO_USER"));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Dipendente", "ROLE_DIPENDENTE_USER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Dipendente", "ROLE_DIPENDENTE_USER"));
		}

		if (utenteService.findByUsername("admin") == null) {
			Utente utenteAdmin = new Utente("admin", "admin", new Date());
			utenteAdmin.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN"));

			Dipendente dipendentAdmin = new Dipendente("Ivan", "Bendotti", "BNDIVN02P05H501E", "admin@prova.it",
					new SimpleDateFormat("dd/MM/yyyy").parse("05/02/1990"),
					new SimpleDateFormat("dd/MM/yyyy").parse("25/07/2005"), Sesso.MASCHIO);
			
			dipendentAdmin.setUtente(utenteAdmin);
			utenteAdmin.setDipendente(dipendentAdmin);
			
			utenteService.inserisciECollegaDipendenteAUtente(utenteAdmin, dipendentAdmin);
			utenteService.changeUserAbilitation(utenteAdmin.getId());
		}
		
		if (utenteService.findByUsername("backoffice") == null) {
			Utente utenteAdmin = new Utente("backoffice", "backoffice", new Date());
			utenteAdmin.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("BackOffice", "ROLE_BO_USER"));

			Dipendente dipendentAdmin = new Dipendente("Kety", "Nostradu", "NSRKTY08L28H501E", "backoffice@prova.it",
					new SimpleDateFormat("dd/MM/yyyy").parse("28/08/1990"),
					new SimpleDateFormat("dd/MM/yyyy").parse("14/07/2003"), Sesso.FEMMINA);
			
			dipendentAdmin.setUtente(utenteAdmin);
			utenteAdmin.setDipendente(dipendentAdmin);
			
			utenteService.inserisciECollegaDipendenteAUtente(utenteAdmin, dipendentAdmin);
			utenteService.changeUserAbilitation(utenteAdmin.getId());
		}

	}

}
