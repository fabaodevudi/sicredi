package votacao.scredi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AssociadoException extends RuntimeException {	

	private static final long serialVersionUID = -5366887563187735339L;

	public AssociadoException(String message) {
		super(message);
	}

}
