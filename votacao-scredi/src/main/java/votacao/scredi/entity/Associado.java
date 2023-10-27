package votacao.scredi.entity;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
public class Associado {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_associado")
    private Long id;
  
    @Column(nullable = false, unique = true)
    private String nome;
    
    @Column(nullable = false, unique = false)
    private String cpf;
    
    @OneToMany(mappedBy = "associado", cascade = {CascadeType.ALL})
    private List<Voto> votos ;

}
