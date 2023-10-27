package votacao.scredi.exception.handler;

import lombok.Data;

@Data
public class ErrorDetalhes {
	private String message;
	private int status;
}
