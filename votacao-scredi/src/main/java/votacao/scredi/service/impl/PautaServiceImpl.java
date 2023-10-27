package votacao.scredi.service.impl;

import java.util.List;

import votacao.scredi.entity.Pauta;
import votacao.scredi.exception.PautaException;
import votacao.scredi.repository.PautaRepository;
import votacao.scredi.service.PautaService;


public class PautaServiceImpl implements PautaService {
	private PautaRepository pautaRepository;

	@Override
    public List<Pauta> listar() {
        return (List<Pauta>) pautaRepository.findAll();
    }

    @Override
    public Pauta obterPorId(Long id) {
        return pautaRepository.findById(id).orElseThrow(() -> new PautaException("Pauta não existe"));
    }

    @Override
    public void criar(Pauta pauta) {
        pautaRepository.findByTitulo(pauta.getTitulo()).ifPresent(p -> {
            throw new PautaException("Pauta já existente com Titulo: " + pauta.getTitulo());
        });
        pautaRepository.save(pauta);
    }

    @Override
    public void deletar(Long id) {
    	obterPorId(id);
        pautaRepository.deleteById(id);
    }
    
}
