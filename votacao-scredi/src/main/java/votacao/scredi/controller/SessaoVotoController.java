package votacao.scredi.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import votacao.scredi.dto.VotoDTO;
import votacao.scredi.exception.PautaNaoExisteException;
@RestController
@RequestMapping(path = "/sessoes", produces = MediaType.APPLICATION_JSON_VALUE)
public interface SessaoVotoController {
	
	@PostMapping
	ResponseEntity<?> criar(@RequestParam Long idPauta) throws PautaNaoExisteException;

	@GetMapping
	ResponseEntity<?> listar() throws Exception;		
	
	@GetMapping("/{id}")
	ResponseEntity<?> obter(@PathVariable Long id) throws Exception;
	
	@PostMapping("/{id}/voto")
	ResponseEntity<?> votar(@PathVariable Long id, @RequestBody VotoDTO voto);

}
