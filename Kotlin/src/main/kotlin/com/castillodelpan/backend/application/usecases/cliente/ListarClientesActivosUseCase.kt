package com.castillodelpan.backend.application.usecases.cliente

import com.castillodelpan.backend.domain.models.Cliente
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.repositories.ClienteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Listar Clientes Activos
 */
@Service
@Transactional(readOnly = true)
class ListarClientesActivosUseCase(
    private val clienteRepository: ClienteRepository
) {
    operator fun invoke(): List<Cliente> {
        return clienteRepository.findByEstadoActivo()
    }
}