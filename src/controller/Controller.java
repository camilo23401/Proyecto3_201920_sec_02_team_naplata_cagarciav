package controller;

import java.util.Scanner;

import com.sun.javafx.collections.MapAdapterChange;
import com.teamdev.jxmaps.LatLng;

import model.logic.Coordenadas;
import model.logic.MVCModelo;
import view.MVCView;
import model.logic.Maps;

public class Controller {

	/* Instancia del Modelo*/
	private MVCModelo modelo;
	
	/* Instancia de la Vista*/
	private MVCView view;
	
	private Maps mapa;
	
	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller ()
	{
		view = new MVCView();
		modelo = new MVCModelo();
	}
		
	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		String dato = "";
		String respuesta = "";

		while( !fin ){
			view.printMenu();
			int option = lector.nextInt();
			switch(option){
				case 1:
					try
					{
						modelo.cargarInfo();	
						System.out.println("Se cargaron satisfactoriamente los datos al sistema");
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}	
					break;

				case 2:
					int conec=modelo.darCantidadConectadas();
					System.out.println("Hay "+conec+" componentes conectadas");
					break;

				case 3:
					try
					{
						modelo.crearJSON();
						System.out.println("Se creó el archivo de forma satisfactoria");
					}
					catch(Exception e)
					{
						System.out.println("Se produjo un error creando el archivo JSON: " + e.getMessage());
					}
					break;

				case 4:
					try
					{
						modelo.tomarJson();
						System.out.println("Se cargó la información correctamente desde el archivo JSON");
					}
					catch(Exception e)
					{
						System.out.println("Se produjo un error leyendo el archivo JSON: " + e.getMessage());
					}
					break;

				case 5:
					break;	
					
				case 6: 
					break;	

				default: 
					break;
			}
		}
		
	}	
}
