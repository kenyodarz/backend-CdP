package com.castillodelpan.backend.infrastructure.persistence.mappers

import com.castillodelpan.backend.domain.models.Empleado
import com.castillodelpan.backend.infrastructure.persistence.entities.core.EmpleadoData
import java.time.LocalDateTime

object EmpleadoMapper {
    fun toDomain(data: EmpleadoData): Empleado {
        return Empleado(
            idEmpleado = data.idEmpleado,
            numeroDocumento = data.numeroDocumento,
            nombres = data.nombres,
            apellidos = data.apellidos,
            telefono = data.telefono,
            email = data.email,
            cargo = data.cargo,
            fechaIngreso = data.fechaIngreso,
            estado = data.estado
        )
    }

    fun toData(domain: Empleado): EmpleadoData {
        return EmpleadoData(
            idEmpleado = domain.idEmpleado,
            numeroDocumento = domain.numeroDocumento,
            nombres = domain.nombres,
            apellidos = domain.apellidos,
            telefono = domain.telefono,
            email = domain.email,
            cargo = domain.cargo,
            fechaIngreso = domain.fechaIngreso,
            estado = domain.estado,
            createdAt = domain.createdAt ?: LocalDateTime.now()
        )
    }
}