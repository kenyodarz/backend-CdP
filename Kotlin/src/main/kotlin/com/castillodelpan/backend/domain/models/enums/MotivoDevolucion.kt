package com.castillodelpan.backend.domain.models.enums

/**
 * Motivos de devolución de productos
 */
enum class MotivoDevolucion(val descripcion: String, val reingresaStock: Boolean) {
    NO_VENDIDO("Producto no vendido", true),
    DANADO("Producto dañado durante transporte", false),
    VENCIDO("Producto vencido", false),
    ERROR_DESPACHO("Error en el despacho", true)
}