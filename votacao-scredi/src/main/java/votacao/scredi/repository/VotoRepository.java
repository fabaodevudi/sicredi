package votacao.scredi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import votacao.scredi.entity.Voto;

@Repository
public interface VotoRepository extends CrudRepository<Voto, Long> {

}
