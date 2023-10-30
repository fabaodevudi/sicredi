package votacao.scredi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class AssociadoExisteException extends RuntimeException {	

	private static final long serialVersionUID = -5366887563187735339L;

	public AssociadoExisteException(String message) {
		super(message);
	}

}
