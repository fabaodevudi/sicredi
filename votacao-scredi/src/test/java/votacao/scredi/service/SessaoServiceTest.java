package votacao.scredi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import votacao.scredi.builders.AssociadoBuilder;
import votacao.scredi.builders.PautaBuilder;
import votacao.scredi.builders.SessaoBuilder;
import votacao.scredi.builders.VotoBuilder;
import votacao.scredi.repository.SessaoRepository;
import votacao.scredi.repository.VotoRepository;
import votacao.scredi.service.impl.SessaoServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessaoServiceTest {

    private static final Long ID = 1L;

    @InjectMocks
    private SessaoServiceImpl service;

    @Mock
    private SessaoRepository rep;

    @Mock
    private VotoRepository votoRep;

    @Mock
    private AssociadoService associadoService;

    @Mock
    private PautaService pautaService;


    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "rep", rep);
        ReflectionTestUtils.setField(service, "votoRep", votoRep);
        ReflectionTestUtils.setField(service, "associadoRep", associadoService);
        ReflectionTestUtils.setField(service, "pautaRep", pautaService);
    }

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

        when(votoRep.save(any())).thenReturn(VotoBuilder.votoSim().getVoto());

        service.votar(ID,VotoBuilder.votoSim().getVoto());

        verify(rep, times(1)).findById(ID);
        verify(associadoService, times(1)).obterPorCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf());
        verify(votoRep, times(1)).save(any());
    }

}