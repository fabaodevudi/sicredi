package votacao.scredi.builders;

import lombok.Getter;
import votacao.scredi.entity.Voto;

@Getter
public class VotoBuilder {
	
	private Voto voto;
	
	public static VotoBuilder votoSim() {
		VotoBuilder voto = new VotoBuilder();
		voto.voto = new Voto();
		voto.voto.setAssociado(AssociadoBuilder.fabaoId1().getAssociado());
		voto.voto.setId(1L);
		voto.voto.setSessao(SessaoBuilder.abreSessaoPautaAumentoSalario().getSessao());

		return voto;
	}
	

}
