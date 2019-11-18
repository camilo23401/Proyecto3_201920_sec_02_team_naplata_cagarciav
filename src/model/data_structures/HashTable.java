package model.data_structures;

import java.util.Iterator;

public interface HashTable <K,V>{

	void put(K llave, V valor);
	
	void putInSet(K llave, V valor);
	
	V get(K llave);
	
	Iterator<V> getSet(K llave);

	V delete(K llave);
	
	Iterator<V> deleteSet(K llave);
	
	
	Iterator<K> keys();
	

}