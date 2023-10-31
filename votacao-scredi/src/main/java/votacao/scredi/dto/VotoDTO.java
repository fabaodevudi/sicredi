package votacao.scredi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import votacao.scredi.entity.Voto;
import votacao.scredi.enumerate.VotoEnum;

@Data
@NoArgsConstructor
public class VotoDTO {

    private Long id;  
    private VotoEnum voto;    
    private AssociadoDTO associado;
    private SessaoDTO sessao;
    
    public static VotoDTO fromEntity(Voto entity) {
    	VotoDTO dto = new VotoDTO();    	
    	dto.setAssociado(AssociadoDTO.fromEntity(entity.getAssociado()));
    	dto.setId(entity.getId());
    	dto.setVoto(entity.getVoto()); 
    	dto.setSessao(SessaoDTO.fromEntity(entity.getSessao()));
    	return dto;
    }
    
    public static Voto fromDTO(VotoDTO dto) {
    	Voto entity = new Voto();    	
    	entity.setAssociado(AssociadoDTO.fromDTO(dto.getAssociado()));
    	entity.setId(dto.getId());
    	entity.setVoto(dto.getVoto());  
    	entity.setSessao(SessaoDTO.fromDTO(dto.getSessao()));
    	return entity;
    }
    
}
