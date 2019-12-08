package model.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.Iterator;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.opencsv.CSVReader;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Polyline;

import jdk.nashorn.internal.parser.JSONParser;

import com.google.gson.*;

import model.data_structures.ArbolRojoNegro;
import model.data_structures.Arco;
import model.data_structures.ArregloDinamico;
import model.data_structures.GrafoNoDirigido;
import model.data_structures.HashSeparateChaining;


/**
 * Definicion del modelo del mundo
 *
 */
public class MVCModelo 
{

	private GrafoNoDirigido<Integer, Coordenadas> grafo = new GrafoNoDirigido<Integer, Coordenadas>(500000);
	private GrafoNoDirigido<Integer, Coordenadas> subGrafo = new GrafoNoDirigido<Integer, Coordenadas>(5000);
	private HashSeparateChaining<String, ViajeUber> viajesSemanales = new HashSeparateChaining<String, ViajeUber>(1000000);
	private ArregloDinamico<Interseccion>inter=new ArregloDinamico<Interseccion>(7000);
	public void cargarInfo() throws IOException
	{
		cargarViajesSemanales();
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

				double distancia = calcularPeso(Integer.parseInt(partes[0]), Integer.parseInt(partes[i]));
				double tiempo = calcularPeso2(sacarMovementIdVertices(Integer.parseInt(partes[0])),sacarMovementIdVertices(Integer.parseInt(partes[i])));
				double velocidad = calcularPeso3(distancia, tiempo);
				grafo.addEdge(Integer.parseInt(partes[0]), Integer.parseInt(partes[i]), distancia,tiempo, velocidad);
				if(grafo.getInfoVertex(Integer.parseInt(partes[i]))!=null&&grafo.getInfoVertex(Integer.parseInt(partes[0]))!=null) {		
					double lat1=grafo.getInfoVertex(Integer.parseInt(partes[0])).darLatitud();
					double lon1=grafo.getInfoVertex(Integer.parseInt(partes[0])).darLongitud();
					double lat2=grafo.getInfoVertex(Integer.parseInt(partes[i])).darLatitud();
					double lon2=grafo.getInfoVertex(Integer.parseInt(partes[i])).darLongitud();
					if(lon1>=-74.094723 && lon1<= -74.062707&&lon2>=-74.094723 && lon2<= -74.062707 && lat1>=4.597714 && lat1<=4.621360&& lat2>=4.597714 && lat2<=4.621360)
					{
						Interseccion agregado=new Interseccion(lat1,lon1,lat2,lon2);
						inter.agregar(agregado);
					}
				}
				i++;
			}

			linea2 = lector2.readLine();

		}	

		System.out.println("Cantidad de vertices cargados:"+ grafo.V());
		System.out.println("Cantidad de Arcos cargados:"+ grafo.E());

	}
	public int cargarViajesSemanales() throws IOException
	{
		int contador = 0;
		String rutaSemanal = "data/bogota-cadastral-2018-1-WeeklyAggregate.csv";
		CSVReader lector = new CSVReader(new FileReader(rutaSemanal));
		String [] siguiente;
		while ((siguiente = lector.readNext()) != null) 
		{
			if(contador!=0)
			{
				ViajeUber viajeNuevo = new ViajeUber(Integer.parseInt(siguiente[0]), Integer.parseInt(siguiente[1]), Short.parseShort("-1"), Double.parseDouble(siguiente[3]), Short.parseShort("-1"), Short.parseShort(siguiente[2]), Double.parseDouble(siguiente[4]), Double.parseDouble(siguiente[5]), Double.parseDouble(siguiente[6]));
				viajesSemanales.putInSet(siguiente[0]+"-"+siguiente[1], viajeNuevo);
			}
			contador++;
		}
		lector.close();
		return contador;
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
	public double calcularPeso2(int pMovementIdInicio, int pMovementIdFinal)
	{
		double rta = 0.0;
		if(pMovementIdInicio==pMovementIdFinal)
		{
			rta = calcularTiempoPromedioEntreZonas(pMovementIdInicio+"", pMovementIdFinal+"");
			if(rta == 0.0)
			{
				rta = 10;
			}
		}
		else
		{
			rta = calcularTiempoPromedioEntreZonas(pMovementIdInicio+"", pMovementIdFinal+"");
			if(rta == 0.0)
			{
				rta = 100;
			}
		}
		return rta;
	}
	public double calcularTiempoPromedioEntreZonas(String pIdZona1, String pIdZona2)
	{ 
		double rta = 0.0;
		int contador = 0;
		Iterator iter = viajesSemanales.getSet(pIdZona1+"-"+pIdZona2);
		while(iter.hasNext())
		{
			ViajeUber actual = (ViajeUber)iter.next();
			rta+=actual.darTiempoPromedio();
			contador++;
		}
		if(contador<=0)
		{
			return 0.0;
		}
		return rta/contador;
	}
	public double calcularPeso3(double pDistancia, double pTiempo)
	{
		double rta = 0.0;
		if(pTiempo!=0)
		{
			rta = pDistancia/pTiempo;
		}
		return rta;
	}
	public int darCantidadConectadas() {
		return grafo.CC();
	}

	public void crearJSON() throws IOException
	{
		try
		{
			Gson gson = new Gson();
			String mierfda = gson.toJson(grafo);
			PrintWriter p = new PrintWriter(new FileWriter("./data/grafoNoDirigido.txt"), true);
			p.print(mierfda);
			p.close();
		}
		catch (Exception error)
		{
			System.out.println("error");
		}
	}

	public void leerJSON()
	{
		try
		{
			Gson gson = new Gson();
			Type type = new TypeToken<GrafoNoDirigido<Integer,Coordenadas>>(){}.getType();
			JsonReader lector = new JsonReader(new FileReader("./data/grafoNoDirigido.txt"));
			JsonElement element = new JsonParser().parse(lector);
			grafo = gson.fromJson(element, type);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Error leyendo archivo");
		}
	}

	public GrafoNoDirigido<Integer, Coordenadas> darGrafo()
	{
		return grafo;
	}

	public GrafoNoDirigido<Integer, Coordenadas> darsubGrafo()
	{
		return subGrafo;
	}
	public ArregloDinamico<Coordenadas> sacarCoordenadasVertices()
	{
		ArregloDinamico<Coordenadas> rta = new ArregloDinamico<Coordenadas>(300000);
		for(int i=0; i<subGrafo.darCapacidad();i++)
		{
			if(subGrafo.getVertexPosi(i)!=null)
			{
				Coordenadas actual = subGrafo.getInfoVertex(subGrafo.getVertexPosi(i));
				rta.agregar(actual);	
			}
		}

		return rta;
	}
	public int sacarMovementIdVertices(int pIdVertice)
	{
		int rta = 0;
		Coordenadas actual = grafo.getInfoVertex(pIdVertice);
		if(actual!=null)
		{
			rta = actual.darId();		
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

	public ArregloDinamico<String>dar5componentes(){
		return grafo.componentesMasGrandes();
	}
	public void cargarMapa()
	{


		Maps maps = new Maps(sacarCoordenadasVertices(),darsubGrafo(),darArcosRango());
		maps.initFrame("Mapa");
	}

	public ArregloDinamico<String>darNVerticesConMenorVelocidad(){
		ArregloDinamico<String>ret=new ArregloDinamico<String>(300000);
		for (int i = 0; i < grafo.darCapacidad(); i++) {
			double cant=0.0;
			ArregloDinamico<Arco<Integer>>adj=grafo.adyacentes(i)	;			
			for (int j = 0; adj!=null&&j < adj.darTamano(); j++) {
				Arco<Integer>actual=adj.darElementoPos(j);
				cant+=actual.getCosto3();	
		
			}
			if(adj!=null&&adj.darTamano()>0) {
				cant=cant/adj.darTamano();
				DecimalFormat df = new DecimalFormat("#.########");
				String next=df.format(cant);
				ret.agregar(i+","+next);
			}
			
		}
		ret.shellSortStringDouble();;
		return ret;


	}

	public void cargarMapaCamino(LatLng inicio,LatLng fin,ArregloDinamico<Interseccion>arcos) {
		Maps maps = new Maps(inicio,fin,arcos);
		maps.initFrame("Mapa");
	}

	public int darIdVertice(double lon,double lat) {
		int pos=-1;
		for (int i = 0; i < grafo.darCapacidad(); i++) {
			Coordenadas actual=grafo.getVertexpos(i);
			if(actual!=null&&actual.darLatitud()==lat&&actual.darLongitud()==lon) {

				pos= grafo.getVertexPosi(i);
			}
		}		
		return pos;
	}


	public ArregloDinamico<Interseccion> darArcosRango(){
		return inter;

	}
	public void cargarMapaNVertices(ArregloDinamico<Coordenadas>arcos) {
		Maps maps = new Maps(arcos);
		maps.initFrame("Mapa");
	}

	

}
