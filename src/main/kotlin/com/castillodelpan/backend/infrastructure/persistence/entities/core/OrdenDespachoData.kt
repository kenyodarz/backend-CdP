package com.castillodelpan.backend.infrastructure.persistence.entities.core

import com.castillodelpan.backend.domain.models.enums.EstadoOrden
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "ordenes_despacho")
data class OrdenDespachoData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    var idOrden: Int? = null,

    @Column(name = "numero_orden", nullable = false, unique = true, length = 20)
    @NotBlank
    var numeroOrden: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    var cliente: ClienteData,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado", nullable = false)
    var empleado: EmpleadoData,

    @Column(name = "fecha_orden", nullable = false)
    var fechaOrden: LocalDate = LocalDate.now(),

    @Column(name = "fecha_entrega_programada")
    var fechaEntregaProgramada: LocalDate? = null,

    @Column(precision = 10, scale = 2)
    var subtotal: BigDecimal = BigDecimal.ZERO,

    @Column(precision = 10, scale = 2)
    var descuento: BigDecimal = BigDecimal.ZERO,

    @Column(precision = 10, scale = 2)
    var total: BigDecimal = BigDecimal.ZERO,

    @Column(columnDefinition = "TEXT")
    var observaciones: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    var estado: EstadoOrden = EstadoOrden.PENDIENTE,

    @OneToMany(mappedBy = "orden", cascade = [CascadeType.ALL], orphanRemoval = true)
    var detalles: MutableList<DetalleOrdenData> = mutableListOf()
) : EntidadAuditable() {

    fun calcularTotal() {
        subtotal = detalles.sumOf { it.subtotal }
        total = subtotal - descuento
    }

    fun agregarDetalle(detalle: DetalleOrdenData) {
        detalles.add(detalle)
        detalle.orden = this
        calcularTotal()
    }
}