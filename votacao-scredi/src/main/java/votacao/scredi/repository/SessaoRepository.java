package votacao.scredi.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import votacao.scredi.dto.ResultadoVotacaoDTO;
import votacao.scredi.entity.Sessao;

@Repository
public interface SessaoRepository extends CrudRepository<Sessao, Long> {
    @Query(value = "SELECT new votacao.scredi.dto.ResultadoVotacaoDTO(" +
            "s.id, p.titulo, p.descricao, " +
            "SUM(CASE WHEN v.voto = 'SIM' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN v.voto = 'NAO' THEN 1 ELSE 0 END), " +
            "COUNT(v.id)) " +
            "FROM Sessao s " +
            "JOIN s.pauta p " +
            "LEFT JOIN s.votos v " +
            "WHERE s.id = :idSessao " +
            "GROUP BY s.id, p.titulo, p.descricao", nativeQuery = false)
    ResultadoVotacaoDTO consolidarVotosPorSessao(Long idSessao);
}
