package votacao.scredi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import votacao.scredi.entity.Associado;


@Service
public interface AssociadoService {
	List<Associado> listar();
	Associado obterPorId(Long id);
	void criar(Associado associado);
	void deletar(Long id);
	Associado obterPorCpf(String cpf);
}
