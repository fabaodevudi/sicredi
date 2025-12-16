package votacao.scredi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultadoVotacaoDTO {
    private Long idSessao;
    private String tituloPauta;
    private String descricaoPauta;
    private Long totalVotosSim;
    private Long totalVotosNao;
    private Long totalVotosGeral;

    public ResultadoVotacaoDTO(Long idSessao, String tituloPauta, String descricaoPauta,
                               Long totalVotosSim, Long totalVotosNao, Long totalVotosGeral) {
        this.idSessao = idSessao;
        this.tituloPauta = tituloPauta;
        this.descricaoPauta = descricaoPauta;
        this.totalVotosSim = totalVotosSim != null ? totalVotosSim : 0L;
        this.totalVotosNao = totalVotosNao != null ? totalVotosNao : 0L;
        this.totalVotosGeral = totalVotosGeral != null ? totalVotosGeral : 0L;
    }
}
