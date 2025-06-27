package com.pettrack.historial_medico.services;

import org.springframework.stereotype.Service;

import com.pettrack.historial_medico.models.HistorialMedico;
import com.pettrack.historial_medico.repositories.HistorialMedicoRepository;

import java.util.List;

@Service
public class HistorialMedicoService {
  private final HistorialMedicoRepository repo;

  public HistorialMedicoService(HistorialMedicoRepository repo) {
    this.repo = repo;
  }

  public List<HistorialMedico> findAll() {
    return repo.findAll();
  }

  public HistorialMedico findById(Long id) {
    return repo.findById(id).orElse(null);
  }

  public List<HistorialMedico> findByIdMascota(Long idMascota) {
    return repo.findByIdMascota(idMascota);
  }

  public HistorialMedico save(HistorialMedico historial) {
    return repo.save(historial);
  }

  public void deleteById(Long id) {
    repo.deleteById(id);
  }
}
