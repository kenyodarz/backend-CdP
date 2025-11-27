package com.castillodelpan.backend.application.usecases.cliente

import com.castillodelpan.backend.domain.models.Cliente
import com.castillodelpan.backend.domain.repositories.ClienteRepository
import com.castillodelpan.backend.domain.repositories.ConductorRepository
import com.castillodelpan.backend.domain.repositories.RutaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Crear Cliente
 */
@Service
@Transactional
class CrearClienteUseCase(
    private val clienteRepository: ClienteRepository,
    private val rutaRepository: RutaRepository,
    private val conductorRepository: ConductorRepository
) {
    operator fun invoke(cliente: Cliente): Cliente {
        // Validaciones
        require(cliente.nombre.isNotBlank()) { "El nombre del cliente es obligatorio" }

        // Validar documento único si existe
        cliente.numeroDocumento?.let { documento ->
            require(!clienteRepository.existsByNumeroDocumento(documento)) {
                "Ya existe un cliente con el documento: $documento"
            }
        }

        // Validar código único si existe
        cliente.codigo?.let { codigo ->
            clienteRepository.findByCodigo(codigo)?.let {
                throw IllegalArgumentException("Ya existe un cliente con el código: $codigo")
            }
        }

        // Validar ruta existe
        cliente.ruta?.let { ruta ->
            rutaRepository.findById(ruta.idRuta!!)
                ?: throw IllegalArgumentException("Ruta no encontrada: ${ruta.idRuta}")
        }

        // Validar conductor existe
        cliente.conductor?.let { conductor ->
            conductorRepository.findById(conductor.idConductor!!)
                ?: throw IllegalArgumentException("Conductor no encontrado: ${conductor.idConductor}")
        }

        return clienteRepository.save(cliente)
    }
}

