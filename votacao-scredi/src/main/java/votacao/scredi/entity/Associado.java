package votacao.scredi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data
@NoArgsConstructor
public class Associado {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_associado")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = false)
    private String cpf;

    @OneToMany(mappedBy = "associado", cascade = {CascadeType.ALL})
    private List<Voto> votos ;

}
