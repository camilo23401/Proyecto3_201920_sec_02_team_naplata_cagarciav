package model.logic;

import model.data_structures.Arco;
import model.data_structures.ArregloDinamico;
import model.data_structures.GrafoNoDirigido;
import model.data_structures.Stack;

public class Djikstra2 {

	private double[] distTo;         
	private Arco<Integer>[] edgeTo;  
	private IndexMinPQ<Double> pq;   


	public Djikstra2(GrafoNoDirigido<Integer,Coordenadas> G, int s) {

		distTo = new double[G.V()];
		edgeTo = new Arco[G.V()];

		validarVertice(s);

		for (int v = 0; v < G.V(); v++)
		{
			distTo[v] = Double.POSITIVE_INFINITY;
			distTo[s] = 0.0;

		}   
		pq = new IndexMinPQ<Double>(G.V());
		pq.insert(s, distTo[s]);
		while (!pq.isEmpty()) {
			int v = pq.delMin();

			for (int i = 0; i < G.adyacentes(v).darTamano(); i++) {
				Arco<Integer>e= G.adyacentes(v).darElementoPos(i);
				relajar(e);
			}
		}  
	}
	private void relajar(Arco<Integer> e) {
		int v = e.getOrigen(), w = e.getDestino();
		if (distTo[w] > distTo[v] + e.getCosto()) { //Se asegura que se usa para haversine
			distTo[w] = distTo[v] + e.getCosto();
		edgeTo[w] = e;
		if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
		else                pq.insert(w, distTo[w]);
		}
	}

	public double distTo(int v) {
		validarVertice(v);
		return distTo[v];
	}

	public boolean hasPathTo(int v) {
		validarVertice(v);
		return distTo[v] < Double.POSITIVE_INFINITY;
	}

	public ArregloDinamico<Arco<Integer>> pathTo(int v) {
		validarVertice(v);
		if (!hasPathTo(v)) return null;
		ArregloDinamico<Arco<Integer>>path=new ArregloDinamico<Arco<Integer>>(100);
		for (Arco<Integer> e = edgeTo[v]; e != null; e = edgeTo[e.getOrigen()]) {
			path.agregar(e);
		}
		return path;
	}   
	private void validarVertice(int v) {
		int V = distTo.length;
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	}
}
