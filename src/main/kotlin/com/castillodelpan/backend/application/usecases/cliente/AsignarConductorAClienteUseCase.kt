package com.castillodelpan.backend.application.usecases.cliente

import com.castillodelpan.backend.domain.models.Cliente
import com.castillodelpan.backend.domain.repositories.ClienteRepository
import com.castillodelpan.backend.domain.repositories.ConductorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Asignar Conductor a Cliente
 */
@Service
@Transactional
class AsignarConductorAClienteUseCase(
    private val clienteRepository: ClienteRepository,
    private val conductorRepository: ConductorRepository
) {
    operator fun invoke(idCliente: Int, idConductor: Int): Cliente {
        val cliente = clienteRepository.findById(idCliente)
            ?: throw IllegalArgumentException("Cliente no encontrado con ID: $idCliente")

        val conductor = conductorRepository.findById(idConductor)
            ?: throw IllegalArgumentException("Conductor no encontrado con ID: $idConductor")

        val clienteActualizado = cliente.copy(conductor = conductor)
        return clienteRepository.save(clienteActualizado)
    }
}