package com.castillodelpan.backend.infrastructure.persistence.repositories.impl

import com.castillodelpan.backend.domain.models.OrdenDespacho
import com.castillodelpan.backend.domain.models.enums.EstadoOrden
import com.castillodelpan.backend.domain.repositories.OrdenDespachoRepository
import com.castillodelpan.backend.infrastructure.persistence.mappers.OrdenDespachoMapper
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaClienteRepository
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaEmpleadoRepository
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaOrdenDespachoRepository
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaProductoRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Repository
@Transactional
class OrdenDespachoRepositoryImpl(
    private val jpaOrdenDespachoRepository: JpaOrdenDespachoRepository,
    private val jpaClienteRepository: JpaClienteRepository,
    private val jpaEmpleadoRepository: JpaEmpleadoRepository,
    private val jpaProductoRepository: JpaProductoRepository
) : OrdenDespachoRepository {

    override fun findById(id: Int): OrdenDespacho? {
        return jpaOrdenDespachoRepository.findById(id)
            .map { OrdenDespachoMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByNumeroOrden(numeroOrden: String): OrdenDespacho? {
        return jpaOrdenDespachoRepository.findByNumeroOrden(numeroOrden)
            .map { OrdenDespachoMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findAll(): List<OrdenDespacho> {
        return jpaOrdenDespachoRepository.findAll()
            .map { OrdenDespachoMapper.toDomain(it) }
    }

    override fun findByFechaOrden(fecha: LocalDate): List<OrdenDespacho> {
        return jpaOrdenDespachoRepository.findByFechaOrden(fecha)
            .map { OrdenDespachoMapper.toDomain(it) }
    }

    override fun findByEstado(estado: EstadoOrden): List<OrdenDespacho> {
        return jpaOrdenDespachoRepository.findByEstado(estado)
            .map { OrdenDespachoMapper.toDomain(it) }
    }

    override fun findByCliente(idCliente: Int): List<OrdenDespacho> {
        return jpaOrdenDespachoRepository.findByCliente(idCliente)
            .map { OrdenDespachoMapper.toDomain(it) }
    }

    override fun findByFechaOrdenBetween(
        fechaInicio: LocalDate,
        fechaFin: LocalDate
    ): List<OrdenDespacho> {
        return jpaOrdenDespachoRepository.findByFechaOrdenBetween(fechaInicio, fechaFin)
            .map { OrdenDespachoMapper.toDomain(it) }
    }

    override fun findByFechaAndEstado(fecha: LocalDate, estado: EstadoOrden): List<OrdenDespacho> {
        return jpaOrdenDespachoRepository.findByFechaAndEstado(fecha, estado)
            .map { OrdenDespachoMapper.toDomain(it) }
    }

    override fun contarOrdenesPendientesDelDia(fecha: LocalDate): Long {
        return jpaOrdenDespachoRepository.contarOrdenesPendientesDelDia(fecha)
    }

    override fun save(orden: OrdenDespacho): OrdenDespacho {
        val clienteData = jpaClienteRepository.findById(orden.cliente.idCliente!!)
            .orElseThrow { IllegalArgumentException("Cliente no encontrado") }

        val empleadoData = jpaEmpleadoRepository.findById(orden.empleado.idEmpleado!!)
            .orElseThrow { IllegalArgumentException("Empleado no encontrado") }

        val ordenData = OrdenDespachoMapper.toData(orden, clienteData, empleadoData)

        // Resolver productos para los detalles
        ordenData.detalles.forEach { detalle ->
            val productoData = jpaProductoRepository.findById(detalle.producto.idProducto!!)
                .orElseThrow { IllegalArgumentException("Producto no encontrado: ${detalle.producto.idProducto}") }
            detalle.producto = productoData
        }

        val savedData = jpaOrdenDespachoRepository.save(ordenData)
        return OrdenDespachoMapper.toDomain(savedData)
    }

    override fun delete(id: Int) {
        jpaOrdenDespachoRepository.deleteById(id)
    }
}