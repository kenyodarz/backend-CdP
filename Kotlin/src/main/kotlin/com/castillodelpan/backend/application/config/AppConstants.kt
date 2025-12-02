package com.castillodelpan.backend.application.config

object AppConstants {
    // Formato de fechas
    const val DATE_FORMAT = "yyyy-MM-dd"
    const val DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

    // Paginación por defecto
    const val DEFAULT_PAGE_SIZE = 20
    const val MAX_PAGE_SIZE = 100

    // Códigos de error
    const val ERROR_VALIDATION = "VALIDATION_ERROR"
    const val ERROR_NOT_FOUND = "RESOURCE_NOT_FOUND"
    const val ERROR_DUPLICATE = "DUPLICATE_RESOURCE"
    const val ERROR_BUSINESS = "BUSINESS_RULE_VIOLATION"

    // Stock
    const val STOCK_MINIMO_DEFAULT = 5
    const val DIAS_ALERTA_VENCIMIENTO = 3
}