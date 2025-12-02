package com.castillodelpan.backend.application.usecases.inventario

import com.castillodelpan.backend.domain.models.inventario.Lote
import com.castillodelpan.backend.infrastructure.persistence.entities.inventario.LoteData
import com.castillodelpan.backend.infrastructure.persistence.entities.inventario.MovimientoInventario
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaLoteRepository
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaMovimientoInventarioRepository
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaProductoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

// ============================================
// LOTES - Use Cases
// ============================================

/**
 * Use Case: Crear Lote de Producto
 */
@Service
@Transactional
class CrearLoteUseCase(
    private val jpaLoteRepository: JpaLoteRepository,
    private val jpaProductoRepository: JpaProductoRepository,
    private val jpaMovimientoInventarioRepository: JpaMovimientoInventarioRepository
) {
    operator fun invoke(
        idProducto: Int,
        codigoLote: String,
        fechaElaboracion: LocalDate,
        fechaVencimiento: LocalDate?,
        cantidad: Int,
        idUsuario: Int? = null
    ): Lote {
        require(cantidad > 0) { "La cantidad debe ser mayor a 0" }

        val productoData = jpaProductoRepository.findById(idProducto)
            .orElseThrow { IllegalArgumentException("Producto no encontrado: $idProducto") }

        // Validar que el producto requiere lote
        require(productoData.requiereLote) {
            "El producto ${productoData.nombre} no requiere control por lotes"
        }

        // Validar fecha de vencimiento
        fechaVencimiento?.let { vencimiento ->
            require(vencimiento.isAfter(fechaElaboracion)) {
                "La fecha de vencimiento debe ser posterior a la fecha de elaboración"
            }
        }

        // Validar código de lote único
        jpaLoteRepository.findByCodigoLote(codigoLote).ifPresent {
            if (it.producto.idProducto == idProducto) {
                throw IllegalArgumentException("Ya existe un lote con el código: $codigoLote para este producto")
            }
        }

        // Crear lote
        val loteData = LoteData(
            idLote = null,
            producto = productoData,
            codigoLote = codigoLote,
            fechaElaboracion = fechaElaboracion,
            fechaVencimiento = fechaVencimiento,
            cantidadInicial = cantidad,
            cantidadActual = cantidad,
            estado = com.castillodelpan.backend.domain.models.enums.EstadoGeneral.ACTIVO
        )

        val loteGuardado = jpaLoteRepository.save(loteData)

        // Actualizar stock del producto
        productoData.stockActual += cantidad
        jpaProductoRepository.save(productoData)

        // Registrar movimiento de entrada
        val movimiento = MovimientoInventario.crearEntrada(
            producto = productoData,
            cantidad = cantidad,
            motivo = "Entrada de producción - Lote: $codigoLote",
            lote = loteGuardado,
            referencia = codigoLote,
            observaciones = "Lote creado: $codigoLote",
            idUsuario = idUsuario
        )
        jpaMovimientoInventarioRepository.save(movimiento)

        // Convertir a dominio
        return com.castillodelpan.backend.infrastructure.persistence.mappers.LoteMapper.toDomain(
            loteGuardado
        )
    }
}


