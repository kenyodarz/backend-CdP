package com.castillodelpan.backend.infrastructure.web.controllers

import com.castillodelpan.backend.domain.models.Ruta
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.repositories.RutaRepository
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rutas")
@CrossOrigin(origins = ["http://localhost:4200"])
class RutaController(private val repository: RutaRepository) {

    @PostMapping
    fun crear(@Valid @RequestBody dto: RutaDTO): ResponseEntity<Ruta> {
        val ruta = Ruta(
            idRuta = null,
            nombre = dto.nombre,
            descripcion = dto.descripcion,
            estado = EstadoGeneral.ACTIVO
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(ruta))
    }

    @GetMapping
    fun listar(): ResponseEntity<List<Ruta>> {
        return ResponseEntity.ok(repository.findAll())
    }

    @GetMapping("/activas")
    fun listarActivas(): ResponseEntity<List<Ruta>> {
        return ResponseEntity.ok(repository.findByEstado(EstadoGeneral.ACTIVO))
    }

    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Int): ResponseEntity<Ruta> {
        val ruta = repository.findById(id)
            ?: throw IllegalArgumentException("Ruta no encontrada con ID: $id")
        return ResponseEntity.ok(ruta)
    }

    data class RutaDTO(
        @field:NotBlank(message = "El nombre es obligatorio")
        @field:Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
        val nombre: String,
        val descripcion: String? = null
    )
}