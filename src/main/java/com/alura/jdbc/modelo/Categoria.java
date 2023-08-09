package com.alura.jdbc.modelo;

public class Categoria {
//Se agregan los mismos campos que de la tabla
	
	private Integer id;
	private String nombre;
	
	public Categoria(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}
	
	
}
