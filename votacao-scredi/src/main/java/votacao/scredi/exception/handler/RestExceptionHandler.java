package votacao.scredi.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import votacao.scredi.exception.AssociadoExisteException;
import votacao.scredi.exception.AssociadoNaoExisteException;
import votacao.scredi.exception.PautaExisteException;
import votacao.scredi.exception.PautaNaoExisteException;
import votacao.scredi.exception.SessaoNaoExisteException;

@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler(AssociadoExisteException.class)
	public ResponseEntity<?> handleAssociadoExisteException(AssociadoExisteException ex) {
		ErrorDetalhes detalhes = new ErrorDetalhes();
		detalhes.setMessage(ex.getMessage());
		detalhes.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
		return new ResponseEntity<ErrorDetalhes>(detalhes, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(AssociadoNaoExisteException.class)
	public ResponseEntity<?> handleAssociadoNaoExisteException(AssociadoNaoExisteException ex) {
		ErrorDetalhes detalhes = new ErrorDetalhes();
		detalhes.setMessage(ex.getMessage());
		detalhes.setStatus(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ErrorDetalhes>(detalhes, HttpStatus.NOT_FOUND);
	}	
	
	@ExceptionHandler(PautaExisteException.class)
	public ResponseEntity<?> handlePautaExisteException(PautaExisteException ex) {
		ErrorDetalhes detalhes = new ErrorDetalhes();
		detalhes.setMessage(ex.getMessage());
		detalhes.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
		return new ResponseEntity<ErrorDetalhes>(detalhes, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(PautaNaoExisteException.class)
	public ResponseEntity<?> handlePautaNaoExisteException(PautaNaoExisteException ex) {
		ErrorDetalhes detalhes = new ErrorDetalhes();
		detalhes.setMessage(ex.getMessage());
		detalhes.setStatus(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ErrorDetalhes>(detalhes, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(SessaoNaoExisteException.class)
	public ResponseEntity<?> handleSessaoNaoExisteException(SessaoNaoExisteException ex) {
		ErrorDetalhes detalhes = new ErrorDetalhes();
		detalhes.setMessage(ex.getMessage());
		detalhes.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
		return new ResponseEntity<ErrorDetalhes>(detalhes, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
}
