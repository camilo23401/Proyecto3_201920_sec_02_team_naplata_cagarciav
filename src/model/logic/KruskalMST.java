package model.logic;

import java.util.Arrays;

public class KruskalMST {
	public KruskalMST(int v, int e)  //Toca practicamente crear un grafo nuevo, no importa porque se usa el resultado del conectado más grande
	{
		V = v;
		E = e;
		edge = new Edge[E];
		for (int i=0; i<e; ++i)
			edge[i] = new Edge();
	}
	class Edge implements Comparable<Edge> //Clase de arcos interna para el algoritmo
	{
		int src, dest, weight;

		public int compareTo(Edge compareEdge)
		{
			return this.weight-compareEdge.weight;
		}
	};
	class subset
	{
		int parent; 
		int rank;
	};

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

			int x = find(subsets, next_edge.src);
			int y = find(subsets, next_edge.dest);

			if (x != y)
			{
				result[e++] = next_edge;
				Union(subsets, x, y);
			}
		}
	}

}
