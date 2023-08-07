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

	public void eliminar(Integer id) {
		// TODO
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

    public void guardar(Object producto) {
		// TODO
	}
    

}



