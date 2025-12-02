package com.castillodelpan.backend.application.usecases.producto

import com.castillodelpan.backend.domain.models.producto.Producto
import com.castillodelpan.backend.domain.repositories.ProductoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Obtener Productos con Stock Bajo
 */
@Service
@Transactional(readOnly = true)
class ObtenerProductosConStockBajoUseCase(
    private val productoRepository: ProductoRepository
) {
    operator fun invoke(): List<Producto> {
        return productoRepository.findProductosConStockBajo()
    }
}