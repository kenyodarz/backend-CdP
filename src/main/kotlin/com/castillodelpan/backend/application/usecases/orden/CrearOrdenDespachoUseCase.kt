package com.castillodelpan.backend.application.usecases.orden

import com.castillodelpan.backend.domain.models.DetalleOrden
import com.castillodelpan.backend.domain.models.OrdenDespacho
import com.castillodelpan.backend.domain.models.enums.EstadoOrden
import com.castillodelpan.backend.domain.repositories.ClienteRepository
import com.castillodelpan.backend.domain.repositories.EmpleadoRepository
import com.castillodelpan.backend.domain.repositories.OrdenDespachoRepository
import com.castillodelpan.backend.domain.repositories.ProductoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Use Case: Crear Orden de Despacho
 */
@Service
@Transactional
class CrearOrdenDespachoUseCase(
    private val ordenRepository: OrdenDespachoRepository,
    private val clienteRepository: ClienteRepository,
    private val empleadoRepository: EmpleadoRepository,
    private val productoRepository: ProductoRepository
) {
    operator fun invoke(
        idCliente: Int,
        idEmpleado: Int,
        detalles: List<DetalleOrdenRequest>,
        observaciones: String? = null,
        descuento: BigDecimal = BigDecimal.ZERO
    ): OrdenDespacho {

        // Validar cliente
        val cliente = clienteRepository.findById(idCliente)
            ?: throw IllegalArgumentException("Cliente no encontrado con ID: $idCliente")

        // Validar empleado
        val empleado = empleadoRepository.findById(idEmpleado)
            ?: throw IllegalArgumentException("Empleado no encontrado con ID: $idEmpleado")

        // Validar que hay detalles
        require(detalles.isNotEmpty()) { "La orden debe tener al menos un producto" }

        // Crear detalles validando productos
        val detallesOrden = detalles.map { detalle ->
            val producto = productoRepository.findById(detalle.idProducto)
                ?: throw IllegalArgumentException("Producto no encontrado: ${detalle.idProducto}")

            // Validar stock disponible
            require(producto.tieneStockDisponible(detalle.cantidad)) {
                "Stock insuficiente para ${producto.nombre}. Disponible: ${producto.stockActual}"
            }

            // Obtener precio según tarifa del cliente
            val precio = producto.getPrecioPorTarifa(cliente.tipoTarifa)
            require(precio > BigDecimal.ZERO) {
                "No hay precio definido para el producto ${producto.nombre} con la tarifa ${cliente.tipoTarifa}"
            }

            DetalleOrden.crear(
                producto = producto,
                cantidad = detalle.cantidad,
                precioUnitario = precio
            )
        }

        // Generar número de orden único
        val numeroOrden = generarNumeroOrden()

        // Crear orden
        val orden = OrdenDespacho(
            idOrden = null,
            numeroOrden = numeroOrden,
            cliente = cliente,
            empleado = empleado,
            fechaOrden = LocalDate.now(),
            fechaEntregaProgramada = null,
            subtotal = BigDecimal.ZERO,
            descuento = descuento,
            total = BigDecimal.ZERO,
            observaciones = observaciones,
            estado = EstadoOrden.PENDIENTE,
            detalles = detallesOrden.toMutableList()
        )

        // Calcular totales
        val ordenConTotales = orden.calcularTotal()

        return ordenRepository.save(ordenConTotales)
    }

    private fun generarNumeroOrden(): String {
        val fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        val contadorDia = ordenRepository.contarOrdenesPendientesDelDia(LocalDate.now()) + 1
        return "ORD-$fecha-${contadorDia.toString().padStart(4, '0')}"
    }

    data class DetalleOrdenRequest(
        val idProducto: Int,
        val cantidad: Int
    )
}

