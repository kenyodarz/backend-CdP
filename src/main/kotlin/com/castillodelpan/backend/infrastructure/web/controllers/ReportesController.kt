package com.castillodelpan.backend.infrastructure.web.controllers

import com.castillodelpan.backend.application.usecases.reportes.DashboardGeneralUseCase
import com.castillodelpan.backend.application.usecases.reportes.ReporteClientesTopUseCase
import com.castillodelpan.backend.application.usecases.reportes.ReporteInventarioValorizadoUseCase
import com.castillodelpan.backend.application.usecases.reportes.ReporteMovimientosInventarioUseCase
import com.castillodelpan.backend.application.usecases.reportes.ReporteProductosMasVendidosUseCase
import com.castillodelpan.backend.application.usecases.reportes.ReporteVentasPorPeriodoUseCase
import com.castillodelpan.backend.domain.models.reportes.ClienteTop
import com.castillodelpan.backend.domain.models.reportes.DashboardData
import com.castillodelpan.backend.domain.models.reportes.ProductoVendido
import com.castillodelpan.backend.domain.models.reportes.ReporteInventario
import com.castillodelpan.backend.domain.models.reportes.ReporteMovimientos
import com.castillodelpan.backend.domain.models.reportes.ReporteVentas
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/reportes")
@CrossOrigin(origins = ["http://localhost:4200"])
class ReportesController(
    private val reporteVentasPorPeriodoUseCase: ReporteVentasPorPeriodoUseCase,
    private val reporteProductosMasVendidosUseCase: ReporteProductosMasVendidosUseCase,
    private val reporteClientesTopUseCase: ReporteClientesTopUseCase,
    private val reporteInventarioValorizadoUseCase: ReporteInventarioValorizadoUseCase,
    private val reporteMovimientosInventarioUseCase: ReporteMovimientosInventarioUseCase,
    private val dashboardGeneralUseCase: DashboardGeneralUseCase
) {

    /**
     * GET /reportes/ventas
     * Reporte de ventas por período
     */
    @GetMapping("/ventas")
    fun reporteVentas(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) fechaInicio: LocalDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) fechaFin: LocalDate
    ): ResponseEntity<ReporteVentas> {
        val reporte = reporteVentasPorPeriodoUseCase(fechaInicio, fechaFin)
        return ResponseEntity.ok(reporte)
    }

    /**
     * GET /reportes/productos-mas-vendidos
     * Top productos más vendidos
     */
    @GetMapping("/productos-mas-vendidos")
    fun productosMasVendidos(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) fechaInicio: LocalDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) fechaFin: LocalDate,
        @RequestParam(defaultValue = "10") limite: Int
    ): ResponseEntity<List<ProductoVendido>> {
        val reporte = reporteProductosMasVendidosUseCase(fechaInicio, fechaFin, limite)
        return ResponseEntity.ok(reporte)
    }

    /**
     * GET /reportes/clientes-top
     * Top clientes por compras
     */
    @GetMapping("/clientes-top")
    fun clientesTop(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) fechaInicio: LocalDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) fechaFin: LocalDate,
        @RequestParam(defaultValue = "10") limite: Int
    ): ResponseEntity<List<ClienteTop>> {
        val reporte = reporteClientesTopUseCase(fechaInicio, fechaFin, limite)
        return ResponseEntity.ok(reporte)
    }

    /**
     * GET /reportes/inventario-valorizado
     * Reporte de inventario valorizado
     */
    @GetMapping("/inventario-valorizado")
    fun inventarioValorizado(): ResponseEntity<ReporteInventario> {
        val reporte = reporteInventarioValorizadoUseCase()
        return ResponseEntity.ok(reporte)
    }

    /**
     * GET /reportes/movimientos-inventario
     * Reporte de movimientos de inventario
     */
    @GetMapping("/movimientos-inventario")
    fun movimientosInventario(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) fechaInicio: LocalDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) fechaFin: LocalDate
    ): ResponseEntity<ReporteMovimientos> {
        val reporte = reporteMovimientosInventarioUseCase(fechaInicio, fechaFin)
        return ResponseEntity.ok(reporte)
    }

    /**
     * GET /reportes/dashboard
     * Dashboard general
     */
    @GetMapping("/dashboard")
    fun dashboard(): ResponseEntity<DashboardData> {
        val dashboard = dashboardGeneralUseCase()
        return ResponseEntity.ok(dashboard)
    }
}