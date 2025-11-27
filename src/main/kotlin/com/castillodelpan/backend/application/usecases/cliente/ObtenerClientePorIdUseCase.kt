package com.castillodelpan.backend.application.usecases.cliente

import com.castillodelpan.backend.domain.models.Cliente
import com.castillodelpan.backend.domain.repositories.ClienteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Obtener Cliente por ID
 */
@Service
@Transactional(readOnly = true)
class ObtenerClientePorIdUseCase(
    private val clienteRepository: ClienteRepository
) {
    operator fun invoke(id: Int): Cliente {
        return clienteRepository.findById(id)
            ?: throw IllegalArgumentException("Cliente no encontrado con ID: $id")
    }
}