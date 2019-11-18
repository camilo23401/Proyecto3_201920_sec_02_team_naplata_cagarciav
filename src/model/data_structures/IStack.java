package model.data_structures;

public interface IStack<E> {

	/**
	 * Retornar el numero de elementos presentes en el arreglo
	 * @return tamanio arreglo
	 */
	int darTamanio( );
	/**
	 * Inserta un nuevo elemento en el tope de la pila (elemento más reciente)
	 */
	public void push(E elemento);
	/**
	 * Saca el elemento mas reciente y lo retorna
	 * @return elemento sacado
	 */
	public E pop();
	/**
	 * Indica si la lista esta vacia
	 * @return true si esta vacia , false en caso contrario
	 */
	public boolean isEmpty();
	/**
	 * Retorna el ultimo elemento de la lista
	 * @return E ulitmo momento de la lsita
	 */
	public E darUltimoElemento();
	


}
