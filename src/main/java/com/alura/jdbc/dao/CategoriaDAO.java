package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;

public class CategoriaDAO {
	
	private Connection con;

	public CategoriaDAO(Connection recuperaConexion) {
		this.con = recuperaConexion;
	}

	//Solucion 1: Metodo CargarReporte - Muestra los productos por categoria
	// Usando Queries - Util para consultas sencilla, para complejas No es recomendable xq abre muchas conexiones a la BD. 
	  	  
	public List<Categoria> listar() {
		List<Categoria> resultado = new ArrayList<>();
		try {
			
			//Para imprimir la query
			var querySelect = "SELECT ID, NOMBRE FROM CATEGORIA";
			System.out.println(querySelect);
		
			
			/*// Si el codigo anterior de imprimir la query
			final PreparedStatement statement = con.prepareStatement(
					"SELECT ID, NOMBRE FROM CATEGORIA");
			*/
			
			final PreparedStatement statement = con.prepareStatement(
					querySelect);
					
			try (statement){
				final ResultSet resultSet = statement.executeQuery(); //Otra forma de hacerlo 
					try (resultSet){
						//Tomar cada fila resultado para crear un objeto categoria.
						while(resultSet.next()) {
							var categoria = new Categoria(resultSet.getInt("ID"),
									resultSet.getString("NOMBRE"));
							
							resultado.add(categoria); //Agregamos a nuestro resultado.
						}
					};
				}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return resultado;
	}

	
	//Solucion 2: Metodo CargarReporte - Muestra los productos por categoria
	// usando INNER JOIN  - Util para consultas sencilla y para complejas recomendable xq  no abre muchas conexiones a la BD. 
	
	public List<Categoria> listarConProductos() {
		/*El objetivo es: cargar la informacion de productos y las categorias juntos en la misma Query a travez de INNER JOIN.
		INNER JOIN  se puede utilizar ya que ambas tablas estan conectadas por categoria_id*/
		
		List<Categoria> resultado = new ArrayList<>();
		
		try {
			
			//Para imprimir la query
			var querySelect = "SELECT C.ID, C.NOMBRE, P.ID, P.NOMBRE, P.CANTIDAD " // P. //devuelve la informacion de producto que estamos imprimiendo en el reporte.
					+ "FROM CATEGORIA C "
					+ "INNER JOIN PRODUCTO P ON C.ID = P.CATEGORIA_ID"; // union por su clave foranea, Campo categoria_id de Producto con id de categoria.
			
			System.out.println(querySelect);
				
			final PreparedStatement statement = con.prepareStatement(
					querySelect);
					
			try (statement){
				final ResultSet resultSet = statement.executeQuery(); //Otra forma de hacerlo 
					
				try (resultSet){
					//Tomar cada fila resultado para crear un objeto categoria.
					while(resultSet.next()) {
						
						Integer categoriaId = resultSet.getInt("C.ID");
						String categoriaNombre = resultSet.getString("C.NOMBRE");
						
						var categoria = resultado  //el resultado es un list de categoria  
								.stream() //transformo el resultado en un Stream, 
								.filter(cat -> cat.getId().equals(categoriaId))  //se hace un filtro buscando si en este listado, ya tenemos una categoria con este id, de la variable categoriaId
								.findAny().orElseGet(() -> {	 //findAny(si encontramos cualquier resultado que tenga la igualdad de la condicion del filter, se asigna el resultado a la variable de categoria.  
																// orElseGet - si no exite nada de eso es la primera vez que pasamos por esta categoria con id especifico creamos el objeto de la categoria.   	 							
									Categoria cat = new Categoria(categoriaId, //si no lo encontramos la categoria la creamos una nueva categoria y la agregamos al listado de resultado.
											categoriaNombre);
									
									resultado.add(cat); //Agregamos a nuestro resultado.
									return cat;
								});	
						
							Producto producto = new Producto(resultSet.getInt("P.ID"),
									resultSet.getString("P.NOMBRE"),
									resultSet.getInt("P.CANTIDAD"));
							
							categoria.agregar(producto);
						}
					};
				}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return resultado;
		
	}

}














