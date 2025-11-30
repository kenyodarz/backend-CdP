-- ============================================
-- SCHEMA V2 - Modelo de Precios Simplificado
-- ============================================
-- Cambios principales:
-- 1. Agregado campo precio_base a tabla productos
-- 2. Reemplazada tabla precios_producto por precios_especiales
-- 3. precios_especiales solo almacena JM y CR (precios manuales)
-- 4. Los demás precios (0D, 10D, 5D, ES) se calculan automáticamente
-- ============================================

-- ============================================
-- EXTENSIONES
-- ============================================
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- ============================================
-- TIPOS ENUM
-- ============================================

CREATE TYPE estado_general AS ENUM ('ACTIVO', 'INACTIVO');
CREATE TYPE estado_producto AS ENUM ('ACTIVO', 'INACTIVO', 'DESCONTINUADO');
CREATE TYPE tipo_movimiento AS ENUM ('ENTRADA', 'SALIDA', 'AJUSTE', 'DEVOLUCION');
CREATE TYPE tipo_documento AS ENUM ('NIT', 'CC', 'CE', 'PASAPORTE');
CREATE TYPE tipo_tarifa AS ENUM ('0D', '10D', '5D', 'ES', 'JM', 'CR');
CREATE TYPE estado_orden AS ENUM ('PENDIENTE', 'EN_PREPARACION', 'LISTA', 'DESPACHADA', 'ENTREGADA', 'CANCELADA');
CREATE TYPE rol_usuario AS ENUM ('ADMIN', 'SUPERVISOR', 'BODEGUERO', 'VENDEDOR', 'CONSULTA');
CREATE TYPE motivo_devolucion AS ENUM ('NO_VENDIDO', 'DANADO', 'VENCIDO', 'ERROR_DESPACHO');

-- ============================================
-- TABLAS DE CATÁLOGOS
-- ============================================

-- Categorías de productos
CREATE TABLE categorias
(
    id_categoria SERIAL PRIMARY KEY,
    nombre       VARCHAR(100) NOT NULL UNIQUE,
    descripcion  TEXT,
    estado       estado_general DEFAULT 'ACTIVO',
    created_at   TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP      DEFAULT CURRENT_TIMESTAMP
);

-- Unidades de medida
CREATE TABLE unidades_medida
(
    id_unidad   SERIAL PRIMARY KEY,
    codigo      VARCHAR(10) NOT NULL UNIQUE,
    nombre      VARCHAR(50) NOT NULL,
    abreviatura VARCHAR(10) NOT NULL,
    estado      estado_general DEFAULT 'ACTIVO'
);

-- Rutas de distribución
CREATE TABLE rutas
(
    id_ruta     SERIAL PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT,
    estado      estado_general DEFAULT 'ACTIVO'
);

-- Conductores
CREATE TABLE conductores
(
    id_conductor     SERIAL PRIMARY KEY,
    numero_documento VARCHAR(20)  NOT NULL UNIQUE,
    nombres          VARCHAR(100) NOT NULL,
    apellidos        VARCHAR(100) NOT NULL,
    telefono         VARCHAR(20),
    licencia         VARCHAR(50),
    estado           estado_general DEFAULT 'ACTIVO',
    created_at       TIMESTAMP      DEFAULT CURRENT_TIMESTAMP
);

-- Carros/Vehículos
CREATE TABLE carros
(
    id_carro     SERIAL PRIMARY KEY,
    placa        VARCHAR(10) NOT NULL UNIQUE,
    modelo       VARCHAR(50),
    capacidad_kg DECIMAL(8, 2),
    estado       estado_general DEFAULT 'ACTIVO',
    created_at   TIMESTAMP      DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- PRODUCTOS (MODELO SIMPLIFICADO V2)
-- ============================================

CREATE TABLE productos
(
    id_producto    SERIAL PRIMARY KEY,
    codigo         VARCHAR(50) UNIQUE,
    nombre        VARCHAR(200)   NOT NULL,
    descripcion    TEXT,
    id_categoria  INTEGER        NOT NULL REFERENCES categorias (id_categoria),
    id_unidad     INTEGER        NOT NULL REFERENCES unidades_medida (id_unidad),
    stock_actual  INTEGER                 DEFAULT 0,
    stock_minimo  INTEGER                 DEFAULT 5,
    stock_maximo   INTEGER,
    requiere_lote BOOLEAN                 DEFAULT FALSE,
    dias_vida_util INTEGER,
    imagen_url     VARCHAR(255),
    estado        estado_producto         DEFAULT 'ACTIVO',
    -- NUEVO: Precio base del producto (precio completo sin descuento)
    precio_base   DECIMAL(10, 2) NOT NULL DEFAULT 0,
    created_at    TIMESTAMP               DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP               DEFAULT CURRENT_TIMESTAMP
);

-- Precios especiales (solo para JM y CR que no siguen la regla de descuento)
-- Los precios 0D, 10D, 5D, ES se calculan automáticamente desde precio_base
CREATE TABLE precios_especiales
(
    id_precio_especial SERIAL PRIMARY KEY,
    id_producto        INTEGER        NOT NULL REFERENCES productos (id_producto) ON DELETE CASCADE,
    tipo_tarifa        tipo_tarifa    NOT NULL,
    precio             DECIMAL(10, 2) NOT NULL,
    UNIQUE (id_producto, tipo_tarifa),
    -- Solo permitir JM y CR en esta tabla
    CHECK (tipo_tarifa IN ('JM', 'CR'))
);

-- ============================================
-- CLIENTES
-- ============================================

CREATE TABLE clientes
(
    id_cliente       SERIAL PRIMARY KEY,
    nombre           VARCHAR(200) NOT NULL,
    codigo           VARCHAR(50) UNIQUE,
    tipo_documento   tipo_documento,
    numero_documento VARCHAR(20),
    direccion        TEXT,
    telefono         VARCHAR(20),
    barrio           VARCHAR(100),
    comuna           VARCHAR(50),
    tipo_negocio     VARCHAR(100),
    tipo_tarifa      tipo_tarifa    DEFAULT '0D',
    id_ruta          INTEGER REFERENCES rutas (id_ruta),
    id_conductor     INTEGER REFERENCES conductores (id_conductor),
    horario_entrega  VARCHAR(50),
    estado           estado_general DEFAULT 'ACTIVO',
    created_at       TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP      DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- INVENTARIO
-- ============================================

-- Lotes de productos
CREATE TABLE lotes
(
    id_lote           SERIAL PRIMARY KEY,
    id_producto       INTEGER     NOT NULL REFERENCES productos (id_producto),
    codigo_lote       VARCHAR(50) NOT NULL,
    fecha_elaboracion DATE        NOT NULL,
    fecha_vencimiento DATE,
    cantidad_inicial  INTEGER     NOT NULL,
    cantidad_actual   INTEGER     NOT NULL,
    estado            estado_general DEFAULT 'ACTIVO',
    created_at        TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (id_producto, codigo_lote)
);

-- Movimientos de inventario
CREATE TABLE movimientos_inventario
(
    id_movimiento    SERIAL PRIMARY KEY,
    id_producto      INTEGER         NOT NULL REFERENCES productos (id_producto),
    id_lote          INTEGER REFERENCES lotes (id_lote),
    tipo_movimiento  tipo_movimiento NOT NULL,
    cantidad         INTEGER         NOT NULL,
    stock_anterior   INTEGER         NOT NULL,
    stock_nuevo      INTEGER         NOT NULL,
    motivo           VARCHAR(200)    NOT NULL,
    referencia       VARCHAR(100),
    observaciones    TEXT,
    id_usuario       INTEGER,
    fecha_movimiento TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- USUARIOS Y EMPLEADOS
-- ============================================

CREATE TABLE empleados
(
    id_empleado      SERIAL PRIMARY KEY,
    numero_documento VARCHAR(20)  NOT NULL UNIQUE,
    nombres          VARCHAR(100) NOT NULL,
    apellidos        VARCHAR(100) NOT NULL,
    telefono         VARCHAR(20),
    email            VARCHAR(100),
    cargo            VARCHAR(100),
    fecha_ingreso    DATE,
    estado           estado_general DEFAULT 'ACTIVO',
    created_at       TIMESTAMP      DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE usuarios
(
    id_usuario    SERIAL PRIMARY KEY,
    id_empleado   INTEGER      NOT NULL REFERENCES empleados (id_empleado),
    username      VARCHAR(50)  NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    rol           rol_usuario  NOT NULL,
    ultimo_acceso TIMESTAMP,
    estado        estado_general DEFAULT 'ACTIVO',
    created_at    TIMESTAMP      DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- ÓRDENES DE DESPACHO (reemplazan proformas)
-- ============================================

CREATE TABLE ordenes_despacho
(
    id_orden                 SERIAL PRIMARY KEY,
    numero_orden             VARCHAR(20) NOT NULL UNIQUE,
    id_cliente               INTEGER     NOT NULL REFERENCES clientes (id_cliente),
    id_empleado              INTEGER     NOT NULL REFERENCES empleados (id_empleado),
    fecha_orden              DATE        NOT NULL DEFAULT CURRENT_DATE,
    fecha_entrega_programada DATE,
    subtotal                 DECIMAL(10, 2)       DEFAULT 0,
    descuento                DECIMAL(10, 2)       DEFAULT 0,
    total                    DECIMAL(10, 2)       DEFAULT 0,
    observaciones            TEXT,
    estado                   estado_orden         DEFAULT 'PENDIENTE',
    created_at               TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
    updated_at               TIMESTAMP            DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE detalle_ordenes
(
    id_detalle      SERIAL PRIMARY KEY,
    id_orden        INTEGER        NOT NULL REFERENCES ordenes_despacho (id_orden) ON DELETE CASCADE,
    id_producto     INTEGER        NOT NULL REFERENCES productos (id_producto),
    cantidad        INTEGER        NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    subtotal        DECIMAL(10, 2) NOT NULL
);

-- ============================================
-- DESPACHOS
-- ============================================

CREATE TABLE despachos_carros
(
    id_despacho         SERIAL PRIMARY KEY,
    id_orden            INTEGER NOT NULL REFERENCES ordenes_despacho (id_orden),
    id_carro            INTEGER NOT NULL REFERENCES carros (id_carro),
    id_conductor        INTEGER NOT NULL REFERENCES conductores (id_conductor),
    fecha_despacho      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    hora_salida         TIME,
    hora_retorno        TIME,
    observaciones       TEXT,
    id_usuario_despacho INTEGER REFERENCES usuarios (id_usuario)
);

-- ============================================
-- DEVOLUCIONES
-- ============================================

CREATE TABLE devoluciones
(
    id_devolucion    SERIAL PRIMARY KEY,
    id_despacho      INTEGER           NOT NULL REFERENCES despachos_carros (id_despacho),
    id_producto      INTEGER           NOT NULL REFERENCES productos (id_producto),
    cantidad         INTEGER           NOT NULL,
    motivo           motivo_devolucion NOT NULL,
    observaciones    TEXT,
    reingresar_stock BOOLEAN   DEFAULT TRUE,
    fecha_devolucion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_usuario       INTEGER REFERENCES usuarios (id_usuario)
);

-- ============================================
-- ÍNDICES
-- ============================================

CREATE INDEX idx_productos_nombre ON productos (nombre);
CREATE INDEX idx_productos_codigo ON productos (codigo);
CREATE INDEX idx_productos_categoria ON productos (id_categoria);
CREATE INDEX idx_productos_stock ON productos (stock_actual);
CREATE INDEX idx_productos_precio_base ON productos (precio_base);

CREATE INDEX idx_precios_especiales_producto ON precios_especiales (id_producto);

CREATE INDEX idx_clientes_nombre ON clientes (nombre);
CREATE INDEX idx_clientes_documento ON clientes (numero_documento);
CREATE INDEX idx_clientes_ruta ON clientes (id_ruta);
CREATE INDEX idx_clientes_barrio ON clientes (barrio);

CREATE INDEX idx_ordenes_fecha ON ordenes_despacho (fecha_orden);
CREATE INDEX idx_ordenes_cliente ON ordenes_despacho (id_cliente);
CREATE INDEX idx_ordenes_estado ON ordenes_despacho (estado);

CREATE INDEX idx_movimientos_producto ON movimientos_inventario (id_producto);
CREATE INDEX idx_movimientos_fecha ON movimientos_inventario (fecha_movimiento);
CREATE INDEX idx_movimientos_tipo ON movimientos_inventario (tipo_movimiento);

CREATE INDEX idx_lotes_producto ON lotes (id_producto);
CREATE INDEX idx_lotes_vencimiento ON lotes (fecha_vencimiento);

-- ============================================
-- TRIGGERS
-- ============================================

-- Trigger para actualizar updated_at automáticamente
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_categorias_updated_at
    BEFORE UPDATE
    ON categorias
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_productos_updated_at
    BEFORE UPDATE
    ON productos
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_clientes_updated_at
    BEFORE UPDATE
    ON clientes
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_ordenes_updated_at
    BEFORE UPDATE
    ON ordenes_despacho
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

-- ============================================
-- VISTAS ÚTILES (ACTUALIZADAS PARA V2)
-- ============================================

-- Vista de productos con stock bajo
CREATE VIEW v_productos_stock_bajo AS
SELECT p.id_producto,
       p.codigo,
       p.nombre,
       p.stock_actual,
       p.stock_minimo,
       p.precio_base,
       c.nombre as categoria
FROM productos p
         INNER JOIN categorias c ON p.id_categoria = c.id_categoria
WHERE p.stock_actual <= p.stock_minimo
  AND p.estado = 'ACTIVO';

-- Vista de productos próximos a vencer
CREATE VIEW v_productos_por_vencer AS
SELECT p.id_producto,
       p.nombre,
       l.codigo_lote,
       l.fecha_vencimiento,
       l.cantidad_actual,
       (l.fecha_vencimiento - CURRENT_DATE) as dias_restantes
FROM lotes l
         INNER JOIN productos p ON l.id_producto = p.id_producto
WHERE l.fecha_vencimiento <= CURRENT_DATE + INTERVAL '3 days'
  AND l.cantidad_actual > 0
  AND l.estado = 'ACTIVO'
ORDER BY l.fecha_vencimiento;

-- Vista de inventario valorizado (ACTUALIZADA)
CREATE VIEW v_inventario_valorizado AS
SELECT p.id_producto,
       p.codigo,
       p.nombre,
       p.stock_actual,
       p.precio_base,
       (p.stock_actual * p.precio_base) as valor_total,
       c.nombre                         as categoria
FROM productos p
         INNER JOIN categorias c ON p.id_categoria = c.id_categoria
WHERE p.estado = 'ACTIVO'
ORDER BY valor_total DESC;

-- Vista de órdenes completas
CREATE VIEW v_ordenes_completas AS
SELECT o.id_orden,
       o.numero_orden,
       o.fecha_orden,
       c.nombre                                  as cliente,
       c.barrio,
       r.nombre                                  as ruta,
       CONCAT(cond.nombres, ' ', cond.apellidos) as conductor,
       o.total,
       o.estado,
       COUNT(d.id_detalle)                       as num_productos
FROM ordenes_despacho o
         INNER JOIN clientes c ON o.id_cliente = c.id_cliente
         LEFT JOIN rutas r ON c.id_ruta = r.id_ruta
         LEFT JOIN conductores cond ON c.id_conductor = cond.id_conductor
         LEFT JOIN detalle_ordenes d ON o.id_orden = d.id_orden
GROUP BY o.id_orden, o.numero_orden, o.fecha_orden, c.nombre,
         c.barrio, r.nombre, cond.nombres, cond.apellidos, o.total, o.estado;

-- ============================================
-- COMENTARIOS
-- ============================================

COMMENT ON DATABASE db_castillo_pan IS 'Base de datos sistema El Castillo del Pan - V2 con modelo de precios simplificado';
COMMENT ON TABLE productos IS 'Catálogo de productos con precio base. Los precios con descuento se calculan automáticamente.';
COMMENT ON TABLE precios_especiales IS 'Precios especiales solo para tarifas JM y CR que no siguen la regla de descuento automático';
COMMENT ON TABLE clientes IS 'Clientes con información de rutas y tarifas';
COMMENT ON TABLE ordenes_despacho IS 'Órdenes de despacho (reemplazan proformas)';
COMMENT ON TABLE movimientos_inventario IS 'Historial completo de movimientos de inventario';
COMMENT ON TABLE devoluciones IS 'Productos devueltos por los carros';

-- ============================================
-- NOTAS DE MIGRACIÓN
-- ============================================

-- Para migrar desde schema v1 (con tabla precios_producto):
-- 1. Ejecutar migration-pricing-model.sql
-- 2. Verificar que todos los productos tengan precio_base
-- 3. Verificar que precios_especiales solo contenga JM y CR
-- 4. Eliminar tabla precios_producto antigua
INSERT INTO categorias (nombre, descripcion, created_at, updated_at, estado)
VALUES ('PAN', 'Productos de panadería', now(), now(), 'ACTIVO'),
       ('PASTELES', 'Pastelería y repostería', now(), now(), 'ACTIVO'),
       ('GALLETAS', 'Galletas y productos secos', now(), now(), 'ACTIVO')
ON CONFLICT (nombre) DO NOTHING;