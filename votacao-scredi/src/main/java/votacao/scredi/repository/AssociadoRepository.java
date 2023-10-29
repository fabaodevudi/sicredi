package votacao.scredi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import votacao.scredi.entity.Associado;

@Repository
public interface AssociadoRepository extends CrudRepository<Associado, Long>{

}
