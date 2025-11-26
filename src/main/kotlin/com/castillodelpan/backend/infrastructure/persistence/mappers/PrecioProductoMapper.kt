package com.castillodelpan.backend.infrastructure.persistence.mappers

import com.castillodelpan.backend.domain.models.producto.PrecioProducto
import com.castillodelpan.backend.infrastructure.persistence.entities.core.PrecioProductoData
import com.castillodelpan.backend.infrastructure.persistence.entities.core.ProductoData

object PrecioProductoMapper {
    fun toDomain(data: PrecioProductoData): PrecioProducto {
        return PrecioProducto(
            idPrecio = data.idPrecio,
            tipoTarifa = data.tipoTarifa,
            precio = data.precio
        )
    }

    fun toData(domain: PrecioProducto, productoData: ProductoData): PrecioProductoData {
        return PrecioProductoData(
            idPrecio = domain.idPrecio,
            producto = productoData,
            tipoTarifa = domain.tipoTarifa,
            precio = domain.precio
        )
    }
}