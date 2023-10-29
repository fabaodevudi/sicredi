package votacao.scredi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import votacao.scredi.entity.Associado;

@Data
@NoArgsConstructor
public class AssociadoDTO {
	
    private Long id;  
    private String nome;    
    private String cpf;
    
    public static AssociadoDTO fromEntity(Associado entity) {
    	AssociadoDTO dto = new AssociadoDTO();
    	dto.setCpf(entity.getCpf());
    	dto.setId(entity.getId());
    	dto.setNome(entity.getNome());
    	return dto;
    }
    
    public static Associado fromDTO(AssociadoDTO dto) {
    	Associado entity = new Associado();
    	entity.setCpf(dto.getCpf());
    	entity.setId(dto.getId());
    	entity.setNome(dto.getNome());
    	return entity;
    }

}
