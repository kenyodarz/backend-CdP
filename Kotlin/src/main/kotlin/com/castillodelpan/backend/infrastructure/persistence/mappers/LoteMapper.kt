package com.castillodelpan.backend.infrastructure.persistence.mappers

import com.castillodelpan.backend.domain.models.inventario.Lote
import com.castillodelpan.backend.infrastructure.persistence.entities.inventario.LoteData

object LoteMapper {
    fun toDomain(data: LoteData): Lote {
        return Lote(
            idLote = data.idLote,
            producto = ProductoMapper.toDomain(data.producto),
            codigoLote = data.codigoLote,
            fechaElaboracion = data.fechaElaboracion,
            fechaVencimiento = data.fechaVencimiento,
            cantidadInicial = data.cantidadInicial,
            cantidadActual = data.cantidadActual,
            estado = data.estado
        )
    }
}