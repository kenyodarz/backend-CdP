package com.castillodelpan.backend.application.usecases.producto

import com.castillodelpan.backend.domain.models.producto.Producto
import com.castillodelpan.backend.domain.repositories.ProductoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Actualizar Producto
 */
@Service
@Transactional
class ActualizarProductoUseCase(
    private val productoRepository: ProductoRepository
) {
    operator fun invoke(id: Int, producto: Producto): Producto {
        val productoExistente = productoRepository.findById(id)
            ?: throw IllegalArgumentException("Producto no encontrado con ID: $id")

        // Validar que no se cambie el código a uno existente
        producto.codigo?.let { nuevoCodigo ->
            if (nuevoCodigo != productoExistente.codigo) {
                require(!productoRepository.existsByCodigo(nuevoCodigo)) {
                    "Ya existe otro producto con el código: $nuevoCodigo"
                }
            }
        }

        // Validaciones de negocio
        require(producto.nombre.isNotBlank()) { "El nombre del producto es obligatorio" }
        require(producto.stockMinimo >= 0) { "El stock mínimo no puede ser negativo" }

        val productoActualizado = producto.copy(idProducto = id)
        return productoRepository.save(productoActualizado)
    }
}