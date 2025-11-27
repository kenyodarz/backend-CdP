package com.castillodelpan.backend.application.usecases.producto

import com.castillodelpan.backend.domain.models.producto.Producto
import com.castillodelpan.backend.domain.repositories.ProductoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Obtener Producto por ID
 */
@Service
@Transactional(readOnly = true)
class ObtenerProductoPorIdUseCase(
    private val productoRepository: ProductoRepository
) {
    operator fun invoke(id: Int): Producto {
        return productoRepository.findById(id)
            ?: throw IllegalArgumentException("Producto no encontrado con ID: $id")
    }
}