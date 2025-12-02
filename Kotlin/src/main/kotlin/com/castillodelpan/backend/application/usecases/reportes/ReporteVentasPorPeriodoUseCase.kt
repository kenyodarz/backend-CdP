package com.castillodelpan.backend.application.usecases.reportes

import com.castillodelpan.backend.domain.models.enums.EstadoOrden
import com.castillodelpan.backend.domain.models.reportes.ReporteVentas
import com.castillodelpan.backend.domain.models.reportes.VentaDiaria
import com.castillodelpan.backend.domain.repositories.OrdenDespachoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDate

// ============================================
// Use Cases de Reportes
// ============================================

/**
 * Use Case: Reporte de Ventas por Período
 */
@Service
@Transactional(readOnly = true)
class ReporteVentasPorPeriodoUseCase(
    private val ordenRepository: OrdenDespachoRepository
) {
    operator fun invoke(fechaInicio: LocalDate, fechaFin: LocalDate): ReporteVentas {
        val ordenes = ordenRepository.findByFechaOrdenBetween(fechaInicio, fechaFin)
            .filter { it.estado in listOf(EstadoOrden.DESPACHADA, EstadoOrden.ENTREGADA) }

        val totalVentas = ordenes.sumOf { it.total }
        val numeroOrdenes = ordenes.size
        val promedioVenta = if (numeroOrdenes > 0) totalVentas.divide(
            BigDecimal(numeroOrdenes),
            2,
            java.math.RoundingMode.HALF_UP
        ) else BigDecimal.ZERO

        // Ventas por día
        val ventasPorDia = ordenes.groupBy { it.fechaOrden }
            .map { (fecha, ordenesDelDia) ->
                VentaDiaria(
                    fecha = fecha,
                    numeroOrdenes = ordenesDelDia.size,
                    totalVentas = ordenesDelDia.sumOf { it.total }
                )
            }
            .sortedBy { it.fecha }

        return ReporteVentas(
            fechaInicio = fechaInicio,
            fechaFin = fechaFin,
            totalVentas = totalVentas,
            numeroOrdenes = numeroOrdenes,
            promedioVenta = promedioVenta,
            ventasPorDia = ventasPorDia
        )
    }
}

