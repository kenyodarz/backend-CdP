package com.castillodelpan.backend.application.usecases.producto

import com.castillodelpan.backend.domain.models.producto.Producto
import com.castillodelpan.backend.domain.repositories.ProductoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Buscar Productos por Nombre
 */
@Service
@Transactional(readOnly = true)
class BuscarProductosPorNombreUseCase(
    private val productoRepository: ProductoRepository
) {
    operator fun invoke(nombre: String): List<Producto> {
        require(nombre.length >= 3) {
            "Debe proporcionar al menos 3 caracteres para la búsqueda"
        }
        return productoRepository.findByNombreContaining(nombre)
    }
}