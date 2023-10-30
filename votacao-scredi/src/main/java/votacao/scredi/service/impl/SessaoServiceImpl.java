package votacao.scredi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import votacao.scredi.entity.Pauta;
import votacao.scredi.entity.Sessao;
import votacao.scredi.entity.Voto;
import votacao.scredi.exception.PautaNaoExisteException;
import votacao.scredi.exception.SessaoNaoExisteException;
import votacao.scredi.repository.SessaoRepository;
import votacao.scredi.service.AssociadoService;
import votacao.scredi.service.PautaService;
import votacao.scredi.service.SessaoService;

@Service
public class SessaoServiceImpl implements SessaoService {

	@Autowired
	SessaoRepository rep;
	
	@Autowired
	PautaService pautaRep;
	
	@Autowired
	AssociadoService associadoRep;	
	
	@Override
	public List<Sessao> listar() {
		return (List<Sessao>) rep.findAll();
	}

	@Override
	public Sessao obterPorId(Long id) {		
		return rep.findById(id).orElseThrow(() -> new SessaoNaoExisteException("Sessão não existe"));
	}

	@Override
	public void criar(Pauta pauta) {
		pautaRep.obterPorId(pauta.getId());		
		Sessao sessao = new Sessao(pauta);
		rep.save(sessao);		
	}

	@Override
	public void votar(Long idSessao, Voto voto) {
		// TODO Auto-generated method stub
		
	}

}
