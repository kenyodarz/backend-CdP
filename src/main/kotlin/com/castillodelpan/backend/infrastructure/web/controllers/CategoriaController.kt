package com.castillodelpan.backend.infrastructure.web.controllers

import com.castillodelpan.backend.domain.models.Categoria
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.repositories.CategoriaRepository
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// ============================================
// CATEGORIA CONTROLLER
// ============================================

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = ["http://localhost:4200"])
class CategoriaController(private val repository: CategoriaRepository) {

    @PostMapping
    fun crear(@Valid @RequestBody dto: CategoriaDTO): ResponseEntity<Categoria> {
        val categoria = Categoria(
            idCategoria = null,
            nombre = dto.nombre,
            descripcion = dto.descripcion,
            estado = EstadoGeneral.ACTIVO
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(categoria))
    }

    @GetMapping
    fun listar(): ResponseEntity<List<Categoria>> {
        return ResponseEntity.ok(repository.findAll())
    }

    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Int): ResponseEntity<Categoria> {
        val categoria = repository.findById(id)
            ?: throw IllegalArgumentException("Categoría no encontrada con ID: $id")
        return ResponseEntity.ok(categoria)
    }

    @PutMapping("/{id}")
    fun actualizar(
        @PathVariable id: Int,
        @Valid @RequestBody dto: CategoriaDTO
    ): ResponseEntity<Categoria> {
        repository.findById(id)
            ?: throw IllegalArgumentException("Categoría no encontrada con ID: $id")

        val categoria = Categoria(
            idCategoria = id,
            nombre = dto.nombre,
            descripcion = dto.descripcion,
            estado = dto.estado
        )
        return ResponseEntity.ok(repository.save(categoria))
    }

    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Int): ResponseEntity<Void> {
        repository.delete(id)
        return ResponseEntity.noContent().build()
    }

    data class CategoriaDTO(
        @field:NotBlank(message = "El nombre es obligatorio")
        @field:Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
        val nombre: String,
        val descripcion: String? = null,
        val estado: EstadoGeneral = EstadoGeneral.ACTIVO
    )
}