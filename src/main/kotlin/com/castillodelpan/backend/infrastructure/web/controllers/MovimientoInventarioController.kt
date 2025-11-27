package com.castillodelpan.backend.infrastructure.web.controllers

import com.castillodelpan.backend.application.dto.movimientos.MovimientoInventarioResponseDTO
import com.castillodelpan.backend.application.dto.movimientos.RegistrarEntradaDTO
import com.castillodelpan.backend.application.dto.movimientos.RegistrarSalidaDTO
import com.castillodelpan.backend.application.usecases.movimientos.ObtenerHistorialMovimientosUseCase
import com.castillodelpan.backend.application.usecases.movimientos.ObtenerMovimientosPorFechaUseCase
import com.castillodelpan.backend.application.usecases.movimientos.ObtenerMovimientosPorTipoYFechaUseCase
import com.castillodelpan.backend.application.usecases.movimientos.RegistrarEntradaInventarioUseCase
import com.castillodelpan.backend.application.usecases.movimientos.RegistrarSalidaInventarioUseCase
import com.castillodelpan.backend.domain.models.enums.TipoMovimiento
import jakarta.validation.Valid
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

/**
 * Controlador REST para Movimientos de Inventario
 */
@RestController
@RequestMapping("/inventario/movimientos")
@CrossOrigin(origins = ["http://localhost:4200"])
class MovimientoInventarioController(
    private val registrarEntradaInventarioUseCase: RegistrarEntradaInventarioUseCase,
    private val registrarSalidaInventarioUseCase: RegistrarSalidaInventarioUseCase,
    private val obtenerHistorialMovimientosUseCase: ObtenerHistorialMovimientosUseCase,
    private val obtenerMovimientosPorFechaUseCase: ObtenerMovimientosPorFechaUseCase,
    private val obtenerMovimientosPorTipoYFechaUseCase: ObtenerMovimientosPorTipoYFechaUseCase
) {

    /**
     * POST /inventario/movimientos/entrada
     * Registrar entrada de inventario
     */
    @PostMapping("/entrada")
    fun registrarEntrada(
        @Valid @RequestBody dto: RegistrarEntradaDTO
    ): ResponseEntity<MovimientoInventarioResponseDTO> {

        val movimiento = registrarEntradaInventarioUseCase(
            idProducto = dto.idProducto,
            cantidad = dto.cantidad,
            motivo = dto.motivo,
            codigoLote = dto.codigoLote,
            referencia = dto.referencia,
            observaciones = dto.observaciones
        )

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(MovimientoInventarioResponseDTO.fromDomain(movimiento))
    }

    /**
     * POST /inventario/movimientos/salida
     * Registrar salida de inventario
     */
    @PostMapping("/salida")
    fun registrarSalida(
        @Valid @RequestBody dto: RegistrarSalidaDTO
    ): ResponseEntity<MovimientoInventarioResponseDTO> {

        val movimiento = registrarSalidaInventarioUseCase(
            idProducto = dto.idProducto,
            cantidad = dto.cantidad,
            motivo = dto.motivo,
            codigoLote = dto.codigoLote,
            referencia = dto.referencia,
            observaciones = dto.observaciones
        )

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(MovimientoInventarioResponseDTO.fromDomain(movimiento))
    }

    /**
     * GET /inventario/movimientos/producto/{idProducto}
     * Obtener historial de movimientos de un producto
     */
    @GetMapping("/producto/{idProducto}")
    fun obtenerHistorialProducto(
        @PathVariable idProducto: Int
    ): ResponseEntity<List<MovimientoInventarioResponseDTO>> {
        val movimientos = obtenerHistorialMovimientosUseCase(idProducto)
        return ResponseEntity.ok(
            movimientos.map { MovimientoInventarioResponseDTO.fromDomain(it) }
        )
    }

    /**
     * GET /inventario/movimientos/fecha/{fecha}
     * Obtener movimientos por fecha
     */
    @GetMapping("/fecha/{fecha}")
    fun obtenerPorFecha(
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) fecha: LocalDate
    ): ResponseEntity<List<MovimientoInventarioResponseDTO>> {
        val movimientos = obtenerMovimientosPorFechaUseCase(fecha)
        return ResponseEntity.ok(
            movimientos.map { MovimientoInventarioResponseDTO.fromDomain(it) }
        )
    }

    /**
     * GET /inventario/movimientos
     * Obtener movimientos con filtros
     */
    @GetMapping
    fun obtenerMovimientos(
        @RequestParam(required = false) tipo: TipoMovimiento?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) fecha: LocalDate?
    ): ResponseEntity<List<MovimientoInventarioResponseDTO>> {

        val movimientos = when {
            tipo != null && fecha != null -> {
                obtenerMovimientosPorTipoYFechaUseCase(tipo, fecha)
            }

            fecha != null -> {
                obtenerMovimientosPorFechaUseCase(fecha)
            }

            else -> {
                obtenerMovimientosPorFechaUseCase(LocalDate.now())
            }
        }

        return ResponseEntity.ok(
            movimientos.map { MovimientoInventarioResponseDTO.fromDomain(it) }
        )
    }
}