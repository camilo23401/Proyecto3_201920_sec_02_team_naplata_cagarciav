package model.data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

public class Queue<E>  implements IQueue<E>,Iterable<E> {


	private NodoQueue<E>ultimoNodo;

	private NodoQueue<E>primerNodo;

	private int tamanio;
	/**
	 * Construye la lista vacía.
	 * <b>post: </b> Se ha inicializado el primer nodo en null
	 */
	public Queue(){
		primerNodo=null;

	}

	public Queue(NodoQueue<E>nuevo){
		primerNodo=nuevo;
		ultimoNodo=nuevo;

	}


	@Override
	public void enqueue(E elemento) {
		NodoQueue<E>agregado=new NodoQueue<E>(elemento);
		if(primerNodo==null){
			primerNodo=agregado;
			ultimoNodo=agregado;
			tamanio++;
		}
		else{
			ultimoNodo.cambiarSiguiente(agregado);
			ultimoNodo=agregado;
			tamanio++;
		}


	}
	public E dequeue() {
		E eliminado=null;
		if(primerNodo.darSiguiente()==null){
			eliminado=primerNodo.darElemento();
			primerNodo=null;
			tamanio--;

		}
		else{
			eliminado=primerNodo.darElemento();
			NodoQueue<E>nuevo=primerNodo.darSiguiente();
			primerNodo=nuevo;
			tamanio--;



		}
		return eliminado;
	}

	@Override
	public int darTamanio() {
		return tamanio;
	}

	@Override
	public boolean isEmpty() {
		if(primerNodo==null){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public E darPrimerElemento() {
		return primerNodo.darElemento();
	}
	
	   public Iterator<E> iterator()  {
	        return new ListIterator(primerNodo);  
	    }

	    // an iterator, doesn't implement remove() since it's optional
	    private class ListIterator implements Iterator<E> {
	        private NodoQueue<E> current;

	        public ListIterator(NodoQueue<E> first) {
	            current = first;
	        }

	        public boolean hasNext()  { return current != null;                     }
	        public void remove()      { throw new UnsupportedOperationException();  }

	        public E next() {
	            if (!hasNext()) throw new NoSuchElementException();
	            E item= current.elemento;
	            current = current.siguiente; 
	            return item;
	        }
	    }


}
