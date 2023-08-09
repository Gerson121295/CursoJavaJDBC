package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.modelo.Categoria;

public class CategoriaDAO {
	
	private Connection con;

	public CategoriaDAO(Connection recuperaConexion) {
		this.con = recuperaConexion;
	}

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

}














