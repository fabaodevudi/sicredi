package votacao.scredi.controller.impl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import votacao.scredi.controller.SessaoVotoController;
import votacao.scredi.dto.PautaDTO;
import votacao.scredi.dto.SessaoDTO;
import votacao.scredi.dto.VotoDTO;
import votacao.scredi.exception.PautaNaoExisteException;
import votacao.scredi.exception.SessaoNaoExisteException;
import votacao.scredi.service.SessaoService;

@Controller
public class SessaoVotoControllerImpl implements SessaoVotoController {

	@Autowired
	SessaoService service;
	
	@Override
	public ResponseEntity<?> criar(Long idPauta) throws PautaNaoExisteException {
		PautaDTO dto = new PautaDTO();
		dto.setId(idPauta);
		service.criar(PautaDTO.fromDTO(dto));
		return ResponseEntity.status(HttpStatus.CREATED).build();		
	}

	@Override
	public ResponseEntity<?> listar() throws SessaoNaoExisteException {
		  return ResponseEntity.ok(service.listar().stream().map(item -> SessaoDTO.fromEntity(item)).collect(Collectors.toList()));
	}

	@Override
	public ResponseEntity<?> obter(Long id) throws Exception {
		return ResponseEntity.ok(SessaoDTO.fromEntity(service.obterPorId(id)));
	}

	@Override
	public ResponseEntity<?> votar(Long id, VotoDTO voto) {
		return ResponseEntity.status(HttpStatus.CREATED).build();	
	}
	
}
