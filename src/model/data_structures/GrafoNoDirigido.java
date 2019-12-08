package model.data_structures;


import java.util.ArrayList;
import java.util.Iterator;

public class GrafoNoDirigido<K extends Comparable<K>,T> {
	//cantidad de vertices
	private int V;
	//cantidad de arcos
	private int E;
	//lista de adyacencia
	private HashSeparateChaining<K,T>val;
	private HashSeparateChaining<K, ArregloDinamico<Arco<K>>> adj;
	private HashSeparateChaining<K,Boolean>mark;
	public HashSeparateChaining<K, T> recuperados;
	private ArregloDinamico<Integer>cantidadConectados;
	private ArrayList<ArrayList<K>>conectadosa;
	private int capacidad;
	private int count;//numero de componentes 


	public GrafoNoDirigido(int tamanio) {

		if (V < 0) throw new IllegalArgumentException("La cantidad de vertices debe ser positivos");
		V = 0;
		E = 0;
		adj = new HashSeparateChaining<K,ArregloDinamico<Arco<K>>>(tamanio); 
		val=new HashSeparateChaining<K,T>(tamanio);
		capacidad=tamanio;
		mark=new HashSeparateChaining<K,Boolean>(tamanio);
		cantidadConectados=new ArregloDinamico<Integer>(152);
		count=0;
		recuperados = new HashSeparateChaining<K, T>(tamanio);
		conectadosa=new ArrayList<ArrayList<K>>();
	}
	/**
	 * @return V
	 */
	public int V() {
		return V;
	}

	public int E() {
		return E;
	}
	public void addVertex(K idVertex, T infoVertex) {
		val.put(idVertex, infoVertex);
		adj.put(idVertex, new ArregloDinamico<Arco<K>>(1000));
		mark.put(idVertex, false);
		V++;
	}

	public void addEdge(K idVertexIni, K idVertexFin, double cost, double cost2, double cost3) {
		ArregloDinamico<Arco<K>>origen=adj.get(idVertexIni);
		ArregloDinamico<Arco<K>>destino=adj.get(idVertexFin);
		if(origen!=null&&destino!=null) {
			Arco<K>ori=new Arco<K>(cost, cost2, cost3, idVertexIni,idVertexFin);
			Arco<K>dest=new Arco<K>(cost, cost2, cost3, idVertexFin,idVertexIni);
			origen.agregar(dest);
			destino.agregar(ori);
			E++;
		}

	}


	public T getInfoVertex(K idVertex) {
		T buscado=val.get(idVertex);
		return buscado;

	}
	public T getVertexpos(int i) {
		T buscado=val.getPos(i);
		return buscado;

	}

	public K getVertexPosi(int i) {
		K buscado=val.getPosKey(i);
		return buscado;

	}

	public double getCostArc(K idVertexIni,K idVertexFin) {
		double cost=0.0;
		ArregloDinamico<Arco<K>>buscado=adj.get(idVertexFin);
		if(buscado==null) {
			//System.out.println("No existen arcos con estas caracteristicas");
		}
		else {
			boolean encontrado=false;
			for (int i = 0; i < buscado.darTamano()&&!encontrado; i++) {
				Arco<K>actual=buscado.darElementoPos(i);
				if(actual.getDestino()==idVertexIni) {
					cost=actual.getCosto();
					encontrado=true;
				}
			}
		}
		return cost;
	}
	public double getCost2Arc(K idVertexIni,K idVertexFin) {
		double cost=0.0;
		ArregloDinamico<Arco<K>>buscado=adj.get(idVertexFin);
		if(buscado==null) {
			//System.out.println("No existen arcos con estas caracteristicas");
		}
		else {
			boolean encontrado=false;
			for (int i = 0; i < buscado.darTamano()&&!encontrado; i++) {
				Arco<K>actual=buscado.darElementoPos(i);
				if(actual.getDestino()==idVertexIni) {
					cost=actual.getCosto2();
					encontrado=true;
				}
			}
		}
		return cost;
	}
	public double getCost3Arc(K idVertexIni,K idVertexFin) {
		double cost=0.0;
		ArregloDinamico<Arco<K>>buscado=adj.get(idVertexFin);
		if(buscado==null) {
			//System.out.println("No existen arcos con estas caracteristicas");
		}
		else {
			boolean encontrado=false;
			for (int i = 0; i < buscado.darTamano()&&!encontrado; i++) {
				Arco<K>actual=buscado.darElementoPos(i);
				if(actual.getDestino()==idVertexIni) {
					cost=actual.getCosto3();
					encontrado=true;
				}
			}
		}
		return cost;
	}

	public void setInfoVertex(K idVertex, T infoVertex) {
		if(val.getKey(idVertex)==null) {
			System.out.println("No existe vertice con estas caracteristcas");
		}
		else {
			val.setValue(idVertex, infoVertex);
		}

	}
	public void setCostArc(K idVertexIni, K idVertexFin, double cost) {
		ArregloDinamico<Arco<K>>inic=adj.get(idVertexIni);
		ArregloDinamico<Arco<K>>fin=adj.get(idVertexFin);
		boolean encontrado1=false;
		boolean encontrado2=false;
		for (int i = 0; i <inic.darTamano()&&!encontrado1; i++) {
			Arco<K>actual=inic.darElementoPos(i);
			if(actual.getDestino()==idVertexFin) {
				encontrado1=true;
				actual.setCosto(cost);
			}

		}
		for (int i = 0; i <fin.darTamano()&&!encontrado2; i++) {
			Arco<K>actual=fin.darElementoPos(i);
			if(actual.getDestino()==idVertexIni) {
				encontrado2=true;
				actual.setCosto(cost);
			}

		}


	}

	public Iterable<K> adje (K idVertex) {
		ArregloDinamico<Arco<K>>ret=adj.get(idVertex);
		ArregloDinamico<K>retorno=new ArregloDinamico<K>(2000000);
		System.out.println(retorno.darTamano());
		for (int i = 0; i < ret.darTamano(); i++) {
			retorno.agregar(ret.darElementoPos(i).getDestino());
		}
		return retorno;
	}

	public void dfs(K s) {

		mark.setValue(s, true);
		int val=cantidadConectados.darElementoPos(count);
		val++;
		cantidadConectados.setPos(val, count);

		ArregloDinamico<Arco<K>>lista=adj.get(s);
		for (int i = 0; i < lista.darTamano(); i++) {
			K destino=lista.darElementoPos(i).getDestino();
			if(!marked(destino)) {
			
				mark.setValue(destino, true);
				dfs(destino);		
			}
		}


	}

	public K getKeyValue(T valor) {
		return val.getKeyWithValue(valor);
	}

	public int CC() {
		uncheck();
		int cantcon=0;
		for (int v = 0; v < capacidad; v++) {	
			cantidadConectados.setPos(0, count);
			if (mark.getPos(v)!=null&&!mark.getPos(v)) {
				conectadosa.add(new ArrayList<K>());

				dfs(mark.getPosKey(v));
				cantcon++;
				count++;

			}
		}
		return count;
	}



	public void uncheck() {
		for (int i = 0; i < mark.darCapacidad(); i++) {
			K actual=mark.getPosKey(i);
			if(actual!=null) {
				mark.setValue(actual, false);
			}
			count=0;

		}

	}

	public boolean marked(K v) {
		return  mark.get(v)==true;
	}



	public int darCapacidad()
	{
		return capacidad;
	}
	public ArregloDinamico<Arco<K>> adyacentes(K pId)
	{
		ArregloDinamico<Arco<K>> rta = adj.get(pId);
		return rta;

	}

	public ArregloDinamico<String> componentesMasGrandes() {
		this.CC();
		ArregloDinamico<String>ret=new ArregloDinamico<String>(200);
		for (int i = 0; i < cantidadConectados.darCapacidad(); i++) {
			K actual=(K) cantidadConectados.darElementoPos(i);
			if(actual!=null) {
				ret.agregar((i+1)+","+actual);
			}
		}
		ret.shellSortString();
		return ret;
	}

	public ArrayList<ArrayList<K>>darVerticesComponentes(){
		return conectadosa;
	}

}
