package com.castillodelpan.backend.application.usecases.producto

import com.castillodelpan.backend.domain.models.enums.EstadoProducto
import com.castillodelpan.backend.domain.models.producto.Producto
import com.castillodelpan.backend.domain.repositories.ProductoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Listar Productos Activos
 */
@Service
@Transactional(readOnly = true)
class ListarProductosActivosUseCase(
    private val productoRepository: ProductoRepository
) {
    operator fun invoke(): List<Producto> {
        return productoRepository.findByEstado(EstadoProducto.ACTIVO)
    }
}