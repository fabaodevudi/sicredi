package votacao.scredi.dto;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import votacao.scredi.entity.Pauta;
import votacao.scredi.entity.Sessao;

@Data
@NoArgsConstructor
public class SessaoDTO {
   
    private Long id;
    private PautaDTO pauta;
    private LocalDateTime inicioSessao = LocalDateTime.now();
    private LocalDateTime finalSessao = inicioSessao.plusMinutes(1);
    
    public static SessaoDTO fromEntity(Sessao entity) {
    	SessaoDTO dto = new SessaoDTO();
    	dto.setId(entity.getId());
    	dto.setInicioSessao(entity.getInicioSessao());
    	dto.setFinalSessao(entity.getFinalSessao());
    	dto.setPauta(PautaDTO.fromEntity(entity.getPauta()));
    	return dto;
    }
    
    public static Sessao fromDTO(SessaoDTO dto) {
    	Sessao entity = new Sessao();
    	entity.setId(dto.getId());
    	entity.setInicioSessao(dto.getInicioSessao());
    	entity.setFinalSessao(dto.getFinalSessao());
    	entity.setPauta(PautaDTO.fromDTO(dto.getPauta()));
    	return entity;
    }
    

}
