package model.logic;

import model.data_structures.Arco;
import model.data_structures.ArregloDinamico;
import model.data_structures.GrafoNoDirigido;
import model.data_structures.Stack;

public class Djikstra {

	private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private Arco<Integer>[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices

    /**
     * Computes a shortest-paths tree from the source vertex {@code s} to every other
     * vertex in the edge-weighted digraph {@code G}.
     *
     * @param  G the edge-weighted digraph
     * @param  s the source vertex
     * @throws IllegalArgumentException if an edge weight is negative
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public Djikstra(GrafoNoDirigido<Integer,Coordenadas> G, int s) {
      
        distTo = new double[G.V()];
        edgeTo = new Arco[G.V()];

        validateVertex(s);

        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
            distTo[s] = 0.0;

        // relax vertices in order of distance from s
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
          
            for (int i = 0; i < G.adyacentes(v).darTamano(); i++) {
				Arco<Integer>e= G.adyacentes(v).darElementoPos(i);
            	relax(e);
			}
        }

    
       
    }

    // relax edge e and update pq if changed
    private void relax(Arco<Integer> e) {
        int v = e.getOrigen(), w = e.getDestino();
        if (distTo[w] > distTo[v] + e.getCosto2()) {
            distTo[w] = distTo[v] + e.getCosto2();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else                pq.insert(w, distTo[w]);
        }
    }


    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }


    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }


    public ArregloDinamico<Arco<Integer>> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        ArregloDinamico<Arco<Integer>>path=new ArregloDinamico<Arco<Integer>>(100);
        for (Arco<Integer> e = edgeTo[v]; e != null; e = edgeTo[e.getOrigen()]) {
            path.agregar(e);
        }
        return path;
    }


 
    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    

	
	
	
}
