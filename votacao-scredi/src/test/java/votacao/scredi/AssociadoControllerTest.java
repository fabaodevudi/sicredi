package votacao.scredi;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import votacao.scredi.builders.AssociadoBuilder;
import votacao.scredi.controller.AssociadoController;
import votacao.scredi.dto.AssociadoDTO;
import votacao.scredi.exception.AssociadoExisteException;
import votacao.scredi.exception.AssociadoNaoExisteException;
import votacao.scredi.service.AssociadoService;

@WebMvcTest(controllers = AssociadoController.class)
public class AssociadoControllerTest {

	@MockBean
	private AssociadoService service;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("Deve retornar 201 na criação")
	void deveRetornar201CasoSucesso() throws Exception {
		String jsonBody = new Gson().toJson(AssociadoDTO.fromEntity(AssociadoBuilder.fabaoSemId().getAssociado()));

		doNothing().when(service).criar(AssociadoBuilder.fabaoSemId().getAssociado());

		mockMvc.perform(post("/associados")
				.contentType(APPLICATION_JSON)
				.content(jsonBody))
		.andExpect(status().isCreated());
		verify(service,times(1)).criar(AssociadoBuilder.fabaoSemId().getAssociado());
	}

	@Test
	@DisplayName("Deve retornar 422 na criação")
	void deveRetornar422CasoUsuarioExista() throws Exception {
		String jsonBody = new Gson().toJson(AssociadoDTO.fromEntity(AssociadoBuilder.fabaoSemId().getAssociado()));

		doThrow(AssociadoExisteException.class).when(service).criar(AssociadoBuilder.fabaoSemId().getAssociado());

		mockMvc.perform(post("/associados")
				.contentType(APPLICATION_JSON)
				.content(jsonBody))
		.andExpect(status().isUnprocessableEntity());
		verify(service,times(1)).criar(AssociadoBuilder.fabaoSemId().getAssociado());
	}
	
	@Test
	@DisplayName("Deve retornar 204 quando deletar")
	void deveRetornar204QuandoDeletar() throws Exception {

		doNothing().when(service).deletar(1L);

		mockMvc.perform(delete(String.format("/associados/%s", 1L)))
				.andExpect(status().isNoContent());
		verify(service,times(1)).deletar(1L);
	}
	
	@Test
	@DisplayName("Deve retornar 404 quando id inexistente")
	void deveRetornar404QuandoDeletarIdInexistente() throws Exception {

		doThrow(AssociadoNaoExisteException.class).when(service).deletar(5L);

		mockMvc.perform(delete(String.format("/associados/%s", 5L)))
				.andExpect(status().isNotFound());
		verify(service,times(1)).deletar(5L);
	}
	
	@Test
	@DisplayName("Deve retornar 200 quando cpf existe")
	void deveRetornar200QuandoBuscarCPFExistente() throws Exception {

		String jsonBody = new Gson().toJson(AssociadoDTO.fromEntity(AssociadoBuilder.fabaoId1().getAssociado()));
		
        when(service.obterPorCpf(AssociadoDTO.fromEntity(AssociadoBuilder.fabaoId1().getAssociado()).getCpf())).thenReturn(AssociadoBuilder.fabaoId1().getAssociado());

		mockMvc.perform(get(String.format("/associados/%s", AssociadoBuilder.fabaoId1().getAssociado().getCpf()))
				.contentType(APPLICATION_JSON)
				.content(jsonBody))
		.andExpect(status().isOk());
		verify(service,times(1)).obterPorCpf(AssociadoBuilder.fabaoId1().getAssociado().getCpf());
	}
	
	@Test
	@DisplayName("Deve retornar 404 quando cpf não existe")	
	void deveRetornar404QuandoBuscarCPFInexistente() throws Exception {
		
		doThrow(AssociadoNaoExisteException.class).when(service).obterPorCpf("555555555555");

		mockMvc.perform(get(String.format("/associados/%s", "555555555555")))
		.andExpect(status().isNotFound());
		verify(service,times(1)).obterPorCpf("555555555555");
	}
	
	@Test
	@DisplayName("Deve retornar 200 quando listar todos")
	void deveRetornar200QuandoListar() throws Exception {
		
		List<AssociadoDTO> lista = List.of(
				AssociadoDTO.fromEntity(AssociadoBuilder.fabaoId1().getAssociado()),
				AssociadoDTO.fromEntity(AssociadoBuilder.pedroId2().getAssociado()));

		String jsonBody = new Gson().toJson(lista);
		
		when(service.listar()).thenReturn(lista.stream().map(item -> AssociadoDTO.fromDTO(item)).collect(Collectors.toList()));

        mockMvc.perform(get("/associados")
                        .contentType(APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk());
        verify(service,times(1)).listar();
	}


}
