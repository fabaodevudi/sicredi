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
import votacao.scredi.client.ClienteValidacaoCpf;
import votacao.scredi.dto.AssociadoDTO;
import votacao.scredi.dto.PautaDTO;
import votacao.scredi.dto.RespostaValidacaoCpfDTO;
import votacao.scredi.enumerate.StatusValidacaoCpfEnum;
import votacao.scredi.exception.CpfNaoEncontradoNoServicoExternoException;
import votacao.scredi.exception.CpfNaoHabilitadoParaVotoException;
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

    @Mock
    private ClienteValidacaoCpf clienteValidacaoCpf;


    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "rep", rep);
        ReflectionTestUtils.setField(service, "votoRep", votoRep);
        ReflectionTestUtils.setField(service, "associadoRep", associadoService);
        ReflectionTestUtils.setField(service, "pautaRep", pautaService);
        ReflectionTestUtils.setField(service, "clienteValidacaoCpf", clienteValidacaoCpf);
    }

    @Test
    @DisplayName("Deve abrir nova sessao")
    void deveAbrirNovaSessao() {
        when(pautaService.obterPorId(ID)).thenReturn(PautaDTO.fromEntity(PautaBuilder.pautaAumentoSalario().getPauta()));
        when(rep.save(any())).thenReturn(SessaoBuilder.abreSessaoPautaAumentoSalario().getSessao());

        service.criar(PautaDTO.fromEntity(PautaBuilder.pautaAumentoSalario().getPauta()));

        verify(rep, times(1)).save(any());
        verify(pautaService, times(1)).obterPorId(ID);

    }

    @Test
    @DisplayName("Deve salvar voto quando CPF habilitado")
    void deveSalvarVotoQuandoCpfHabilitado(){
        RespostaValidacaoCpfDTO respostaHabilitado = new RespostaValidacaoCpfDTO();
        respostaHabilitado.setStatus(StatusValidacaoCpfEnum.HABILITADO_PARA_VOTAR);

        when(rep.findById(ID)).thenReturn(Optional.of(SessaoBuilder.abreSessaoPautaAumentoSalario().getSessao()));
        when(associadoService.obterPorCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf()))
                .thenReturn(AssociadoDTO.fromEntity(AssociadoBuilder.fabaoId1().getAssociado()));
        when(clienteValidacaoCpf.validarCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf()))
                .thenReturn(respostaHabilitado);
        when(votoRep.save(any())).thenReturn(VotoBuilder.votoSim().getVoto());

        service.votar(ID,VotoBuilder.votoSim().getVoto());

        verify(rep, times(1)).findById(ID);
        verify(associadoService, times(1)).obterPorCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf());
        verify(clienteValidacaoCpf, times(1)).validarCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf());
        verify(votoRep, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve lancar excecao quando CPF nao habilitado")
    void deveLancarExcecaoQuandoCpfNaoHabilitado() {
        RespostaValidacaoCpfDTO respostaNaoHabilitado = new RespostaValidacaoCpfDTO();
        respostaNaoHabilitado.setStatus(StatusValidacaoCpfEnum.NAO_HABILITADO_PARA_VOTAR);

        when(rep.findById(ID)).thenReturn(Optional.of(SessaoBuilder.abreSessaoPautaAumentoSalario().getSessao()));
        when(associadoService.obterPorCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf()))
                .thenReturn(AssociadoDTO.fromEntity(AssociadoBuilder.fabaoId1().getAssociado()));
        when(clienteValidacaoCpf.validarCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf())).thenReturn(respostaNaoHabilitado);

        org.junit.jupiter.api.Assertions.assertThrows(CpfNaoHabilitadoParaVotoException.class, () -> {
            service.votar(ID, VotoBuilder.votoSim().getVoto());
        });

        verify(rep, times(1)).findById(ID);
        verify(associadoService, times(1)).obterPorCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf());
        verify(clienteValidacaoCpf, times(1)).validarCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf());
        verify(votoRep, times(0)).save(any());
    }

    @Test
    @DisplayName("Deve lancar excecao quando CPF nao encontrado no servico externo")
    void deveLancarExcecaoQuandoCpfNaoEncontradoExternamente() {
        // Cenario: CPF nao encontrado no servico externo (simula 404)
        when(rep.findById(ID)).thenReturn(Optional.of(SessaoBuilder.abreSessaoPautaAumentoSalario().getSessao()));
        when(associadoService.obterPorCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf()))
                .thenReturn(AssociadoDTO.fromEntity(AssociadoBuilder.fabaoId1().getAssociado()));
        when(clienteValidacaoCpf.validarCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf()))
                .thenThrow(new CpfNaoEncontradoNoServicoExternoException("CPF não encontrado no serviço externo."));

        org.junit.jupiter.api.Assertions.assertThrows(CpfNaoEncontradoNoServicoExternoException.class, () -> {
            service.votar(ID, VotoBuilder.votoSim().getVoto());
        });

        verify(rep, times(1)).findById(ID);
        verify(associadoService, times(1)).obterPorCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf());
        verify(clienteValidacaoCpf, times(1)).validarCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf());
        verify(votoRep, times(0)).save(any());
    }

}