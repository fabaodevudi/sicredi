package votacao.scredi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SessaoException extends RuntimeException {

	private static final long serialVersionUID = -3862943593532640773L;

	public SessaoException(String message) {
		super(message);
	}
}
