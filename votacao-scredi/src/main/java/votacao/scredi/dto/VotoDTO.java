package votacao.scredi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import votacao.scredi.enumerate.VotoEnum;

@Data
@NoArgsConstructor
public class VotoDTO {

    private Long id;  
    private VotoEnum voto;    
    private AssociadoDTO associado;
}
