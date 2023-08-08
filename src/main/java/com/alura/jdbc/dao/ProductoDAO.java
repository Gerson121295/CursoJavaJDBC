package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoDAO {

	//DAO - Data Access Object - 
	/*
	El Patrón de diseño DAO. La finalidad de este patrón de diseño es tener un objeto que 
	tiene como responsabilidad acceder a la base de datos y realizar las operaciones necesarias 
	sobre la entidad.
	*/
	
	//Esta clase lo que está haciendo es tratar todas las operaciones en la tabla de producto. 
	// Entonces su finalidad es acceder a los datos de nuestro objeto.
	//Estas clases del tipo DAO trabajan con las operaciones de acceso a los datos de una entidad. 
	
	
	final private Connection con;
	
	public ProductoDAO(Connection con) {
		this.con = con;
	}
	
	public void guardar(Producto producto){
			//Logica y codigo estaba en ProductoController por Refactorizacion se movio aquí.
		   // public void guardar(Map<String, String> producto) throws SQLException { //definicion de recorrido por Map <String, String)
		   // 	public void guardar(Producto producto) throws SQLException { // Se le envia el objeto Producto   // Se Refactorizo y se movio este metodo que estaba en ProductoController
		    	
		    	//Funciona este Bloque1.
		    	//Connection con = new ConnectionFactory().recuperaConexion();

				//Opcion de Insert Para evitar SQL Injection utilizando PreparedStatement: ingresar:  Mouse'  con el signo '  genera error por en en SQL las comillas simples '' señalan un String 
				//COn esto pasamos la seguridad al JDBC
				//El PreparedStatement se encarga de normalizar el texto ingresado como texto común. SI se agrega un ' el lo agrega asi.
			/*
				PreparedStatement statement = con.prepareStatement("INSERT INTO PRODUCTO (nombre, descripcion, cantidad)"
							+ " VALUES (?, ?, ?)",
							Statement.RETURN_GENERATED_KEYS);
							statement.setString(1, producto.get("NOMBRE"));
							statement.setString(2, producto.get("DESCRIPCION"));
							statement.setInt(3, Integer.valueOf(producto.get("CANTIDAD")));					
							statement.execute();
				
			*/
				
			//Bloque de codigo anterior pero con Regla de negocio: 
			//Codigo para cumplir la regla de negocio: Soporta 50 cajas de un producto por registro. Si se ingresan la caontidad de 100 productos, entonces automaticamente se haran 2 registros del mismo producto con 50 cada uno.
		    	
		    	/*//Codigo Utilizado para: public void guardar(Map<String, String> producto)
		    	String nombre = producto.get("NOMBRE");
		    	String descripcion = producto.get("DESCRIPCION");
		    	Integer cantidad = Integer.valueOf(producto.get("CANTIDAD"));
		    	Integer maximoCantidad = 50;
		    	*/
		    
		    
		    //codigo para: public void guardar(Producto producto) ahora es por objeto y se necesitará los Getters and Setters
		    /*   //Logica de negocio: Permite registrar solo 50 productos.
		    		var nombre = producto.getNombre();
		        	var descripcion = producto.getDescripcion();
		        	var cantidad = producto.getCantidad();
		        	final var maximoCantidad = 50;
		        */	
		    	
		    	//Connection con = new ConnectionFactory().recuperaConexion();
	
/*        // No necesitamos Instanciar esta conexion porque ya la tenemos en el constructor de esta clase ProductoDAO		
		    	//Para version 9 > java - Agregando el try-with-resource para asegurar que se ci
		    	ConnectionFactory factory = new ConnectionFactory();
		    	
		    	//para el try-with-resources se agrego final
		    	final Connection con = factory.recuperaConexion();
		 	    	
	*/	    	
		    	try {
					try(con){//para el try-with-resources se agrego el try(con)
						
						/*//Codigo 1 para Transaccion 
						//Comando para tomar el control de la transaccion: Transaction Para asegurar que se inserten todos los dato o no se inserte ninguno.	
						con.setAutoCommit(false); //Para tomar el control de la transaccion
						*/
						
						//para el try-with-resources se agrego final 
						final PreparedStatement statement = con.prepareStatement(
									"INSERT INTO PRODUCTO (nombre, descripcion, cantidad)"
										+ " VALUES (?, ?, ?)",
										Statement.RETURN_GENERATED_KEYS);
								
						//Logica para cumplir la regla de negocio
						try(statement){ //para el try-with-resources se agrego el try(statement)
						//try { //Se elimino este try debido a que ya se tiene el que recibe statement del try-with-resource.
							
							//do {	//Logica de negocio: Permite registrar solo 50 productos.
										 
								//int cantidadParaGuardar = Math.min(cantidad, maximoCantidad); //Logica de negocio: Permite registrar solo 50 productos. // si cantidad = 100 y maximoCantidad = 50 El valor minimo sera: 50.  otro Ejemp: si cantidad = 40, maxCant = 50, el valor a guardar es: 40.
								
								//ejecutaRegistro(nombre, descripcion, cantidadParaGuardar, statement);	//Funcion ejecutarRegistro: A la Funcion usando Map anterior se necesitaba enviarle las variables que almacenaba la data.
								
								ejecutaRegistro(producto, statement);	//Funcion ejecutarRegistro: A la Funcion Se le envia el objeto producto.
								
								//cantidad -= maximoCantidad; //Logica de negocio: Permite registrar solo 50 productos. // cantidad = cantidad - maximoCantidad //para asegurar que se ingresen 50 registros si es mas de 50, el resto se ingresa en el siguiente registro.
								
							//}while(cantidad > 0);  	//Logica de negocio: Permite registrar solo 50 productos.
							
						/*//Codigo 2 para Transaccion 
							con.commit(); //para garantizar que todos los comandos del ciclo while hallan sido ejecutados correctamente.
							System.out.println("Commit");	
						*/
							
						}catch(SQLException e) { //aqui cerraba el try que se elimino arriba
							throw new RuntimeException(e);
						
						/*//Codigo 3 para Transaccion 	
							System.out.println("Rollback de la transaccion");
							con.rollback(); //Si hubo un erro y no se ejecutaron todas las lineas de codigo, se revierten las que se ejecutaron. Por lo tanto no habra registros, Es como no paso nada.	
						*/
							
						}
					
					}
					
				} catch (SQLException e) { //Cierre del Primer Try
					throw new RuntimeException(e);
					
				} //llave del try
		    	// } //llave del try, se elimino debido a que se elimino un try arriba.
				
		    	//ya no es necesario cerrar la conexion manual debido a que ya fue cerrada con el try-with-resource
				//statement.close(); //Cerrar el PreparedStatement
				//con.close(); //cerrar la conexion	
				
		    	
				
				/* //Opcion: no segura para insert vulnerable a SQL Injection
				 
				  //Logica de Insert con Statement	
				Statement statement = con.createStatement();
				
				String sqlInsert = "INSERT INTO PRODUCTO(nombre, descripcion, cantidad) " //No se escribe el id porque es auto_incremente en la BD
						+ " VALUES('" + producto.get("NOMBRE") + "', '" //se agrego comillas simples debido a que es un String el campo nombre, comillas simples '' en BD señala un String y en java son las comillas dobles "" para el String
						+ producto.get("DESCRIPCION") + "', "
						+ producto.get("CANTIDAD") + ")";
				
				System.out.println(sqlInsert);
				statement.execute(
						sqlInsert,
						Statement.RETURN_GENERATED_KEYS);
				*/
				
				/*//Opcion: no segura para insert vulnerable a SQL Injection
				statement.execute("INSERT INTO PRODUCTO(nombre, descripcion, cantidad) " //No se escribe el id porque es auto_incremente en la BD
							+ " VALUES('" + producto.get("NOMBRE") + "', '" //se agrego comillas simples debido a que es un String el campo nombre, comillas simples '' en BD señala un String y en java son las comillas dobles "" para el String
							+ producto.get("DESCRIPCION") + "', "
							+ producto.get("CANTIDAD") + ")", Statement.RETURN_GENERATED_KEYS); //")", cerrar el parentesis de la Query //Statement.RETURN_GENERATED_KEYS - Cuando se ejecuta un insert (se incrementa el id) obtiene el id generado con insert 
				*/
				
				
				/*//Parte  siguiente del Bloque1
				 //Funciona pero esta parte fue extraida e ingresada en el metodo ejecutaRegistro
				
				ResultSet resultSet = statement.getGeneratedKeys(); // Resultado de los id generados con la ejecucion de la Query
				
				while(resultSet.next()) {//while para obtener el valor del id generado
						System.out.println(
								String.format(
									"Fue insertado el producto de ID %d",
									 resultSet.getInt(1)));//posicion 1 para saber cual id fue generado										
				}
				*/
				
		   //----Cierre de public   }
		    	} 	


	
			//private void ejecutaRegistro(String nombre, String descripcion, Integer cantidad, PreparedStatement statement)  //metodo anterior usando Map se le envia las variables que tendria la data.
		    private void ejecutaRegistro(Producto producto, PreparedStatement statement)  //Recibe el objeto Producto producto que contiene sus atributos.
		    		
					throws SQLException {
				
				//if(cantidad < 50) { //Lanzar un error cuando sean menor a 50 no se ejecutaria el codigo.
				//	throw new RuntimeException("Ocurrio un error");
				//}
				/*codigo para -  private void ejecutaRegistro(String nombre, String descripcion, Integer cantidad, PreparedStatement statement)
				statement.setString(1, nombre);							
				statement.setString(2, descripcion);								
				statement.setInt(3, cantidad);													
				statement.execute();
				*/
		    	
		    	
		    	// codigo para -  private void ejecutaRegistro(Producto producto, PreparedStatement statement)
				statement.setString(1, producto.getNombre());							
				statement.setString(2, producto.getDescripcion());								
				statement.setInt(3, producto.getCantidad());													
				statement.execute();
				
		 
				/*
				//Funciona: Codigo simple sin try-with-resources para asegurar de cerrar la conexion
				ResultSet resultSet = statement.getGeneratedKeys(); // Resultado de los id generados con la ejecucion de la Query
				
				while(resultSet.next()) {//while para obtener el valor del id generado
						System.out.println(
								String.format(
									"Fue insertado el producto de ID %d",
									 resultSet.getInt(1)));//posicion 1 para saber cual id fue generado										
				}
				
				resultSet.close(); // Cerrar el resultSet
				*/
			
				
				//Version 9 > JAVA - Implementar el try-with-resourse - para asegurarse de cerrar la conexion, JVM se encarga de cerrarla.
				
				final ResultSet resultSet = statement.getGeneratedKeys(); // Resultado de los id generados con la ejecucion de la Query
				
				try(resultSet){
					while(resultSet.next()) {	//while para obtener el valor del id generado
						
						/*// Bloque de codigo para: //private void ejecutaRegistro(String nombre, String descripcion, Integer cantidad, PreparedStatement statement)  //metodo anterior usando Map se le envia las variables que tendria la data.		 
							System.out.println(String.format(
										"Fue insertado el producto de ID %d",
										 resultSet.getInt(1)));//posicion 1 para saber cual id fue generado										
					*/
						
						 // Bloque de codigo para: private void ejecutaRegistro(Producto producto, PreparedStatement statement)
							producto.setId(resultSet.getInt(1));
							System.out.println(String.format("Fue insertado el producto %s", producto));
					
					}
				}
				
						
				
			/*
				//Version 7 de JAVA - Implementar el try-with-resourse - para asegurarse de cerrar la conexion	
				try(ResultSet resultSet = statement.getGeneratedKeys();){
					while(resultSet.next()) {//while para obtener el valor del id generado
						System.out.println(String.format(
									"Fue insertado el producto de ID %d",
									 resultSet.getInt(1)));//posicion 1 para saber cual id fue generado										
				}
				}
			*/	
				
							
			}
		    
		    
		    // Metodo Listar  - Logica y codigo estaba en ProductoController por Refactorizacon se movio aqui.
			
		    public List<Producto> listar() { //Se mapea el objeto
				List<Producto> resultado = new ArrayList<>();
				
		    
		    //public List<Map<String, String>> listar() { //forma anterior
			//	List<Map<String, String>> resultado = new ArrayList<>();
				
				//public List<Map<String, String>> listar() throws SQLException {
							
					//Agregando el try -with-resources para asegugar cerrar la conexion. Para no cerrarla manualmente. JVM se encarga de cerrarla.
					//se definio como final para el try with resource
					final Connection con = new ConnectionFactory().recuperaConexion();
					
					try(con){ //se agrego esta linea de codigo con el try para el try-with-resource
							
						//Evitando SQL Injection
						//se definio como final para el try with resource
						final PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");

						try(statement){  //se agrego esta linea de codigo con el try para el try-with-resource
							statement.execute();
						
							
				/*	//Metodo vulnerable a SQL Injection
					Statement statement = con.createStatement(); //con este objeto podremos declarar el query para la consulta a la BD
					
					statement.execute("SELECT id, nombre, descripcion, cantidad FROM producto");
					//execute devuelve un boolean  para indicar si el resultado del statement es un listado como el select devuelve un true, 
					// si no retorna un false que seria un update, delete o insert.     
				 */
					
							final ResultSet resultSet = statement.getResultSet(); //Estado de resultado
					
							//List<Map<String, String>> resultado = new ArrayList<>(); //Esta arriba al inicio del metodo.
					
							try(resultSet){ //se agrego estr try
							while(resultSet.next()) {//Para poder ver el contenido para agregarlo al listado del resultado
								
								/*// anterior
								Map<String, String> fila = new HashMap<>();
								fila.put("ID", String.valueOf(resultSet.getInt("ID")));
								fila.put("NOMBRE", resultSet.getString("NOMBRE"));
								fila.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
								fila.put("CANTIDAD", String.valueOf(resultSet.getInt("CANTIDAD")));
							
								resultado.add(fila);			
								*/
								
								//Se asigna el objeto 
								Producto fila = new Producto(resultSet.getInt("ID"),
											resultSet.getString("NOMBRE"),
											resultSet.getString("DESCRIPCION"),
											resultSet.getInt("CANTIDAD"));
									
								resultado.add(fila);
														
							}
						}
					}
					
							//con.close(); //cerramos la conexion //no es necesario cerrarla se agrego el try-with-resource la cierra automaticamente
					
							return resultado; // Devolvemos la informacion
						}catch(SQLException e) {
							throw new RuntimeException(e);
						}
						
						// } //cierre del metodo public
			}


		    //Metodo Eliminar: Está logica y codigo estaba en ProductoController por refactorizacion se traslado para acá.
			public int eliminar(Integer id)  {
				
				// Eliminar el producto
				//Se agrego el try-with-resource para no cerrar la conexion manualmente, con esto JVM se encarga de cerrarla
				//se define final por el try-with-resource
				//final Connection con = new ConnectionFactory().recuperaConexion(); // No es necesario crear la conexion ya que está esta definida en el constructor de esta clase(ProductoDAO).
				
				try { //Se agrego este try en esta clase ProductoDAO
				//try(con){ //se agrega esta linea de codigo con el try para el try-with-resource
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
				}catch(SQLException e) {
					throw new RuntimeException(e);
				}
				
			}

			
			
			//Metodo Modificar
			public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) {
				//Por motivos de Refactorizacion el codigo de ProductoController se traslado para acá en ProductoDAO
			
			//public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) throws SQLException {
				//Modificar
				//Connection con = new ConnectionFactory().recuperaConexion(); //crea la conexion
				
				/* no necesitamos la conexion debido a que en esta clase esta definidad en el constructor.
				ConnectionFactory factory = new ConnectionFactory();
			    final Connection con = factory.recuperaConexion();
			    */
				
				try {
			    //try(con){ //la conexion esta definida en el constructor
			    	
				  //Evitando SQL Injection
				    final PreparedStatement statement = con.prepareStatement(
				    		"UPDATE producto SET "
				            + " nombre = ?, "
				            + " descripcion = ?, "
				            + " cantidad = ?"
				            + " WHERE id = ?");
				    
				    try(statement) {		    
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
			    }catch (SQLException e) {
			    	throw new RuntimeException(e);
			    }
			}
			
			
			
			
}

				
					





