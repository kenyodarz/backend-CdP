package com.castillodelpan.backend.infrastructure.persistence.mappers

import com.castillodelpan.backend.domain.models.enums.TipoTarifa
import com.castillodelpan.backend.domain.models.producto.Producto
import com.castillodelpan.backend.infrastructure.persistence.entities.core.CategoriaData
import com.castillodelpan.backend.infrastructure.persistence.entities.core.PrecioEspecialData
import com.castillodelpan.backend.infrastructure.persistence.entities.core.ProductoData
import com.castillodelpan.backend.infrastructure.persistence.entities.core.UnidadMedidaData

object ProductoMapper {
    fun toDomain(data: ProductoData): Producto {
        // Convertir precios especiales de lista a mapa
        val preciosEspecialesMap = data.preciosEspeciales.associate { it.tipoTarifa to it.precio }

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
            precioBase = data.precioBase,
            preciosEspeciales = preciosEspecialesMap
        )
    }

    fun toData(
        domain: Producto,
        categoriaData: CategoriaData,
        unidadData: UnidadMedidaData
    ): ProductoData {
        val productoData =
            ProductoData(
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
                estado = domain.estado,
                precioBase = domain.precioBase
            )

        // Mapear precios especiales (solo JM y CR si existen)
        productoData.preciosEspeciales =
            domain.preciosEspeciales
                .filter { it.key == TipoTarifa.JM || it.key == TipoTarifa.CR }
                .map { (tarifa, precio) ->
                    PrecioEspecialData(
                        producto = productoData,
                        tipoTarifa = tarifa,
                        precio = precio
                    )
                }
                .toMutableList()

        return productoData
    }
}
