# 🚀 Guía de Testing API - El Castillo del Pan

## 📋 Índice

1. [Configuración Inicial](#configuración-inicial)
2. [Endpoints de Productos](#endpoints-de-productos)
3. [Endpoints de Clientes](#endpoints-de-clientes)
4. [Endpoints de Órdenes](#endpoints-de-órdenes)
5. [Códigos de Estado HTTP](#códigos-de-estado)

---

## ⚙️ Configuración Inicial

### 1. Configurar Base de Datos

```bash
# Crear la base de datos
createdb db_castillo_pan

# Aplicar el schema
psql -d db_castillo_pan -f src/main/resources/schema.sql
```

### 2. Configurar Variables de Entorno

```bash
export PASSWORD=tu_password_postgres
```

### 3. Ejecutar la Aplicación

```bash
# Con Maven Wrapper
./mvnw spring-boot:run

# O compilar y ejecutar
./mvnw clean package
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

### 4. Acceder a Swagger UI

```
http://localhost:8080/api/swagger-ui.html
```

---

## 🛍️ Endpoints de Productos

### Base URL

```
http://localhost:8080/api/productos
```

### 1. Crear Producto

**POST** `/productos`

```json
{
  "codigo": "PAN001",
  "nombre": "Pan Integral Grande",
  "descripcion": "Pan integral de 500g",
  "idCategoria": 1,
  "idUnidad": 1,
  "stockActual": 100,
  "stockMinimo": 20,
  "stockMaximo": 500,
  "requiereLote": true,
  "diasVidaUtil": 3,
  "precios": [
    {
      "tipoTarifa": "PRECIO_0D",
      "precio": 3500.00
    },
    {
      "tipoTarifa": "PRECIO_10D",
      "precio": 3150.00
    },
    {
      "tipoTarifa": "PRECIO_5D",
      "precio": 3325.00
    }
  ]
}
```

**Respuesta (201 Created):**

```json
{
  "idProducto": 1,
  "codigo": "PAN001",
  "nombre": "Pan Integral Grande",
  "descripcion": "Pan integral de 500g",
  "categoria": {
    "idCategoria": 1,
    "nombre": "Panes"
  },
  "unidadMedida": {
    "idUnidad": 1,
    "nombre": "Unidad",
    "abreviatura": "UN"
  },
  "stockActual": 100,
  "stockMinimo": 20,
  "stockMaximo": 500,
  "requiereLote": true,
  "diasVidaUtil": 3,
  "imagenUrl": null,
  "estado": "ACTIVO",
  "stockBajo": false,
  "precios": [
    {
      "idPrecio": 1,
      "tipoTarifa": "PRECIO_0D",
      "precio": 3500.00
    },
    {
      "idPrecio": 2,
      "tipoTarifa": "PRECIO_10D",
      "precio": 3150.00
    },
    {
      "idPrecio": 3,
      "tipoTarifa": "PRECIO_5D",
      "precio": 3325.00
    }
  ]
}
```

### 2. Listar Productos Activos

**GET** `/productos`

**Respuesta (200 OK):**

```json
[
  {
    "idProducto": 1,
    "codigo": "PAN001",
    "nombre": "Pan Integral Grande",
    "stockActual": 100,
    "stockMinimo": 20,
    "stockBajo": false,
    "precio0D": 3500.00,
    "estado": "ACTIVO"
  }
]
```

### 3. Buscar Producto por ID

**GET** `/productos/{id}`

```bash
curl http://localhost:8080/api/productos/1
```

### 4. Buscar Productos por Nombre

**GET** `/productos/buscar?nombre={texto}`

```bash
curl "http://localhost:8080/api/productos/buscar?nombre=integral"
```

### 5. Obtener Productos con Stock Bajo

**GET** `/productos/stock-bajo`

```bash
curl http://localhost:8080/api/productos/stock-bajo
```

### 6. Obtener Productos por Categoría

**GET** `/productos/categoria/{idCategoria}`

```bash
curl http://localhost:8080/api/productos/categoria/1
```

### 7. Ajustar Stock de Producto

**PATCH** `/productos/{id}/ajustar-stock`

```json
{
  "nuevaCantidad": 150,
  "motivo": "Recepción de producción diaria"
}
```

### 8. Desactivar Producto

**PATCH** `/productos/{id}/desactivar`

```bash
curl -X PATCH http://localhost:8080/api/productos/1/desactivar
```

---

## 👥 Endpoints de Clientes

### Base URL

```
http://localhost:8080/api/clientes
```

### 1. Crear Cliente

**POST** `/clientes`

```json
{
  "nombre": "Tienda Don Pepe",
  "codigo": "CLI001",
  "tipoDocumento": "NIT",
  "numeroDocumento": "900123456-1",
  "direccion": "Calle 10 # 20-30",
  "telefono": "3001234567",
  "barrio": "Centro",
  "comuna": "1",
  "tipoNegocio": "Tienda de Barrio",
  "tipoTarifa": "PRECIO_10D",
  "idRuta": 1,
  "idConductor": 1,
  "horarioEntrega": "7:00 AM - 8:00 AM",
  "estado": "ACTIVO"
}
```

### 2. Listar Clientes Activos

**GET** `/clientes`

### 3. Buscar Cliente por Nombre

**GET** `/clientes/buscar?nombre={texto}`

### 4. Listar Clientes por Ruta

**GET** `/clientes/ruta/{idRuta}`

### 5. Listar Clientes por Barrio

**GET** `/clientes/barrio/{barrio}`

---

## 📦 Endpoints de Órdenes de Despacho

### Base URL

```
http://localhost:8080/api/ordenes
```

### 1. Crear Orden de Despacho

**POST** `/ordenes`

```json
{
  "idCliente": 1,
  "idEmpleado": 1,
  "observaciones": "Cliente solicita entrega temprano",
  "descuento": 0,
  "detalles": [
    {
      "idProducto": 1,
      "cantidad": 20
    },
    {
      "idProducto": 2,
      "cantidad": 15
    }
  ]
}
```

**Respuesta:**

```json
{
  "idOrden": 1,
  "numeroOrden": "ORD-20241126-0001",
  "cliente": {
    "idCliente": 1,
    "nombre": "Tienda Don Pepe"
  },
  "fechaOrden": "2024-11-26",
  "subtotal": 122500.00,
  "descuento": 0.00,
  "total": 122500.00,
  "estado": "PENDIENTE",
  "detalles": [
    {
      "idDetalle": 1,
      "producto": {
        "idProducto": 1,
        "nombre": "Pan Integral Grande"
      },
      "cantidad": 20,
      "precioUnitario": 3150.00,
      "subtotal": 63000.00
    },
    {
      "idDetalle": 2,
      "producto": {
        "idProducto": 2,
        "nombre": "Pan Blanco Mediano"
      },
      "cantidad": 15,
      "precioUnitario": 3500.00,
      "subtotal": 52500.00
    }
  ]
}
```

### 2. Cambiar Estado de Orden

**PATCH** `/ordenes/{id}/estado`

```json
{
  "nuevoEstado": "EN_PREPARACION"
}
```

Estados válidos:

- `PENDIENTE` → `EN_PREPARACION` o `CANCELADA`
- `EN_PREPARACION` → `LISTA` o `CANCELADA`
- `LISTA` → `DESPACHADA` o `CANCELADA`
- `DESPACHADA` → `ENTREGADA`

### 3. Cancelar Orden

**PATCH** `/ordenes/{id}/cancelar`

```json
{
  "motivo": "Cliente canceló el pedido"
}
```

### 4. Listar Órdenes por Fecha

**GET** `/ordenes/fecha/{fecha}`

```bash
curl http://localhost:8080/api/ordenes/fecha/2024-11-26
```

### 5. Listar Órdenes por Estado

**GET** `/ordenes/estado/{estado}`

```bash
curl http://localhost:8080/api/ordenes/estado/PENDIENTE
```

### 6. Obtener Órdenes Pendientes del Día

**GET** `/ordenes/pendientes-hoy`

### 7. Obtener Historial de Cliente

**GET** `/ordenes/cliente/{idCliente}`

---

## 📊 Códigos de Estado HTTP

| Código | Significado           | Cuándo se usa                       |
|--------|-----------------------|-------------------------------------|
| 200    | OK                    | Operación GET exitosa               |
| 201    | Created               | Recurso creado exitosamente (POST)  |
| 204    | No Content            | Operación DELETE exitosa            |
| 400    | Bad Request           | Error de validación o negocio       |
| 404    | Not Found             | Recurso no encontrado               |
| 409    | Conflict              | Conflicto (ej: documento duplicado) |
| 500    | Internal Server Error | Error inesperado del servidor       |

---

## 🧪 Ejemplos con cURL

### Crear Categoría

```bash
curl -X POST http://localhost:8080/api/categorias \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Panes",
    "descripcion": "Productos de panadería",
    "estado": "ACTIVO"
  }'
```

### Crear Unidad de Medida

```bash
curl -X POST http://localhost:8080/api/unidades \
  -H "Content-Type: application/json" \
  -d '{
    "codigo": "UN",
    "nombre": "Unidad",
    "abreviatura": "UN",
    "estado": "ACTIVO"
  }'
```

### Crear Ruta

```bash
curl -X POST http://localhost:8080/api/rutas \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Ruta Centro",
    "descripcion": "Clientes del centro de la ciudad",
    "estado": "ACTIVO"
  }'
```

---

## 🔐 Notas de Seguridad

⚠️ **IMPORTANTE**: Esta versión NO incluye autenticación/autorización.

Para implementar en producción, necesitas agregar:

- Spring Security
- JWT tokens
- Roles y permisos
- Protección CSRF
- Rate limiting

---

## 📝 Próximos Pasos

1. ✅ Crear los DTOs y controllers faltantes (Categoría, Unidad, Ruta, etc.)
2. ⚠️ Implementar autenticación con JWT
3. 📊 Crear endpoints de reportes
4. 🔄 Implementar manejo de lotes de inventario
5. 📱 Desarrollar el frontend en Angular
6. ✅ Escribir tests unitarios e integración
7. 📦 Configurar Docker y docker-compose

---

## 🐛 Debugging

### Ver logs en consola

Los logs SQL se muestran en consola en modo desarrollo.

### Verificar base de datos

```bash
psql -d db_castillo_pan

# Ver productos
SELECT * FROM productos;

# Ver órdenes
SELECT * FROM ordenes_despacho;
```

### Probar endpoints con Swagger

```
http://localhost:8080/api/swagger-ui.html
```

---

## 📞 Soporte

Para reportar problemas o sugerencias, crear un issue en el repositorio.