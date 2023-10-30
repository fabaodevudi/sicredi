package votacao.scredi.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import votacao.scredi.dto.PautaDTO;
import votacao.scredi.exception.AssociadoNaoExisteException;
import votacao.scredi.exception.PautaException;
import votacao.scredi.exception.PautaExisteException;

@RestController
@RequestMapping(path = "/pautas", produces = MediaType.APPLICATION_JSON_VALUE)
public interface PautaController {

	@PostMapping
	ResponseEntity<?> criar(@RequestBody PautaDTO pauta) throws PautaExisteException;

	@GetMapping
	ResponseEntity<?> listar() throws PautaException;
	
	@DeleteMapping("/{id}")
	ResponseEntity<?> deletar(@PathVariable Long id) throws PautaException;
	
	@GetMapping("/{id}")
	ResponseEntity<?> obter(@PathVariable Long id) throws PautaException;
	
}
