package it.prova.gestionepermessi.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import it.prova.gestionepermessi.dto.RichiestaPermessoDTO;
import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.service.DipendenteService;
import it.prova.gestionepermessi.service.RichiestaPermessoService;

@Controller
@RequestMapping(value = "/richiestaPermesso")
public class RichiestaPermessoController {

	@Autowired
	private RichiestaPermessoService richiestaPermessoService;

	@Autowired
	private DipendenteService dipendenteService;
	
	@GetMapping("/list")
	public ModelAndView listAllRichiestePermessi() {
		ModelAndView mv = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			throw new RuntimeException("Errore!");
		}
		Dipendente dipendenteInSessione = dipendenteService.caricaTramiteUsername(auth.getName());
		List<RichiestaPermesso> richiestePermessi = richiestaPermessoService.listAllRichiestePerIdDipendente(dipendenteInSessione.getId());
		mv.addObject("list_richiesta_dipendente_attr", RichiestaPermessoDTO.createRichiestePermessiListDTOFromModelList(richiestePermessi));
		mv.setViewName("richiestaPermesso/list");
		return mv;
	}
	
	@GetMapping("/search")
	public String search(Model model) {
		
		model.addAttribute("search_richiesta_dipendente_attr",
				DipendenteDTO.createDipendenteDTOListFromModelList(dipendenteService.listAllDipendente()));
		model.addAttribute("search_richiesta_attr", new RichiestaPermessoDTO());
		
		return "richiestaPermesso/search";
	}
	
	@PostMapping("/list")
	public String list(RichiestaPermessoDTO richiestaPermesso, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			ModelMap model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			throw new RuntimeException("Errore!");
		}
		Dipendente dipendenteInSessione = dipendenteService.caricaTramiteUsername(auth.getName());
		
		richiestaPermesso.setDipendenteId(dipendenteInSessione.getId());
		
		List<RichiestaPermesso> listaRichieste =  richiestaPermessoService.findByExample(richiestaPermesso.buildRichiestaPermessoFromModel(), pageNo, pageSize, sortBy).getContent();
		
		model.addAttribute("list_richiesta_dipendente_attr", RichiestaPermessoDTO.createRichiestePermessiListDTOFromModelList(listaRichieste));
		return "richiestaPermesso/list";
	}

	@GetMapping("/insert")
	public String create(Model model) {

		model.addAttribute("insert_richiesta_attr", new RichiestaPermessoDTO());

		return "richiestaPermesso/insert";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("insert_richiesta_attr") RichiestaPermessoDTO richiestaPermessoDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs) {

		RichiestaPermesso richiestaDaInserire = richiestaPermessoDTO.buildRichiestaPermessoFromModel();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null) {
			throw new RuntimeException("Errore1!");
		}
		
		Dipendente dipendenteInsessione = dipendenteService.caricaTramiteUsername(auth.getName());
		if (dipendenteInsessione == null) {
			throw new RuntimeException("Errore2!");
		}
		
		richiestaDaInserire.setDipendente(dipendenteInsessione);
		richiestaPermessoService.inserisciNuovo(richiestaDaInserire, richiestaPermessoDTO.getGiornoSingolo(),
				richiestaPermessoDTO.getAttachment());
		
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/richiestaPermesso/list";
	}
	
	@GetMapping("/show/{idRichiestaPermesso}")
	public String showUtente(@PathVariable(required = true) Long idRichiestaPermesso, Model model) {
		
		model.addAttribute("show_richiesta_attr", richiestaPermessoService.caricaSingoloTramiteId(idRichiestaPermesso));
		
		return "richiestaPermesso/show";
	}

}
