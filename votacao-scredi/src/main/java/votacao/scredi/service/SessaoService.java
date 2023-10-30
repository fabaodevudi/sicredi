package votacao.scredi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import votacao.scredi.entity.Pauta;
import votacao.scredi.entity.Sessao;
import votacao.scredi.entity.Voto;

@Service
public interface SessaoService {
	List<Sessao> listar();
	Sessao obterPorId(Long id);
	void criar(Pauta pauta);
	void votar(Long idSessao, Voto voto);
}
