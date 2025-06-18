package votacao.scredi.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

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
import votacao.scredi.entity.Sessao;
import votacao.scredi.enumerate.StatusValidacaoCpfEnum;
import votacao.scredi.exception.CpfNaoEncontradoNoServicoExternoException;
import votacao.scredi.exception.CpfNaoHabilitadoParaVotoException;
import votacao.scredi.repository.SessaoRepository;
import votacao.scredi.repository.VotoRepository;
import votacao.scredi.service.impl.SessaoServiceImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;


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
    @DisplayName("Deve abrir nova sessao com duracao padrao de 1 minuto")
    void deveAbrirNovaSessaoComDuracaoPadrao() {

        when(pautaService.obterPorId(ID)).thenReturn(PautaDTO.fromEntity(PautaBuilder.pautaAumentoSalario().getPauta()));
        when(rep.save(any())).thenReturn(SessaoBuilder.abreSessaoPautaAumentoSalario().getSessao());

        // Ação: Criar sessão
        service.criar(PautaDTO.fromEntity(PautaBuilder.pautaAumentoSalario().getPauta()), null); // duracaoMinutos = null para usar o padrão

        // Verificação: Garante que o save foi chamado
        verify(rep, times(1)).save(any());
        verify(pautaService, times(1)).obterPorId(ID);// Garante que obterPorId da pauta foi chamado

    }

    @Test
    @DisplayName("Deve abrir nova sessao com duracao determinada")
    void deveAbrirNovaSessaoComDuracaoDeterminada() {
        // Cenário: Uma sessão e um associado existem e o CPF do usuário é válido
        PautaDTO pautaDTO = PautaDTO.fromEntity(PautaBuilder.pautaAumentoSalario().getPauta());
        Long duracaoDeterminada = 5L; // 5 minutos

        Sessao sessaoMock = new Sessao(PautaBuilder.pautaAumentoSalario().getPauta(), duracaoDeterminada);
        sessaoMock.setId(ID);

        when(pautaService.obterPorId(ID)).thenReturn(pautaDTO);
        when(rep.save(any(Sessao.class))).thenReturn(sessaoMock);

        // Ação: Criar sessão
        service.criar(pautaDTO, duracaoDeterminada);

        // Verificação: Garante que o save foi chamado
        verify(rep, times(1)).save(any());
        verify(pautaService, times(1)).obterPorId(ID);

    }

    @Test
    @DisplayName("Deve salvar voto quando CPF habilitado")
    void deveSalvarVotoQuandoCpfHabilitado(){
        // Cenário: Uma sessão e um associado existem e o CPF do usuário é válido
        RespostaValidacaoCpfDTO respostaHabilitado = new RespostaValidacaoCpfDTO();
        respostaHabilitado.setStatus(StatusValidacaoCpfEnum.HABILITADO_PARA_VOTAR);

        when(rep.findById(ID)).thenReturn(Optional.of(SessaoBuilder.abreSessaoPautaAumentoSalario().getSessao()));
        when(associadoService.obterPorCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf()))
                .thenReturn(AssociadoDTO.fromEntity(AssociadoBuilder.fabaoId1().getAssociado()));
        when(clienteValidacaoCpf.validarCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf()))
                .thenReturn(respostaHabilitado);
        when(votoRep.save(any())).thenReturn(VotoBuilder.votoSim().getVoto());

        // Ação: Chamar o método 'votar' do serviço com um voto.
        service.votar(ID,VotoBuilder.votoSim().getVoto());

        // Verificação:
        // Garante o voto foi salvo
        verify(rep, times(1)).findById(ID);
        verify(associadoService, times(1)).obterPorCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf());
        verify(clienteValidacaoCpf, times(1)).validarCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf());
        verify(votoRep, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve lancar excecao quando CPF nao habilitado")
    void deveLancarExcecaoQuandoCpfNaoHabilitado() {
        // Cenário: sessão e associado existe e CPF inválido
        RespostaValidacaoCpfDTO respostaNaoHabilitado = new RespostaValidacaoCpfDTO();
        respostaNaoHabilitado.setStatus(StatusValidacaoCpfEnum.NAO_HABILITADO_PARA_VOTAR);

        when(rep.findById(ID)).thenReturn(Optional.of(SessaoBuilder.abreSessaoPautaAumentoSalario().getSessao()));
        when(associadoService.obterPorCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf()))
                .thenReturn(AssociadoDTO.fromEntity(AssociadoBuilder.fabaoId1().getAssociado()));
        when(clienteValidacaoCpf.validarCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf())).thenReturn(respostaNaoHabilitado);

        // Ação e Verificação: CpfNaoHabilitadoParaVotoException deve ser lançada
        assertThrows(CpfNaoHabilitadoParaVotoException.class, () -> {
            service.votar(ID, VotoBuilder.votoSim().getVoto());
        });

        // Garante que save do rep de voto NÃO foi chamado.
        verify(rep, times(1)).findById(ID);
        verify(associadoService, times(1)).obterPorCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf());
        verify(clienteValidacaoCpf, times(1)).validarCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf());
        verify(votoRep, times(0)).save(any());
    }

    @Test
    @DisplayName("Deve lancar excecao quando CPF nao encontrado no servico externo")
    void deveLancarExcecaoQuandoCpfNaoEncontradoExternamente() {
        // Cenário: sessão e um associado existe e o CPF é inválido no serviço externo (serviço fake)
        when(rep.findById(ID)).thenReturn(Optional.of(SessaoBuilder.abreSessaoPautaAumentoSalario().getSessao()));
        when(associadoService.obterPorCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf()))
                .thenReturn(AssociadoDTO.fromEntity(AssociadoBuilder.fabaoId1().getAssociado()));
        // Ação: Mocka o cliente para lançar a exceção de CPF inválido
        when(clienteValidacaoCpf.validarCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf()))
                .thenThrow(new CpfNaoEncontradoNoServicoExternoException("CPF não encontrado no serviço externo."));

        // Verificação: verifica se exceção foi lançada
        assertThrows(CpfNaoEncontradoNoServicoExternoException.class, () -> {
            service.votar(ID, VotoBuilder.votoSim().getVoto());
        });


        verify(rep, times(1)).findById(ID);
        verify(associadoService, times(1)).obterPorCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf());
        verify(clienteValidacaoCpf, times(1)).validarCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf());
        verify(votoRep, times(0)).save(any());// Garante que o método save do rep de voto NÃO foi chamado.
    }
}