CREATE DATABASE IF NOT EXISTS crud_clientes;
USE crud_clientes;

CREATE TABLE clientes (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nombres VARCHAR(100) NOT NULL,
                          apellidos VARCHAR(150) NOT NULL,
                          DNI CHAR(8) NOT NULL,
                          correo VARCHAR(100) UNIQUE NOT NULL,
                          telefono VARCHAR(20)
);
CREATE TABLE productos
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    nombre         VARCHAR(100)   NOT NULL,
    descripcion    TEXT,
    precio         DECIMAL(10, 2) NOT NULL,
    categoria      VARCHAR(50)    NOT NULL,
    disponibilidad BOOLEAN        NOT NULL
);