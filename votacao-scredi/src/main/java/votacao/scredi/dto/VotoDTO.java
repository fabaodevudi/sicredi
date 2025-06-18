package votacao.scredi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import votacao.scredi.entity.Voto;
import votacao.scredi.enumerate.VotoEnum;

@Data
@NoArgsConstructor
public class VotoDTO {

    private VotoEnum voto;
    private AssociadoDTO associado;

    public static VotoDTO fromEntity(Voto entity) {
    	VotoDTO dto = new VotoDTO();    	
    	dto.setAssociado(AssociadoDTO.fromEntity(entity.getAssociado()));
    	dto.setVoto(entity.getVoto());
    	return dto;
    }
    
    public static Voto fromDTO(VotoDTO dto) {
    	Voto entity = new Voto();    	
    	entity.setAssociado(AssociadoDTO.fromDTO(dto.getAssociado()));
    	entity.setVoto(dto.getVoto());
    	return entity;
    }
    
}
