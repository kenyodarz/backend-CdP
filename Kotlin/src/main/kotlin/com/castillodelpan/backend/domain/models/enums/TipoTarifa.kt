package com.castillodelpan.backend.domain.models.enums

import java.math.BigDecimal
import java.math.RoundingMode

enum class TipoTarifa(
    val descripcion: String,
    val descuento: Int,
    val esCalculado: Boolean = true,
    val code: String
) {
    `0D`("Precio Full - Sin descuento", 0, true, "0D"),
    `10D`("Precio con 10% descuento", 10, true, "10D"),
    `5D`("Precio con 5% descuento", 5, true, "5D"),
    ES("Precio Especial PyF - 15% descuento", 15, true, "ES"),

    // CAMBIO AQUÍ → nombres exactamente iguales a los que están en la BD
    JM("Precio JM - Especial", 0, false, "JM"),
    CR("Precio CR - Especial", 0, false, "CR");

    // ← Esto es lo que hace que funcione con ENUM nativo de PostgreSQL
    override fun toString(): String = name  // <-- name, no code

    fun calcularPrecio(precioBase: BigDecimal): BigDecimal {
        if (!esCalculado || descuento == 0) return precioBase
        val factor =
            BigDecimal.ONE - BigDecimal(descuento).divide(BigDecimal(100), 10, RoundingMode.HALF_UP)
        return precioBase.multiply(factor).setScale(0, RoundingMode.HALF_UP)
    }

    companion object {
        fun fromCode(code: String): TipoTarifa =
            values().find { it.code.equals(code, ignoreCase = true) } ?: `0D`
    }
}