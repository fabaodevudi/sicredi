package votacao.scredi.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import votacao.scredi.client.ClienteValidacaoCpf;
import votacao.scredi.dto.AssociadoDTO;
import votacao.scredi.dto.PautaDTO;
import votacao.scredi.dto.RespostaValidacaoCpfDTO;
import votacao.scredi.dto.SessaoDTO;
import votacao.scredi.entity.Associado;
import votacao.scredi.entity.Pauta;
import votacao.scredi.entity.Sessao;
import votacao.scredi.entity.Voto;
import votacao.scredi.enumerate.StatusValidacaoCpfEnum;
import votacao.scredi.exception.CpfNaoHabilitadoParaVotoException;
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

	@Autowired
	private ClienteValidacaoCpf clienteValidacaoCpf;
	
	@Override
	public List<SessaoDTO> listar() {
		List<Sessao> lista = (List<Sessao>) rep.findAll();
		return lista.stream()
				.map(SessaoDTO::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public SessaoDTO obterPorId(Long id) {
		Sessao sessao = rep.findById(id).orElseThrow(() -> new SessaoNaoExisteException("Sessão não existe"));
		return SessaoDTO.fromEntity(sessao);
	}

	@Override
	public void criar(PautaDTO pauta) {
		pautaRep.obterPorId(pauta.getId());
		Sessao sessao = new Sessao(PautaDTO.fromDTO(pauta));
		rep.save(sessao);		
	}

	@Override
	public void votar(Long idSessao, Voto voto) {

		LocalDateTime fim = LocalDateTime.now();
		Sessao sessao = SessaoDTO.fromDTO(obterPorId(idSessao));
		
		if (fim.isAfter(sessao.getFinalSessao())) {
			throw new SessaoException("Sessão já está encerrada");
		}
		
		Associado associado = AssociadoDTO.fromDTO(associadoRep.obterPorCpf(voto.getAssociado().getCpf()));

		RespostaValidacaoCpfDTO respostaValidacao = clienteValidacaoCpf.validarCpf(associado.getCpf());

		if (respostaValidacao.getStatus().equals(StatusValidacaoCpfEnum.NAO_HABILITADO_PARA_VOTAR)) {
			throw new CpfNaoHabilitadoParaVotoException("Associado com CPF " + associado.getCpf() + " não está habilitado para votar.");
		}
		
		if (sessao.getVotos() != null && sessao.getVotos().stream().anyMatch(item -> item.getAssociado().getCpf().equals(associado.getCpf()))) {			
			throw new SessaoException("O associado já votou.");
		}

		
		voto.setSessao(sessao);
		voto.setAssociado(associado);
		votoRep.save(voto);				
	}

}
