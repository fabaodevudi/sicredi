package votacao.scredi.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import votacao.scredi.builders.AssociadoBuilder;
import votacao.scredi.builders.PautaBuilder;
import votacao.scredi.builders.SessaoBuilder;
import votacao.scredi.builders.VotoBuilder;
import votacao.scredi.repository.SessaoRepository;
import votacao.scredi.service.impl.AssociadoServiceImpl;
import votacao.scredi.service.impl.PautaServiceImpl;
import votacao.scredi.service.impl.SessaoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SessaoServiceTest {
	
	private static final Long ID = 1L;

	@InjectMocks 
	private SessaoServiceImpl service;
	
	@Mock
	private SessaoRepository rep;
	
	@Mock
	private AssociadoServiceImpl associadoService;
	
	@Mock
	private PautaServiceImpl pautaService;
	
		
	@Test
    @DisplayName("Deve abrir nova sessao")
    void deveAbrirNovaSessao() {
        when(pautaService.obterPorId(ID)).thenReturn(PautaBuilder.pautaAumentoSalario().getPauta());
        when(rep.save(any())).thenReturn(SessaoBuilder.abreSessaoPautaAumentoSalario().getSessao());

        service.criar(PautaBuilder.pautaAumentoSalario().getPauta());

        verify(rep, times(1)).save(any());
        verify(pautaService, times(1)).obterPorId(ID);
      
    }
	
	@Test
    @DisplayName("Deve salvar voto")
    void deveSalvarVoto(){

        when(rep.findById(ID)).thenReturn(Optional.of(SessaoBuilder.abreSessaoPautaAumentoSalario().getSessao()));
        when(associadoService.obterPorCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf())).thenReturn(AssociadoBuilder.fabaoId1().getAssociado());

        service.votar(ID,VotoBuilder.votoSim().getVoto());

        verify(service, times(1)).obterPorId(ID);
        verify(associadoService, times(1)).obterPorCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf());
    }
	
}
