package com.castillodelpan.backend.infrastructure.web.controllers

import com.castillodelpan.backend.application.dto.cliente.ActualizarClienteDTO
import com.castillodelpan.backend.application.dto.cliente.AsignarConductorDTO
import com.castillodelpan.backend.application.dto.cliente.AsignarRutaDTO
import com.castillodelpan.backend.application.dto.cliente.ClienteResponseDTO
import com.castillodelpan.backend.application.dto.cliente.ClienteSimpleDTO
import com.castillodelpan.backend.application.dto.cliente.CrearClienteDTO
import com.castillodelpan.backend.application.usecases.cliente.ActualizarClienteUseCase
import com.castillodelpan.backend.application.usecases.cliente.AsignarConductorAClienteUseCase
import com.castillodelpan.backend.application.usecases.cliente.AsignarRutaAClienteUseCase
import com.castillodelpan.backend.application.usecases.cliente.BuscarClientesPorNombreUseCase
import com.castillodelpan.backend.application.usecases.cliente.CrearClienteUseCase
import com.castillodelpan.backend.application.usecases.cliente.DesactivarClienteUseCase
import com.castillodelpan.backend.application.usecases.cliente.ListarClientesActivosUseCase
import com.castillodelpan.backend.application.usecases.cliente.ListarClientesPorBarrioUseCase
import com.castillodelpan.backend.application.usecases.cliente.ListarClientesPorRutaUseCase
import com.castillodelpan.backend.application.usecases.cliente.ObtenerClientePorIdUseCase
import com.castillodelpan.backend.domain.models.Cliente
import com.castillodelpan.backend.domain.repositories.ConductorRepository
import com.castillodelpan.backend.domain.repositories.RutaRepository
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Controlador REST para Clientes
 */
@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = ["http://localhost:4200"])
class ClienteController(
    private val crearClienteUseCase: CrearClienteUseCase,
    private val actualizarClienteUseCase: ActualizarClienteUseCase,
    private val obtenerClientePorIdUseCase: ObtenerClientePorIdUseCase,
    private val listarClientesActivosUseCase: ListarClientesActivosUseCase,
    private val buscarClientesPorNombreUseCase: BuscarClientesPorNombreUseCase,
    private val listarClientesPorRutaUseCase: ListarClientesPorRutaUseCase,
    private val listarClientesPorBarrioUseCase: ListarClientesPorBarrioUseCase,
    private val desactivarClienteUseCase: DesactivarClienteUseCase,
    private val asignarRutaAClienteUseCase: AsignarRutaAClienteUseCase,
    private val asignarConductorAClienteUseCase: AsignarConductorAClienteUseCase,
    private val rutaRepository: RutaRepository,
    private val conductorRepository: ConductorRepository
) {

    /**
     * POST /clientes
     * Crear un nuevo cliente
     */
    @PostMapping
    fun crearCliente(
        @Valid @RequestBody dto: CrearClienteDTO
    ): ResponseEntity<ClienteResponseDTO> {

        val ruta = dto.idRuta?.let {
            rutaRepository.findById(it)
        }

        val conductor = dto.idConductor?.let {
            conductorRepository.findById(it)
        }

        val cliente = Cliente(
            idCliente = null,
            nombre = dto.nombre,
            codigo = dto.codigo,
            tipoDocumento = dto.tipoDocumento,
            numeroDocumento = dto.numeroDocumento,
            direccion = dto.direccion,
            telefono = dto.telefono,
            barrio = dto.barrio,
            comuna = dto.comuna,
            tipoNegocio = dto.tipoNegocio,
            tipoTarifa = dto.tipoTarifa,
            ruta = ruta,
            conductor = conductor,
            horarioEntrega = dto.horarioEntrega,
            estado = com.castillodelpan.backend.domain.models.enums.EstadoGeneral.ACTIVO
        )

        val clienteCreado = crearClienteUseCase(cliente)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ClienteResponseDTO.fromDomain(clienteCreado))
    }

    /**
     * PUT /clientes/{id}
     * Actualizar un cliente existente
     */
    @PutMapping("/{id}")
    fun actualizarCliente(
        @PathVariable id: Int,
        @Valid @RequestBody dto: ActualizarClienteDTO
    ): ResponseEntity<ClienteResponseDTO> {

        val ruta = dto.idRuta?.let {
            rutaRepository.findById(it)
        }

        val conductor = dto.idConductor?.let {
            conductorRepository.findById(it)
        }

        val cliente = Cliente(
            idCliente = id,
            nombre = dto.nombre,
            codigo = dto.codigo,
            tipoDocumento = dto.tipoDocumento,
            numeroDocumento = dto.numeroDocumento,
            direccion = dto.direccion,
            telefono = dto.telefono,
            barrio = dto.barrio,
            comuna = dto.comuna,
            tipoNegocio = dto.tipoNegocio,
            tipoTarifa = dto.tipoTarifa,
            ruta = ruta,
            conductor = conductor,
            horarioEntrega = dto.horarioEntrega,
            estado = dto.estado
        )

        val clienteActualizado = actualizarClienteUseCase(id, cliente)

        return ResponseEntity.ok(ClienteResponseDTO.fromDomain(clienteActualizado))
    }

    /**
     * GET /clientes/{id}
     * Obtener un cliente por ID
     */
    @GetMapping("/{id}")
    fun obtenerClientePorId(@PathVariable id: Int): ResponseEntity<ClienteResponseDTO> {
        val cliente = obtenerClientePorIdUseCase(id)
        return ResponseEntity.ok(ClienteResponseDTO.fromDomain(cliente))
    }

    /**
     * GET /clientes
     * Listar todos los clientes activos
     */
    @GetMapping
    fun listarClientesActivos(): ResponseEntity<List<ClienteSimpleDTO>> {
        val clientes = listarClientesActivosUseCase()
        return ResponseEntity.ok(
            clientes.map { ClienteSimpleDTO.fromDomain(it) }
        )
    }

    /**
     * GET /clientes/buscar?nombre=don
     * Buscar clientes por nombre
     */
    @GetMapping("/buscar")
    fun buscarClientes(
        @RequestParam nombre: String
    ): ResponseEntity<List<ClienteSimpleDTO>> {
        val clientes = buscarClientesPorNombreUseCase(nombre)
        return ResponseEntity.ok(
            clientes.map { ClienteSimpleDTO.fromDomain(it) }
        )
    }

    /**
     * GET /clientes/ruta/{idRuta}
     * Listar clientes por ruta
     */
    @GetMapping("/ruta/{idRuta}")
    fun listarClientesPorRuta(
        @PathVariable idRuta: Int
    ): ResponseEntity<List<ClienteSimpleDTO>> {
        val clientes = listarClientesPorRutaUseCase(idRuta)
        return ResponseEntity.ok(
            clientes.map { ClienteSimpleDTO.fromDomain(it) }
        )
    }

    /**
     * GET /clientes/barrio/{barrio}
     * Listar clientes por barrio
     */
    @GetMapping("/barrio/{barrio}")
    fun listarClientesPorBarrio(
        @PathVariable barrio: String
    ): ResponseEntity<List<ClienteSimpleDTO>> {
        val clientes = listarClientesPorBarrioUseCase(barrio)
        return ResponseEntity.ok(
            clientes.map { ClienteSimpleDTO.fromDomain(it) }
        )
    }

    /**
     * PATCH /clientes/{id}/desactivar
     * Desactivar un cliente
     */
    @PatchMapping("/{id}/desactivar")
    fun desactivarCliente(@PathVariable id: Int): ResponseEntity<ClienteResponseDTO> {
        val cliente = desactivarClienteUseCase(id)
        return ResponseEntity.ok(ClienteResponseDTO.fromDomain(cliente))
    }

    /**
     * PATCH /clientes/{id}/asignar-ruta
     * Asignar ruta a un cliente
     */
    @PatchMapping("/{id}/asignar-ruta")
    fun asignarRuta(
        @PathVariable id: Int,
        @Valid @RequestBody dto: AsignarRutaDTO
    ): ResponseEntity<ClienteResponseDTO> {
        val cliente = asignarRutaAClienteUseCase(id, dto.idRuta)
        return ResponseEntity.ok(ClienteResponseDTO.fromDomain(cliente))
    }

    /**
     * PATCH /clientes/{id}/asignar-conductor
     * Asignar conductor a un cliente
     */
    @PatchMapping("/{id}/asignar-conductor")
    fun asignarConductor(
        @PathVariable id: Int,
        @Valid @RequestBody dto: AsignarConductorDTO
    ): ResponseEntity<ClienteResponseDTO> {
        val cliente = asignarConductorAClienteUseCase(id, dto.idConductor)
        return ResponseEntity.ok(ClienteResponseDTO.fromDomain(cliente))
    }
}