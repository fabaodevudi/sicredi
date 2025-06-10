package votacao.scredi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import votacao.scredi.dto.PautaDTO;
import votacao.scredi.dto.SessaoDTO;
import votacao.scredi.entity.Pauta;
import votacao.scredi.entity.Sessao;
import votacao.scredi.entity.Voto;

@Service
public interface SessaoService {
	List<SessaoDTO> listar();
	SessaoDTO obterPorId(Long id);
	void criar(PautaDTO pauta);
	void votar(Long idSessao, Voto voto);
}
