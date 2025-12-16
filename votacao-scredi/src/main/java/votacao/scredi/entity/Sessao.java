package votacao.scredi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Sessao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sessao")
    private Long id;
	
    @OneToOne
    @JoinColumn(name = "id_pauta")
    private Pauta pauta;

    @Column
    private LocalDateTime inicioSessao = LocalDateTime.now();

    @Column(name = "FIM_SESSAO")  
    private LocalDateTime finalSessao = LocalDateTime.now().plusMinutes(1);

    @OneToMany(mappedBy = "sessao", cascade = {CascadeType.ALL})
    private List<Voto> votos;

    public Sessao(Pauta pauta, Long duracaoMinutos) {
        this.pauta = pauta;
        this.inicioSessao = LocalDateTime.now();
        this.finalSessao = this.inicioSessao.plusMinutes(duracaoMinutos != null && duracaoMinutos > 0 ? duracaoMinutos : 1);
    }

    public Sessao(Pauta pauta) {
        this.pauta = pauta;
        this.inicioSessao = LocalDateTime.now();
        this.finalSessao = this.inicioSessao.plusMinutes(1);
    }

}
