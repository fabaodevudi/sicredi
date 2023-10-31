package votacao.scredi.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import votacao.scredi.builders.VotoBuilder;
import votacao.scredi.dto.VotoDTO;
import votacao.scredi.entity.Pauta;
import votacao.scredi.service.SessaoService;

@WebMvcTest(controllers = SessaoVotoController.class)
public class SessaoVotoControllerTest {
	
	public static final Long ID = 1L;
	
	@MockBean
	private SessaoService service;

	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
    @DisplayName("Deve retornar status 201 quando criada nova sessão")
    void deveRetornar201QuandoSessaoForAberta() throws Exception {
				
		Pauta pauta = new Pauta();
		pauta.setId(ID);
        doNothing().when(service).criar(pauta);
        
        mockMvc.perform(post("/sessoes")
                .param("idPauta","1"))
                .andExpect(status().isCreated());
        verify(service, times(1)).criar(pauta);
    }
	
	@Test
    @DisplayName("Deve retornar status 201 quando votar")
    void deveRetornar201QuandoVotar() throws Exception {
		String jsonBody = new Gson().toJson(VotoDTO.fromEntity(VotoBuilder.votoSim().getVoto()));
				
        doNothing().when(service).votar(ID, VotoBuilder.votoSim().getVoto());
        
        mockMvc.perform(post(String.format("/sessoes/%s/voto", ID))
		        .contentType(APPLICATION_JSON)
				.content(jsonBody))
                .andExpect(status().isCreated());
        verify(service, times(1)).votar(ID, VotoBuilder.votoSim().getVoto());
    }

}
