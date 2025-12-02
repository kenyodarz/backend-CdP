package com.castillodelpan.backend.infrastructure.persistence.entities.core

import com.castillodelpan.backend.domain.models.enums.TipoTarifa
import com.castillodelpan.backend.infrastructure.persistence.converters.TipoTarifaConverter
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.math.BigDecimal

/**
 * Entidad para precios especiales (solo JM y CR) Los demás precios se calculan automáticamente
 * desde el precio base
 */
@Entity
@Table(
    name = "precios_especiales",
    uniqueConstraints = [UniqueConstraint(columnNames = ["id_producto", "tipo_tarifa"])]
)
data class PrecioEspecialData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_precio_especial")
    var idPrecioEspecial: Int? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    @JsonIgnore
    var producto: ProductoData,
    @Convert(converter = TipoTarifaConverter::class)
    @Column(name = "tipo_tarifa", nullable = false, length = 20)
    var tipoTarifa: TipoTarifa,
    @Column(nullable = false, precision = 10, scale = 2)
    var precio: BigDecimal = BigDecimal.ZERO
)
