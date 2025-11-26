package com.castillodelpan.backend.infrastructure.persistence.repositories.impl

import com.castillodelpan.backend.domain.models.Categoria
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.repositories.CategoriaRepository
import com.castillodelpan.backend.infrastructure.persistence.mappers.CategoriaMapper
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaCategoriaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class CategoriaRepositoryImpl(
    private val jpaCategoriaRepository: JpaCategoriaRepository
) : CategoriaRepository {

    override fun findById(id: Int): Categoria? {
        return jpaCategoriaRepository.findById(id)
            .map { CategoriaMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByNombre(nombre: String): Categoria? {
        return jpaCategoriaRepository.findByNombre(nombre)
            .map { CategoriaMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findAll(): List<Categoria> {
        return jpaCategoriaRepository.findAll()
            .map { CategoriaMapper.toDomain(it) }
    }

    override fun findByEstado(estado: EstadoGeneral): List<Categoria> {
        return jpaCategoriaRepository.findByEstado(estado)
            .map { CategoriaMapper.toDomain(it) }
    }

    override fun save(categoria: Categoria): Categoria {
        val categoriaData = CategoriaMapper.toData(categoria)
        val savedData = jpaCategoriaRepository.save(categoriaData)
        return CategoriaMapper.toDomain(savedData)
    }

    override fun delete(id: Int) {
        jpaCategoriaRepository.deleteById(id)
    }
}