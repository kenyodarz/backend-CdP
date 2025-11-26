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
@Table(name = "categorias")
data class CategoriaData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    var idCategoria: Int? = null,

    @Column(nullable = false, unique = true, length = 100)
    @NotBlank
    var nombre: String,

    @Column(columnDefinition = "TEXT")
    var descripcion: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var estado: EstadoGeneral = EstadoGeneral.ACTIVO
) : EntidadAuditable()