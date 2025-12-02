package com.castillodelpan.backend.application.usecases.producto

import com.castillodelpan.backend.domain.models.enums.EstadoProducto
import com.castillodelpan.backend.domain.models.producto.Producto
import com.castillodelpan.backend.domain.repositories.ProductoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Desactivar Producto
 */
@Service
@Transactional
class DesactivarProductoUseCase(
    private val productoRepository: ProductoRepository
) {
    operator fun invoke(id: Int): Producto {
        val producto = productoRepository.findById(id)
            ?: throw IllegalArgumentException("Producto no encontrado con ID: $id")

        val productoDesactivado = producto.copy(estado = EstadoProducto.INACTIVO)
        return productoRepository.save(productoDesactivado)
    }
}