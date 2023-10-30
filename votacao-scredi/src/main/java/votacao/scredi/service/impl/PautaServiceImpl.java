package votacao.scredi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import votacao.scredi.entity.Pauta;
import votacao.scredi.exception.PautaExisteException;
import votacao.scredi.exception.PautaNaoExisteException;
import votacao.scredi.repository.PautaRepository;
import votacao.scredi.service.PautaService;

@Service
public class PautaServiceImpl implements PautaService {
	
	@Autowired
	private PautaRepository pautaRepository;

	@Override
    public List<Pauta> listar() {
        return (List<Pauta>) pautaRepository.findAll();
    }

    @Override
    public Pauta obterPorId(Long id) {
        return pautaRepository.findById(id).orElseThrow(() -> new PautaNaoExisteException("Pauta não existe"));
    }

    @Override
    public void criar(Pauta pauta) {
        pautaRepository.findByTitulo(pauta.getTitulo()).ifPresent(p -> {
            throw new PautaExisteException("Pauta já existente com Titulo: " + pauta.getTitulo());
        });
        pautaRepository.save(pauta);
    }

    @Override
    public void deletar(Long id) {
    	obterPorId(id);
        pautaRepository.deleteById(id);
    }
    
}
