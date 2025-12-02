// ============================================
// 2. OpenApiConfig.kt
// ============================================
package com.castillodelpan.backend.application.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("API El Castillo del Pan")
                    .version("1.0.0")
                    .description(
                        """
                        Sistema de gestión de inventario y ventas para panadería.
                        
                        ## Módulos Principales:
                        - **Productos**: Gestión del catálogo de productos
                        - **Clientes**: Administración de clientes y rutas
                        - **Órdenes**: Creación y seguimiento de órdenes de despacho
                        - **Inventario**: Control de stock y movimientos
                        - **Reportes**: Consultas y estadísticas
                    """.trimIndent()
                    )
                    .contact(
                        Contact()
                            .name("El Castillo del Pan")
                            .email("info@castillodelpan.com")
                    )
                    .license(
                        License()
                            .name("Propietario")
                            .url("https://castillodelpan.com")
                    )
            )
            .servers(
                listOf(
                    Server()
                        .url("http://localhost:8080/api")
                        .description("Servidor de Desarrollo"),
                    Server()
                        .url("https://api.castillodelpan.com/api")
                        .description("Servidor de Producción")
                )
            )
    }
}