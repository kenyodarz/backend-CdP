package com.castillodelpan.backend.domain.repositories

import com.castillodelpan.backend.domain.models.enums.EstadoProducto
import com.castillodelpan.backend.domain.models.producto.Producto

/**
 * Repositorio de Productos
 * Puerto de salida del dominio
 */
interface ProductoRepository {
    fun findById(id: Int): Producto?
    fun findByCodigo(codigo: String): Producto?
    fun findAll(): List<Producto>
    fun findByEstado(estado: EstadoProducto): List<Producto>
    fun findByCategoria(idCategoria: Int): List<Producto>
    fun findByNombreContaining(nombre: String): List<Producto>
    fun findProductosConStockBajo(): List<Producto>
    fun save(producto: Producto): Producto
    fun delete(id: Int)
    fun existsByCodigo(codigo: String): Boolean
}

