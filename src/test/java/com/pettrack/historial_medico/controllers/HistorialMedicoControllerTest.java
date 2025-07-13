package com.pettrack.historial_medico.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pettrack.historial_medico.models.HistorialMedico;
import com.pettrack.historial_medico.services.HistorialMedicoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HistorialMedicoController.class)

class HistorialMedicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HistorialMedicoService historialService;

    @Autowired
    private ObjectMapper objectMapper;

    private HistorialMedico sample() {
        HistorialMedico h = new HistorialMedico();
        h.setId(1L);
        h.setIdMascota(100L);
        h.setIdUsuario(200L);
        h.setFecha(LocalDate.of(2024, 1, 10));
        h.setDiagnostico("Resfriado");
        h.setTratamiento("Antibióticos");
        h.setObservaciones("Revisar en 1 semana");
        return h;
    }

    @Test
    void debeListarTodosLosHistoriales() throws Exception {
        when(historialService.findAll()).thenReturn(List.of(sample()));

        mockMvc.perform(get("/historial_medico"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].diagnostico").value("Resfriado"));
    }

    @Test
    void debeObtenerHistorialPorId() throws Exception {
        when(historialService.findById(1L)).thenReturn(sample());

        mockMvc.perform(get("/historial_medico/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.tratamiento").value("Antibióticos"));
    }

    @Test
    void debeRetornar404SiNoExisteHistorialPorId() throws Exception {
        when(historialService.findById(99L)).thenReturn(null);

        mockMvc.perform(get("/historial_medico/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void debeObtenerHistorialesPorIdMascota() throws Exception {
        when(historialService.findByIdMascota(100L)).thenReturn(List.of(sample()));

        mockMvc.perform(get("/historial_medico/mascota/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].idMascota").value(100));
    }

    @Test
    void debeCrearHistorialMedico() throws Exception {
        HistorialMedico entrada = sample();
        entrada.setId(null);

        HistorialMedico guardado = sample();

        when(historialService.save(any(HistorialMedico.class))).thenReturn(guardado);

        mockMvc.perform(post("/historial_medico")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void debeActualizarHistorialMedicoSiExiste() throws Exception {
        HistorialMedico actualizado = sample();
        actualizado.setDiagnostico("Actualizado");

        when(historialService.findById(1L)).thenReturn(sample());
        when(historialService.save(any(HistorialMedico.class))).thenReturn(actualizado);

        mockMvc.perform(put("/historial_medico/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.diagnostico").value("Actualizado"));
    }

    @Test
    void debeRetornar404SiNoExisteAlActualizar() throws Exception {
        HistorialMedico h = sample();
        h.setDiagnostico("No existe");

        when(historialService.findById(999L)).thenReturn(null);

        mockMvc.perform(put("/historial_medico/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(h)))
                .andExpect(status().isNotFound());
    }

    @Test
    void debeEliminarHistorialMedico() throws Exception {
        doNothing().when(historialService).deleteById(1L);

        mockMvc.perform(delete("/historial_medico/1"))
                .andExpect(status().isNoContent());

        verify(historialService, times(1)).deleteById(1L);
    }
}
