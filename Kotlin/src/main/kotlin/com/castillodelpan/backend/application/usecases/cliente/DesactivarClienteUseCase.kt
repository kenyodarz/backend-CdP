package com.castillodelpan.backend.application.usecases.cliente

import com.castillodelpan.backend.domain.models.Cliente
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.repositories.ClienteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Desactivar Cliente
 */
@Service
@Transactional
class DesactivarClienteUseCase(
    private val clienteRepository: ClienteRepository
) {
    operator fun invoke(id: Int): Cliente {
        val cliente = clienteRepository.findById(id)
            ?: throw IllegalArgumentException("Cliente no encontrado con ID: $id")

        val clienteDesactivado = cliente.copy(estado = EstadoGeneral.INACTIVO)
        return clienteRepository.save(clienteDesactivado)
    }
}