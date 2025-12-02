package com.castillodelpan.backend.application.usecases.reportes

import com.castillodelpan.backend.domain.models.enums.EstadoOrden
import com.castillodelpan.backend.domain.models.reportes.ClienteTop
import com.castillodelpan.backend.domain.repositories.OrdenDespachoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate

/**
 * Use Case: Reporte de Clientes Top
 */
@Service
@Transactional(readOnly = true)
class ReporteClientesTopUseCase(
    private val ordenRepository: OrdenDespachoRepository
) {
    operator fun invoke(
        fechaInicio: LocalDate,
        fechaFin: LocalDate,
        limite: Int = 10
    ): List<ClienteTop> {
        val ordenes = ordenRepository.findByFechaOrdenBetween(fechaInicio, fechaFin)
            .filter { it.estado in listOf(EstadoOrden.DESPACHADA, EstadoOrden.ENTREGADA) }

        // Agrupar por cliente
        val clientesMap = mutableMapOf<Int, ClienteTop>()

        ordenes.forEach { orden ->
            val idCliente = orden.cliente.idCliente!!
            val actual = clientesMap[idCliente]

            if (actual == null) {
                clientesMap[idCliente] = ClienteTop(
                    idCliente = idCliente,
                    nombreCliente = orden.cliente.nombre,
                    totalCompras = orden.total,
                    numeroOrdenes = 1,
                    promedioCompra = orden.total
                )
            } else {
                val nuevoTotal = actual.totalCompras + orden.total
                val nuevoNumero = actual.numeroOrdenes + 1
                clientesMap[idCliente] = actual.copy(
                    totalCompras = nuevoTotal,
                    numeroOrdenes = nuevoNumero,
                    promedioCompra = nuevoTotal.divide(
                        BigDecimal(nuevoNumero),
                        2,
                        RoundingMode.HALF_UP
                    )
                )
            }
        }

        return clientesMap.values
            .sortedByDescending { it.totalCompras }
            .take(limite)
    }
}