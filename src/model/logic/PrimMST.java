package model.logic;

import model.data_structures.Arco;
import model.data_structures.GrafoNoDirigido;
import model.data_structures.Queue;

public class PrimMST {
    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private Arco<Integer>[] edgeTo;        
    private double[] distTo;      
    private boolean[] marked;     
    private IndexMinPQ<Double> pq;

    public PrimMST(GrafoNoDirigido G) {
        edgeTo = new Arco[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        pq = new IndexMinPQ<Double>(G.V());
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;

        for (int v = 0; v < G.V(); v++)      
            if (!marked[v]) prim(G, v);      

       
    }
    private void prim(GrafoNoDirigido<Integer,Coordenadas> G, int s) { //Corre el algoritmo en el grafo G desde el vértice S
        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            scan(G, v);
        }
    }
    private void scan(GrafoNoDirigido<Integer,Coordenadas> G, int v) {
        marked[v] = true;
        for (Arco e : G.adyacentes(v)) {
            int w = (int) e.other(v);
            if (marked[w]) continue;         
            if (e.getCosto() < distTo[w]) {
                distTo[w] = e.getCosto();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
                else                pq.insert(w, distTo[w]);
            }
        }
    }
    public Iterable<Arco> darArcos() {
        Queue<Arco> mst = new Queue<Arco>();
        for (int v = 0; v < edgeTo.length; v++) {
            Arco e = edgeTo[v];
            if (e != null) {
                mst.enqueue(e);
            }
        }
        return mst;
    }
    public double weight() {
        double weight = 0.0;
        for (Arco e : darArcos())
            weight += e.getCosto();
        return weight;
    }
}
