package votacao.scredi.builders;

import lombok.Getter;
import votacao.scredi.entity.Sessao;

import java.time.LocalDateTime;

@Getter
public class SessaoBuilder {
	
	private static final LocalDateTime INICIO = LocalDateTime.now();
    private static final LocalDateTime FIM = INICIO.plusMinutes(1);
    
    private Sessao sessao;
    
    public static SessaoBuilder abreSessaoPautaAumentoSalario() {
    	SessaoBuilder sessao = new SessaoBuilder();
    	sessao.sessao = new Sessao();
    	sessao.sessao.setInicioSessao(INICIO);
    	sessao.sessao.setFinalSessao(FIM);
    	sessao.sessao.setPauta(PautaBuilder.pautaAumentoSalario().getPauta());
    	sessao.sessao.setId(1L);
    	
    	return sessao;
    }

}
