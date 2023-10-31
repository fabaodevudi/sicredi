package votacao.scredi.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    
    public Sessao(Pauta pauta) {
    	this.pauta = pauta;
    }

}
