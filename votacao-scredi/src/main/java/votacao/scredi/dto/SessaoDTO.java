package votacao.scredi.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import votacao.scredi.entity.Sessao;

@Data
@NoArgsConstructor
public class SessaoDTO {
   
    private Long id;
    private PautaDTO pauta;
    private LocalDateTime inicioSessao = LocalDateTime.now();
    private LocalDateTime finalSessao = inicioSessao.plusMinutes(1);
    private List<VotoDTO> votos;
    
    public static SessaoDTO fromEntity(Sessao entity) {
    	SessaoDTO dto = new SessaoDTO();
    	dto.setId(entity.getId());
    	dto.setInicioSessao(entity.getInicioSessao());
    	dto.setFinalSessao(entity.getFinalSessao());
    	dto.setPauta(PautaDTO.fromEntity(entity.getPauta()));
    	dto.setVotos(entity.getVotos().stream().map(item -> VotoDTO.fromEntity(item)).collect(Collectors.toList()));
    	return dto;
    }
    
    public static Sessao fromDTO(SessaoDTO dto) {
    	Sessao entity = new Sessao();
    	entity.setId(dto.getId());
    	entity.setInicioSessao(dto.getInicioSessao());
    	entity.setFinalSessao(dto.getFinalSessao());
    	entity.setPauta(PautaDTO.fromDTO(dto.getPauta()));
    	entity.setVotos(dto.getVotos().stream().map(item -> VotoDTO.fromDTO(item)).collect(Collectors.toList()));
    	return entity;
    }
    

}
