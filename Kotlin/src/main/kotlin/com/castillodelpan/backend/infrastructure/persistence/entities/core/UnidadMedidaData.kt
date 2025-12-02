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

@Entity
@Table(name = "unidades_medida")
data class UnidadMedidaData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unidad")
    var idUnidad: Int? = null,

    @Column(nullable = false, unique = true, length = 10)
    @NotBlank
    var codigo: String,

    @Column(nullable = false, length = 50)
    @NotBlank
    var nombre: String,

    @Column(nullable = false, length = 10)
    @NotBlank
    var abreviatura: String,

    @Enumerated(EnumType.STRING)
    var estado: EstadoGeneral = EstadoGeneral.ACTIVO
)