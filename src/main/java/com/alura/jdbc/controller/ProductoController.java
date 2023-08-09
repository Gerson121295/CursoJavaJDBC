package com.alura.jdbc.controller;

import java.util.List;

import com.alura.jdbc.dao.ProductoDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoController {
	
	private ProductoDAO productoDAO;
	
	
	public ProductoController() { //Cuando se inicilialize este objeto creará una conexion
		this.productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
				
	}
	
	
	//Metodo Modificar
	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id){
		//Por refactorizacion de codigo la logica se traslado a ProductoDAO
		return productoDAO.modificar(nombre, descripcion, cantidad, id);
	}

	
	//Metodo Eliminar
	public int eliminar(Integer id){
		//Se factorizo el codigo y la logica se traslado a ProductoDAO
		return productoDAO.eliminar(id);		
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



