package votacao.scredi.controller;

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

import votacao.scredi.builders.PautaBuilder;
import votacao.scredi.controller.PautaController;
import votacao.scredi.dto.PautaDTO;
import votacao.scredi.exception.AssociadoExisteException;
import votacao.scredi.exception.AssociadoNaoExisteException;
import votacao.scredi.service.PautaService;

@WebMvcTest(controllers = PautaController.class)
public class PautaControllerTest {
	
	@MockBean
	private PautaService service;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("Deve retornar 201 na criação")
	void deveRetornar201CasoSucesso() throws Exception {
		String jsonBody = new Gson().toJson(PautaDTO.fromEntity(PautaBuilder.pautaAumentoSalarioSemId().getPauta()));

		doNothing().when(service).criar(PautaBuilder.pautaAumentoSalarioSemId().getPauta());

		mockMvc.perform(post("/pautas")
				.contentType(APPLICATION_JSON)
				.content(jsonBody))
		.andExpect(status().isCreated());
		verify(service,times(1)).criar(PautaBuilder.pautaAumentoSalarioSemId().getPauta());
	}	
	
	
	@Test
	@DisplayName("Deve retornar 422 na criação se pauta existe")
	void deveRetornar422CasoUsuarioExista() throws Exception {
		String jsonBody = new Gson().toJson(PautaDTO.fromEntity(PautaBuilder.pautaAumentoSalarioSemId().getPauta()));

		doThrow(AssociadoExisteException.class).when(service).criar(PautaBuilder.pautaAumentoSalarioSemId().getPauta());

		mockMvc.perform(post("/pautas")
				.contentType(APPLICATION_JSON)
				.content(jsonBody))
		.andExpect(status().isUnprocessableEntity());
		verify(service,times(1)).criar(PautaBuilder.pautaAumentoSalarioSemId().getPauta());
	}
	
	@Test
	@DisplayName("Deve retornar 204 quando deletar")
	void deveRetornar204QuandoDeletar() throws Exception {

		doNothing().when(service).deletar(1L);

		mockMvc.perform(delete(String.format("/pautas/%s", 1L)))
				.andExpect(status().isNoContent());
		verify(service,times(1)).deletar(1L);
	}
	
	@Test
	@DisplayName("Deve retornar 404 quando id inexistente")
	void deveRetornar404QuandoDeletarIdInexistente() throws Exception {

		doThrow(AssociadoNaoExisteException.class).when(service).deletar(5L);

		mockMvc.perform(delete(String.format("/pautas/%s", 5L)))
				.andExpect(status().isNotFound());
		verify(service,times(1)).deletar(5L);
	}
	
	@Test
	@DisplayName("Deve retornar 200 quando id existe")
	void deveRetornar200QuandoBuscarIdExistente() throws Exception {

		String jsonBody = new Gson().toJson(PautaDTO.fromEntity(PautaBuilder.pautaAumentoSalario().getPauta()));
		
        when(service.obterPorId(PautaBuilder.pautaAumentoSalario().getPauta().getId())).thenReturn(PautaBuilder.pautaAumentoSalario().getPauta());

		mockMvc.perform(get(String.format("/pautas/%s", PautaBuilder.pautaAumentoSalario().getPauta().getId()))
				.contentType(APPLICATION_JSON)
				.content(jsonBody))
		.andExpect(status().isOk());
		verify(service,times(1)).obterPorId(PautaBuilder.pautaAumentoSalario().getPauta().getId());
	}
	
	@Test
	@DisplayName("Deve retornar 404 quando id não existe")	
	void deveRetornar404QuandoBuscarIdInexistente() throws Exception {
		
		doThrow(AssociadoNaoExisteException.class).when(service).obterPorId(88L);

		mockMvc.perform(get(String.format("/pautas/%s", 88L)))
		.andExpect(status().isNotFound());
		verify(service,times(1)).obterPorId(88L);
	}
	
	@Test
	@DisplayName("Deve retornar 200 quando listar todos")
	void deveRetornar200QuandoListar() throws Exception {
		
		List<PautaDTO> lista = List.of(
				PautaDTO.fromEntity(PautaBuilder.pautaAumentoSalario().getPauta()),
				PautaDTO.fromEntity(PautaBuilder.pautaMudancaPlanoSaude().getPauta()));

		String jsonBody = new Gson().toJson(lista);
		
		when(service.listar()).thenReturn(lista.stream().map(item -> PautaDTO.fromDTO(item)).collect(Collectors.toList()));

        mockMvc.perform(get("/pautas")
                        .contentType(APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk());
        verify(service,times(1)).listar();
	}
	
	

}
