package model.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import jdk.nashorn.internal.parser.JSONParser;

import com.google.gson.*;

import model.data_structures.ArregloDinamico;
import model.data_structures.GrafoNoDirigido;


/**
 * Definicion del modelo del mundo
 *
 */
public class MVCModelo 
{

	private GrafoNoDirigido<Integer, Coordenadas> grafo = new GrafoNoDirigido<Integer, Coordenadas>(9999999);
	private GrafoNoDirigido<Integer, Coordenadas> subGrafo = new GrafoNoDirigido<Integer, Coordenadas>(999999);
	public void cargarInfo() throws IOException
	{
		String rutaArchivoVertices = "data/bogota_vertices.txt";
		FileReader lectorArchivo = new FileReader(rutaArchivoVertices);
		BufferedReader lector = new BufferedReader(lectorArchivo);
		String linea = lector.readLine();
		linea=lector.readLine();
		int cont=0;
		while((linea!=null))
		{
			String [] partes = linea.split(";");
			double longitud = Double.parseDouble(partes[1]);
			double latitud = Double.parseDouble(partes[2]);
			Coordenadas interseccion = new Coordenadas(latitud, longitud , Integer.parseInt(partes[3]));
			if(longitud>=-74.094723 && longitud<= -74.062707 && latitud>=4.597714 && latitud<=4.621360)
			{
				subGrafo.addVertex(Integer.parseInt(partes[0]), interseccion);
			}
			grafo.addVertex(Integer.parseInt(partes[0]),interseccion);
			cont++;
			linea = lector.readLine();
		}
		lector.close();
		String rutaArchivoArcos = "data/bogota_arcos.txt";
		FileReader lectorArchivo2 = new FileReader(rutaArchivoArcos);
		BufferedReader lector2 = new BufferedReader(lectorArchivo2);
		String linea2 = lector2.readLine();
		while(linea2!=null)
		{
			String [] partes = linea2.split(" ");

			int i=1;
			while(i<partes.length)
			{

				grafo.addEdge(Integer.parseInt(partes[0]), Integer.parseInt(partes[i]), calcularPeso(Integer.parseInt(partes[0]), Integer.parseInt(partes[i])));
				i++;
			}
			linea2 = lector2.readLine();
		}
		System.out.println("Cantidad de vertices cargados:"+ grafo.V());
		System.out.println("Cantidad de Arcos cargados:"+ grafo.E());
	}


	public double calcularPeso(int pIdInicio, int pIdFinal)
	{
		double rta = 0.0;
		if(grafo.getInfoVertex(pIdInicio)!=null && grafo.getInfoVertex(pIdFinal)!=null)
		{
			double latInicial = grafo.getInfoVertex(pIdInicio).darLatitud();
			double latFinal = grafo.getInfoVertex(pIdFinal).darLatitud();
			double lonInicial =grafo.getInfoVertex(pIdInicio).darLongitud();
			double lonFinal = grafo.getInfoVertex(pIdFinal).darLongitud();

			rta = Haversine.distance(latInicial, lonInicial, latFinal, lonFinal);
		}
		return rta;
	}


	public int darCantidadConectadas() {
		return grafo.CC();
	}

	public void crearJSON() throws IOException
	{
		Gson gson = new Gson();
		String estrucJsonGrafo = gson.toJson(grafo);
		PrintWriter impresora = new PrintWriter(new FileWriter("data/grafoNoDirigido.json"));
		impresora.print(estrucJsonGrafo);
	}

	public void tomarJson() throws IOException
	{
		Type caracteristicas = new TypeToken<GrafoNoDirigido<Integer,Coordenadas>>(){}.getType();
		Gson gson = new Gson();
		FileReader lectorArchivos = new FileReader("data/grafoNoDirigido.json");
		JsonReader parser = new JsonReader(lectorArchivos);
		JsonElement aux = new JsonParser().parse(parser);
		GrafoNoDirigido<Integer, Coordenadas> grafoAux = gson.fromJson(aux, caracteristicas);
		grafo = grafoAux;
		lectorArchivos.close();
	}
	public GrafoNoDirigido<Integer, Coordenadas> darGrafo()
	{
		return grafo;
	}
	public ArregloDinamico<Coordenadas> sacarCoordenadasVertices()
	{
		ArregloDinamico<Coordenadas> rta = new ArregloDinamico<Coordenadas>(300000);
		for(int i=0; i<subGrafo.darCapacidad();i++)
		{
			Coordenadas actual = subGrafo.getInfoVertex(i);
			if(actual!=null)
			{
				rta.agregar(actual);	
			}
		}
		return rta;
	}
	public Coordenadas[] sacarPareja(int pId, int pIdAdyacente)
	{
		Coordenadas[]  rta = new Coordenadas[2];
		rta[0] = subGrafo.getInfoVertex(pId);
		rta[1] = subGrafo.getInfoVertex(pIdAdyacente);
		return rta;
	}


}
