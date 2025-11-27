package com.castillodelpan.backend.application.usecases.movimientos

import com.castillodelpan.backend.domain.models.inventario.MovimientoInventarioDomain
import com.castillodelpan.backend.infrastructure.persistence.entities.inventario.MovimientoInventario
import com.castillodelpan.backend.infrastructure.persistence.mappers.MovimientoInventarioMapper
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaLoteRepository
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaMovimientoInventarioRepository
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaProductoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Registrar Salida de Inventario
 */
@Service
@Transactional
class RegistrarSalidaInventarioUseCase(
    private val jpaProductoRepository: JpaProductoRepository,
    private val jpaLoteRepository: JpaLoteRepository,
    private val jpaMovimientoInventarioRepository: JpaMovimientoInventarioRepository
) {
    operator fun invoke(
        idProducto: Int,
        cantidad: Int,
        motivo: String,
        codigoLote: String? = null,
        referencia: String? = null,
        observaciones: String? = null,
        idUsuario: Int? = null
    ): MovimientoInventarioDomain {

        require(cantidad > 0) { "La cantidad debe ser mayor a 0" }
        require(motivo.isNotBlank()) { "El motivo es obligatorio" }

        val productoData = jpaProductoRepository.findById(idProducto)
            .orElseThrow { IllegalArgumentException("Producto no encontrado: $idProducto") }

        // Validar stock disponible
        require(productoData.stockActual >= cantidad) {
            "Stock insuficiente. Disponible: ${productoData.stockActual}, Solicitado: $cantidad"
        }

        // Buscar lote si se proporciona
        val loteData = codigoLote?.let { codigo ->
            jpaLoteRepository.findByCodigoLote(codigo)
                .orElseThrow { IllegalArgumentException("Lote no encontrado: $codigo") }
        }

        // Validar stock del lote
        loteData?.let {
            require(it.cantidadActual >= cantidad) {
                "Stock insuficiente en el lote. Disponible: ${it.cantidadActual}, Solicitado: $cantidad"
            }
        }

        // Crear movimiento
        val movimiento = MovimientoInventario.Companion.crearSalida(
            producto = productoData,
            cantidad = cantidad,
            motivo = motivo,
            lote = loteData,
            referencia = referencia,
            observaciones = observaciones,
            idUsuario = idUsuario
        )

        // Actualizar stock del producto
        productoData.stockActual -= cantidad
        jpaProductoRepository.save(productoData)

        // Actualizar lote si existe
        loteData?.let {
            it.cantidadActual -= cantidad
            jpaLoteRepository.save(it)
        }

        // Guardar movimiento
        val movimientoGuardado = jpaMovimientoInventarioRepository.save(movimiento)

        return MovimientoInventarioMapper.toDomain(
            movimientoGuardado
        )
    }
}