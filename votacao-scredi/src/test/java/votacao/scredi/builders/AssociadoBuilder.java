package votacao.scredi.builders;

import votacao.scredi.entity.Associado;

public class AssociadoBuilder  {

	private Associado associado;
	
	public static AssociadoBuilder fabaoId1() {
		AssociadoBuilder builder = new AssociadoBuilder();
		builder.associado = new Associado();
		builder.associado.setId(1L);
		builder.associado.setNome("Fabao");
		builder.associado.setCpf("07474748690");
		return builder;
	}
	
	public static AssociadoBuilder fabaoSemId() {		
		AssociadoBuilder builder = new AssociadoBuilder();
		builder.associado = new Associado();
		builder.associado.setNome("Fabao");
		builder.associado.setCpf("07474748690");
		return builder;
	}
	
	public static AssociadoBuilder pedroId2() {
		AssociadoBuilder builder = new AssociadoBuilder();
		builder.associado = new Associado();
		builder.associado.setId(2L);
		builder.associado.setNome("Ana");
		builder.associado.setCpf("70112651631");
		return builder;
	}
	
	public Associado getAssociado() {
		return associado;		
	}
}
