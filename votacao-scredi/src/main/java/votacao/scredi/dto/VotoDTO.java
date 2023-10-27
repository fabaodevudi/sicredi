package votacao.scredi.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import votacao.scredi.enumerate.VotoEnum;

@Entity
@Data
@NoArgsConstructor
public class VotoDTO {

    private Long id;
    @Enumerated(EnumType.STRING)
    private VotoEnum votoEnum;    
    private AssociadoDTO associado;
}
