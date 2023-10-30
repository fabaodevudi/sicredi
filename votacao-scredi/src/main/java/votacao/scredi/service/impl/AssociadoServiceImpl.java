package votacao.scredi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<Associado> listar() {		
		return (List<Associado>) rep.findAll();
	}

	@Override
	public Associado obterPorId(Long id) {
		return rep.findById(id).orElseThrow(() -> new AssociadoNaoExisteException("Associado inexistente"));
	}

	@Override
	public void criar(Associado associado) {
		rep.findByCpf(associado.getCpf()).ifPresent(item -> {
			throw new AssociadoExisteException("Associado já existente com Cpf: " + associado.getCpf());
        });		
		
		rep.save(associado);		
	}

	@Override
	public void deletar(Long id) {		
		obterPorId(id);
		rep.deleteById(id);		
	}

	@Override
	public Associado obterPorCpf(String cpf) {
		Optional<Associado> associado = rep.findByCpf(cpf);
		
		if (!associado.isPresent()) {
			throw new AssociadoNaoExisteException("Usuário inexistente");
		}
		
		return associado.get();
	}

}
