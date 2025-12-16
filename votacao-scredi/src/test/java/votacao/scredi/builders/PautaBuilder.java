package votacao.scredi.builders;

import votacao.scredi.entity.Pauta;

public class PautaBuilder {
	
	private Pauta pauta;
	
	public static PautaBuilder pautaAumentoSalario() {
		PautaBuilder pauta = new PautaBuilder();
		pauta.pauta = new Pauta();
		pauta.pauta.setTitulo("Aumento Salário");
		pauta.pauta.setDescricao("Pauta sobre aumento de salário dos colaboradores");
		pauta.pauta.setId(1L);
		return pauta;		
	}
	
	public static PautaBuilder pautaAumentoSalarioSemId() {
		PautaBuilder pauta = new PautaBuilder();
		pauta.pauta = new Pauta();
		pauta.pauta.setTitulo("Aumento Salário");
		pauta.pauta.setDescricao("Pauta sobre aumento de salário dos colaboradores");
		return pauta;		
	}
	
	public static PautaBuilder pautaMudancaPlanoSaude() {
		PautaBuilder pauta = new PautaBuilder();
		pauta.pauta = new Pauta();
		pauta.pauta.setTitulo("Mudar plano de saúde para operadora X");
		pauta.pauta.setDescricao("Mudar plano de saúde para operadora X");
		pauta.pauta.setId(2L);
		return pauta;		
	}
	
	public Pauta getPauta() {
		return pauta;		
	}	

}
