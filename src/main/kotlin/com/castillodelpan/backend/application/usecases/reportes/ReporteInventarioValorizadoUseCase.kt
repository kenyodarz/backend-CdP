package com.castillodelpan.backend.application.usecases.reportes

import com.castillodelpan.backend.domain.models.enums.TipoTarifa
import com.castillodelpan.backend.domain.models.reportes.ItemInventario
import com.castillodelpan.backend.domain.models.reportes.ReporteInventario
import com.castillodelpan.backend.domain.repositories.ProductoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDate

/**
 * Use Case: Reporte de Inventario Valorizado
 */
@Service
@Transactional(readOnly = true)
class ReporteInventarioValorizadoUseCase(
    private val productoRepository: ProductoRepository
) {
    operator fun invoke(): ReporteInventario {
        val productos = productoRepository.findAll()

        val items = productos.map { producto ->
            val precio = producto.getPrecioPorTarifa(TipoTarifa.`0D`)
            val valorTotal = precio.multiply(BigDecimal(producto.stockActual))

            ItemInventario(
                idProducto = producto.idProducto!!,
                nombreProducto = producto.nombre,
                categoria = producto.categoria.nombre,
                stockActual = producto.stockActual,
                precioUnitario = precio,
                valorTotal = valorTotal
            )
        }

        val valorTotalInventario = items.sumOf { it.valorTotal }
        val numeroProductos = items.size
        val productosStockBajo = items.count { it.stockActual <= 0 }

        return ReporteInventario(
            fecha = LocalDate.now(),
            valorTotalInventario = valorTotalInventario,
            numeroProductos = numeroProductos,
            productosStockBajo = productosStockBajo,
            items = items.sortedByDescending { it.valorTotal }
        )
    }
}