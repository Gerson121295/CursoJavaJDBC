package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.dao.ProductoDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoController {
	
	private ProductoDAO productoDAO;
	
	
	public ProductoController() { //Cuando se inicilialize este objeto creará una conexion
		this.productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
				
	}
	
	
	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) throws SQLException {
		//Modificar
		//Connection con = new ConnectionFactory().recuperaConexion(); //crea la conexion
		
		ConnectionFactory factory = new ConnectionFactory();
	    final Connection con = factory.recuperaConexion();
	    
	    try(con){
	    	
		  //Evitando SQL Injection
		    final PreparedStatement statement = con.prepareStatement("UPDATE PRODUCTO SET "
		            + " NOMBRE = ?,"
		            + " DESCRIPCION = ?,"
		            + " CANTIDAD = ?"
		            + " WHERE ID = ?");
		    try(statement){		    
			    statement.setString(1, nombre);
			    statement.setString(2, descripcion);
			    statement.setInt(3, cantidad);
			    statement.setInt(4, id);
	    
			    statement.execute();
	 /*   
	  //Metodo vulnerable a SQL Injection
	    	//Logica de Update con Statement
		Statement statement = con.createStatement();
		
		statement.execute("UPDATE PRODUCTO SET "
	            + " NOMBRE = '" + nombre + "'"
	            + ", DESCRIPCION = '" + descripcion + "'"
	            + ", CANTIDAD = " + cantidad
	            + " WHERE ID = " + id);
		*/
	    
				int updateCount = statement.getUpdateCount();
				//con.close();
				return updateCount;
			
				//return statement.getUpdateCount();
		    }
	    }	
	}

	
	public int eliminar(Integer id) throws SQLException {
		// Eliminar el producto
		//Se agrego el try-with-resource para no cerrar la conexion manualmente, con esto JVM se encarga de cerrarla
		//se define final por el try-with-resource
		final Connection con = new ConnectionFactory().recuperaConexion(); //crea la conexion
		
		try(con){ //se agrega esta linea de codigo con el try para el try-with-resource
			//Evitando SQL Injection
			
			//se define final por el try-with-resource
			final PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");
				
			try(statement){//se agrega esta linea de codigo con el try para el try-with-resource
				statement.setInt(1, id);
				statement.execute();
				
			/*	//Metodo vulnerable a SQL Injection
				//Logica de Delete con Statement
				Statement statement = con.createStatement();
				
				statement.execute("DELETE FROM PRODUCTO WHERE ID = " + id);
				
				// int updateCount = statement.getUpdateCount(); //devuelve un int el cual nos dice cuantas filas fueron modificadas. -- usado cuando el metodo es void
				
				return statement.getUpdateCount(); //devuelve un int el cual nos dice cuantas filas fueron modificadas. -- usado cuando el metodo es int y tiene return
				
				*/
				
				int updateCount = statement.getUpdateCount();
				//con.close(); //cerrar la conexion manualmente. 
				return updateCount;
			}
		}
		
	}

	//Metodo de Listar Productos
	// public List<Map<String, String>> listar() {
	public List<Producto> listar() {
		//Se refactorizo el codigo y la logica se traslado a ProductoDAO		
		return productoDAO.listar();
	}
	

	
   // public void guardar(Map<String, String> producto) throws SQLException { //definicion de recorrido por Map <String, String)
    	public void guardar(Producto producto){ // Se le envia el objeto Producto
    		//Se refactorizo el codigo y la logica se traslado a ProductoDAO
    		
    		productoDAO.guardar(producto);
    	
    	}	
    	
	
}



