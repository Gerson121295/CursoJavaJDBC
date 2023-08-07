package com.alura.jdbc.pruebas;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.alura.jdbc.factory.ConnectionFactory;

public class PruebaDelete {
	
	public static void main(String[] args) throws SQLException {
		
		// Eliminar el producto
		Connection con = new ConnectionFactory().recuperaConexion(); //crea la conexion
		
		//Logica de Delete con Statement
		Statement statement = con.createStatement();
		
		statement.execute("DELETE FROM PRODUCTO WHERE ID = 99");
		// int updateCount = statement.getUpdateCount(); //devuelve un int el cual nos dice cuantas filas fueron modificadas. -- usado cuando el metodo es void
		
		
		System.out.println(statement.getUpdateCount()); //devuelve la cantidad eliminada	// el valor esperado debe ser = 0 ya que el id 99 no existe en la BD
				
	}
	
}
