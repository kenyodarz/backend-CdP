package com.castillodelpan.backend.application.usecases.cliente

import com.castillodelpan.backend.domain.models.Cliente
import com.castillodelpan.backend.domain.repositories.ClienteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Listar Clientes por Barrio
 */
@Service
@Transactional(readOnly = true)
class ListarClientesPorBarrioUseCase(
    private val clienteRepository: ClienteRepository
) {
    operator fun invoke(barrio: String): List<Cliente> {
        require(barrio.isNotBlank()) { "El barrio no puede estar vacío" }
        return clienteRepository.findByBarrio(barrio)
    }
}