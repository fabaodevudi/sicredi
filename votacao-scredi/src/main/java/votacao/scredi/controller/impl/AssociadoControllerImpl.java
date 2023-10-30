package votacao.scredi.controller.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import votacao.scredi.controller.AssociadoController;
import votacao.scredi.dto.AssociadoDTO;
import votacao.scredi.exception.AssociadoExisteException;
import votacao.scredi.exception.AssociadoNaoExisteException;
import votacao.scredi.service.AssociadoService;

@Controller
public class AssociadoControllerImpl implements AssociadoController {
	
	@Autowired
	AssociadoService service;

	@Override
	public ResponseEntity<?> criar(AssociadoDTO dto) throws AssociadoExisteException {
		service.criar(AssociadoDTO.fromDTO(dto));
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Override
	public ResponseEntity<?> listar() throws AssociadoExisteException {
		List<AssociadoDTO> lista = service.listar().stream().map(item -> AssociadoDTO.fromEntity(item)).collect(Collectors.toList());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deletar(Long id) throws AssociadoNaoExisteException {
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}

	
	@Override
	public ResponseEntity<?> obterPorCPF(String cpf) throws AssociadoNaoExisteException {		
		return new ResponseEntity<>(service.obterPorCpf(cpf), HttpStatus.OK);		
	}

}
