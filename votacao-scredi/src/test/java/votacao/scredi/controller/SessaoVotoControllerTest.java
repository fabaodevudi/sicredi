package votacao.scredi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import votacao.scredi.builders.VotoBuilder;
import votacao.scredi.controller.v1.SessaoVotoController;
import votacao.scredi.dto.PautaDTO;
import votacao.scredi.dto.VotoDTO;
import votacao.scredi.entity.Pauta;
import votacao.scredi.service.SessaoService;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SessaoVotoController.class)
public class SessaoVotoControllerTest {
	
	public static final Long ID = 1L;
	
	@MockBean
	private SessaoService service;

	@Autowired
	private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
	
	
	@Test
    @DisplayName("Deve retornar status 201 quando criada nova sessão com duracao padrao")
    void deveRetornar201QuandoSessaoForAbertaComDuracaoPadrao() throws Exception {
		Pauta pauta = new Pauta();
		pauta.setId(ID);
        doNothing().when(service).criar(PautaDTO.fromEntity(pauta), null);
        
        mockMvc.perform(post("/v1/sessoes")
                .param("idPauta","1"))
                .andExpect(status().isCreated());
        verify(service, times(1)).criar(PautaDTO.fromEntity(pauta), null);
    }

    @Test
    @DisplayName("Deve retornar status 201 quando criada nova sessão com duracao especifica")
    void deveRetornar201QuandoSessaoForAbertaComDuracaoEspecifica() throws Exception {
        Pauta pauta = new Pauta();
        pauta.setId(ID);
        Long duracaoMinutos = 5L;

        // O método criar agora espera um Integer duracaoMinutos
        doNothing().when(service).criar(PautaDTO.fromEntity(pauta), duracaoMinutos);

        mockMvc.perform(post("/v1/sessoes")
                        .param("idPauta", "1")
                        .param("duracaoMinutos", String.valueOf(duracaoMinutos))) // Passar a duração em str
                .andExpect(status().isCreated());
        // Verifique se o método criar foi chamado com a duração de 5m
        verify(service, times(1)).criar(PautaDTO.fromEntity(pauta), duracaoMinutos);
    }
	
	@Test
    @DisplayName("Deve retornar status 201 quando votar")
    void deveRetornar201QuandoVotar() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(VotoDTO.fromEntity(VotoBuilder.votoSim().getVoto()));

        doNothing().when(service).votar(org.mockito.ArgumentMatchers.eq(ID), org.mockito.ArgumentMatchers.any(votacao.scredi.entity.Voto.class));
        
        mockMvc.perform(post(String.format("/v1/sessoes/%s/voto", ID))
		        .contentType(APPLICATION_JSON)
				.content(jsonBody))
                .andExpect(status().isCreated());
        verify(service, times(1)).votar(org.mockito.ArgumentMatchers.eq(ID), org.mockito.ArgumentMatchers.any(votacao.scredi.entity.Voto.class));
    }

}
