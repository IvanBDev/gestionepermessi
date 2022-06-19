package it.prova.gestionepermessi.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.service.DipendenteService;

@Controller
@RequestMapping(value = "/dipendente")
public class DipendenteController {

	@Autowired
	private DipendenteService dipendenteService;

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
	public String listUtenti(Dipendente utenteExample, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			ModelMap model) {

		List<Dipendente> dipendenti = dipendenteService.findByExample(utenteExample, pageNo, pageSize, sortBy)
				.getContent();

		model.addAttribute("dipendente_list_attribute", DipendenteDTO.createDipendenteDTOListFromModelList(dipendenti));
		return "dipendente/list";
	}

	@GetMapping("/show/{idDipendente}")
	public String showUtente(@PathVariable(required = true) Long idDipendente, Model model) {
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

}
