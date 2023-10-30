package votacao.scredi.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import votacao.scredi.exception.AssociadoNaoExisteException;
import votacao.scredi.exception.AssociadoExisteException;
import votacao.scredi.exception.PautaException;
import votacao.scredi.exception.SessaoException;
import votacao.scredi.exception.VotoException;

@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler(AssociadoExisteException.class)
	public ResponseEntity<?> handleException(AssociadoExisteException ex) {
		ErrorDetalhes detalhes = new ErrorDetalhes();
		detalhes.setMessage(ex.getMessage());
		detalhes.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
		return new ResponseEntity<ErrorDetalhes>(detalhes, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(AssociadoNaoExisteException.class)
	public ResponseEntity<?> handleException(AssociadoNaoExisteException ex) {
		ErrorDetalhes detalhes = new ErrorDetalhes();
		detalhes.setMessage(ex.getMessage());
		detalhes.setStatus(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ErrorDetalhes>(detalhes, HttpStatus.NOT_FOUND);
	}	
	
}
