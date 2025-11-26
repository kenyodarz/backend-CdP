package com.castillodelpan.backend.domain.models.enums

/**
 * Roles de usuarios del sistema
 */
enum class RolUsuario(val descripcion: String) {
    ADMIN("Administrador - Acceso total"),
    SUPERVISOR("Supervisor - Gestión operativa"),
    BODEGUERO("Bodeguero - Entradas/Salidas"),
    VENDEDOR("Vendedor - Solo crear órdenes"),
    CONSULTA("Consulta - Solo lectura");

    fun tienePermiso(permisoRequerido: RolUsuario): Boolean {
        return when (this) {
            ADMIN -> true
            SUPERVISOR -> permisoRequerido != ADMIN
            BODEGUERO -> permisoRequerido in listOf(BODEGUERO, CONSULTA)
            VENDEDOR -> permisoRequerido in listOf(VENDEDOR, CONSULTA)
            CONSULTA -> permisoRequerido == CONSULTA
        }
    }
}