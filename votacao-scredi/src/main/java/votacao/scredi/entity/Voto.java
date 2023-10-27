package votacao.scredi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import votacao.scredi.enumerate.VotoEnum;

@Entity
@Data
@NoArgsConstructor
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private VotoEnum votoEnum;
    @ManyToOne
    @JoinColumn(name = "id_sessao")
    private Sessao sessao;
    @ManyToOne
    @JoinColumn(name = "id_associado")
    private Associado associado;
}
