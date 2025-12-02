package com.castillodelpan.backend.infrastructure.persistence.mappers

import com.castillodelpan.backend.domain.models.Categoria
import com.castillodelpan.backend.infrastructure.persistence.entities.core.CategoriaData

object CategoriaMapper {
    fun toDomain(data: CategoriaData): Categoria {
        return Categoria(
            idCategoria = data.idCategoria,
            nombre = data.nombre,
            descripcion = data.descripcion,
            estado = data.estado
        )
    }

    fun toData(domain: Categoria): CategoriaData {
        return CategoriaData(
            idCategoria = domain.idCategoria,
            nombre = domain.nombre,
            descripcion = domain.descripcion,
            estado = domain.estado
        )
    }
}
