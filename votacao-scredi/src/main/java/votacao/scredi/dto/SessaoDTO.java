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

@Data
@NoArgsConstructor
public class SessaoDTO {
   
    private Long id;
    private PautaDTO pauta;
    private LocalDateTime inicioSessao = LocalDateTime.now();
    private LocalDateTime finalSessao = inicioSessao.plusMinutes(1);

}
