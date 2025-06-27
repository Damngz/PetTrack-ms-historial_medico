package com.pettrack.historial_medico.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "historial_medico")
public class HistorialMedico {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_historial")
  private Long id;

  @Column(name = "id_mascota", nullable = false)
  private Long idMascota;

  @Column(name = "id_usuario", nullable = false)
  private Long idUsuario; // Veterinario

  @Column
  private LocalDate fecha;

  @Column
  private String diagnostico;

  @Column
  private String tratamiento;

  @Column
  private String observaciones;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getIdMascota() {
    return idMascota;
  }

  public void setIdMascota(Long idMascota) {
    this.idMascota = idMascota;
  }

  public Long getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(Long idUsuario) {
    this.idUsuario = idUsuario;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }

  public String getDiagnostico() {
    return diagnostico;
  }

  public void setDiagnostico(String diagnostico) {
    this.diagnostico = diagnostico;
  }

  public String getTratamiento() {
    return tratamiento;
  }

  public void setTratamiento(String tratamiento) {
    this.tratamiento = tratamiento;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }
}
