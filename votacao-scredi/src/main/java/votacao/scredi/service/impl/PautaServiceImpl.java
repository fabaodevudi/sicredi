package votacao.scredi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import votacao.scredi.dto.PautaDTO;
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
    public List<PautaDTO> listar() {
        List<Pauta> pautas = (List<Pauta>) pautaRepository.findAll();

        return pautas.stream()
                .map(PautaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public PautaDTO obterPorId(Long id) {
        Pauta pauta = pautaRepository.findById(id).orElseThrow(() -> new PautaNaoExisteException("Pauta não existe"));
        return PautaDTO.fromEntity(pauta);
    }

    @Override
    public void criar(PautaDTO pauta) {
        pautaRepository.findByTitulo(pauta.getTitulo()).ifPresent(p -> {
            throw new PautaExisteException("Pauta já existente com Titulo: " + pauta.getTitulo());
        });
        pautaRepository.save(PautaDTO.fromDTO(pauta));
    }

    @Override
    public void deletar(Long id) {
    	obterPorId(id);
        pautaRepository.deleteById(id);
    }
    
}
