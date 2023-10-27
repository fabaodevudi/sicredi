package votacao.scredi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VotoException extends RuntimeException {
	
	private static final long serialVersionUID = -4569261580058996260L;

	public VotoException(String message) {
		super(message);
	}

}
