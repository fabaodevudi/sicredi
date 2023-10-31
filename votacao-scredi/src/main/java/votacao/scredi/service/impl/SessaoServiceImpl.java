package votacao.scredi.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import votacao.scredi.entity.Associado;
import votacao.scredi.entity.Pauta;
import votacao.scredi.entity.Sessao;
import votacao.scredi.entity.Voto;
import votacao.scredi.exception.SessaoException;
import votacao.scredi.exception.SessaoNaoExisteException;
import votacao.scredi.repository.SessaoRepository;
import votacao.scredi.repository.VotoRepository;
import votacao.scredi.service.AssociadoService;
import votacao.scredi.service.PautaService;
import votacao.scredi.service.SessaoService;

@Service
public class SessaoServiceImpl implements SessaoService {

	@Autowired
	private SessaoRepository rep;
	
	@Autowired
	private VotoRepository votoRep;
	
	@Autowired
	private PautaService pautaRep;
	
	@Autowired
	private AssociadoService associadoRep;	
	
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
		LocalDateTime fim = LocalDateTime.now();
		Sessao sessao = obterPorId(idSessao);
		
		if (fim.isAfter(sessao.getFinalSessao())) {
			throw new SessaoException("Sessão já está encerrada");
		}
		
		Associado associado = associadoRep.obterPorCpf(voto.getAssociado().getCpf());		
		
		if (sessao.getVotos() != null && sessao.getVotos().stream().anyMatch(item -> item.getAssociado().getCpf().equals(associado.getCpf()))) {			
			throw new SessaoException("O associado já votou.");
		}
		
		voto.setSessao(sessao);
		voto.setAssociado(associado);
		votoRep.save(voto);				
	}

}
