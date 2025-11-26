package com.castillodelpan.backend.infrastructure.persistence.entities.core

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral.ACTIVO
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

@Entity
@Table(name = "conductores")
data class ConductorData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conductor")
    var idConductor: Int? = null,

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

    @Column(length = 50)
    var licencia: String? = null,

    @Enumerated(EnumType.STRING)
    var estado: EstadoGeneral = ACTIVO,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
) {
    val nombreCompleto: String
        get() = "$nombres $apellidos"
}