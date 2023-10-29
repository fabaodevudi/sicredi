package votacao.scredi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import votacao.scredi.entity.Associado;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void criar(Associado associado) {
		rep.save(associado);		
	}

	@Override
	public void deletar(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Associado obterPorCpf(String cpf) {
		// TODO Auto-generated method stub
		return null;
	}

}
