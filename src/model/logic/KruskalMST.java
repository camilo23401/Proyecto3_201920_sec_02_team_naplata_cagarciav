package model.logic;

import java.util.Arrays;

import model.data_structures.ArregloDinamico;

public class KruskalMST {
	private Edge[] mst;
	class Edge implements Comparable<Edge> //Clase de arcos interna para el algoritmo
	{
		int origen; 
		int destino;
		int peso; //Es necesario pasar el peso (haversine) a int...

		public int compareTo(Edge compareEdge)
		{
			return this.peso-compareEdge.peso;
		}
	};
	class subset
	{
		int parent; 
		int rank;
	};
	public KruskalMST(int v, int e)  //Toca practicamente crear un grafo nuevo, no importa porque se usa el resultado del conectado más grande
	{
		V = v;
		E = e;
		edge = new Edge[E];
		for (int i=0; i<e; ++i)
			edge[i] = new Edge();
		mst = new Edge[V];
	}
	int V; 
	int E; 
	Edge edge[];

	int find(subset subsets[], int i)
	{
		if (subsets[i].parent != i)
			subsets[i].parent = find(subsets, subsets[i].parent);

		return subsets[i].parent;
	}

	void Union(subset subsets[], int x, int y) //O UNION FIND
	{
		int xroot = find(subsets, x);
		int yroot = find(subsets, y);

		if (subsets[xroot].rank < subsets[yroot].rank)
			subsets[xroot].parent = yroot;
		else if (subsets[xroot].rank > subsets[yroot].rank)
			subsets[yroot].parent = xroot;
		else
		{
			subsets[yroot].parent = xroot;
			subsets[xroot].rank++;
		}
	}

	void Kruskal() //Acá se construye el MST
	{
		Edge result[] = new Edge[V]; 
		int e = 0; 
		int i = 0; 
		for (i=0; i<V; ++i)
			result[i] = new Edge();


		Arrays.sort(edge);

		subset subsets[] = new subset[V];
		for(i=0; i<V; ++i)
			subsets[i]=new subset();

		for (int v = 0; v < V; ++v)
		{
			subsets[v].parent = v;
			subsets[v].rank = 0;
		}

		i = 0; 

		while (e < V - 1)
		{
			Edge next_edge = new Edge();
			next_edge = edge[i++];

			int x = find(subsets, next_edge.origen);
			int y = find(subsets, next_edge.destino);

			if (x != y)
			{
				result[e++] = next_edge;
				Union(subsets, x, y);
			}
		}
		mst = result;
	}
	public void imprimirRta()
	{
		System.out.println(mst.length);
		for(int i=0; i<mst.length; i++)
		{
			System.out.println("Id inicial: "+ mst[i].origen);
			System.out.println("Id final: " + mst[i].destino);
			System.out.println("Costo total: " + mst[i].peso);
		}
	}


}
