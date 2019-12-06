package controller;

import java.util.Scanner;

import com.sun.javafx.collections.MapAdapterChange;
import com.teamdev.jxmaps.LatLng;

import model.data_structures.Arco;
import model.data_structures.ArregloDinamico;
import model.logic.Coordenadas;
import model.logic.Djikstra;
import model.logic.Interseccion;
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

					System.out.println("Se cargó la información correctamente desde el archivo JSON");
				}
				catch(Exception e)
				{
					e.printStackTrace();
					System.out.println("Se produjo un error leyendo el archivo JSON: " + e.getMessage());
				}
				break;

			case 4:
				System.out.println("Ingresar longitud de origen");
				double lon1  = Double.parseDouble(lector.next());
				System.out.println("Ingresar latitud de origen");
				double lat1 = Double.parseDouble(lector.next());
				int vinic=modelo.darIdVertice(lon1, lat1);					
				System.out.println("Ingresar longitud de destino");
				double lon2  =  Double.parseDouble(lector.next());
				System.out.println("Ingresar latitud de destino");
				double lat2  =  Double.parseDouble(lector.next());
				int vinfin=modelo.darIdVertice(lon2, lat2);	
				if(vinic!=-1||vinfin!=-1) {
					Djikstra dk=new Djikstra(modelo.darGrafo(),vinic);
					ArregloDinamico<Arco<Integer>>path= dk.pathTo(vinfin);
					double tiempoPromedio=0.0;
					double hav=0.0;
					System.out.println("Cantidad Vertices: "+path.darTamano());
					System.out.println("ID,latitud,longitud");
					for (int i = 0; i < path.darTamano(); i++) {
						Arco<Integer>actual=path.darElementoPos(i);
						Coordenadas act=modelo.darGrafo().getInfoVertex(actual.getDestino());
						System.out.println((i+1)+"."+actual.getDestino()+","+act.darLatitud()+","+act.darLongitud());
						tiempoPromedio=+actual.getCosto2();
						hav=+actual.getCosto();
					}
					ArregloDinamico<Interseccion>cor=new ArregloDinamico<Interseccion>(1000);

					for (int i = 0; i < path.darTamano(); i++) {
						Coordenadas inic=new Coordenadas(lat1,lon1,0);
						Arco<Integer>act3=path.darElementoPos(i);
						Coordenadas act=modelo.darGrafo().getInfoVertex(act3.getDestino());
						Interseccion agrega=new Interseccion(inic.darLatitud(),inic.darLongitud(),act.darLatitud(),act.darLongitud());
						cor.agregar(agrega);
						inic=act;
					}
					tiempoPromedio=tiempoPromedio/path.darTamano();
					System.out.println("Tiempo Promedio "+tiempoPromedio);
					System.out.println("Distancia Haversine "+hav);
					LatLng inic=new LatLng(lat1, lon1);
					LatLng fini=new LatLng(lat2, lon2);
					modelo.cargarMapaCamino(inic, fini, cor);

				}else {
					System.out.println("No hay vertices con estas caracteristicas");
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
