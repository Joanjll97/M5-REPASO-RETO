CREATE TABLE cliente (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    direccion VARCHAR(255) NOT NULL
);


CREATE TABLE prestamo (
    id SERIAL PRIMARY KEY,
    monto DOUBLE PRECISION NOT NULL,
    interes DOUBLE PRECISION NOT NULL,
    duracion_meses INTEGER NOT NULL,
    estado VARCHAR(50) NOT NULL,
    fecha_creacion TIMESTAMP,
    fecha_actualizacion TIMESTAMP,
    cliente_id BIGINT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

INSERT INTO cliente (nombre, email, telefono, direccion) VALUES
('Juan Pérez', 'juan.perez@example.com', '1234567890', 'Calle Falsa 123'),
('María Gómez', 'maria.gomez@example.com', '0987654321', 'Avenida Siempre Viva 456'),
('Carlos López', 'carlos.lopez@example.com', '1122334455', 'Boulevard de los Sueños 789'),
('Ana Martínez', 'ana.martinez@example.com', '6677889900', 'Calle de la Esperanza 101');

INSERT INTO prestamo (monto, interes, duracion_meses, estado, fecha_creacion, fecha_actualizacion, cliente_id) VALUES
(5000.00, 5.5, 24, 'Pendiente', '2023-01-01 10:00:00', '2023-01-01 10:00:00', 1),
(3000.00, 5.0, 12, 'Aprobado', '2023-02-01 10:00:00', '2023-02-01 10:00:00', 1),
(7000.00, 4.5, 36, 'Rechazado', '2023-03-01 10:00:00', '2023-03-01 10:00:00', 1),
(2000.00, 6.0, 18, 'Pendiente', '2023-04-01 10:00:00', '2023-04-01 10:00:00', 1),
(10000.00, 4.0, 36, 'Aprobado', '2023-05-01 10:00:00', '2023-05-01 10:00:00', 2),
(7500.00, 6.0, 12, 'Rechazado', '2023-06-01 10:00:00', '2023-06-01 10:00:00', 3),
(15000.00, 3.5, 48, 'Pendiente', '2023-07-01 10:00:00', '2023-07-01 10:00:00', 4);

