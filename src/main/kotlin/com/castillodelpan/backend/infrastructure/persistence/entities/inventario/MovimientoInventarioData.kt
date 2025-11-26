package com.castillodelpan.backend.infrastructure.persistence.entities.inventario

import com.castillodelpan.backend.domain.models.enums.TipoMovimiento
import com.castillodelpan.backend.infrastructure.persistence.entities.core.ProductoData
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
import jakarta.persistence.Table
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

@Entity
@Table(name = "movimientos_inventario")
data class MovimientoInventario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    var idMovimiento: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    var producto: ProductoData,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lote")
    var lote: LoteData? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento", nullable = false, length = 20)
    var tipoMovimiento: TipoMovimiento,

    @Column(nullable = false)
    @Min(1)
    var cantidad: Int,

    @Column(name = "stock_anterior", nullable = false)
    var stockAnterior: Int,

    @Column(name = "stock_nuevo", nullable = false)
    var stockNuevo: Int,

    @Column(nullable = false, length = 200)
    @NotBlank
    var motivo: String,

    @Column(length = 100)
    var referencia: String? = null,

    @Column(columnDefinition = "TEXT")
    var observaciones: String? = null,

    @Column(name = "id_usuario")
    var idUsuario: Int? = null,

    @Column(name = "fecha_movimiento")
    var fechaMovimiento: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun crearEntrada(
            producto: ProductoData,
            cantidad: Int,
            motivo: String,
            lote: LoteData? = null,
            referencia: String? = null,
            observaciones: String? = null,
            idUsuario: Int? = null
        ): MovimientoInventario {
            val stockAnterior = producto.stockActual
            val stockNuevo = stockAnterior + cantidad

            return MovimientoInventario(
                producto = producto,
                lote = lote,
                tipoMovimiento = TipoMovimiento.ENTRADA,
                cantidad = cantidad,
                stockAnterior = stockAnterior,
                stockNuevo = stockNuevo,
                motivo = motivo,
                referencia = referencia,
                observaciones = observaciones,
                idUsuario = idUsuario
            )
        }

        fun crearSalida(
            producto: ProductoData,
            cantidad: Int,
            motivo: String,
            lote: LoteData? = null,
            referencia: String? = null,
            observaciones: String? = null,
            idUsuario: Int? = null
        ): MovimientoInventario {
            val stockAnterior = producto.stockActual
            val stockNuevo = stockAnterior - cantidad

            return MovimientoInventario(
                producto = producto,
                lote = lote,
                tipoMovimiento = TipoMovimiento.SALIDA,
                cantidad = cantidad,
                stockAnterior = stockAnterior,
                stockNuevo = stockNuevo,
                motivo = motivo,
                referencia = referencia,
                observaciones = observaciones,
                idUsuario = idUsuario
            )
        }
    }
}