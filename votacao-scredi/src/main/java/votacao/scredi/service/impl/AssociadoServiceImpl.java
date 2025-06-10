package votacao.scredi.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import votacao.scredi.dto.AssociadoDTO;
import votacao.scredi.entity.Associado;
import votacao.scredi.exception.AssociadoNaoExisteException;
import votacao.scredi.exception.AssociadoExisteException;
import votacao.scredi.repository.AssociadoRepository;
import votacao.scredi.service.AssociadoService;

@Service
public class AssociadoServiceImpl implements AssociadoService {

	@Autowired
	AssociadoRepository rep;
	
	@Override
	public List<AssociadoDTO> listar() {
		List<Associado> lista = (List<Associado>) rep.findAll();

		return lista.stream()
				.map(AssociadoDTO::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public Associado obterPorId(Long id) {
		return rep.findById(id).orElseThrow(() -> new AssociadoNaoExisteException("Associado inexistente"));
	}

	@Override
	public void criar(AssociadoDTO associado) {
		rep.findByCpf(associado.getCpf()).ifPresent(item -> {
			throw new AssociadoExisteException("Associado já existente com Cpf: " + associado.getCpf());
        });		
		
		rep.save(AssociadoDTO.fromDTO(associado));
	}

	@Override
	public void deletar(Long id) {		
		obterPorId(id);
		rep.deleteById(id);		
	}

	@Override
	public AssociadoDTO obterPorCpf(String cpf) {
		Optional<Associado> associado = rep.findByCpf(cpf);
		
		if (!associado.isPresent()) {
			throw new AssociadoNaoExisteException("Usuário inexistente");
		}
		
		return AssociadoDTO.fromEntity(associado.get());
	}

}
