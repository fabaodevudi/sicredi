package votacao.scredi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class PautaExisteException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public PautaExisteException(String message) {
		super(message);
	}

}

