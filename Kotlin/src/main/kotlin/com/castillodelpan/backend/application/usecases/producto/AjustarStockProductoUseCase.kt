package com.castillodelpan.backend.application.usecases.producto

import com.castillodelpan.backend.domain.models.producto.Producto
import com.castillodelpan.backend.domain.repositories.ProductoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Ajustar Stock del Producto
 */
@Service
@Transactional
class AjustarStockProductoUseCase(
    private val productoRepository: ProductoRepository
) {
    operator fun invoke(idProducto: Int, nuevaCantidad: Int, motivo: String): Producto {
        require(nuevaCantidad >= 0) { "La cantidad no puede ser negativa" }
        require(motivo.isNotBlank()) { "Debe especificar un motivo para el ajuste" }

        val producto = productoRepository.findById(idProducto)
            ?: throw IllegalArgumentException("Producto no encontrado con ID: $idProducto")

        val productoAjustado = producto.copy(stockActual = nuevaCantidad)

        // Aquí se debería crear un movimiento de inventario tipo AJUSTE
        // Eso lo haremos en el MovimientoInventarioUseCase

        return productoRepository.save(productoAjustado)
    }
}