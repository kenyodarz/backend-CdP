package com.castillodelpan.backend.infrastructure.persistence.repositories

import com.castillodelpan.backend.domain.models.enums.EstadoProducto
import com.castillodelpan.backend.infrastructure.persistence.entities.core.ProductoData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JpaProductoRepository : JpaRepository<ProductoData, Int> {
    fun findByCodigo(codigo: String): Optional<ProductoData>
    fun findByNombreContainingIgnoreCase(nombre: String): List<ProductoData>
    fun existsByCodigo(codigo: String): Boolean

    @Query(
        """
        SELECT p FROM ProductoData p 
        WHERE p.estado = :estado 
        ORDER BY p.nombre
    """
    )
    fun findByEstado(@Param("estado") estado: EstadoProducto): List<ProductoData>

    @Query(
        """
        SELECT p FROM ProductoData p 
        WHERE p.stockActual <= p.stockMinimo 
        AND p.estado = 'ACTIVO'
        ORDER BY p.stockActual ASC
    """
    )
    fun findProductosConStockBajo(): List<ProductoData>

    @Query(
        """
        SELECT p FROM ProductoData p 
        WHERE p.categoria.idCategoria = :idCategoria 
        AND p.estado = 'ACTIVO'
        ORDER BY p.nombre
    """
    )
    fun findByCategoria(@Param("idCategoria") idCategoria: Int): List<ProductoData>
}