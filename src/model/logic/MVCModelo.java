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
	private ArregloDinamico<Interseccion>interZonas=new ArregloDinamico<Interseccion>(7000);

	private GrafoNoDirigido<Integer,Coordenadas>subGn;
	private GrafoNoDirigido<Integer, Coordenadas> grafoZonas = new GrafoNoDirigido<Integer, Coordenadas>(500000);
	public ArregloDinamico<Coordenadas>corZonas=new ArregloDinamico<Coordenadas>(7000);
	private HashSeparateChaining<String, Boolean> hayvisaje = new HashSeparateChaining<String, Boolean>(1000000);


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
			if(grafoZonas.getInfoVertex(Integer.parseInt(partes[3]))==null){
				grafoZonas.addVertex(Integer.parseInt(partes[3]), interseccion);
				corZonas.agregar(interseccion);

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
					int idinic=this.darIdVertice(lon1, lat1);
					int idFin=this.darIdVertice(lon2, lat2);



				}
				i++;
			}

			linea2 = lector2.readLine();

		}	

		System.out.println("Cantidad de vertices cargados:"+ grafo.V());
		System.out.println("Cantidad de Arcos cargados:"+ grafo.E());

	}

	public boolean buscarSihayviajes(int inic,int end) {
		boolean hay=false;
		ViajeUber ac=viajesSemanales.get(inic+"-"+end);
		if(ac!=null&&hayvisaje.get(inic+"-"+end)==false) {
			hay=true;
			hayvisaje.setValue(inic+"-"+end, true);
			if(viajesSemanales.get(end+"-"+inic)!=null) {
				hayvisaje.setValue(end+"-"+inic, true);
			}
		}
		return hay;
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
				hayvisaje.putInSet(siguiente[0]+"-"+siguiente[1], false);

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
		Maps maps = new Maps(arcos,0);
		maps.initFrame("Mapa");
	}



	public GrafoNoDirigido<Integer,Coordenadas>darSubGrafoN(ArregloDinamico<Coordenadas>vertic){
		subGn=new GrafoNoDirigido<Integer,Coordenadas>(100);
		for (int i = 0; i < vertic.darTamano();i++) {
			Coordenadas actual=vertic.darElementoPos(i);
			int id=this.darIdVertice(actual.darLongitud(), actual.darLatitud());
			subGn.addVertex(id, actual);
			ArregloDinamico<Arco<Integer>>adj=grafo.adyacentes(id);
			for (int j = 0; j < adj.darTamano(); j++) {
				Arco<Integer>act=adj.darElementoPos(j);
				for (int j2 = 0; j2 < vertic.darTamano(); j2++) {
					Coordenadas actual1=vertic.darElementoPos(j2);
					int id1=this.darIdVertice(actual1.darLongitud(), actual1.darLongitud());
					if(act.getDestino()==id1) {
						subGn.addEdge(id, act.getDestino(), act.getCosto(), act.getCosto2(), act.getCosto3());
					}

				}
			}

		}
		System.out.println(subGn.E());
		return subGn;
	}
	public void menosCostosoHaversine(double pLat1, double pLon1, double pLat2, double pLon2)
	{
		int idInicial = darIdVertice(pLon1, pLat1);
		int idFinal = darIdVertice(pLon2, pLat2);
		if(idInicial!=-1||idFinal!=-1) {
			Djikstra2 algoritmo =new Djikstra2(grafo,idInicial);
			ArregloDinamico<Arco<Integer>>path= algoritmo.pathTo(idFinal);
			double tiempoPromedio=0.0;
			double hav=0.0;
			System.out.println("Cantidad Vertices: "+path.darTamano());
			System.out.println("ID,latitud,longitud");
			for (int i = 0; i < path.darTamano(); i++) {
				Arco<Integer>actual=path.darElementoPos(i);
				Coordenadas act=grafo.getInfoVertex(actual.getDestino());
				System.out.println((i+1)+"."+actual.getDestino()+","+act.darLatitud()+","+act.darLongitud());
				tiempoPromedio=+actual.getCosto2();
				hav=+actual.getCosto();
			}
			ArregloDinamico<Interseccion>cor=new ArregloDinamico<Interseccion>(1000);
			Coordenadas inic=new Coordenadas(pLat1,pLon1,0);
			for (int i = path.darTamano(); i >0; i--) {
				Arco<Integer>act3=path.darElementoPos(i);
				Coordenadas act=grafo.getInfoVertex(act3.getDestino());
				Interseccion agrega=new Interseccion(inic.darLatitud(),inic.darLongitud(),act.darLatitud(),act.darLongitud());
				cor.agregar(agrega);
				inic=new Coordenadas(agrega.getLatin1(),agrega.getLonin2(),0);
			}
			tiempoPromedio=tiempoPromedio/path.darTamano();
			System.out.println("Tiempo Promedio "+tiempoPromedio);
			System.out.println("Distancia Haversine "+hav);
			LatLng inic2=new LatLng(pLat1, pLon1);
			LatLng fin =new LatLng(pLat2, pLon2);
			cargarMapaCamino(inic2, fin, cor);

		}
	}
	public ArregloDinamico<Integer> encontrarVerticesAlcanzables(double pTiempo,int pIdVerticeDado)
	{
		ArregloDinamico<Arco<Integer>> adyacentes = grafo.adyacentes(pIdVerticeDado);
		ArregloDinamico<Integer> rta = new ArregloDinamico<Integer>(100);
		for(int i=0;i<adyacentes.darTamano();i++)
		{
			Arco<Integer> actual = adyacentes.darElementoPos(i);
			System.out.println(actual.getCosto2());
			if(actual.getCosto2()<=pTiempo)
			{
				rta.agregar(actual.getDestino());
			}
		}
		System.out.println(rta.darTamano()+00);
		return rta;
	}
	public GrafoNoDirigido<Integer, Coordenadas> conectadoMasGrandeGrafo()
	{
		return grafo;
	}
	public ArregloDinamico<Arco<Integer>> conectadoMasGrandeArreglo()
	{
		return grafo.adyacentes(10);
	}
	public void mstDistanciaKruskal()
	{
		GrafoNoDirigido<Integer, Coordenadas> aux = conectadoMasGrandeGrafo();
		ArregloDinamico<Arco<Integer>> arreglo = conectadoMasGrandeArreglo();
		KruskalMST kruskalMasConectados = new KruskalMST(aux.V(), aux.E());
		for(int i=0;i<arreglo.darTamano();i++)
		{
			kruskalMasConectados.edge[i].origen = arreglo.darElementoPos(i).getOrigen();
			kruskalMasConectados.edge[i].destino = arreglo.darElementoPos(i).getDestino();
			kruskalMasConectados.edge[i].peso = (int)arreglo.darElementoPos(i).getCosto();
		}
		System.out.println(kruskalMasConectados.E + "Vs" + kruskalMasConectados.edge.length);
		kruskalMasConectados.Kruskal();
		kruskalMasConectados.imprimirRta();
	}
	public GrafoNoDirigido<Integer, Coordenadas> dargrafoZonas(){
		return grafoZonas;
	}
	
	public void cargarMapasZonas() {
		Maps maps = new Maps(corZonas);
		maps.initFrame("Mapa");
	}
}
