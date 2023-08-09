package com.alura.jdbc.controller;

import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.dao.CategoriaDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Categoria;

public class CategoriaController {
	
	private CategoriaDAO categoriaDAO;
	
	public CategoriaController() {
		//Estamos inicializando el DAO enviando una conexión del pool de conexiones.
		var factory = new ConnectionFactory();
		this.categoriaDAO = new CategoriaDAO(factory.recuperaConexion());
	}
	
	public List<Categoria> listar() {
		return categoriaDAO.listar();
	}

	
	//Solucion 1: Metodo CargarReporte - Muestra los productos por categoria
/*	// Usando Queries - Util para consultas sencilla, para complejas No es recomendable xq abre muchas conexiones a la BD. 
    public List<Categoria> cargaReporte() {
        return this.listar();
    }
*/
	
	//Solucion 2: Metodo CargarReporte - Muestra los productos por categoria
	// usando INNER JOIN  - Util para consultas sencilla y para complejas recomendable xq  no abre muchas conexiones a la BD. 
	    public List<Categoria> cargaReporte() {
	        return this.categoriaDAO.listarConProductos();
	    }
	
	

}
