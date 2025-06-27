package com.pettrack.historial_medico.repositories;

import com.pettrack.historial_medico.models.HistorialMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistorialMedicoRepository extends JpaRepository<HistorialMedico, Long> {
  List<HistorialMedico> findByIdMascota(Long idMascota);
}

