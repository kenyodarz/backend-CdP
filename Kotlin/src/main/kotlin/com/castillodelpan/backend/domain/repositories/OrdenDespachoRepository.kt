package com.castillodelpan.backend.domain.repositories

import com.castillodelpan.backend.domain.models.OrdenDespacho
import com.castillodelpan.backend.domain.models.enums.EstadoOrden
import java.time.LocalDate

/**
 * Repositorio de Órdenes de Despacho
 */
interface OrdenDespachoRepository {
    fun findById(id: Int): OrdenDespacho?
    fun findByNumeroOrden(numeroOrden: String): OrdenDespacho?
    fun findAll(): List<OrdenDespacho>
    fun findByFechaOrden(fecha: LocalDate): List<OrdenDespacho>
    fun findByEstado(estado: EstadoOrden): List<OrdenDespacho>
    fun findByCliente(idCliente: Int): List<OrdenDespacho>
    fun findByFechaOrdenBetween(fechaInicio: LocalDate, fechaFin: LocalDate): List<OrdenDespacho>
    fun findByFechaAndEstado(fecha: LocalDate, estado: EstadoOrden): List<OrdenDespacho>
    fun contarOrdenesPendientesDelDia(fecha: LocalDate): Long
    fun save(orden: OrdenDespacho): OrdenDespacho
    fun delete(id: Int)
}