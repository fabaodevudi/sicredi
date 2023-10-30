package votacao.scredi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import votacao.scredi.entity.Pauta;

@Data
@NoArgsConstructor
public class PautaDTO {	
    
    private Long id;  
    private String titulo;    
    private String descricao;
    
    public static PautaDTO fromEntity(Pauta entity) {
    	PautaDTO dto = new PautaDTO();
    	dto.setId(entity.getId());
    	dto.setTitulo(entity.getTitulo());
    	dto.setDescricao(entity.getDescricao());
    	return dto;
    }
    
    public static Pauta fromDTO(PautaDTO dto) {
    	Pauta entity = new Pauta();
    	entity.setId(dto.getId());
    	entity.setTitulo(dto.getTitulo());
    	entity.setDescricao(dto.getDescricao());
    	return entity;
    }
    
   
}
