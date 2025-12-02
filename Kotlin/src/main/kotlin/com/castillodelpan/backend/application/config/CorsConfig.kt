// ============================================
// 1. CorsConfig.kt
// ============================================
package com.castillodelpan.backend.application.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()

        // Permitir credenciales
        config.allowCredentials = true

        // Orígenes permitidos (Frontend Angular)
        config.allowedOrigins = listOf("http://localhost:4200", "http://localhost:8080")

        // Headers permitidos
        config.allowedHeaders = listOf("*")

        // Métodos HTTP permitidos
        config.allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")

        // Exponer headers
        config.exposedHeaders = listOf("Authorization", "Content-Type")

        source.registerCorsConfiguration("/**", config)

        return CorsFilter(source)
    }
}