package model.data_structures;




/**
 * Representa un nodo dentro de la lista. Este nodo almacena un elemento.
 *
 * @param <E> Tipo de elemento que se está almacenando dentro de los nodos.
 */
public class NodoStack<E> 
{

	/**
	 * Elemento almacenado en el nodo.
	 */

	protected E elemento;

	/**
	 * anterior nodo.
	 */
	
	protected NodoStack<E>anterior;
	/**
	 * Constructor del nodo.
	 * @param elemento El elemento que se almacenará en el nodo. elemento != null
	 */
	public NodoStack(E elemento)
	{
		this.elemento=elemento;
	
	}

	/**
	 * Método que cambia el anterior nodo.
	 * <b>post: </b> Se ha cambiado el siguiente nodo
	 * @param siguiente El nuevo siguiente nodo
	 */
	public void cambiarAnterior(NodoStack<E> anterior)
	{
		this.anterior=anterior;
		
	}

	/**
	 * Método que retorna el elemento almacenado en el nodo.
	 * @return El elemento almacenado en el nodo.
	 */
	public E darElemento()
	{
		return elemento;
	}

	/**
	 * Cambia el elemento almacenado en el nodo.
	 * @param elemento El nuevo elemento que se almacenará en el nodo.
	 */
	public void cambiarElemento(E elemento)
	{
		this.elemento = elemento;
	}
	/**
	 * Método que retorna el anterior nodo.
	 * @return Siguiente nodo
	 */
	public NodoStack<E> darAnterior()
	{
		return anterior;
	}

	

}
