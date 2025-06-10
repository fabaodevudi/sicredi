package votacao.scredi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import votacao.scredi.dto.RespostaValidacaoCpfDTO;
import votacao.scredi.enumerate.StatusValidacaoCpfEnum;
import votacao.scredi.exception.handler.ErrorDetalhes;

import java.util.Random;

@RestController
@RequestMapping(path = "/servico-validacao-cpf-externo", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Validação de CPF Externa (FAKE)", description = "Endpoint para simular um serviço externo de validação de CPF.")
public class ServicoValidacaoCpfController {

    private final Random random = new Random();

    @Operation(summary = "Simula a validação de CPF externo",
            description = "Retorna aleatoriamente se um CPF é válido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CPF validado com sucesso (pode ou não votar)",
                    content = @Content(schema = @Schema(implementation = RespostaValidacaoCpfDTO.class))),
            @ApiResponse(responseCode = "404", description = "CPF inválido ou não encontrado no serviço externo",
                    content = @Content(schema = @Schema(implementation = ErrorDetalhes.class)))
    })
    @GetMapping("/{cpf}")
    public ResponseEntity<RespostaValidacaoCpfDTO> validarCpf(@Parameter(description = "CPF a ser validado") @PathVariable String cpf) {

        if (random.nextInt(10) == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        RespostaValidacaoCpfDTO resposta = new RespostaValidacaoCpfDTO();
        if (random.nextBoolean()) {
            resposta.setStatus(StatusValidacaoCpfEnum.HABILITADO_PARA_VOTAR);
        } else {
            resposta.setStatus(StatusValidacaoCpfEnum.NAO_HABILITADO_PARA_VOTAR);
        }
        return ResponseEntity.ok(resposta);
    }
}