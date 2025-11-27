package com.castillodelpan.backend.application.usecases.producto

import com.castillodelpan.backend.domain.models.producto.Producto
import com.castillodelpan.backend.domain.repositories.ProductoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Obtener Productos por Categoría
 */
@Service
@Transactional(readOnly = true)
class ObtenerProductosPorCategoriaUseCase(
    private val productoRepository: ProductoRepository
) {
    operator fun invoke(idCategoria: Int): List<Producto> {
        return productoRepository.findByCategoria(idCategoria)
    }
}