package com.castillodelpan.backend.domain.models.enums

/** Estados generales para catálogos */
enum class EstadoGeneral(val code: String) {
    ACTIVO("ACTIVO"),
    INACTIVO("INACTIVO");

    companion object {
        fun fromCode(code: String): EstadoGeneral {
            return when (code.uppercase()) {
                "ACTIVO" -> ACTIVO
                "INACTIVO" -> INACTIVO
                else -> ACTIVO
            }
        }
    }
}
