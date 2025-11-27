package com.castillodelpan.backend.application.usecases.cliente

import com.castillodelpan.backend.domain.models.Cliente
import com.castillodelpan.backend.domain.repositories.ClienteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Buscar Clientes por Nombre
 */
@Service
@Transactional(readOnly = true)
class BuscarClientesPorNombreUseCase(
    private val clienteRepository: ClienteRepository
) {
    operator fun invoke(nombre: String): List<Cliente> {
        require(nombre.length >= 3) {
            "Debe proporcionar al menos 3 caracteres para la búsqueda"
        }
        return clienteRepository.findByNombreContaining(nombre)
    }
}