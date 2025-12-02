package com.castillodelpan.backend.infrastructure.web.controllers

import com.castillodelpan.backend.domain.models.UnidadMedida
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.repositories.UnidadMedidaRepository
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
@RequestMapping("/unidades")
@CrossOrigin(origins = ["http://localhost:4200"])
class UnidadMedidaController(private val repository: UnidadMedidaRepository) {

    @PostMapping
    fun crear(@Valid @RequestBody dto: UnidadMedidaDTO): ResponseEntity<UnidadMedida> {
        val unidad = UnidadMedida(
            idUnidad = null,
            codigo = dto.codigo,
            nombre = dto.nombre,
            abreviatura = dto.abreviatura,
            estado = EstadoGeneral.ACTIVO
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(unidad))
    }

    @GetMapping
    fun listar(): ResponseEntity<List<UnidadMedida>> {
        return ResponseEntity.ok(repository.findAll())
    }

    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Int): ResponseEntity<UnidadMedida> {
        val unidad = repository.findById(id)
            ?: throw IllegalArgumentException("Unidad no encontrada con ID: $id")
        return ResponseEntity.ok(unidad)
    }

    data class UnidadMedidaDTO(
        @field:NotBlank(message = "El código es obligatorio")
        @field:Size(max = 10, message = "El código no puede exceder 10 caracteres")
        val codigo: String,

        @field:NotBlank(message = "El nombre es obligatorio")
        @field:Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
        val nombre: String,

        @field:NotBlank(message = "La abreviatura es obligatoria")
        @field:Size(max = 10, message = "La abreviatura no puede exceder 10 caracteres")
        val abreviatura: String
    )
}