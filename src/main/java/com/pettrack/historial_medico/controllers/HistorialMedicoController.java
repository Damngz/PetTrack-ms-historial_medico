package com.pettrack.historial_medico.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.pettrack.historial_medico.models.HistorialMedico;
import com.pettrack.historial_medico.services.HistorialMedicoService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/historial_medico")
public class HistorialMedicoController {
  private final HistorialMedicoService service;

  public HistorialMedicoController(HistorialMedicoService service) {
    this.service = service;
  }

  @GetMapping
  public List<HistorialMedico> getAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<HistorialMedico> getById(@PathVariable Long id) {
    HistorialMedico historial = service.findById(id);
    return historial != null ? ResponseEntity.ok(historial) : ResponseEntity.notFound().build();
  }

  @GetMapping("/mascota/{idMascota}")
  public List<HistorialMedico> getByMascota(@PathVariable Long idMascota) {
    return service.findByIdMascota(idMascota);
  }

  @PostMapping
  public HistorialMedico create(@RequestBody HistorialMedico historial) {
    return service.save(historial);
  }

  @PutMapping("/{id}")
  public ResponseEntity<HistorialMedico> update(@PathVariable Long id, @RequestBody HistorialMedico historial) {
    HistorialMedico existing = service.findById(id);
    if (existing == null) {
      return ResponseEntity.notFound().build();
    }
    historial.setId(id);
    return ResponseEntity.ok(service.save(historial));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
