package votacao.scredi.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import votacao.scredi.dto.PautaDTO;
import votacao.scredi.exception.PautaException;


@RestController
@RequestMapping(path = "/pautas", produces = MediaType.APPLICATION_JSON_VALUE)
public interface PautaController {

	@PostMapping
	ResponseEntity<?> create(@RequestBody PautaDTO pauta) throws PautaException;

	@GetMapping
	ResponseEntity<?> listar() throws PautaException;
	
}
