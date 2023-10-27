package votacao.scredi.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import votacao.scredi.exception.AssociadoException;
import votacao.scredi.exception.PautaException;
import votacao.scredi.exception.SessaoException;
import votacao.scredi.exception.VotoException;

@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler(AssociadoException.class)
	public ResponseEntity<?> handleAutorizacaoException(AssociadoException ex) {
		ErrorDetalhes detalhes = new ErrorDetalhes();
		detalhes.setMessage(ex.getMessage());
		detalhes.setStatus(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ErrorDetalhes>(detalhes, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(VotoException.class)
	public ResponseEntity<?> handleAutorizacaoException(VotoException ex) {
		ErrorDetalhes detalhes = new ErrorDetalhes();
		detalhes.setMessage(ex.getMessage());
		detalhes.setStatus(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ErrorDetalhes>(detalhes, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(SessaoException.class)
	public ResponseEntity<?> handleProcedimentoException(SessaoException ex) {
		ErrorDetalhes detalhes = new ErrorDetalhes();
		detalhes.setMessage(ex.getMessage());
		detalhes.setStatus(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ErrorDetalhes>(detalhes, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(PautaException.class)
	public ResponseEntity<?> handleProcedimentoException(PautaException ex) {
		ErrorDetalhes detalhes = new ErrorDetalhes();
		detalhes.setMessage(ex.getMessage());
		detalhes.setStatus(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ErrorDetalhes>(detalhes, HttpStatus.NOT_FOUND);
	}
}
