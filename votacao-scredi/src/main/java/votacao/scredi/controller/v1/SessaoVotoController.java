package votacao.scredi.controller.v1;

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
import votacao.scredi.dto.ResultadoVotacaoDTO;
import votacao.scredi.dto.SessaoDTO;
import votacao.scredi.dto.VotoDTO;
import votacao.scredi.exception.PautaNaoExisteException;
import votacao.scredi.exception.handler.ErrorDetalhes;
import votacao.scredi.service.SessaoService;


@RestController
@RequestMapping(path = "/v1/sessoes", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Sessões de Votação e Votos - V1", description = "Operações relacionadas a sessões de votação e registro de votos (Versão 1)")
public class SessaoVotoController {

	@Autowired
	SessaoService service;

	@Operation(summary = "Abre uma nova sessão de votação para uma pauta", description = "Cria uma sessão de votação, associando-a a uma pauta existente. A sessão tem duração padrão de 1 minuto, ou um tempo determinado.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Sessão criada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Pauta não encontrada",
					content = @Content(schema = @Schema(implementation = ErrorDetalhes.class)))
	})
	@PostMapping
	ResponseEntity<?> criar(@Parameter(description = "ID da pauta para a qual a sessão será aberta") @RequestParam Long idPauta,
							@Parameter(description = "Duração da sessão em minutos (padrão: 1 minuto)") @RequestParam(required = false) Long duracaoMinutos) throws PautaNaoExisteException {
		PautaDTO dto = new PautaDTO();
		dto.setId(idPauta);
		service.criar(dto, duracaoMinutos);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "Lista todas as sessões de votação", description = "Retorna uma lista de todas as sessões cadastradas.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de sessões retornada com sucesso",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = SessaoDTO.class))) // Schema para lista de sessões
	})
	@GetMapping
	ResponseEntity<?> listar() throws Exception {
		return ResponseEntity.ok(service.listar());
	}

	@Operation(summary = "Busca uma sessão de votação por ID", description = "Retorna uma sessão específica com base no ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sessão encontrada com sucesso",
					content = @Content(schema = @Schema(implementation = SessaoDTO.class))), // Schema para sessão única
			@ApiResponse(responseCode = "404", description = "Sessão não encontrada",
					content = @Content(schema = @Schema(implementation = ErrorDetalhes.class)))
	})
	@GetMapping("/{id}")
	ResponseEntity<?> obter(@Parameter(description = "ID da sessão a ser buscada") @PathVariable Long id) throws Exception {
		return ResponseEntity.ok(service.obterPorId(id));
	}

	@Operation(summary = "Registra um voto em uma sessão", description = "Registra o voto de um associado em uma sessão de votação específica.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Voto registrado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Sessão encerrada ou associado já votou nesta sessão",
					content = @Content(schema = @Schema(implementation = ErrorDetalhes.class))),
			@ApiResponse(responseCode = "404", description = "Sessão ou associado não encontrado",
					content = @Content(schema = @Schema(implementation = ErrorDetalhes.class)))
	})
	@PostMapping("/{id}/voto")
	ResponseEntity<?> votar(@Parameter(description = "ID da sessão para registrar o voto") @PathVariable Long id,
							@RequestBody VotoDTO voto) {
		service.votar(id, VotoDTO.fromDTO(voto));
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "Consolida os votos de uma sessão", description = "Retorna o resultado da votação (total de votos Sim e Não) para uma sessão específica.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Resultado da votação retornado com sucesso",
					content = @Content(schema = @Schema(implementation = ResultadoVotacaoDTO.class))),
			@ApiResponse(responseCode = "404", description = "Sessão não encontrada",
					content = @Content(schema = @Schema(implementation = ErrorDetalhes.class)))
	})
	@GetMapping("/{id}/resultado")
	ResponseEntity<ResultadoVotacaoDTO> obterResultadoVotacao(@Parameter(description = "ID da sessão para consolidar os votos") @PathVariable Long id) {
		ResultadoVotacaoDTO resultado = service.consolidarVotos(id);
		return ResponseEntity.ok(resultado);
	}

}