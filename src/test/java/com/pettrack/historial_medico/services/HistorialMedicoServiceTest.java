package com.pettrack.historial_medico.services;

import com.pettrack.historial_medico.models.HistorialMedico;
import com.pettrack.historial_medico.repositories.HistorialMedicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistorialMedicoServiceTest {
    @Mock
    private HistorialMedicoRepository historialRepository;

    @InjectMocks
    private HistorialMedicoService historialService;

    private HistorialMedico historial1;
    private HistorialMedico historial2;

    @BeforeEach
    void setUp() {
        historial1 = new HistorialMedico();
        historial1.setId(1L);
        historial1.setIdMascota(100L);
        historial1.setIdUsuario(200L);
        historial1.setFecha(LocalDate.of(2023, 5, 10));
        historial1.setDiagnostico("Control anual");
        historial1.setTratamiento("Vacunación");
        historial1.setObservaciones("Mascota en buen estado");

        historial2 = new HistorialMedico();
        historial2.setId(2L);
        historial2.setIdMascota(100L);
        historial2.setIdUsuario(200L);
        historial2.setFecha(LocalDate.of(2023, 6, 15));
        historial2.setDiagnostico("Dermatitis");
        historial2.setTratamiento("Antibióticos");
        historial2.setObservaciones("Seguimiento en 2 semanas");
    }

    @Test
    void findAll_deberiaRetornarTodosLosHistoriales() {
        when(historialRepository.findAll()).thenReturn(Arrays.asList(historial1, historial2));

        List<HistorialMedico> resultado = historialService.findAll();

        assertEquals(2, resultado.size());
        verify(historialRepository, times(1)).findAll();
    }

    @Test
    void findById_deberiaRetornarHistorialCuandoExiste() {
        when(historialRepository.findById(1L)).thenReturn(Optional.of(historial1));

        HistorialMedico resultado = historialService.findById(1L);

        assertNotNull(resultado);
        assertEquals("Control anual", resultado.getDiagnostico());
    }

    @Test
    void findById_deberiaRetornarNullCuandoNoExiste() {
        when(historialRepository.findById(99L)).thenReturn(Optional.empty());

        HistorialMedico resultado = historialService.findById(99L);

        assertNull(resultado);
    }

    @Test
    void findByIdMascota_deberiaRetornarHistorialesDeMascota() {
        when(historialRepository.findByIdMascota(100L)).thenReturn(Arrays.asList(historial1, historial2));

        List<HistorialMedico> resultado = historialService.findByIdMascota(100L);

        assertEquals(2, resultado.size());
        assertEquals(100L, resultado.get(0).getIdMascota());
        assertEquals(100L, resultado.get(1).getIdMascota());
    }

    @Test
    void save_deberiaGuardarHistorial() {
        when(historialRepository.save(any(HistorialMedico.class))).thenReturn(historial1);

        HistorialMedico nuevoHistorial = new HistorialMedico();
        nuevoHistorial.setIdMascota(100L);
        nuevoHistorial.setDiagnostico("Control anual");

        HistorialMedico resultado = historialService.save(nuevoHistorial);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(historialRepository, times(1)).save(nuevoHistorial);
    }

    @Test
    void deleteById_deberiaEliminarHistorial() {
        doNothing().when(historialRepository).deleteById(1L);

        historialService.deleteById(1L);

        verify(historialRepository, times(1)).deleteById(1L);
    }
}
