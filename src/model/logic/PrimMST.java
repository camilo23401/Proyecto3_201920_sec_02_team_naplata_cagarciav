package model.logic;

import model.data_structures.Arco;
import model.data_structures.GrafoNoDirigido;
import model.data_structures.Queue;

public class PrimMST {
    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private Arco<Integer>[] edgeTo;        // edgeTo[v] = shortest edge from tree vertex to non-tree vertex
    private double[] distTo;      // distTo[v] = weight of shortest such edge
    private boolean[] marked;     // marked[v] = true if v on tree, false otherwise
    private IndexMinPQ<Double> pq;

    /**
     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
     * @param G the edge-weighted graph
     */
    public PrimMST(GrafoNoDirigido G) {
        edgeTo = new Arco[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        pq = new IndexMinPQ<Double>(G.V());
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;

        for (int v = 0; v < G.V(); v++)      // run from each vertex to find
            if (!marked[v]) prim(G, v);      // minimum spanning forest

       
    }

    // run Prim's algorithm in graph G, starting from vertex s
    private void prim(GrafoNoDirigido<Integer,Coordenadas> G, int s) {
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

    /**
     * Returns the edges in a minimum spanning tree (or forest).
     * @return the edges in a minimum spanning tree (or forest) as
     *    an iterable of edges
     */
    public Iterable<Arco> edges() {
        Queue<Arco> mst = new Queue<Arco>();
        for (int v = 0; v < edgeTo.length; v++) {
            Arco e = edgeTo[v];
            if (e != null) {
                mst.enqueue(e);
            }
        }
        return mst;
    }

    /**
     * Returns the sum of the edge weights in a minimum spanning tree (or forest).
     * @return the sum of the edge weights in a minimum spanning tree (or forest)
     */
    public double weight() {
        double weight = 0.0;
        for (Arco e : edges())
            weight += e.getCosto();
        return weight;
    }
}
