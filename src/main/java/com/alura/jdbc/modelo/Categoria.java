package com.alura.jdbc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Categoria {
//Se agregan los mismos campos que de la tabla
	
	private Integer id;
	private String nombre;
	
	//Categoria agregar una referencia a los productos: Ya que cada producto tiene
	//referencia a una categoria, una categoria puede tener muchos productos.
	private List<Producto> productos; //referencia de productos dentro de categoria
	
	public Categoria(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	
	public Integer getId() {
		return this.id;
	}
	
	
	@Override
	public String toString() {
		return this.nombre;
	}


	public void agregar(Producto producto) {
		if(this.productos == null) {
			this.productos = new ArrayList<>();
		}
		this.productos.add(producto);
	}


	public List<Producto> getProductos() {
		return this.productos;
	}

	
	
	
}
