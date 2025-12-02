package com.castillodelpan.backend.infrastructure.persistence.repositories.impl

import com.castillodelpan.backend.domain.models.enums.EstadoProducto
import com.castillodelpan.backend.domain.models.producto.Producto
import com.castillodelpan.backend.domain.repositories.ProductoRepository
import com.castillodelpan.backend.infrastructure.persistence.mappers.ProductoMapper
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaCategoriaRepository
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaPrecioProductoRepository
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaProductoRepository
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaUnidadMedidaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class ProductoRepositoryImpl(
    private val jpaProductoRepository: JpaProductoRepository,
    private val jpaCategoriaRepository: JpaCategoriaRepository,
    private val jpaUnidadMedidaRepository: JpaUnidadMedidaRepository,
    private val jpaPrecioProductoRepository: JpaPrecioProductoRepository
) : ProductoRepository {

    override fun findById(id: Int): Producto? {
        return jpaProductoRepository.findById(id)
            .map { ProductoMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByCodigo(codigo: String): Producto? {
        return jpaProductoRepository.findByCodigo(codigo)
            .map { ProductoMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findAll(): List<Producto> {
        return jpaProductoRepository.findAll()
            .map { ProductoMapper.toDomain(it) }
    }

    override fun findByEstado(estado: EstadoProducto): List<Producto> {
        return jpaProductoRepository.findByEstado(estado)
            .map { ProductoMapper.toDomain(it) }
    }

    override fun findProductosActivos(): List<Producto> {
        return jpaProductoRepository.findProductosActivos()
            .map { ProductoMapper.toDomain(it) }
    }

    override fun findProductosInactivos(): List<Producto> {
        return jpaProductoRepository.findProductosInactivos()
            .map { ProductoMapper.toDomain(it) }
    }

    override fun findByCategoria(idCategoria: Int): List<Producto> {
        return jpaProductoRepository.findByCategoria(idCategoria)
            .map { ProductoMapper.toDomain(it) }
    }

    override fun findByNombreContaining(nombre: String): List<Producto> {
        return jpaProductoRepository.findByNombreContainingIgnoreCase(nombre)
            .map { ProductoMapper.toDomain(it) }
    }

    override fun findProductosConStockBajo(): List<Producto> {
        return jpaProductoRepository.findProductosConStockBajo()
            .map { ProductoMapper.toDomain(it) }
    }

    override fun save(producto: Producto): Producto {
        val categoriaData = jpaCategoriaRepository.findById(producto.categoria.idCategoria!!)
            .orElseThrow { IllegalArgumentException("Categoría no encontrada") }
        val unidadData = jpaUnidadMedidaRepository.findById(producto.unidadMedida.idUnidad!!)
            .orElseThrow { IllegalArgumentException("Unidad de medida no encontrada") }
        val productoData = ProductoMapper.toData(producto, categoriaData, unidadData)
        val savedData = jpaProductoRepository.save(productoData)
        return ProductoMapper.toDomain(savedData)
    }

    override fun delete(id: Int) {
        jpaProductoRepository.deleteById(id)
    }

    override fun existsByCodigo(codigo: String): Boolean {
        return jpaProductoRepository.existsByCodigo(codigo)
    }
}