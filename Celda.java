package gameOfLife;

import java.util.*;
import java.awt.*;

import javax.swing.*;

/**
* En esta clase se define una celda por su posición (x, y) y su estado (vivo o muerto). 
* Se definen también métodos para actualizar su estado (reglas del juego de la vida) y 
* para leer o grabar su estado.
*/
public class Celda {

	boolean state = false;
	int x = 0;
	int y = 0;
	
	// Constructor
	public Celda(int x, int y){
		this.x = x;
		this.y = y;
	};
	
	
	// Leer estado
	public boolean getState(){
		return state;
	}
	
	// Grabar estado
	public void setState(boolean state){
		this.state = state;
	}
	
	
	// Actualización
	public void update(Celda[][] mapa){
		
		// Cuenta vecinas vivas
		int vecinasVivas = 0;
		for(int i = (this.x-1); i < (this.x+2); i++){
			for(int j= (this.y-1); j < (this.y+2); j++){
				if(i<0 || j<0 || i>(mapa.length-1) || j>(mapa.length-1)){continue;}	// Si está fuera del rango: siguiente iteración
				if(mapa[i][j].getState()){vecinasVivas++;} // Si está dentro del rango y viva: suma 1
			}
		}
		
		// Actualiza FSM
		if(this.getState()){									// Si está viva...
			if(vecinasVivas==2 || vecinasVivas==3){ return; }		// Y tiene 2 o 3 vecinas vivas... sigue igual
			state = false;											// Si no... muere
		}else{													// Si está muerta...
			if(vecinasVivas==3){ state=true; }						// Y tiene 3 vecinas vivas... nace
		}
		return;
	}
}