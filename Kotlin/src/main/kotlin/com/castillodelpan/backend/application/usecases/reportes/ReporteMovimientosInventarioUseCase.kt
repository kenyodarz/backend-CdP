package com.castillodelpan.backend.application.usecases.reportes

import com.castillodelpan.backend.domain.models.enums.TipoMovimiento
import com.castillodelpan.backend.domain.models.reportes.MovimientosDia
import com.castillodelpan.backend.domain.models.reportes.ReporteMovimientos
import com.castillodelpan.backend.domain.repositories.MovimientoInventarioRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import kotlin.streams.asSequence

@Service
@Transactional(readOnly = true)
class ReporteMovimientosInventarioUseCase(
    private val movimientoRepository: MovimientoInventarioRepository
) {

    operator fun invoke(fechaInicio: LocalDate, fechaFin: LocalDate): ReporteMovimientos {

        require(!fechaInicio.isAfter(fechaFin)) {
            "La fecha de inicio no puede ser mayor que la fecha fin."
        }

        // Generamos la lista de días a consultar
        val dias = fechaInicio
            .datesUntil(fechaFin.plusDays(1))
            .asSequence()
            .toList()

        // Consultamos movimientos por cada día
        val movimientos = dias
            .flatMap { dia -> movimientoRepository.findByFecha(dia) }

        // Acumuladores
        var totalEntradas = 0
        var totalSalidas = 0
        var totalAjustes = 0

        movimientos.forEach {
            when (it.tipoMovimiento) {
                TipoMovimiento.ENTRADA -> totalEntradas += it.cantidad
                TipoMovimiento.SALIDA -> totalSalidas += it.cantidad
                TipoMovimiento.AJUSTE -> totalAjustes += it.cantidad
                TipoMovimiento.DEVOLUCION -> {
                    // ¿Define cómo sumar las devoluciones (entrada y retorno?)
                    totalEntradas += it.cantidad
                }
            }
        }


        // Agrupamos movimientos por día
        val movimientosPorDia = movimientos
            .groupBy { it.fechaMovimiento.toLocalDate() }
            .map { (fecha, movs) ->
                MovimientosDia(
                    fecha = fecha,
                    entradas = movs.count { it.tipoMovimiento == TipoMovimiento.ENTRADA },
                    salidas = movs.count { it.tipoMovimiento == TipoMovimiento.SALIDA },
                    ajustes = movs.count { it.tipoMovimiento == TipoMovimiento.AJUSTE }
                )
            }
            .sortedBy { it.fecha }

        return ReporteMovimientos(
            fechaInicio = fechaInicio,
            fechaFin = fechaFin,
            totalEntradas = totalEntradas,
            totalSalidas = totalSalidas,
            totalAjustes = totalAjustes,
            numeroMovimientos = movimientos.size,
            movimientosPorDia = movimientosPorDia
        )
    }
}
