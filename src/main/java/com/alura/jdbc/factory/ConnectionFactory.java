package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
	
	private DataSource datasource; // de la clase .sql
	
	public ConnectionFactory() {//constructor
		
		/*
		 La idea acá es que la connectionFactory utilice las conexiones desde el pool 
		 de conexiones, en lugar de salir creando una conexión de forma pura, como 
		 estamos haciendo ahora.
		 */
		
		/*
		 En esta instancia del pool nosotros vamos a estar configurando la URL de la 
		 BD, el usuario y la contraseña, tal como estamos haciendo con la conexión.	 
		 */
		
		var pooledDataSource = new ComboPooledDataSource();
		
		pooledDataSource.setJdbcUrl("jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC");
		pooledDataSource.setUser("root");
		pooledDataSource.setPassword("admin");
		
		this.datasource = pooledDataSource;
	}
	
	
	
	 //Forma de conexion con el pool de conexiones
	public Connection recuperaConexion() throws SQLException {
		return this.datasource.getConnection();
	}
	
	
	/*
	 //Forma de conexion sin el pool de conexiones
	public Connection recuperaConexion() throws SQLException {
		return DriverManager.getConnection(
				//Conexion a la BD de MySQL
				"jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC",
				"root", "admin");
	}		
	 */

	
	
}
