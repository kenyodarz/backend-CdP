package com.castillodelpan.backend.infrastructure.persistence.entities.core

import com.castillodelpan.backend.domain.models.enums.EstadoProducto
import com.castillodelpan.backend.domain.models.enums.TipoTarifa
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal

@Entity
@Table(name = "productos")
data class ProductoData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    var idProducto: Int? = null,
    @Column(unique = true, length = 50) var codigo: String? = null,
    @Column(nullable = false, length = 200) @NotBlank var nombre: String,
    @Column(columnDefinition = "TEXT") var descripcion: String? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    var categoria: CategoriaData,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_unidad", nullable = false)
    var unidadMedida: UnidadMedidaData,
    @Column(name = "stock_actual", nullable = false) var stockActual: Int = 0,
    @Column(name = "stock_minimo", nullable = false) var stockMinimo: Int = 5,
    @Column(name = "stock_maximo") var stockMaximo: Int? = null,
    @Column(name = "requiere_lote", nullable = false) var requiereLote: Boolean = false,
    @Column(name = "dias_vida_util") var diasVidaUtil: Int? = null,
    @Column(name = "imagen_url", length = 255) var imagenUrl: String? = null,
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var estado: EstadoProducto = EstadoProducto.ACTIVO,

    // Precio base del producto (precio completo sin descuento)
    @Column(name = "precio_base", nullable = false, precision = 10, scale = 2)
    var precioBase: BigDecimal = BigDecimal.ZERO,

    // Precios especiales opcionales (solo para JM y CR)
    @OneToMany(
        mappedBy = "producto",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JsonIgnore
    var preciosEspeciales: MutableList<PrecioEspecialData> = mutableListOf()
) : EntidadAuditable() {

    /**
     * Obtiene el precio según el tipo de tarifa Si existe precio especial, lo usa; si no, calcula
     * automáticamente
     */
    fun getPrecioPorTarifa(tarifa: TipoTarifa): BigDecimal {
        // Buscar precio especial
        preciosEspeciales.firstOrNull { it.tipoTarifa == tarifa }?.let {
            return it.precio
        }

        // Calcular automáticamente desde precio base
        return tarifa.calcularPrecio(precioBase)
    }

    fun tieneStockDisponible(cantidad: Int): Boolean {
        return stockActual >= cantidad
    }

    val stockBajo: Boolean
        get() = stockActual <= stockMinimo
}
