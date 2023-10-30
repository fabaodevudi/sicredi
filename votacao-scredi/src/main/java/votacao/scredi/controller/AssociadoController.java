package votacao.scredi.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import votacao.scredi.dto.AssociadoDTO;
import votacao.scredi.exception.AssociadoExisteException;
import votacao.scredi.exception.AssociadoNaoExisteException;

@RestController
@RequestMapping(path = "/associados", produces = MediaType.APPLICATION_JSON_VALUE)
public interface AssociadoController {
	
	@PostMapping
	ResponseEntity<?> criar(@RequestBody AssociadoDTO associado) throws AssociadoExisteException;

	@GetMapping
	ResponseEntity<?> listar() throws AssociadoExisteException;

	@DeleteMapping("/{id}")
	ResponseEntity<?> deletar(@PathVariable Long id) throws AssociadoNaoExisteException;
	
	@GetMapping("/{cpf}")
	ResponseEntity<?> obterPorCPF(@PathVariable String cpf) throws AssociadoNaoExisteException;
}
