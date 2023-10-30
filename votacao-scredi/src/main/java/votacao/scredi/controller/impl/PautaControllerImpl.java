package votacao.scredi.controller.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import votacao.scredi.controller.PautaController;
import votacao.scredi.dto.PautaDTO;
import votacao.scredi.exception.PautaException;
import votacao.scredi.exception.PautaExisteException;
import votacao.scredi.service.PautaService;

@Controller
public class PautaControllerImpl implements PautaController {
	
	@Autowired
	private PautaService service;

	@Override
	public ResponseEntity<?> criar(PautaDTO pauta) throws PautaExisteException {
		service.criar(PautaDTO.fromDTO(pauta));
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Override
	public ResponseEntity<?> listar() throws PautaException {
		List<PautaDTO> lista = service.listar().stream().map(item -> PautaDTO.fromEntity(item)).collect(Collectors.toList());
		return ResponseEntity.ok(lista);
	}

	@Override
	public ResponseEntity<?> deletar(Long id) throws PautaException {
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<?> obter(Long id) throws PautaException {
		return ResponseEntity.ok(PautaDTO.fromEntity(service.obterPorId(id)));
	}

	
	
}
