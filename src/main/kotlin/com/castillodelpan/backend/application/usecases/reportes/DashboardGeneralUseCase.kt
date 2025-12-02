package com.castillodelpan.backend.application.usecases.reportes

import com.castillodelpan.backend.domain.models.enums.EstadoOrden
import com.castillodelpan.backend.domain.models.reportes.DashboardData
import com.castillodelpan.backend.domain.repositories.ClienteRepository
import com.castillodelpan.backend.domain.repositories.OrdenDespachoRepository
import com.castillodelpan.backend.domain.repositories.ProductoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

/**
 * Use Case: Dashboard General
 */
@Service
@Transactional(readOnly = true)
class DashboardGeneralUseCase(
    private val ordenRepository: OrdenDespachoRepository,
    private val productoRepository: ProductoRepository,
    private val clienteRepository: ClienteRepository
) {
    operator fun invoke(): DashboardData {
        val hoy = LocalDate.now()

        // Órdenes del día
        val ordenesHoy = ordenRepository.findByFechaOrden(hoy)
        val ordenesPendientes = ordenesHoy.count { it.estado == EstadoOrden.PENDIENTE }
        val ordenesEnPreparacion = ordenesHoy.count { it.estado == EstadoOrden.EN_PREPARACION }
        val ordenesListas = ordenesHoy.count { it.estado == EstadoOrden.LISTA }
        val ordenesDespachadas = ordenesHoy.count { it.estado == EstadoOrden.DESPACHADA }

        // Ventas del día
        val ventasHoy = ordenesHoy
            .filter { it.estado in listOf(EstadoOrden.DESPACHADA, EstadoOrden.ENTREGADA) }
            .sumOf { it.total }

        // Inventario
        val productosStockBajo = productoRepository.findProductosConStockBajo().size
        val totalProductos = productoRepository.findAll().size

        // Clientes activos
        val clientesActivos = clienteRepository.findByEstadoActivo().size

        return DashboardData(
            fecha = hoy,
            ordenesPendientes = ordenesPendientes,
            ordenesEnPreparacion = ordenesEnPreparacion,
            ordenesListas = ordenesListas,
            ordenesDespachadas = ordenesDespachadas,
            ventasHoy = ventasHoy,
            productosStockBajo = productosStockBajo,
            totalProductos = totalProductos,
            clientesActivos = clientesActivos
        )
    }
}