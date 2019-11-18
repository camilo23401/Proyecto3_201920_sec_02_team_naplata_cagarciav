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
			System.out.println("1. Cargar datos al sistema");
			System.out.println("2. Dar cantidad de componentes conectadas");
			System.out.println("3. Crear archivo JSON desde los datos cargados");
			System.out.println("4. Leer archivo JSON preexistente");
			System.out.println("5. Cargar Mapa");
			System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return: (e.g., 1):");
		}

		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}		
		
		public void printModelo(MVCModelo modelo)
		{
			// TODO implementar
		}
}
