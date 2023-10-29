package votacao.scredi.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import votacao.scredi.dto.AssociadoDTO;
import votacao.scredi.exception.AssociadoException;

@RestController
@RequestMapping(path = "/associados", produces = MediaType.APPLICATION_JSON_VALUE)
public interface AssociadoController {
	
	@PostMapping
	ResponseEntity<?> criar(@RequestBody AssociadoDTO associado) throws AssociadoException;

	@GetMapping
	ResponseEntity<?> listar() throws AssociadoException;

}
