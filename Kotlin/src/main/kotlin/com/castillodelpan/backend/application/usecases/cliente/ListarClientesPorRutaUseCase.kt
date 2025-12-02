package com.castillodelpan.backend.application.usecases.cliente

import com.castillodelpan.backend.domain.models.Cliente
import com.castillodelpan.backend.domain.repositories.ClienteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Listar Clientes por Ruta
 */
@Service
@Transactional(readOnly = true)
class ListarClientesPorRutaUseCase(
    private val clienteRepository: ClienteRepository
) {
    operator fun invoke(idRuta: Int): List<Cliente> {
        return clienteRepository.findByRuta(idRuta)
    }
}