package com.castillodelpan.backend.infrastructure.web.controllers

import com.castillodelpan.backend.domain.models.catalogo.Conductor
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.repositories.ConductorRepository
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
@RequestMapping("/conductores")
@CrossOrigin(origins = ["http://localhost:4200"])
class ConductorController(private val repository: ConductorRepository) {

    @PostMapping
    fun crear(@Valid @RequestBody dto: ConductorDTO): ResponseEntity<Conductor> {
        val conductor = Conductor(
            idConductor = null,
            numeroDocumento = dto.numeroDocumento,
            nombres = dto.nombres,
            apellidos = dto.apellidos,
            telefono = dto.telefono,
            licencia = dto.licencia,
            estado = EstadoGeneral.ACTIVO,
            createdAt = null
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(conductor))
    }

    @GetMapping
    fun listar(): ResponseEntity<List<Conductor>> {
        return ResponseEntity.ok(repository.findAll())
    }

    @GetMapping("/activos")
    fun listarActivos(): ResponseEntity<List<Conductor>> {
        return ResponseEntity.ok(repository.findAllActivos())
    }

    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Int): ResponseEntity<Conductor> {
        val conductor = repository.findById(id)
            ?: throw IllegalArgumentException("Conductor no encontrado con ID: $id")
        return ResponseEntity.ok(conductor)
    }

    data class ConductorDTO(
        @field:NotBlank(message = "El número de documento es obligatorio")
        @field:Size(max = 20, message = "El documento no puede exceder 20 caracteres")
        val numeroDocumento: String,

        @field:NotBlank(message = "Los nombres son obligatorios")
        @field:Size(max = 100, message = "Los nombres no pueden exceder 100 caracteres")
        val nombres: String,

        @field:NotBlank(message = "Los apellidos son obligatorios")
        @field:Size(max = 100, message = "Los apellidos no pueden exceder 100 caracteres")
        val apellidos: String,

        @field:Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
        val telefono: String? = null,

        @field:Size(max = 50, message = "La licencia no puede exceder 50 caracteres")
        val licencia: String? = null
    )
}