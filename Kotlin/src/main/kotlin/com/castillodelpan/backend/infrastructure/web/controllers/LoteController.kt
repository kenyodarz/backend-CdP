package com.castillodelpan.backend.infrastructure.web.controllers

import com.castillodelpan.backend.application.dto.inventario.CrearLoteDTO
import com.castillodelpan.backend.application.dto.inventario.LoteResponseDTO
import com.castillodelpan.backend.application.usecases.inventario.CrearLoteUseCase
import com.castillodelpan.backend.application.usecases.inventario.ObtenerLotesPorProductoUseCase
import com.castillodelpan.backend.application.usecases.inventario.ObtenerProductosProximosAVencerUseCase
import com.castillodelpan.backend.application.usecases.inventario.ObtenerProductosVencidosUseCase
import jakarta.validation.Valid
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

/**
 * Controlador REST para Lotes de Inventario
 */
@RestController
@RequestMapping("/inventario/lotes")
@CrossOrigin(origins = ["http://localhost:4200"])
class LoteController(
    private val crearLoteUseCase: CrearLoteUseCase,
    private val obtenerLotesPorProductoUseCase: ObtenerLotesPorProductoUseCase,
    private val obtenerProductosProximosAVencerUseCase: ObtenerProductosProximosAVencerUseCase,
    private val obtenerProductosVencidosUseCase: ObtenerProductosVencidosUseCase
) {

    /**
     * POST /inventario/lotes
     * Crear un nuevo lote
     */
    @PostMapping
    fun crearLote(
        @Valid @RequestBody dto: CrearLoteDTO
    ): ResponseEntity<LoteResponseDTO> {

        val lote = crearLoteUseCase(
            idProducto = dto.idProducto,
            codigoLote = dto.codigoLote,
            fechaElaboracion = dto.fechaElaboracion,
            fechaVencimiento = dto.fechaVencimiento,
            cantidad = dto.cantidad
        )

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(LoteResponseDTO.Companion.fromDomain(lote))
    }

    /**
     * GET /inventario/lotes/producto/{idProducto}
     * Obtener lotes de un producto
     */
    @GetMapping("/producto/{idProducto}")
    fun obtenerLotesPorProducto(
        @PathVariable idProducto: Int
    ): ResponseEntity<List<LoteResponseDTO>> {
        val lotes = obtenerLotesPorProductoUseCase(idProducto)
        return ResponseEntity.ok(
            lotes.map { LoteResponseDTO.Companion.fromDomain(it) }
        )
    }

    /**
     * GET /inventario/lotes/proximos-vencer
     * Obtener productos próximos a vencer
     */
    @GetMapping("/proximos-vencer")
    fun obtenerProximosAVencer(
        @RequestParam(defaultValue = "3") dias: Int
    ): ResponseEntity<List<LoteResponseDTO>> {
        val lotes = obtenerProductosProximosAVencerUseCase(dias)
        return ResponseEntity.ok(
            lotes.map { LoteResponseDTO.Companion.fromDomain(it) }
        )
    }

    /**
     * GET /inventario/lotes/vencidos
     * Obtener productos vencidos
     */
    @GetMapping("/vencidos")
    fun obtenerVencidos(): ResponseEntity<List<LoteResponseDTO>> {
        val lotes = obtenerProductosVencidosUseCase()
        return ResponseEntity.ok(
            lotes.map { LoteResponseDTO.Companion.fromDomain(it) }
        )
    }
}