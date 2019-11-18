package model.data_structures;

public class NodoHash<K,V> implements Comparable <NodoHash<K,V>> {
	private K llave; 
	private V valor; 

	private NodoHash<K, V> Siguiente; 
	public K getLlave() {
		return llave;
	}

	public void setLlave(K llave) {
		this.llave = llave;
	}

	public V getValor() {
		return valor;
	}

	public void setValor(V valor) {
		this.valor = valor;
	}

	public NodoHash<K, V> getSiguiente() {
		return Siguiente;
	}

	public void setSiguiente(NodoHash<K, V> siguiente) {
		Siguiente = siguiente;
	}



	public NodoHash(K key, V value)
	{ 
		this.llave = key; 
		this.valor = value; 
	}

	public int compareTo(NodoHash<K, V> o) {
		if(this.llave.equals(o.llave)) {
			return 0;
		}
		else {
			return 1;
		}
	} 

	
}
