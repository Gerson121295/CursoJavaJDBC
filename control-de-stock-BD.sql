


CREATE DATABASE control_de_stock;
USE control_de_stock;

CREATE TABLE producto (
	id INT AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255),
    cantidad INT NOT NULL DEFAULT 0,
    PRIMARY KEY(id)
    )ENGINE = InnoDB; -- ENGINE = InnoDB - Queremos que la tabla acepte operaciones dentro de una transaccion.
    
    
    INSERT INTO producto(nombre, descripcion, cantidad) 
				values ('Mesa', 'Mesa de 4 lugares', 10);
	
    INSERT INTO producto(nombre, descripcion, cantidad) 
				values ('Celular', 'Celular Samsung', 50);
    
   
    
    -- Creacion de la tabla Categoria
    CREATE TABLE CATEGORIA(
		id INT AUTO_INCREMENT,
        nombre VARCHAR(50),
        PRIMARY KEY(id)
        )Engine=InnoDB;
	   
    INSERT INTO categoria(nombre)
			values("Tecnologia"),
				("Muebles"),
				("Zapatillas"),
				("Hogar");
                
-- Relacion entre tablas:  Producto y Categoria
ALTER TABLE producto ADD COLUMN categoria_id INT; -- Agregamos una nueva columna categoria_id en producto 
    -- Categoria_id es referencia a la columna id de la table categoria
ALTER TABLE producto ADD FOREIGN KEY (categoria_id) REFERENCES categoria(id);

-- Code para resetear id - Al eliminar una tabla y volverla a crear el id no se resete comenzando en 0
DELETE FROM categoria;
TRUNCATE TABLE `categoria`;
ALTER TABLE `categoria` AUTO_INCREMENT=0; -- Es si funciona resetea el id a 0.

 -- Referenciar los productos actuales a una nueva categoria
 
UPDATE producto SET categoria_id = 2 WHERE id = 1;
UPDATE producto SET categoria_id = 4 WHERE id = 44;
    
    
    SELECT * FROM producto;
	 SELECT * FROM categoria;  
    