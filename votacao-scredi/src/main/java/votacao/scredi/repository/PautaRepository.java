package votacao.scredi.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import votacao.scredi.entity.Pauta;

@Repository
public interface PautaRepository extends CrudRepository<Pauta, Long> {
	Optional<Pauta> findByTitulo(String titulo);

}
