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
import votacao.scredi.dto.PautaDTO;
import votacao.scredi.exception.PautaException;
import votacao.scredi.exception.PautaExisteException;
import votacao.scredi.exception.handler.ErrorDetalhes;
import votacao.scredi.service.PautaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/pautas", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Pautas", description = "Operações relacionadas a Pautas de votação")
public class PautaController {

	@Autowired
	private PautaService service;

	@Operation(summary = "Cria uma nova pauta", description = "Cria uma nova pauta para votação.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Pauta criada com sucesso"),
			@ApiResponse(responseCode = "422", description = "Pauta já existente com o título informado",
					content = @Content(schema = @Schema(implementation = ErrorDetalhes.class)))
	})
	@PostMapping
	ResponseEntity<?> criar(@RequestBody PautaDTO pauta) throws PautaExisteException {
		service.criar(PautaDTO.fromDTO(pauta));
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "Lista todas as pautas", description = "Retorna uma lista de todas as pautas cadastradas.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de pautas retornada com sucesso",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = PautaDTO.class)))
	})
	@GetMapping
	ResponseEntity<?> listar() throws PautaException {
		List<PautaDTO> lista = service.listar().stream().map(item -> PautaDTO.fromEntity(item)).collect(Collectors.toList());
		return ResponseEntity.ok(lista);
	}

	@Operation(summary = "Deleta uma pauta por ID", description = "Deleta uma pauta do sistema com base no ID fornecido.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Pauta deletada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Pauta não encontrada",
					content = @Content(schema = @Schema(implementation = ErrorDetalhes.class)))
	})
	@DeleteMapping("/{id}")
	ResponseEntity<?> deletar(@Parameter(description = "ID da pauta a ser deletada") @PathVariable Long id) throws PautaException {
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Busca uma pauta por ID", description = "Retorna uma pauta específica com base no ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Pauta encontrada com sucesso",
					content = @Content(schema = @Schema(implementation = PautaDTO.class))),
			@ApiResponse(responseCode = "404", description = "Pauta não encontrada",
					content = @Content(schema = @Schema(implementation = ErrorDetalhes.class)))
	})
	@GetMapping("/{id}")
	ResponseEntity<?> obter(@Parameter(description = "ID da pauta a ser buscada") @PathVariable Long id) throws PautaException {
		return ResponseEntity.ok(PautaDTO.fromEntity(service.obterPorId(id)));
	}

}