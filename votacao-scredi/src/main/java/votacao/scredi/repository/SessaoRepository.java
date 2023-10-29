package votacao.scredi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import votacao.scredi.entity.Sessao;

@Repository
public interface SessaoRepository extends CrudRepository<Sessao, Long> {

}
