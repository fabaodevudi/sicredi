package votacao.scredi.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import votacao.scredi.entity.Associado;

@Repository
public interface AssociadoRepository extends CrudRepository<Associado, Long>{
	Optional<Associado> findByCpf(String cpf);
}
