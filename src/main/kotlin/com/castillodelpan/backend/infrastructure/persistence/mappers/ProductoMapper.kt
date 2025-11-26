package com.castillodelpan.backend.infrastructure.persistence.mappers

import com.castillodelpan.backend.domain.models.producto.Producto
import com.castillodelpan.backend.infrastructure.persistence.entities.core.CategoriaData
import com.castillodelpan.backend.infrastructure.persistence.entities.core.ProductoData
import com.castillodelpan.backend.infrastructure.persistence.entities.core.UnidadMedidaData

object ProductoMapper {
    fun toDomain(data: ProductoData): Producto {
        return Producto(
            idProducto = data.idProducto,
            codigo = data.codigo,
            nombre = data.nombre,
            descripcion = data.descripcion,
            categoria = CategoriaMapper.toDomain(data.categoria),
            unidadMedida = UnidadMedidaMapper.toDomain(data.unidadMedida),
            stockActual = data.stockActual,
            stockMinimo = data.stockMinimo,
            stockMaximo = data.stockMaximo,
            requiereLote = data.requiereLote,
            diasVidaUtil = data.diasVidaUtil,
            imagenUrl = data.imagenUrl,
            estado = data.estado,
            precios = data.precios.map { PrecioProductoMapper.toDomain(it) }.toMutableList()
        )
    }

    fun toData(
        domain: Producto,
        categoriaData: CategoriaData,
        unidadData: UnidadMedidaData
    ): ProductoData {
        val productoData = ProductoData(
            idProducto = domain.idProducto,
            codigo = domain.codigo,
            nombre = domain.nombre,
            descripcion = domain.descripcion,
            categoria = categoriaData,
            unidadMedida = unidadData,
            stockActual = domain.stockActual,
            stockMinimo = domain.stockMinimo,
            stockMaximo = domain.stockMaximo,
            requiereLote = domain.requiereLote,
            diasVidaUtil = domain.diasVidaUtil,
            imagenUrl = domain.imagenUrl,
            estado = domain.estado
        )

        // Mapear precios
        productoData.precios = domain.precios.map {
            PrecioProductoMapper.toData(it, productoData)
        }.toMutableList()

        return productoData
    }
}