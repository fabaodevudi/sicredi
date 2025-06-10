package votacao.scredi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import votacao.scredi.dto.AssociadoDTO;
import votacao.scredi.entity.Associado;


@Service
public interface AssociadoService {
	List<AssociadoDTO> listar();
	Associado obterPorId(Long id);
	void criar(AssociadoDTO associado);
	void deletar(Long id);
	AssociadoDTO obterPorCpf(String cpf);
}
