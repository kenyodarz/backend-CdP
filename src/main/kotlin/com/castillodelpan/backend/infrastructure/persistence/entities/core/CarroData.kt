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
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "carros")
data class CarroData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carro")
    var idCarro: Int? = null,

    @Column(nullable = false, unique = true, length = 10)
    @NotBlank
    var placa: String,

    @Column(length = 50)
    var modelo: String? = null,

    @Column(name = "capacidad_kg", precision = 8, scale = 2)
    var capacidadKg: BigDecimal? = null,

    @Enumerated(EnumType.STRING)
    var estado: EstadoGeneral = EstadoGeneral.ACTIVO,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
)