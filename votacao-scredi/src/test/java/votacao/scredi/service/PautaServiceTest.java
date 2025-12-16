package votacao.scredi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import votacao.scredi.builders.PautaBuilder;
import votacao.scredi.dto.PautaDTO;
import votacao.scredi.entity.Pauta;
import votacao.scredi.exception.PautaExisteException;
import votacao.scredi.exception.PautaNaoExisteException;
import votacao.scredi.repository.PautaRepository;
import votacao.scredi.service.impl.PautaServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PautaServiceTest {

    private static final Long ID = 1L;
    private static final String TITULO_EXISTENTE = "Aumento Salário";
    private static final String TITULO_INEXISTENTE = "Nova Pauta Teste";
    private static final String MENSAGEM_PAUTA_INEXISTENTE = "Pauta não existe";
    private static final String MENSAGEM_PAUTA_EXISTENTE = "Pauta já existente com Titulo: " + TITULO_EXISTENTE;

    @InjectMocks
    private PautaServiceImpl service;

    @Mock
    private PautaRepository pautaRepository;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "pautaRepository", pautaRepository);
    }

    @Test
    @DisplayName("Deve listar todas as pautas com sucesso")
    void deveListarTodasPautasComSucesso() {
        // Cenário: Existem 2 pautas
        List<Pauta> pautas = Arrays.asList(
                PautaBuilder.pautaAumentoSalario().getPauta(),
                PautaBuilder.pautaMudancaPlanoSaude().getPauta()
        );
        when(pautaRepository.findAll()).thenReturn(pautas);

        // Ação: Chamar listar
        List<PautaDTO> resultado = service.listar();

        // Verificação:
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(PautaDTO.fromEntity(PautaBuilder.pautaAumentoSalario().getPauta()), resultado.get(0));
        assertEquals(PautaDTO.fromEntity(PautaBuilder.pautaMudancaPlanoSaude().getPauta()), resultado.get(1));
        verify(pautaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia se não houver pautas")
    void deveRetornarListaVaziaSeNaoHouverPautas() {
        // Cenário: Não há pautas
        when(pautaRepository.findAll()).thenReturn(Arrays.asList());

        // Ação: Chamar listar
        List<PautaDTO> resultado = service.listar();

        // Verificação:
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(pautaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve obter pauta por ID com sucesso")
    void deveObterPautaPorIdComSucesso() {
        // Cenário: Pauta existe
        Pauta pautaEsperada = PautaBuilder.pautaAumentoSalario().getPauta();
        when(pautaRepository.findById(ID)).thenReturn(Optional.of(pautaEsperada));

        // Ação: Chamar o método obterPorId
        Pauta resultado = PautaDTO.fromDTO(service.obterPorId(ID));

        // Verificação:
        assertNotNull(resultado);
        assertEquals(pautaEsperada, resultado);
        verify(pautaRepository, times(1)).findById(ID);
    }

    @Test
    @DisplayName("Deve lançar exceção se pauta não existe por ID")
    void deveLancarExcecaoSePautaNaoExistePorId() {
        // Cenário: Pauta não existe
        when(pautaRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Ação e Verificação: larçar exception
        PautaNaoExisteException exception = assertThrows(PautaNaoExisteException.class, () -> {
            service.obterPorId(ID);
        });
        assertEquals(MENSAGEM_PAUTA_INEXISTENTE, exception.getMessage());
        verify(pautaRepository, times(1)).findById(ID);
    }

    @Test
    @DisplayName("Deve criar pauta com sucesso")
    void deveCriarPautaComSucesso() {
        // Cenário: Pauta não existe
        Pauta novaPauta = PautaBuilder.pautaAumentoSalarioSemId().getPauta();
        when(pautaRepository.findByTitulo(novaPauta.getTitulo())).thenReturn(Optional.empty());
        when(pautaRepository.save(any(Pauta.class))).thenReturn(novaPauta);

        // Ação: Chamar o método criar
        service.criar(PautaDTO.fromEntity(novaPauta));

        // Verificação:
        verify(pautaRepository, times(1)).findByTitulo(novaPauta.getTitulo());
        verify(pautaRepository, times(1)).save(novaPauta);
    }

    @Test
    @DisplayName("Deve lançar exceção se título de pauta já existe ao criar")
    void deveLancarExcecaoSeTituloPautaJaExisteAoCriar() {
        // Cenário: Título já existe
        Pauta pautaExistente = PautaBuilder.pautaAumentoSalario().getPauta();
        when(pautaRepository.findByTitulo(TITULO_EXISTENTE)).thenReturn(Optional.of(pautaExistente));

        // Ação e Verificação: larçar PautaExisteException
        PautaExisteException exception = assertThrows(PautaExisteException.class, () -> {
            service.criar(PautaDTO.fromEntity(PautaBuilder.pautaAumentoSalarioSemId().getPauta()));
        });
        assertEquals(MENSAGEM_PAUTA_EXISTENTE, exception.getMessage());
        verify(pautaRepository, times(1)).findByTitulo(TITULO_EXISTENTE);
        verify(pautaRepository, times(0)).save(any(Pauta.class)); // Verifica que save não foi chamado
    }

    @Test
    @DisplayName("Deve deletar pauta por ID com sucesso")
    void deveDeletarPautaPorIdComSucesso() {
        // Cenário: Pauta existe
        Pauta pautaExistente = PautaBuilder.pautaAumentoSalario().getPauta();
        when(pautaRepository.findById(ID)).thenReturn(Optional.of(pautaExistente));
        doNothing().when(pautaRepository).deleteById(ID); // Mocka o método void

        // Ação: Chamar o método deletar
        service.deletar(ID);

        // Verificação:
        verify(pautaRepository, times(1)).findById(ID);
        verify(pautaRepository, times(1)).deleteById(ID);
    }

    @Test
    @DisplayName("Deve lançar exceção se pauta não existe ao deletar")
    void deveLancarExcecaoSePautaNaoExisteAoDeletar() {
        // Cenário: Pauta não existe
        when(pautaRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Ação e Verificação: larçar PautaNaoExisteException
        PautaNaoExisteException exception = assertThrows(PautaNaoExisteException.class, () -> {
            service.deletar(ID);
        });
        assertEquals(MENSAGEM_PAUTA_INEXISTENTE, exception.getMessage());
        verify(pautaRepository, times(1)).findById(ID);
        verify(pautaRepository, times(0)).deleteById(anyLong()); // Verifica que deleteById não foi chamado
    }
}