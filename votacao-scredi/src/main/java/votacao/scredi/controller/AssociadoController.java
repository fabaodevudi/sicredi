package votacao.scredi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import votacao.scredi.dto.AssociadoDTO;
import votacao.scredi.exception.AssociadoExisteException;
import votacao.scredi.exception.AssociadoNaoExisteException;
import votacao.scredi.exception.handler.ErrorDetalhes;
import votacao.scredi.service.AssociadoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/associados", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Associados", description = "Operações relacionadas a Associados")
public class AssociadoController {

	@Autowired
	AssociadoService service;

	@Operation(summary = "Cria um novo associado", description = "Cria um novo associado no sistema.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Associado criado com sucesso"),
			@ApiResponse(responseCode = "422", description = "Associado já existente com o CPF informado",
					content = @Content(schema = @Schema(implementation = ErrorDetalhes.class)))
	})
	@PostMapping
	ResponseEntity<?> criar(@RequestBody AssociadoDTO associado) throws AssociadoExisteException {
		service.criar(AssociadoDTO.fromDTO(associado));
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "Lista todos os associados", description = "Retorna uma lista de todos os associados cadastrados.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de associados retornada com sucesso",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = AssociadoDTO.class)))
	})
	@GetMapping
	ResponseEntity<?> listar() throws AssociadoExisteException {
		List<AssociadoDTO> lista = service.listar().stream().map(item -> AssociadoDTO.fromEntity(item)).collect(Collectors.toList());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@Operation(summary = "Deleta um associado por ID", description = "Deleta um associado do sistema com base no ID fornecido.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Associado deletado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Associado não encontrado",
					content = @Content(schema = @Schema(implementation = ErrorDetalhes.class)))
	})
	@DeleteMapping("/{id}")
	ResponseEntity<?> deletar(@Parameter(description = "ID do associado a ser deletado") @PathVariable Long id) throws AssociadoNaoExisteException {
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Busca um associado por CPF", description = "Retorna um associado específico com base no CPF.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Associado encontrado com sucesso",
					content = @Content(schema = @Schema(implementation = AssociadoDTO.class))),
			@ApiResponse(responseCode = "404", description = "Associado não encontrado",
					content = @Content(schema = @Schema(implementation = ErrorDetalhes.class)))
	})
	@GetMapping("/buscar/{cpf}")
	ResponseEntity<?> obterPorCPF(@Parameter(description = "CPF do associado a ser buscado") @PathVariable String cpf) throws AssociadoNaoExisteException {
		return new ResponseEntity<>(service.obterPorCpf(cpf), HttpStatus.OK);
	}
}