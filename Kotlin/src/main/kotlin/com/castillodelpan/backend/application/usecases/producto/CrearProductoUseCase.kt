package com.castillodelpan.backend.application.usecases.producto

import com.castillodelpan.backend.domain.models.producto.Producto
import com.castillodelpan.backend.domain.repositories.ProductoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Crear Producto
 */
@Service
@Transactional
class CrearProductoUseCase(
    private val productoRepository: ProductoRepository
) {
    operator fun invoke(producto: Producto): Producto {
        // Validaciones de negocio
        require(producto.nombre.isNotBlank()) { "El nombre del producto es obligatorio" }

        producto.codigo?.let { codigo ->
            require(!productoRepository.existsByCodigo(codigo)) {
                "Ya existe un producto con el código: $codigo"
            }
        }

        require(producto.stockMinimo >= 0) { "El stock mínimo no puede ser negativo" }
        require(producto.stockActual >= 0) { "El stock actual no puede ser negativo" }

        producto.stockMaximo?.let { max ->
            require(max >= producto.stockMinimo) {
                "El stock máximo debe ser mayor o igual al stock mínimo"
            }
        }

        if (producto.requiereLote) {
            require(producto.diasVidaUtil != null && producto.diasVidaUtil > 0) {
                "Los productos que requieren lote deben tener días de vida útil definidos"
            }
        }

        return productoRepository.save(producto)
    }
}

