package it.prova.gestionepermessi.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.service.UtenteService;

@Controller
@RequestMapping(value = "/cambiaPassword")
public class ResetPasswordController {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordencoder;

	@GetMapping("/resetform")
	public String reset() {
		return "resetpassword";
	}

	@RequestMapping(value = "/confirmResetPassword", method = { RequestMethod.POST })
	public String confirmResetPassword(@RequestParam(value = "vecchiaPassword", required = true) String vecchiaPassword,
			@RequestParam(value = "nuovaPassword", required = true) String nuovaPassword,
			@RequestParam(value = "confermaNPassword", required = true) String confermaNPassword,
			RedirectAttributes redirectAttrs) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			redirectAttrs.addFlashAttribute("errorMessage", "Errore generale");
			return "index";
		}

		Utente utente = utenteService.findByUsername(auth.getName());
		if (utente == null || !(passwordEncoder.matches(utente.getPassword(), vecchiaPassword))) {
			redirectAttrs.addFlashAttribute("errorMessage", "La nuova password e'uguale alla vecchia");
			return "index";
		}

		if (nuovaPassword.equals(confermaNPassword)) {
			utente.setPassword(passwordEncoder.encode(nuovaPassword));
			utenteService.aggiorna(utente);
		} else {
			redirectAttrs.addFlashAttribute("errorMessage", "La nuova password e la conferma non combaciano");
			return "index";
		}

		return "redirect:/logout";
	}

}
