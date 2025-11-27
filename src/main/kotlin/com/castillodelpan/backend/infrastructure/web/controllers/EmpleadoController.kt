package com.castillodelpan.backend.infrastructure.web.controllers

import com.castillodelpan.backend.domain.models.Empleado
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.repositories.EmpleadoRepository
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
import java.time.LocalDate

@RestController
@RequestMapping("/empleados")
@CrossOrigin(origins = ["http://localhost:4200"])
class EmpleadoController(private val repository: EmpleadoRepository) {

    @PostMapping
    fun crear(@Valid @RequestBody dto: EmpleadoDTO): ResponseEntity<Empleado> {
        val empleado = Empleado(
            idEmpleado = null,
            numeroDocumento = dto.numeroDocumento,
            nombres = dto.nombres,
            apellidos = dto.apellidos,
            telefono = dto.telefono,
            email = dto.email,
            cargo = dto.cargo,
            fechaIngreso = dto.fechaIngreso,
            estado = EstadoGeneral.ACTIVO,
            createdAt = null
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(empleado))
    }

    @GetMapping
    fun listar(): ResponseEntity<List<Empleado>> {
        return ResponseEntity.ok(repository.findAll())
    }

    @GetMapping("/activos")
    fun listarActivos(): ResponseEntity<List<Empleado>> {
        return ResponseEntity.ok(repository.findAllActivos())
    }

    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Int): ResponseEntity<Empleado> {
        val empleado = repository.findById(id)
            ?: throw IllegalArgumentException("Empleado no encontrado con ID: $id")
        return ResponseEntity.ok(empleado)
    }

    data class EmpleadoDTO(
        @field:NotBlank(message = "El número de documento es obligatorio")
        @field:Size(max = 20, message = "El documento no puede exceder 20 caracteres")
        val numeroDocumento: String,

        @field:NotBlank(message = "Los nombres son obligatorios")
        @field:Size(max = 100, message = "Los nombres no pueden exceder 100 caracteres")
        val nombres: String,

        @field:NotBlank(message = "Los apellidos son obligatorios")
        @field:Size(max = 100, message = "Los apellidos no pueden exceder 100 caracteres")
        val apellidos: String,

        val telefono: String? = null,
        val email: String? = null,
        val cargo: String? = null,
        val fechaIngreso: LocalDate? = null
    )
}