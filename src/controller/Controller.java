package controller;

import java.util.Scanner;

import com.sun.javafx.collections.MapAdapterChange;
import com.teamdev.jxmaps.LatLng;

import model.data_structures.ArregloDinamico;
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
						int conec=modelo.darCantidadConectadas();
						System.out.println("Hay "+conec+" componentes conectadas");
						System.out.println("5 componentes mas conectadas");
						ArregloDinamico<String>nov=modelo.dar5componentes();
						for (int i = 0; i < 5; i++) {
							System.out.println((i+1)+". Vertices Conectados: "+nov.darElementoPos(i).split(",")[1]);
						}
						System.out.println("Esperar que el mapa cargue completamente para continuar");
						modelo.cargarMapa();
						System.out.println("Mapa cargado");
						System.out.println("Se cargaron satisfactoriamente los datos al sistema");
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}	
					break;

				case 2:
					try
					{
						System.out.println("Creando archivo");
						modelo.crearJSON();
						System.out.println("Se creó el archivo de forma satisfactoria");
					}
					catch(Exception e)
					{
						e.printStackTrace();
					
					}
					break;

				case 3:
					try
					{
					modelo.leerJSON();	
					int conec=modelo.darCantidadConectadas();
					System.out.println("Hay "+conec+" componentes conectadas");
						System.out.println("Se cargó la información correctamente desde el archivo JSON");
					}
					catch(Exception e)
					{
						e.printStackTrace();
						System.out.println("Se produjo un error leyendo el archivo JSON: " + e.getMessage());
					}
					break;

				case 4:
					
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
