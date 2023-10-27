package votacao.scredi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AssociadoDTO {
	
    private Long id;  
    private String nome;    
    private String cpf;

}
