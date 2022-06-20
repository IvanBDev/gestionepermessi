package it.prova.gestionepermessi.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionepermessi.dto.DipendenteDTO;
import it.prova.gestionepermessi.dto.MessaggioDTO;
import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.service.DipendenteService;
import it.prova.gestionepermessi.service.MessaggioService;

@Controller
@RequestMapping(value = "/dipendente")
public class DipendenteController {

	@Autowired
	private DipendenteService dipendenteService;
	
	@Autowired
	private MessaggioService messaggioService;

	@GetMapping
	public ModelAndView listAllDipendenti() {
		ModelAndView mv = new ModelAndView();
		List<Dipendente> utenti = dipendenteService.listAllDipendente();
		mv.addObject("dipendente_list_attribute", utenti);
		mv.setViewName("dipendente/list");
		return mv;
	}

	@GetMapping("/search")
	public String searchUtente(Model model) {
		return "dipendente/search";
	}

	@PostMapping("/list")
	public String list(Dipendente utenteExample, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			ModelMap model) {

		List<Dipendente> dipendenti = dipendenteService.findByExample(utenteExample, pageNo, pageSize, sortBy)
				.getContent();

		model.addAttribute("dipendente_list_attribute", DipendenteDTO.createDipendenteDTOListFromModelList(dipendenti));
		return "dipendente/list";
	}

	@GetMapping("/show/{idDipendente}")
	public String show(@PathVariable(required = true) Long idDipendente, Model model) {
		model.addAttribute("show_dipendente_attr", dipendenteService.caricaSingoloDipendente(idDipendente));
		return "dipendente/show";
	}

	@GetMapping("/insert")
	public String create(Model model) {

		model.addAttribute("insert_dipendente_attr", new DipendenteDTO());

		return "dipendente/insert";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("insert_dipendente_attr") DipendenteDTO dipendenteDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs) {
		
		if(result.hasErrors())
			return "dipendente/insert";
		
		if(dipendenteDTO.getDataNascita().after(dipendenteDTO.getDataAssunzione())) {
			result.rejectValue("dataNascita","dataNascitaMaggioreDiDataAssunzione");
			return "dipendente/insert";
		}
		
		dipendenteService.costruzioneEInserimentoDiDipendenteEUtente(dipendenteDTO.buildModelFromDTO());

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/dipendente";
	}
	
	@GetMapping("/edit/{idDipendente}")
	public String editRegista(@PathVariable(required = true) Long idDipendente, Model model) {
		model.addAttribute("edit_dipendente_attr",
				DipendenteDTO.buildDipendenteDTOFromModel(dipendenteService.caricaSingoloDipendente(idDipendente)));
		return "dipendente/edit";
	}
	
	@PostMapping("/update")
	public String updateRegista(@Valid @ModelAttribute("edit_dipendente_attr") DipendenteDTO dipendenteDTO, BindingResult result,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {

		dipendenteService.aggiornaDipendente(dipendenteDTO.buildModelFromDTO());

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/dipendente";
	}
	
	@GetMapping("/searchMessaggio")
	public String searchMessaggio(Model model) {
		return "dipendente/searchMessaggio";
	}
	
	@PostMapping("/listMessaggi")
	public String listMessaggi(Messaggio messaggioExample, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			ModelMap model) {

		List<Messaggio> messaggi = messaggioService.findByExample(messaggioExample, pageNo, pageSize, sortBy)
				.getContent();

		model.addAttribute("messaggi_list_attribute", MessaggioDTO.createMessaggioDTOListFromModelList(messaggi));
		return "dipendente/listMessaggio";
	}
	
	@GetMapping("/showMessaggio/{idMessaggio}")
	public String showMessaggio(@PathVariable(required = true) Long idMessaggio, Model model) {
		
		Messaggio messaggioVisualizzato = messaggioService.caricaSingoloMessaggio(idMessaggio);
		
		if(!messaggioVisualizzato.isLetto()) {
			messaggioVisualizzato.setLetto(true);
			messaggioVisualizzato.setDataLettura(new Date());
			messaggioService.aggiorna(messaggioVisualizzato);
		}
		
		model.addAttribute("show_messaggio_attr", messaggioVisualizzato);
			
		return "dipendente/showMessaggio";
		
	}
	
	@GetMapping("/listAllMessaggi")
	public ModelAndView listAllMessaggi() {
		ModelAndView mv = new ModelAndView();
		List<Messaggio> messaggi = messaggioService.findAllMessaggi();
		mv.addObject("messaggi_list_attribute", messaggi);
		mv.setViewName("dipendente/listMessaggio");
		return mv;
	}
	
	@GetMapping(value = "/presentiMessaggiNonLetti", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> checkPresenzaMessaggiNonLetti() {

		if (!messaggioService.findAllMessaggiNonLetti().isEmpty())
			return new ResponseEntity<String>(HttpStatus.OK);
		else
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/listNonLetti")
	public ModelAndView listAllMessaggiNonLetti() {
		ModelAndView mv = new ModelAndView();
		List<Messaggio> messaggi = messaggioService.findAllMessaggiNonLetti();
		mv.addObject("messaggiNonLetti_list_attribute", messaggi);
		mv.setViewName("dipendente/showListNonLetti");
		return mv;
	}

}
