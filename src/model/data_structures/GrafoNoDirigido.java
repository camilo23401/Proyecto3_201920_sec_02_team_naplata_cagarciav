package model.data_structures;

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
	private ArregloDinamico<Integer>cantidadConectados;

	private int capacidad;
	private int count;//numero de componentes 


	private class Arco<R> implements  Comparable <Arco<R>>  {
		double costo;
		double costo2;
		double costo3;
		R id;
		public Arco(double pCosto, double pCosto2, double pCosto3,R pDestino) {
			costo=pCosto;
			costo2 = pCosto2;
			costo3 = pCosto3;
			id=pDestino;

		}

		public double getCosto() {
			return costo;
		}
		public double getCosto2()
		{
			return costo2;
		}
		public double getCosto3()
		{
			return costo3;
		}
		public void setCosto(double costo) {
			this.costo = costo;
		}

		public R getId() {
			return id;
		}
		public void setId(R origen) {
			this.id = origen;
		}

		@Override
		public int compareTo(GrafoNoDirigido<K, T>.Arco<R> o) {
			// TODO Auto-generated method stub
			return 0;
		}


	}

	public GrafoNoDirigido(int tamanio) {

		if (V < 0) throw new IllegalArgumentException("La cantidad de vertices debe ser positivos");
		V = 0;
		E = 0;
		adj = new HashSeparateChaining<K,ArregloDinamico<Arco<K>>>(tamanio); 
		val=new HashSeparateChaining<K,T>(tamanio);
		capacidad=tamanio;
		mark=new HashSeparateChaining<K,Boolean>(tamanio);
		cantidadConectados=new ArregloDinamico<Integer>(150);
		count=0;


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
			Arco<K>ori=new Arco<K>(cost, cost2, cost3, idVertexIni);
			Arco<K>dest=new Arco<K>(cost, cost2, cost3, idVertexFin);
			origen.agregar(dest);
			destino.agregar(ori);
			E++;
		}

	}


	public T getInfoVertex(K idVertex) {
		T buscado=val.get(idVertex);
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
				if(actual.getId()==idVertexIni) {
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
				if(actual.getId()==idVertexIni) {
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
				if(actual.getId()==idVertexIni) {
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
			if(actual.getId()==idVertexFin) {
				encontrado1=true;
				actual.setCosto(cost);
			}

		}
		for (int i = 0; i <fin.darTamano()&&!encontrado2; i++) {
			Arco<K>actual=fin.darElementoPos(i);
			if(actual.getId()==idVertexIni) {
				encontrado2=true;
				actual.setCosto(cost);
			}

		}


	}

	public Iterable<K> adje (K idVertex) {
		ArregloDinamico<Arco<K>>ret=adj.get(idVertex);
		ArregloDinamico<K>retorno=new ArregloDinamico<K>(2000000);
		for (int i = 0; i < ret.darTamano(); i++) {
			retorno.agregar(ret.darElementoPos(i).getId());
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
			K destino=lista.darElementoPos(i).getId();
			if(!marked(destino)) {
				mark.setValue(destino, true);
				dfs(destino);		
			}
		}


	}

	public int CC() {
		uncheck();
		for (int v = 0; v < capacidad; v++) {	
			cantidadConectados.setPos(0, count);
			if (mark.getPos(v)!=null&&!mark.getPos(v)) {
				dfs(mark.getPosKey(v));
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


	public Iterable<K>getCC(K idVertex){
		this.uncheck();
		this.dfs(idVertex);
		ArregloDinamico<K>retorno=new ArregloDinamico<K>(100000);
		for (int i = 0; i < mark.darCapacidad(); i++) {
			K actual=mark.getPosKey(i);
			if(actual!=null&&mark.get(actual)) {
				retorno.agregar(actual);
			}
		}
		return retorno;

	}
	public int darCapacidad()
	{
		return capacidad;
	}
	public ArregloDinamico<K> adyacentes(K pId)
	{
		ArregloDinamico<K> rta = new ArregloDinamico<K>(2);
		Iterable iter = adje(pId);
		Iterator iterador = iter.iterator();
		while(iterador.hasNext())
		{
			rta.agregar((K)iterador.next());
		}
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

	 

}
