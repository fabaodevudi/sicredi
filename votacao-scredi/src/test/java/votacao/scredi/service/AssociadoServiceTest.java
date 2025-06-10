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
import votacao.scredi.dto.AssociadoDTO;
import votacao.scredi.entity.Associado;
import votacao.scredi.exception.AssociadoExisteException;
import votacao.scredi.exception.AssociadoNaoExisteException;
import votacao.scredi.repository.AssociadoRepository;
import votacao.scredi.service.impl.AssociadoServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssociadoServiceTest {

    private static final Long ID = 1L;
    private static final String CPF_EXISTENTE = "07474748690";
    private static final String CPF_INEXISTENTE = "99999999999";
    private static final String MENSAGEM_ASSOCIADO_INEXISTENTE = "Associado inexistente";
    private static final String MENSAGEM_ASSOCIADO_EXISTENTE = "Associado já existente com Cpf: " + CPF_EXISTENTE;


    @InjectMocks
    private AssociadoServiceImpl service;

    @Mock
    private AssociadoRepository rep;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "rep", rep);
    }

    @Test
    @DisplayName("Deve listar todos os associados com sucesso")
    void deveListarTodosAssociadosComSucesso() {
        // Cenário: Existem 2 associados no repositório
        List<Associado> associados = Arrays.asList(
                AssociadoBuilder.fabaoId1().getAssociado(),
                AssociadoBuilder.pedroId2().getAssociado()
        );
        when(rep.findAll()).thenReturn(associados);

        // Ação: Chamar o método listar
        List<AssociadoDTO> resultado = service.listar();

        // Verificação: A lista não deve ser nula e deve conter dois DTOs
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        // se os dtos são iguais das entidades
        assertEquals(AssociadoDTO.fromEntity(AssociadoBuilder.fabaoId1().getAssociado()), resultado.get(0));
        assertEquals(AssociadoDTO.fromEntity(AssociadoBuilder.pedroId2().getAssociado()), resultado.get(1));
        verify(rep, times(1)).findAll(); // Verifica se o findAll foi chamado uma vez
    }

    @Test
    @DisplayName("Deve retornar lista vazia se não houver associados")
    void deveRetornarListaVaziaSeNaoHouverAssociados() {
        // Cenário: Não há associados
        when(rep.findAll()).thenReturn(Arrays.asList());

        // Ação: chamar o método listar
        List<AssociadoDTO> resultado = service.listar();

        // Verificação: A lista não deve ser nula e deve estar vazia
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(rep, times(1)).findAll(); // garante que findAll foi chamado
    }

    @Test
    @DisplayName("Deve obter associado por ID com sucesso")
    void deveObterAssociadoPorIdComSucesso() {
        // Cenário: Associado existe
        Associado associadoEsperado = AssociadoBuilder.fabaoId1().getAssociado();
        when(rep.findById(ID)).thenReturn(Optional.of(associadoEsperado));

        // Ação: Obter associado por id
        Associado resultado = service.obterPorId(ID);

        // Verificação:
        assertNotNull(resultado); //O resultado não deve ser nulo
        assertEquals(associadoEsperado, resultado); //deve ser igual ao esperado
        verify(rep, times(1)).findById(ID); //deve ser chamado 1 vez
    }

    @Test
    @DisplayName("Deve lançar exceção se associado não existe por ID")
    void deveLancarExcecaoSeAssociadoNaoExistePorId() {
        // Cenário: Associado não existe
        when(rep.findById(anyLong())).thenReturn(Optional.empty());

        // Ação: lançar exception
        AssociadoNaoExisteException exception = assertThrows(AssociadoNaoExisteException.class, () -> {
            service.obterPorId(ID);
        });
        assertEquals(MENSAGEM_ASSOCIADO_INEXISTENTE, exception.getMessage()); //Verifica mensagem
        verify(rep, times(1)).findById(ID);
    }

    @Test
    @DisplayName("Deve criar associado com sucesso")
    void deveCriarAssociadoComSucesso() {
        // Cenário: Associado não existe
        Associado novoAssociado = AssociadoBuilder.fabaoSemId().getAssociado();
        when(rep.findByCpf(novoAssociado.getCpf())).thenReturn(Optional.empty());
        when(rep.save(any(Associado.class))).thenReturn(novoAssociado);

        // Ação: Chamar o método criar
        service.criar(AssociadoDTO.fromEntity(novoAssociado));

        // Verificação: chamou findById e save
        verify(rep, times(1)).findByCpf(novoAssociado.getCpf());
        verify(rep, times(1)).save(novoAssociado);
    }

    @Test
    @DisplayName("Deve lançar exceção se CPF já existe ao criar")
    void deveLancarExcecaoSeCpfJaExisteAoCriar() {
        // Cenário: CPF já existe
        Associado associadoExistente = AssociadoBuilder.fabaoId1().getAssociado();
        when(rep.findByCpf(CPF_EXISTENTE)).thenReturn(Optional.of(associadoExistente));

        // Ação: lançar exception
        AssociadoExisteException exception = assertThrows(AssociadoExisteException.class, () -> {
            service.criar(AssociadoDTO.fromEntity(AssociadoBuilder.fabaoSemId().getAssociado()));
        });
        assertEquals(MENSAGEM_ASSOCIADO_EXISTENTE, exception.getMessage()); //verificar mensagem de exception que usuário existe
        verify(rep, times(1)).findByCpf(CPF_EXISTENTE);
        verify(rep, times(0)).save(any(Associado.class)); // Verifica que save não foi chamado
    }

    @Test
    @DisplayName("Deve deletar associado por ID com sucesso")
    void deveDeletarAssociadoPorIdComSucesso() {
        // Cenário: Associado existe
        Associado associadoExistente = AssociadoBuilder.fabaoId1().getAssociado();
        when(rep.findById(ID)).thenReturn(Optional.of(associadoExistente));
        doNothing().when(rep).deleteById(ID); // Mocka o método void

        // Ação: Chamar o método deletar
        service.deletar(ID);

        // Verificação:chamou findById e deletou
        verify(rep, times(1)).findById(ID);
        verify(rep, times(1)).deleteById(ID);
    }

    @Test
    @DisplayName("Deve lançar exceção se associado não existe ao deletar")
    void deveLancarExcecaoSeAssociadoNaoExisteAoDeletar() {
        // Cenário: Associado não existe
        when(rep.findById(anyLong())).thenReturn(Optional.empty());

        // Ação e Verificação: lança exception
        AssociadoNaoExisteException exception = assertThrows(AssociadoNaoExisteException.class, () -> {
            service.deletar(ID);
        });
        assertEquals(MENSAGEM_ASSOCIADO_INEXISTENTE, exception.getMessage());
        verify(rep, times(1)).findById(ID);
        verify(rep, times(0)).deleteById(anyLong());
    }

    @Test
    @DisplayName("Deve obter associado por CPF com sucesso")
    void deveObterAssociadoPorCpfComSucesso() {
        // Cenário: Associado existe
        Associado associadoEsperado = AssociadoBuilder.fabaoId1().getAssociado();
        when(rep.findByCpf(CPF_EXISTENTE)).thenReturn(Optional.of(associadoEsperado));

        // Ação: Chamar o método obterPorCpf
        Associado resultado = AssociadoDTO.fromDTO(service.obterPorCpf(CPF_EXISTENTE));

        // Verificação:
        assertNotNull(resultado);
        assertEquals(associadoEsperado, resultado);
        verify(rep, times(1)).findByCpf(CPF_EXISTENTE);
    }

    @Test
    @DisplayName("Deve lançar exceção se associado não existe por CPF")
    void deveLancarExcecaoSeAssociadoNaoExistePorCpf() {
        // Cenário: Associado não existe
        when(rep.findByCpf(anyString())).thenReturn(Optional.empty());

        // Ação e Verificação: lança excption
        AssociadoNaoExisteException exception = assertThrows(AssociadoNaoExisteException.class, () -> {
            service.obterPorCpf(CPF_INEXISTENTE);
        });
        assertEquals("Usuário inexistente", exception.getMessage());
        verify(rep, times(1)).findByCpf(CPF_INEXISTENTE);
    }
}