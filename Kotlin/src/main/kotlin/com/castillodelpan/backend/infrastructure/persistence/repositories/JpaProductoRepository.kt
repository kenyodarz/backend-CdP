package com.castillodelpan.backend.infrastructure.persistence.repositories

import com.castillodelpan.backend.domain.models.enums.EstadoProducto
import com.castillodelpan.backend.infrastructure.persistence.entities.core.ProductoData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface JpaProductoRepository : JpaRepository<ProductoData, Int> {

    fun findByCodigo(codigo: String): Optional<ProductoData>
    fun findByNombreContainingIgnoreCase(nombre: String): List<ProductoData>
    fun existsByCodigo(codigo: String): Boolean

    // Funciona perfecto con parámetro (JPQL + Enum → Hibernate castea solo)
    @Query("SELECT p FROM ProductoData p WHERE p.estado = :estado ORDER BY p.nombre")
    fun findByEstado(@Param("estado") estado: EstadoProducto): List<ProductoData>

    // STOCK BAJO – casteamos explícitamente el literal
    @Query("SELECT p FROM ProductoData p WHERE p.stockActual <= p.stockMinimo AND p.estado = 'ACTIVO' ORDER BY p.stockActual ASC")
    fun findProductosConStockBajo(): List<ProductoData>

    // POR CATEGORÍA – también casteamos
    @Query("SELECT p FROM ProductoData p WHERE p.categoria.idCategoria = :idCategoria AND p.estado = 'ACTIVO' ORDER BY p.nombre")
    fun findByCategoria(@Param("idCategoria") idCategoria: Int): List<ProductoData>

    // BONUS: los que más vas a usar en el dashboard y listados
    @Query("SELECT p FROM ProductoData p WHERE p.estado = 'ACTIVO' ORDER BY p.nombre")
    fun findProductosActivos(): List<ProductoData>

    @Query("SELECT p FROM ProductoData p WHERE p.estado = 'INACTIVO' ORDER BY p.nombre")
    fun findProductosInactivos(): List<ProductoData>

    // Conteo rápido para el dashboard (ultra rápido)
    @Query(value = "SELECT COUNT(*) FROM productos WHERE estado = 'ACTIVO'", nativeQuery = true)
    fun countProductosActivos(): Long

    @Query(value = "SELECT COUNT(*) FROM productos WHERE stock_actual <= stock_minimo AND estado = 'ACTIVO'", nativeQuery = true)
    fun countProductosConStockBajo(): Long
}