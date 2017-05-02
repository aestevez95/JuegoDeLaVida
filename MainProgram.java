package gameOfLife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.concurrent.TimeUnit; 

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Programa que simula "el juego de la vida". 
 * Este juego consiste en crear un tablero de celdas que pueden estar vivas o muertas 
 * (según su color) y usar las siguientes reglas para ir actualizando las celdas:
 * 
 * Si una celda está en una zona sobrepoblada (4 o más vecinos) ... muere
 * Si una celda está en una zona despoblada (0 o 1 vecino) ...      muere
 * Si una celda viva tiene 2 o 3 vecinos ...                        se mantiene viva
 * Si una celda muerta tiene 3 vecinos ...                          nace
 * 
 * 
 * Esta clase principal crea una matriz de celdas, inicializa cada una, prepara el 
 * estado inicial del tablero (crea algunas celdas vivas) y comienza un bucle en el 
 * que cada segundo se actualizan todas las celdas y se representan gráficamente. 
 * @author Antonio
 */
public class MainProgram {
	
	public static void main (String arg[]){
		//Tamaño del tablero
		int Filas = 15;
		int Columnas = 15;
		
		// Mapa con celdas
		Celda[][] mapa = new Celda[Filas][Columnas];
		
		
		// Inicialización de cada celda
		for(int i=0; i < Filas; i++){
			for(int j=0; j < Columnas; j++){
				mapa[i][j] = new Celda(i,j);
			}
		}
		
		
		// Estado inicial del mapa
		mapa[0][0].setState(true);
		mapa[0][1].setState(true);
		mapa[0][2].setState(true);
		mapa[0][3].setState(true);
		mapa[0][4].setState(true);
		mapa[0][5].setState(true);
		mapa[1][0].setState(true);
		mapa[2][1].setState(true);
		mapa[3][2].setState(true);
		mapa[4][3].setState(true);
		mapa[5][4].setState(true);
		mapa[6][5].setState(true);
		
		// Representación gráfica del estado inicial
		new Tablero(mapa);
		
		
		
		// Bucle de actualización
		while(true){
			try {
				TimeUnit.SECONDS.sleep(1);						// Espera 1 segundo	
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("Nueva ventana");				// Avisa de actualización
			
			// Actualiza cada celda
			for(int i=0; i < Filas; i++){
				for(int j=0; j < Columnas; j++){
						mapa[i][j].update(mapa);
					}
				}
			// Representación gráfica del mapa actual
			new Tablero(mapa);
		}
	}
}


/**
 *  Clase para la representación gráfica. Pinta un mapa de celdas y asigna un color a cada celda
 *  en función de si esté viva o muerta.
 */
class Tablero extends JFrame {

	// Colores
	final static Color colorViva = new Color(255,204,51);
	final static Color colorMuerta = new Color(51,102,255);
	final static Color colorBorde = Color.black;

	// Constructor
	public Tablero(Celda[][] mapa) {

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setPreferredSize(new Dimension(800, 800));
		
		final Celda[][] mapaPropio = mapa;

		// Añadir título
		getContentPane().add(
				"North",
				new JLabel("Game of life",
						SwingConstants.CENTER));

		// Añadir tablero
		getContentPane().add("Center", new JPanel() {

			double factor_profundidad = 100;
			double factor_altura = 100;

			private Point cambioEscala(double x, double y, double z) {

				double escalax = (double) (getWidth() - 30)
						/ (mapaPropio[0].length / factor_profundidad + mapaPropio.length);

				double escalay = (double) (getHeight() - 150)
						/ (mapaPropio[0].length / factor_profundidad);

				int vx = 10 + (int) ((x - y / factor_profundidad + mapaPropio[0].length
						/ factor_profundidad) * escalax);
				int vy = getHeight()
						- 20
						+ (int) ((y / factor_profundidad - mapaPropio[0].length
								/ factor_profundidad)
								* escalay - z * factor_altura);
				
				return new Point(vx, vy);
			}

			private void pintaCeldas(Graphics g) {

				for (int x = 0; x < mapaPropio.length; x++) {
					for (int y = 0; y < mapaPropio[x].length; y++) {

						if (mapaPropio[x][y].getState()) {
							pintaCelda(g, x, y, colorViva);
						} else {
							pintaCelda(g, x, y, colorMuerta);
						}
					}
				}
			}

			private void pintaCelda(Graphics g, int x, int y, Color color) {

				// Esquinas de la celda
				Point p1 = cambioEscala(x, y, 0);
				Point p2 = cambioEscala(x, y + 1, 0);
				Point p3 = cambioEscala(x + 1, y + 1, 0);
				Point p4 = cambioEscala(x + 1, y, 0);

				// Poligono de la celda
				Polygon pol = new Polygon();
				pol.addPoint(p1.x, p1.y);
				pol.addPoint(p2.x, p2.y);
				pol.addPoint(p3.x, p3.y);
				pol.addPoint(p4.x, p4.y);

				// Añadir colores
				g.setColor(color);
				g.fillPolygon(pol);
				g.setColor(colorBorde);
				g.drawPolygon(pol);
			}

			protected void paintComponent(Graphics g) {
				pintaCeldas(g);
			}
			
		});
		pack();
		setVisible(true);
	}
}

