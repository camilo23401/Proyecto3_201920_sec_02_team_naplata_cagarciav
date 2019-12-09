package view;

import model.logic.MVCModelo;

public class MVCView 
{
	    /**
	     * Metodo constructor
	     */
	    public MVCView()
	    {
	    	
	    }
	    
		public void printMenu()
		{
			System.out.println("1. Cargar datos y crear grafo");
			System.out.println("2. Crear archivo JSON desde los datos cargados");
			System.out.println("3. Leer archivo JSON preexistente");
			System.out.println("4. Encontrar	el	camino	de	costo	m�nimo	 para un	viaje	entre	dos	localizaciones	geogr�ficas	de	la	ciudad(Por tiempo promedio)	"); 
			System.out.println("5.Determinar	los	n v�rtices	con	menor	velocidad promedio	en	la	ciudad	de	Bogot�."); 
			System.out.println("6.Calcular	 un	 �rbol	 de	 expansi�n	 m�nima	 (MST)	 con	 criterio	 distancia" );
            System.out.println("7. Encontrar el camino de costo m�nimo para un viaje entre dos localizaciones geogr�ficas de la ciudad seg�n su distancia haversine");
            System.out.println("8. Encontrar los v�rtices alcanzables en un tiempo T a partir de una localizaci�n geogr�fica dada");
            System.out.println("9. C�lcular un �rbol de expansi�n m�nima (MST) con criterio distancia por algoritmo Kruskal");
			System.out.println("10.Cargar mapas zonas" );

			System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return: (e.g., 1):");
		}

		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}		
		
	
}
