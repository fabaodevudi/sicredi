package votacao.scredi.builders;

import votacao.scredi.entity.Pauta;

public class PautaBuilder {
	
	private Pauta pauta;
	
	public static PautaBuilder pautaAumentoSalario() {
		PautaBuilder pauta = new PautaBuilder();
		pauta.pauta.setTitulo("Aumnento Salário");
		pauta.pauta.setDescricao("Pauta sobre aumento de salário dos colaboradores");
		pauta.pauta.setId(1L);
		return pauta;		
	}
	
	

}
