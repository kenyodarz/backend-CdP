package com.castillodelpan.backend.application.usecases.cliente

import com.castillodelpan.backend.domain.models.Cliente
import com.castillodelpan.backend.domain.repositories.ClienteRepository
import com.castillodelpan.backend.domain.repositories.RutaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Asignar Ruta a Cliente
 */
@Service
@Transactional
class AsignarRutaAClienteUseCase(
    private val clienteRepository: ClienteRepository,
    private val rutaRepository: RutaRepository
) {
    operator fun invoke(idCliente: Int, idRuta: Int): Cliente {
        val cliente = clienteRepository.findById(idCliente)
            ?: throw IllegalArgumentException("Cliente no encontrado con ID: $idCliente")

        val ruta = rutaRepository.findById(idRuta)
            ?: throw IllegalArgumentException("Ruta no encontrada con ID: $idRuta")

        val clienteActualizado = cliente.copy(ruta = ruta)
        return clienteRepository.save(clienteActualizado)
    }
}