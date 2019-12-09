package model.logic;

import java.util.ArrayList;

import model.data_structures.Arco;
public class Vertice <K extends Comparable <K>,V> implements Comparable<Vertice<K,V>>{

	public K key;
	public V val;
	public boolean marcado;
	public int componentesConectadas;
	public ArrayList<Arco <K>> arcos;

	public Vertice(K pLlave, V pValor)
	{
		key = pLlave;
		val = pValor;
		componentesConectadas = 0;
		arcos = new ArrayList<Arco<K>>();
		marcado = false;
	}

	public void validar()
	{
		marcado = true;
	}

	@Override
	public int compareTo(Vertice<K, V> o) {
		// TODO Auto-generated method stub
		return -1;
	}
}