package com.castillodelpan.backend.infrastructure.persistence.entities.core

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "empleados")
data class EmpleadoData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    var idEmpleado: Int? = null,

    @Column(name = "numero_documento", nullable = false, unique = true, length = 20)
    @NotBlank
    var numeroDocumento: String,

    @Column(nullable = false, length = 100)
    @NotBlank
    var nombres: String,

    @Column(nullable = false, length = 100)
    @NotBlank
    var apellidos: String,

    @Column(length = 20)
    var telefono: String? = null,

    @Column(length = 100)
    var email: String? = null,

    @Column(length = 100)
    var cargo: String? = null,

    @Column(name = "fecha_ingreso")
    var fechaIngreso: LocalDate? = null,

    @Enumerated(EnumType.STRING)
    var estado: EstadoGeneral = EstadoGeneral.ACTIVO,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
) {
    val nombreCompleto: String
        get() = "$nombres $apellidos"
}