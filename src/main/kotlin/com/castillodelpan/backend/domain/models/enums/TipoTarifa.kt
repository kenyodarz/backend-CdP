package com.castillodelpan.backend.domain.models.enums

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Tarifas/Precios según tipo de cliente
 * Basado en el sistema actual de El Castillo del Pan
 *
 * Cada tarifa puede calcular automáticamente su precio a partir del precio base,
 * o usar un precio especial manual (JM, CR)
 */
enum class TipoTarifa(
    val descripcion: String,
    val descuento: Int,
    val esCalculado: Boolean = true
) {
    PRECIO_0D("Precio Full - Sin descuento", 0, true),
    PRECIO_10D("Precio con 10% descuento", 10, true),
    PRECIO_5D("Precio con 5% descuento", 5, true),
    PRECIO_ES("Precio Especial PyF - 15% descuento", 15, true),
    PRECIO_JM("Precio JM - Especial", 0, false),
    PRECIO_CR("Precio CR - Especial", 0, false);

    /**
     * Calcula el precio aplicando el descuento al precio base
     *
     * @param precioBase El precio base del producto
     * @return El precio con el descuento aplicado, redondeado a entero
     */
    fun calcularPrecio(precioBase: BigDecimal): BigDecimal {
        if (!esCalculado || descuento == 0) {
            return precioBase
        }

        val factor = BigDecimal.ONE - (BigDecimal(descuento).divide(BigDecimal(100)))
        return precioBase.multiply(factor).setScale(0, RoundingMode.HALF_UP)
    }

    companion object {
        fun fromCode(code: String): TipoTarifa {
            return when (code.uppercase()) {
                "0D" -> PRECIO_0D
                "10D" -> PRECIO_10D
                "5D" -> PRECIO_5D
                "ES" -> PRECIO_ES
                "JM" -> PRECIO_JM
                "CR" -> PRECIO_CR
                else -> PRECIO_0D
            }
        }
    }
}