package com.castillodelpan.backend.application.usecases.cliente

import com.castillodelpan.backend.domain.models.Cliente
import com.castillodelpan.backend.domain.repositories.ClienteRepository
import com.castillodelpan.backend.domain.repositories.ConductorRepository
import com.castillodelpan.backend.domain.repositories.RutaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Actualizar Cliente
 */
@Service
@Transactional
class ActualizarClienteUseCase(
    private val clienteRepository: ClienteRepository,
    private val rutaRepository: RutaRepository,
    private val conductorRepository: ConductorRepository
) {
    operator fun invoke(id: Int, cliente: Cliente): Cliente {
        val clienteExistente = clienteRepository.findById(id)
            ?: throw IllegalArgumentException("Cliente no encontrado con ID: $id")

        // Validar documento único si cambió
        cliente.numeroDocumento?.let { nuevoDocumento ->
            if (nuevoDocumento != clienteExistente.numeroDocumento) {
                require(!clienteRepository.existsByNumeroDocumento(nuevoDocumento)) {
                    "Ya existe otro cliente con el documento: $nuevoDocumento"
                }
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

        val clienteActualizado = cliente.copy(idCliente = id)
        return clienteRepository.save(clienteActualizado)
    }
}