package votacao.scredi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import votacao.scredi.entity.Pauta;

@Service
public interface PautaService {
	 List<Pauta> listar();
	 Pauta obterPorId(Long id);
	 void criar(Pauta pauta);
	 void deletar(Long id);
}

