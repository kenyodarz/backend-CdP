package com.castillodelpan.backend.application.usecases.reportes

import com.castillodelpan.backend.domain.models.enums.EstadoOrden
import com.castillodelpan.backend.domain.models.reportes.ProductoVendido
import com.castillodelpan.backend.domain.repositories.OrdenDespachoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

/**
 * Use Case: Reporte de Productos Más Vendidos
 */
@Service
@Transactional(readOnly = true)
class ReporteProductosMasVendidosUseCase(
    private val ordenRepository: OrdenDespachoRepository
) {
    operator fun invoke(
        fechaInicio: LocalDate,
        fechaFin: LocalDate,
        limite: Int = 10
    ): List<ProductoVendido> {
        val ordenes = ordenRepository.findByFechaOrdenBetween(fechaInicio, fechaFin)
            .filter { it.estado in listOf(EstadoOrden.DESPACHADA, EstadoOrden.ENTREGADA) }

        // Agrupar productos
        val productosVendidos = mutableMapOf<Int, ProductoVendido>()

        ordenes.forEach { orden ->
            orden.detalles.forEach { detalle ->
                val idProducto = detalle.producto.idProducto!!
                val actual = productosVendidos[idProducto]

                if (actual == null) {
                    productosVendidos[idProducto] = ProductoVendido(
                        idProducto = idProducto,
                        nombreProducto = detalle.producto.nombre,
                        cantidadVendida = detalle.cantidad,
                        valorTotal = detalle.subtotal,
                        numeroOrdenes = 1
                    )
                } else {
                    productosVendidos[idProducto] = actual.copy(
                        cantidadVendida = actual.cantidadVendida + detalle.cantidad,
                        valorTotal = actual.valorTotal + detalle.subtotal,
                        numeroOrdenes = actual.numeroOrdenes + 1
                    )
                }
            }
        }

        return productosVendidos.values
            .sortedByDescending { it.cantidadVendida }
            .take(limite)
    }
}