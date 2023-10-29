package votacao.scredi;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import votacao.scredi.builders.AssociadoBuilder;
import votacao.scredi.controller.AssociadoController;
import votacao.scredi.dto.AssociadoDTO;
import votacao.scredi.service.AssociadoService;

@WebMvcTest(controllers = AssociadoController.class)
public class AssociadoControllerTest {
	
	 @MockBean
	 private AssociadoService service;
	
	 @Autowired
	 private MockMvc mockMvc;
	 
	 @Test
     @DisplayName("Deve retornar status 201 na criação")
     void deveRetornar201CasoSucesso() throws Exception {
        String jsonBody = new Gson().toJson(AssociadoDTO.fromEntity(AssociadoBuilder.fabaoSemId().getAssociado()));

        doNothing().when(service).criar(AssociadoBuilder.fabaoSemId().getAssociado());

        mockMvc.perform(post("/associados")
        		.contentType(APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isCreated());
        verify(service,times(1)).criar(AssociadoBuilder.fabaoSemId().getAssociado());
    }

}
