package votacao.scredi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import votacao.scredi.enumerate.StatusValidacaoCpfEnum;

@Data
@NoArgsConstructor
public class RespostaValidacaoCpfDTO {
    private StatusValidacaoCpfEnum status;
}
