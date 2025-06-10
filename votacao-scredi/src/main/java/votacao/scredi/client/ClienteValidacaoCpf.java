package votacao.scredi.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import votacao.scredi.dto.RespostaValidacaoCpfDTO;
import votacao.scredi.exception.CpfNaoEncontradoNoServicoExternoException;

@Component
public class ClienteValidacaoCpf {

    @Value("${url.servico.validacao.cpf}")
    private String urlServicoValidacaoCpf;

    private final RestTemplate restTemplate;

    public ClienteValidacaoCpf(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RespostaValidacaoCpfDTO validarCpf(String cpf) {
        String url = urlServicoValidacaoCpf + "/" + cpf;

        try {
            ResponseEntity<RespostaValidacaoCpfDTO> responseEntity =
                    restTemplate.getForEntity(url, RespostaValidacaoCpfDTO.class);

            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                return responseEntity.getBody();
            } else {
                throw new RuntimeException("Erro inesperado do serviço de validação de CPF: " + responseEntity.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CpfNaoEncontradoNoServicoExternoException("CPF " + cpf + " não encontrado ou inválido.");
            }
            throw new RuntimeException("Erro HTTP ao validar CPF: " + e.getStatusCode() + " - " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Falha na comunicação com o serviço de validação de CPF externo.", e);
        }
    }
}