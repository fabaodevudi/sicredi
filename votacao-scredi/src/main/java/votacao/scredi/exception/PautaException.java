package votacao.scredi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PautaException extends RuntimeException {

	private static final long serialVersionUID = -6208878408950874713L;
	
	public PautaException(String message) {
		super(message);
	}

}
