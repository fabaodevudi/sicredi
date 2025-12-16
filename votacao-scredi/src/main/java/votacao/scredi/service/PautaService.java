package votacao.scredi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import votacao.scredi.dto.PautaDTO;
import votacao.scredi.entity.Pauta;

@Service
public interface PautaService {
	 List<PautaDTO> listar();
	 PautaDTO obterPorId(Long id);
	 void criar(PautaDTO pauta);
	 void deletar(Long id);
}

