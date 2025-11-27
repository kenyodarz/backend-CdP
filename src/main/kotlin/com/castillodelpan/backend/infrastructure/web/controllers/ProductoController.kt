package com.castillodelpan.backend.infrastructure.web.controllers

import com.castillodelpan.backend.application.dto.producto.ActualizarProductoDTO
import com.castillodelpan.backend.application.dto.producto.AjustarStockDTO
import com.castillodelpan.backend.application.dto.producto.CrearProductoDTO
import com.castillodelpan.backend.application.dto.producto.ProductoResponseDTO
import com.castillodelpan.backend.application.dto.producto.ProductoSimpleDTO
import com.castillodelpan.backend.application.usecases.producto.ActualizarProductoUseCase
import com.castillodelpan.backend.application.usecases.producto.AjustarStockProductoUseCase
import com.castillodelpan.backend.application.usecases.producto.BuscarProductosPorNombreUseCase
import com.castillodelpan.backend.application.usecases.producto.CrearProductoUseCase
import com.castillodelpan.backend.application.usecases.producto.DesactivarProductoUseCase
import com.castillodelpan.backend.application.usecases.producto.ListarProductosActivosUseCase
import com.castillodelpan.backend.application.usecases.producto.ObtenerProductoPorIdUseCase
import com.castillodelpan.backend.application.usecases.producto.ObtenerProductosConStockBajoUseCase
import com.castillodelpan.backend.application.usecases.producto.ObtenerProductosPorCategoriaUseCase
import com.castillodelpan.backend.domain.repositories.CategoriaRepository
import com.castillodelpan.backend.domain.repositories.UnidadMedidaRepository
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Controlador REST para Productos
 */
@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = ["http://localhost:4200"])
class ProductoController(
    private val crearProductoUseCase: CrearProductoUseCase,
    private val actualizarProductoUseCase: ActualizarProductoUseCase,
    private val obtenerProductoPorIdUseCase: ObtenerProductoPorIdUseCase,
    private val listarProductosActivosUseCase: ListarProductosActivosUseCase,
    private val buscarProductosPorNombreUseCase: BuscarProductosPorNombreUseCase,
    private val obtenerProductosConStockBajoUseCase: ObtenerProductosConStockBajoUseCase,
    private val desactivarProductoUseCase: DesactivarProductoUseCase,
    private val ajustarStockProductoUseCase: AjustarStockProductoUseCase,
    private val obtenerProductosPorCategoriaUseCase: ObtenerProductosPorCategoriaUseCase,
    private val categoriaRepository: CategoriaRepository,
    private val unidadMedidaRepository: UnidadMedidaRepository
) {

    /**
     * POST /productos
     * Crear un nuevo producto
     */
    @PostMapping
    fun crearProducto(
        @Valid @RequestBody dto: CrearProductoDTO
    ): ResponseEntity<ProductoResponseDTO> {

        val categoria = categoriaRepository.findById(dto.idCategoria)
            ?: throw IllegalArgumentException("Categoría no encontrada")

        val unidadMedida = unidadMedidaRepository.findById(dto.idUnidad)
            ?: throw IllegalArgumentException("Unidad de medida no encontrada")

        val producto = dto.toDomain(categoria, unidadMedida)
        val productoCreado = crearProductoUseCase(producto)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ProductoResponseDTO.fromDomain(productoCreado))
    }

    /**
     * PUT /productos/{id}
     * Actualizar un producto existente
     */
    @PutMapping("/{id}")
    fun actualizarProducto(
        @PathVariable id: Int,
        @Valid @RequestBody dto: ActualizarProductoDTO
    ): ResponseEntity<ProductoResponseDTO> {

        val categoria = categoriaRepository.findById(dto.idCategoria)
            ?: throw IllegalArgumentException("Categoría no encontrada")

        val unidadMedida = unidadMedidaRepository.findById(dto.idUnidad)
            ?: throw IllegalArgumentException("Unidad de medida no encontrada")

        val productoActualizado = actualizarProductoUseCase(
            id,
            CrearProductoDTO(
                codigo = dto.codigo,
                nombre = dto.nombre,
                descripcion = dto.descripcion,
                idCategoria = dto.idCategoria,
                idUnidad = dto.idUnidad,
                stockActual = 0, // No se actualiza por aquí
                stockMinimo = dto.stockMinimo,
                stockMaximo = dto.stockMaximo,
                requiereLote = dto.requiereLote,
                diasVidaUtil = dto.diasVidaUtil,
                imagenUrl = dto.imagenUrl,
                precios = dto.precios
            ).toDomain(categoria, unidadMedida).copy(
                idProducto = id,
                estado = dto.estado
            )
        )

        return ResponseEntity.ok(ProductoResponseDTO.fromDomain(productoActualizado))
    }

    /**
     * GET /productos/{id}
     * Obtener un producto por ID
     */
    @GetMapping("/{id}")
    fun obtenerProductoPorId(@PathVariable id: Int): ResponseEntity<ProductoResponseDTO> {
        val producto = obtenerProductoPorIdUseCase(id)
        return ResponseEntity.ok(ProductoResponseDTO.fromDomain(producto))
    }

    /**
     * GET /productos
     * Listar todos los productos activos
     */
    @GetMapping
    fun listarProductosActivos(): ResponseEntity<List<ProductoSimpleDTO>> {
        val productos = listarProductosActivosUseCase()
        return ResponseEntity.ok(
            productos.map { ProductoSimpleDTO.fromDomain(it) }
        )
    }

    /**
     * GET /productos/buscar?nombre=pan
     * Buscar productos por nombre
     */
    @GetMapping("/buscar")
    fun buscarProductos(
        @RequestParam nombre: String
    ): ResponseEntity<List<ProductoSimpleDTO>> {
        val productos = buscarProductosPorNombreUseCase(nombre)
        return ResponseEntity.ok(
            productos.map { ProductoSimpleDTO.fromDomain(it) }
        )
    }

    /**
     * GET /productos/stock-bajo
     * Obtener productos con stock bajo
     */
    @GetMapping("/stock-bajo")
    fun obtenerProductosConStockBajo(): ResponseEntity<List<ProductoSimpleDTO>> {
        val productos = obtenerProductosConStockBajoUseCase()
        return ResponseEntity.ok(
            productos.map { ProductoSimpleDTO.fromDomain(it) }
        )
    }

    /**
     * GET /productos/categoria/{idCategoria}
     * Obtener productos por categoría
     */
    @GetMapping("/categoria/{idCategoria}")
    fun obtenerProductosPorCategoria(
        @PathVariable idCategoria: Int
    ): ResponseEntity<List<ProductoSimpleDTO>> {
        val productos = obtenerProductosPorCategoriaUseCase(idCategoria)
        return ResponseEntity.ok(
            productos.map { ProductoSimpleDTO.fromDomain(it) }
        )
    }

    /**
     * PATCH /productos/{id}/desactivar
     * Desactivar un producto
     */
    @PatchMapping("/{id}/desactivar")
    fun desactivarProducto(@PathVariable id: Int): ResponseEntity<ProductoResponseDTO> {
        val producto = desactivarProductoUseCase(id)
        return ResponseEntity.ok(ProductoResponseDTO.fromDomain(producto))
    }

    /**
     * PATCH /productos/{id}/ajustar-stock
     * Ajustar el stock de un producto
     */
    @PatchMapping("/{id}/ajustar-stock")
    fun ajustarStock(
        @PathVariable id: Int,
        @Valid @RequestBody dto: AjustarStockDTO
    ): ResponseEntity<ProductoResponseDTO> {
        val producto = ajustarStockProductoUseCase(
            idProducto = id,
            nuevaCantidad = dto.nuevaCantidad,
            motivo = dto.motivo
        )
        return ResponseEntity.ok(ProductoResponseDTO.fromDomain(producto))
    }
}