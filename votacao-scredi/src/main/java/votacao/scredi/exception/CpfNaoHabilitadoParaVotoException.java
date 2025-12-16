package votacao.scredi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // Status 422 - Entidade Não Processável
public class CpfNaoHabilitadoParaVotoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CpfNaoHabilitadoParaVotoException(String message) {
        super(message);
    }
}
