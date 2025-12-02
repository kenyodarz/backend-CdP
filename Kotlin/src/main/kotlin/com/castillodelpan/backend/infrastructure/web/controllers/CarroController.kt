package com.castillodelpan.backend.infrastructure.web.controllers

import com.castillodelpan.backend.domain.models.catalogo.Carro
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.repositories.CarroRepository
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
import java.math.BigDecimal

@RestController
@RequestMapping("/carros")
@CrossOrigin(origins = ["http://localhost:4200"])
class CarroController(private val repository: CarroRepository) {

    @PostMapping
    fun crear(@Valid @RequestBody dto: CarroDTO): ResponseEntity<Carro> {
        val carro = Carro(
            idCarro = null,
            placa = dto.placa,
            modelo = dto.modelo,
            capacidadKg = dto.capacidadKg,
            estado = EstadoGeneral.ACTIVO,
            createdAt = null
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(carro))
    }

    @GetMapping
    fun listar(): ResponseEntity<List<Carro>> {
        return ResponseEntity.ok(repository.findAll())
    }

    @GetMapping("/activos")
    fun listarActivos(): ResponseEntity<List<Carro>> {
        return ResponseEntity.ok(repository.findAllActivos())
    }

    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Int): ResponseEntity<Carro> {
        val carro = repository.findById(id)
            ?: throw IllegalArgumentException("Carro no encontrado con ID: $id")
        return ResponseEntity.ok(carro)
    }

    data class CarroDTO(
        @field:NotBlank(message = "La placa es obligatoria")
        @field:Size(max = 10, message = "La placa no puede exceder 10 caracteres")
        val placa: String,

        @field:Size(max = 50, message = "El modelo no puede exceder 50 caracteres")
        val modelo: String? = null,

        val capacidadKg: BigDecimal? = null
    )
}