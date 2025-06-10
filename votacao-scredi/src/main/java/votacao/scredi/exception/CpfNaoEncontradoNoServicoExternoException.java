package votacao.scredi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CpfNaoEncontradoNoServicoExternoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CpfNaoEncontradoNoServicoExternoException(String message) {
        super(message);
    }
}
