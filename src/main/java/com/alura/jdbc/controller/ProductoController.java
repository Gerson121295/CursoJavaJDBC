package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.factory.ConnectionFactory;

public class ProductoController {
	
	public void modificar(String nombre, String descripcion, Integer id) {
		// TODO
	}

	public int eliminar(Integer id) throws SQLException {
		// Eliminar el producto
		Connection con = new ConnectionFactory().recuperaConexion(); //crea la conexion
		
		//Logica de Delete con Statement
		Statement statement = con.createStatement();
		
		statement.execute("DELETE FROM PRODUCTO WHERE ID = " + id);
		// int updateCount = statement.getUpdateCount(); //devuelve un int el cual nos dice cuantas filas fueron modificadas. -- usado cuando el metodo es void
		
		return statement.getUpdateCount(); //devuelve un int el cual nos dice cuantas filas fueron modificadas. -- usado cuando el metodo es int y tiene return
			
		
	}

	public List<Map<String, String>> listar() throws SQLException {
		
		Connection con = new ConnectionFactory().recuperaConexion();
		
		Statement statement = con.createStatement(); //con este objeto podremos declarar el query para la consulta a la BD
		
		statement.execute("SELECT id, nombre, descripcion, cantidad FROM producto");
		//execute devuelve un boolean  para indicar si el resultado del statement es un listado como el select devuelve un true, 
		// si no retorna un false que seria un update, delete o insert.     
		
		
		ResultSet resultSet = statement.getResultSet(); //Estado de resultado
		
		List<Map<String, String>> resultado = new ArrayList<>();
		
		while(resultSet.next()) {//Para poder ver el contenido para agregarlo al listado del resultado
			Map<String, String> fila = new HashMap<>();
			fila.put("id", String.valueOf(resultSet.getInt("id")));
			fila.put("nombre", resultSet.getString("nombre"));
			fila.put("descripcion", resultSet.getString("descripcion"));
			fila.put("cantidad", String.valueOf(resultSet.getInt("cantidad")));
			
			resultado.add(fila);
		}
		
		
		con.close(); //cerramos la conexion
		
		return resultado; // Devolvemos la informacion
	}
	

    public void guardar(Map<String, String> producto) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
	
    //Logica de Insert con Statement
		
		Statement statement = con.createStatement();
		
		statement.execute("INSERT INTO PRODUCTO(nombre, descripcion, cantidad) " //No se escribe el id porque es auto_incremente en la BD
					+ " VALUES('" + producto.get("NOMBRE") + "', '" //se agrego comillas simples debido a que es un String el campo nombre, comillas simples '' en BD señala un String y en java son las comillas dobles "" para el String
					+ producto.get("DESCRIPCION") + "', "
					+ producto.get("CANTIDAD") + ")", Statement.RETURN_GENERATED_KEYS); //")", cerrar el parentesis de la Query //Statement.RETURN_GENERATED_KEYS - Cuando se ejecuta un insert (se incrementa el id) obtiene el id generado con insert 
		
		ResultSet resultSet = statement.getGeneratedKeys(); // Resultado de los id generados con la ejecucion de la Query
		
		while(resultSet.next()) {//while para obtener el valor del id generado
				System.out.println(
						String.format(
							"Fue insertado el producto de ID %d",
							 resultSet.getInt(1)));//posicion 1 para saber cual id fue generado
										
		}
    } 
}



