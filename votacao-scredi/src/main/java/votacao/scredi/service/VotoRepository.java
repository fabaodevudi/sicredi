package votacao.scredi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import votacao.scredi.entity.Pauta;
import votacao.scredi.entity.Voto;

@Service
public interface VotoRepository {
	List<Voto> listar();
	Pauta obterPorId(Long id);
	void criar(Pauta pauta);
	void deletar(Long id);
}
