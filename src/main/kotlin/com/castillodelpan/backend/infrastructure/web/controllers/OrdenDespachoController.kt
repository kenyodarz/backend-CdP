package com.castillodelpan.backend.infrastructure.web.controllers

import com.castillodelpan.backend.application.dto.orden.CambiarEstadoOrdenDTO
import com.castillodelpan.backend.application.dto.orden.CancelarOrdenDTO
import com.castillodelpan.backend.application.dto.orden.CrearOrdenDespachoDTO
import com.castillodelpan.backend.application.dto.orden.OrdenDespachoResponseDTO
import com.castillodelpan.backend.application.dto.orden.OrdenDespachoSimpleDTO
import com.castillodelpan.backend.application.dto.orden.ResumenOrdenesDTO
import com.castillodelpan.backend.application.usecases.orden.CambiarEstadoOrdenUseCase
import com.castillodelpan.backend.application.usecases.orden.CancelarOrdenUseCase
import com.castillodelpan.backend.application.usecases.orden.CrearOrdenDespachoUseCase
import com.castillodelpan.backend.application.usecases.orden.ListarOrdenesPorEstadoUseCase
import com.castillodelpan.backend.application.usecases.orden.ListarOrdenesPorFechaUseCase
import com.castillodelpan.backend.application.usecases.orden.ObtenerHistorialClienteUseCase
import com.castillodelpan.backend.application.usecases.orden.ObtenerOrdenesPendientesDelDiaUseCase
import com.castillodelpan.backend.domain.models.enums.EstadoOrden
import jakarta.validation.Valid
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

/**
 * Controlador REST para Órdenes de Despacho
 */
@RestController
@RequestMapping("/ordenes")
@CrossOrigin(origins = ["http://localhost:4200"])
class OrdenDespachoController(
    private val crearOrdenDespachoUseCase: CrearOrdenDespachoUseCase,
    private val cambiarEstadoOrdenUseCase: CambiarEstadoOrdenUseCase,
    private val listarOrdenesPorFechaUseCase: ListarOrdenesPorFechaUseCase,
    private val listarOrdenesPorEstadoUseCase: ListarOrdenesPorEstadoUseCase,
    private val obtenerOrdenesPendientesDelDiaUseCase: ObtenerOrdenesPendientesDelDiaUseCase,
    private val cancelarOrdenUseCase: CancelarOrdenUseCase,
    private val obtenerHistorialClienteUseCase: ObtenerHistorialClienteUseCase,
    private val ordenRepository: com.castillodelpan.backend.domain.repositories.OrdenDespachoRepository
) {

    /**
     * POST /ordenes
     * Crear una nueva orden de despacho
     */
    @PostMapping
    fun crearOrden(
        @Valid @RequestBody dto: CrearOrdenDespachoDTO
    ): ResponseEntity<OrdenDespachoResponseDTO> {

        val detalles = dto.detalles.map { detalle ->
            CrearOrdenDespachoUseCase.DetalleOrdenRequest(
                idProducto = detalle.idProducto,
                cantidad = detalle.cantidad
            )
        }

        val ordenCreada = crearOrdenDespachoUseCase(
            idCliente = dto.idCliente,
            idEmpleado = dto.idEmpleado,
            detalles = detalles,
            observaciones = dto.observaciones,
            descuento = dto.descuento
        )

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(OrdenDespachoResponseDTO.fromDomain(ordenCreada))
    }

    /**
     * GET /ordenes/{id}
     * Obtener una orden por ID
     */
    @GetMapping("/{id}")
    fun obtenerOrdenPorId(@PathVariable id: Int): ResponseEntity<OrdenDespachoResponseDTO> {
        val orden = ordenRepository.findById(id)
            ?: throw IllegalArgumentException("Orden no encontrada con ID: $id")
        return ResponseEntity.ok(OrdenDespachoResponseDTO.fromDomain(orden))
    }

    /**
     * GET /ordenes
     * Listar todas las órdenes (con filtros opcionales)
     */
    @GetMapping
    fun listarOrdenes(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) fecha: LocalDate?,
        @RequestParam(required = false) estado: EstadoOrden?
    ): ResponseEntity<List<OrdenDespachoSimpleDTO>> {

        val ordenes = when {
            fecha != null && estado != null -> {
                ordenRepository.findByFechaAndEstado(fecha, estado)
            }

            fecha != null -> {
                listarOrdenesPorFechaUseCase(fecha)
            }

            estado != null -> {
                listarOrdenesPorEstadoUseCase(estado)
            }

            else -> {
                ordenRepository.findAll()
            }
        }

        return ResponseEntity.ok(
            ordenes.map { OrdenDespachoSimpleDTO.fromDomain(it) }
        )
    }

    /**
     * GET /ordenes/fecha/{fecha}
     * Listar órdenes por fecha específica
     */
    @GetMapping("/fecha/{fecha}")
    fun listarOrdenesPorFecha(
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) fecha: LocalDate
    ): ResponseEntity<List<OrdenDespachoSimpleDTO>> {
        val ordenes = listarOrdenesPorFechaUseCase(fecha)
        return ResponseEntity.ok(
            ordenes.map { OrdenDespachoSimpleDTO.fromDomain(it) }
        )
    }

    /**
     * GET /ordenes/estado/{estado}
     * Listar órdenes por estado
     */
    @GetMapping("/estado/{estado}")
    fun listarOrdenesPorEstado(
        @PathVariable estado: EstadoOrden
    ): ResponseEntity<List<OrdenDespachoSimpleDTO>> {
        val ordenes = listarOrdenesPorEstadoUseCase(estado)
        return ResponseEntity.ok(
            ordenes.map { OrdenDespachoSimpleDTO.fromDomain(it) }
        )
    }

    /**
     * GET /ordenes/pendientes-hoy
     * Obtener órdenes pendientes del día actual
     */
    @GetMapping("/pendientes-hoy")
    fun obtenerOrdenesPendientesHoy(): ResponseEntity<List<OrdenDespachoSimpleDTO>> {
        val ordenes = obtenerOrdenesPendientesDelDiaUseCase()
        return ResponseEntity.ok(
            ordenes.map { OrdenDespachoSimpleDTO.fromDomain(it) }
        )
    }

    /**
     * GET /ordenes/cliente/{idCliente}
     * Obtener historial de órdenes de un cliente
     */
    @GetMapping("/cliente/{idCliente}")
    fun obtenerHistorialCliente(
        @PathVariable idCliente: Int,
        @RequestParam(defaultValue = "20") limite: Int
    ): ResponseEntity<List<OrdenDespachoSimpleDTO>> {
        val ordenes = obtenerHistorialClienteUseCase(idCliente, limite)
        return ResponseEntity.ok(
            ordenes.map { OrdenDespachoSimpleDTO.fromDomain(it) }
        )
    }

    /**
     * PATCH /ordenes/{id}/estado
     * Cambiar el estado de una orden
     */
    @PatchMapping("/{id}/estado")
    fun cambiarEstado(
        @PathVariable id: Int,
        @Valid @RequestBody dto: CambiarEstadoOrdenDTO
    ): ResponseEntity<OrdenDespachoResponseDTO> {
        val orden = cambiarEstadoOrdenUseCase(id, dto.nuevoEstado)
        return ResponseEntity.ok(OrdenDespachoResponseDTO.fromDomain(orden))
    }

    /**
     * PATCH /ordenes/{id}/cancelar
     * Cancelar una orden
     */
    @PatchMapping("/{id}/cancelar")
    fun cancelarOrden(
        @PathVariable id: Int,
        @Valid @RequestBody dto: CancelarOrdenDTO
    ): ResponseEntity<OrdenDespachoResponseDTO> {
        val orden = cancelarOrdenUseCase(id, dto.motivo)
        return ResponseEntity.ok(OrdenDespachoResponseDTO.fromDomain(orden))
    }

    /**
     * GET /ordenes/resumen
     * Obtener resumen de órdenes para dashboard
     */
    @GetMapping("/resumen")
    fun obtenerResumen(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) fecha: LocalDate?
    ): ResponseEntity<ResumenOrdenesDTO> {

        val fechaConsulta = fecha ?: LocalDate.now()
        val ordenes = listarOrdenesPorFechaUseCase(fechaConsulta)

        val resumen = ResumenOrdenesDTO(
            totalOrdenes = ordenes.size,
            ordenesPendientes = ordenes.count { it.estado == EstadoOrden.PENDIENTE },
            ordenesEnPreparacion = ordenes.count { it.estado == EstadoOrden.EN_PREPARACION },
            ordenesListas = ordenes.count { it.estado == EstadoOrden.LISTA },
            ordenesDespachadas = ordenes.count { it.estado == EstadoOrden.DESPACHADA },
            valorTotalOrdenes = ordenes.sumOf { it.total }
        )

        return ResponseEntity.ok(resumen)
    }

    /**
     * GET /ordenes/numero/{numeroOrden}
     * Buscar orden por número
     */
    @GetMapping("/numero/{numeroOrden}")
    fun buscarPorNumero(@PathVariable numeroOrden: String): ResponseEntity<OrdenDespachoResponseDTO> {
        val orden = ordenRepository.findByNumeroOrden(numeroOrden)
            ?: throw IllegalArgumentException("Orden no encontrada con número: $numeroOrden")
        return ResponseEntity.ok(OrdenDespachoResponseDTO.fromDomain(orden))
    }
}