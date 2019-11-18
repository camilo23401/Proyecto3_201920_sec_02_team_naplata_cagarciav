package model.data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack<E> implements IStack<E>, Iterable<E>{


	private NodoStack<E>ultimoAgregado;

	private int tamanio;

	/**
	 * Construye la lista vacía.
	 * <b>post: </b> Se ha inicializado el primer nodo en null
	 */
	public Stack(){
		ultimoAgregado=null;
		tamanio=0;
	}

	public Stack(NodoStack<E>primerAgregado){
		ultimoAgregado=primerAgregado;	
		tamanio++;
	}

	@Override
	public int darTamanio() {
		return tamanio;
	}

	@Override
	public void push(E elemento) {
		NodoStack<E>agregado=new NodoStack<E>(elemento);
		if(ultimoAgregado==null){
			ultimoAgregado=agregado;
			tamanio++;
		}
		else{
			NodoStack<E>actual=ultimoAgregado;
			agregado.cambiarAnterior(actual);
			ultimoAgregado=agregado;
			tamanio++;
		}

	}

	@Override
	public E pop() {
		NodoStack<E>actual=ultimoAgregado;
		if(ultimoAgregado.darAnterior()!=null){
			ultimoAgregado=actual.darAnterior();
			tamanio--;
		}
		else{
			tamanio--;
			ultimoAgregado=null;
		}
		return actual.darElemento();
	}

	@Override
	public boolean isEmpty() {
		if(ultimoAgregado==null){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public E darUltimoElemento() {
		return ultimoAgregado.darElemento();
	}

	public Iterator<E> iterator()  {
		return new ListIterator(ultimoAgregado);  
	}
	private class ListIterator implements Iterator<E> {
		private NodoStack<E> current;

		public ListIterator(NodoStack<E> first) {
			current = first;
		}

		public boolean hasNext()  { return current != null;                     }
		public void remove()      { throw new UnsupportedOperationException();  }

		public E next() {
			if (!hasNext()) throw new NoSuchElementException();
			E item = current.elemento;
			current = current.darAnterior(); 
			return item;
		}
	}
}
