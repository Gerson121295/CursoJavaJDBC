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

    public List<?> cargaReporte() {
        // TODO
        return new ArrayList<>();
    }


}
