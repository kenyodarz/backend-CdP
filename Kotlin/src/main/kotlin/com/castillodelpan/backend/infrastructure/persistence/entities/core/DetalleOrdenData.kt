package com.castillodelpan.backend.infrastructure.persistence.entities.core

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import jakarta.persistence.Table
import jakarta.validation.constraints.Min
import java.math.BigDecimal

@Entity
@Table(name = "detalle_ordenes")
data class DetalleOrdenData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    var idDetalle: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_orden", nullable = false)
    @JsonIgnore
    var orden: OrdenDespachoData,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    var producto: ProductoData,

    @Column(nullable = false)
    @Min(1)
    var cantidad: Int,

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    var precioUnitario: BigDecimal,

    @Column(nullable = false, precision = 10, scale = 2)
    var subtotal: BigDecimal
) {
    @PrePersist
    @PreUpdate
    fun calcularSubtotal() {
        subtotal = precioUnitario.multiply(BigDecimal(cantidad))
    }
}