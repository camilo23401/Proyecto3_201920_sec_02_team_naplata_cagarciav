package model.data_structures;

public interface IQueue<E> {

	public void enqueue(E elemento);

	public E dequeue();

	public int darTamanio();

	public boolean isEmpty();

	public E darPrimerElemento();



}
