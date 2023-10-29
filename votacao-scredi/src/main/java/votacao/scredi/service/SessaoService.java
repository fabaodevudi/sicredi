package votacao.scredi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import votacao.scredi.entity.Pauta;
import votacao.scredi.entity.Sessao;

@Service
public interface SessaoService {
	List<Sessao> listar();
	Pauta obterPorId(Long id);
	void criar(Pauta pauta);
	void deletar(Long id);
}
