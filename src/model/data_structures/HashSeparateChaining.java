package model.data_structures;

import java.util.Iterator;

public class HashSeparateChaining<K extends Comparable<K>,V> implements HashTable<K,V>{

	private int tamanio;

	private int capacidad;

	private ArregloDinamico<NodoHash<K,V>>listaNodos;

	public int size() {
		return tamanio;
	} 
	public HashSeparateChaining(int n) {
		this.capacidad = n;
		listaNodos=new ArregloDinamico<NodoHash<K,V>>(n);
		tamanio=0;
	} 

	public void put(K llave, V valor) {
		NodoHash<K,V>agregado=new NodoHash<K,V>(llave,valor);
		if(tamanio>=1&&listaNodos.contains(hashCode(agregado.getLlave()))) {
			NodoHash<K,V>cambiar=listaNodos.darElementoPos(hashCode(agregado.getLlave()));
			cambiar.setValor(valor);
		}
		else {
			tamanio++;
			int pos = hashCode(agregado.getLlave());
			listaNodos.agregarPos(agregado, pos);
			if ((1.0*tamanio)/capacidad >= 0.5) 
			{ 
				ArregloDinamico<NodoHash<K, V>> temp = listaNodos; 
				capacidad = 2 * capacidad; 
				listaNodos = new ArregloDinamico<>(capacidad);         
				tamanio = 0; 
				for (int i = 0; i < capacidad; i++) 
					listaNodos.agregarPos(null,i); 

				for(int i=0;i<temp.darCapacidad();i++) {
					NodoHash<K,V>act=temp.darElementoPos(i);
					if(act!=null) {
						put(act.getLlave(),act.getValor());
					}
				}
			} 
		}

	}



	public void putInSet(K llave, V valor) {
		NodoHash<K,V>agregado=new NodoHash<K,V>(llave,valor);
		if(tamanio>0&&listaNodos.contains(hashCode(llave))) {
			NodoHash<K,V>cambiar=listaNodos.darElementoPos(hashCode(llave));
			while(cambiar.getSiguiente()!=null){
				cambiar=cambiar.getSiguiente();
			}
			cambiar.setSiguiente(agregado);
			tamanio++;
		}
		else {
			tamanio++;
			int pos = hashCode(agregado.getLlave());
			listaNodos.agregarPos(agregado, pos);
			if ((1.0*tamanio)/capacidad >= 0.5) 
			{ 
				ArregloDinamico<NodoHash<K, V>> temp = listaNodos; 
				capacidad = 2 * capacidad; 
				listaNodos = new ArregloDinamico<>(capacidad);  
				tamanio=0;
				for(int i=0;i<temp.darCapacidad();i++) {
					NodoHash<K,V>actual=temp.darElementoPos(i);				
					while(actual!=null) {
						putInSet(actual.getLlave(),actual.getValor());
						actual=actual.getSiguiente();
					}

				} 
			}
		}

	}

	@Override
	public V get(K llave) {
		boolean encontrado=false;
		int pos=hashCode(llave);
		V buscado=null;
		NodoHash<K,V>actual=listaNodos.darElementoPos(pos);	
		if(actual!=null&&actual.getLlave().compareTo(llave)==0&&!encontrado) {
			encontrado=true;
			buscado=actual.getValor();
		}
		return buscado;
	}

	public K getKey(K llave) {
		boolean encontrado=false;
		int pos=hashCode(llave);
		K buscado=null;
		NodoHash<K,V>actual=listaNodos.darElementoPos(pos);	
		if(actual!=null&&actual.getLlave().compareTo(llave)==0&&!encontrado) {
			encontrado=true;
			buscado=actual.getLlave();
		}
		return buscado;
	}





	public V getPos(int pos) {
		V retorno=null;
		if(listaNodos.darElementoPos(pos)!=null) {
			retorno= listaNodos.darElementoPos(pos).getValor();
		}
		return retorno;
	}
	public K getPosKey(int pos) {
		K retorno=null;
		if(listaNodos.darElementoPos(pos)!=null) {
			retorno= listaNodos.darElementoPos(pos).getLlave();
		}
		return retorno;
	}


	@Override
	public Iterator<V> getSet(K llave) {
		int pos=hashCode(llave);
		V buscado=null;
		Stack<V>dinami=new Stack<V>();
		NodoHash<K,V>actual=listaNodos.darElementoPos(pos);
		if(actual!=null&&actual.getLlave().compareTo(llave)==0) {
			while(actual!=null) {
				buscado=actual.getValor();
				dinami.push(buscado);
				actual=actual.getSiguiente();
			}
		}
		return dinami.iterator();
	}
	public int getSetSize(K llave) {
		Iterator<V>set=this.getSet(llave);
		int i = 0;
		while(set.hasNext()) {
			i++;
			set.next();
		}
		return i;
	}

	@Override
	public V delete(K llave) {
		boolean encontrado=false;
		V eliminado=null;
		for (int i = 0; i < listaNodos.darCapacidad()&&!encontrado; i++) {
			NodoHash<K,V>actual=listaNodos.darElementoPos(i);
			if(actual!=null&&actual.getLlave().compareTo(llave)==0) {
				encontrado=true;
				eliminado=actual.getValor();
				listaNodos.eliminar(actual);
				tamanio--;
			}
		}
		return eliminado;
	}

	@Override
	public Iterator<V> deleteSet(K llave) {
		boolean encontrado=false;
		V eliminado=null;
		Stack<V>dinami=new Stack<V>();
		for (int i = 0; i < listaNodos.darCapacidad()&&!encontrado; i++) {
			NodoHash<K,V>actual=listaNodos.darElementoPos(i);
			if(actual!=null&&actual.getLlave().compareTo(llave)==0) {
				encontrado=true;
				while(actual.getSiguiente()!=null) {
					eliminado=actual.getValor();
					dinami.push(eliminado);;
					actual=actual.getSiguiente();
					tamanio--;
				}
				dinami.push(actual.getValor());;;
				listaNodos.eliminar(actual);
				tamanio--;
			}
		}
		return dinami.iterator();
	}

	@Override
	public Iterator<K> keys() {;
	Stack<K>dinami=new Stack<K>();
	for (int i = 0; i < listaNodos.darCapacidad(); i++) {
		NodoHash<K,V>actual=listaNodos.darElementoPos(i);
		if(actual!=null)
			dinami.push(actual.getLlave());;
	}
	return dinami.iterator();
	}

	public void setValue(K llave, V value) {
		int pos=hashCode(llave);
		NodoHash<K,V>actual=listaNodos.darElementoPos(pos);	
		actual.setValor(value);
	}


	public int hashCode(K param) {
		int hashCode = param.hashCode(); 
		int index = (hashCode& 0x7fffffff) % capacidad; 
		return index; 
	}


	public int darCapacidad() {
		return capacidad;
	}
	public int numDuplas() {
		return listaNodos.darTamano();
	}



}
