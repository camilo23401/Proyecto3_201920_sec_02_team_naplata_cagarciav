package model.data_structures;

public class NodoArbol <K,V>
{
	private K llave;
	private V valor;
	private NodoArbol<K,V> derecha;
	private NodoArbol<K,V> izquierda;
	private boolean color;
	private short tamanio;
	
	public NodoArbol(K pLlave, V pValor, boolean pColor, short pTamanio)
	{
		this.llave = pLlave;
		this.valor = pValor;
		this.color = pColor;
		this.tamanio = pTamanio;
	}
	public K darLlave()
	{
		return llave;
	}
	public V darValor()
	{
		return valor;
	}
	public boolean darColor()
	{
		return color;
	}
	public short darTamanio()
	{
		return tamanio;
	}
	public void cambiarColor(boolean pColor)
	{
		color = pColor;
	}
	public NodoArbol<K, V> darNodoIzquierda()
	{
		return izquierda;
	}
	public NodoArbol<K,V> darNodoDerecha()
	{
		return derecha;
	}
	public void cambiarNodoIzquierda(NodoArbol<K, V> pNodoIzquierda)
	{
		izquierda = pNodoIzquierda;
	}
	public void cambiarNodoDerecha(NodoArbol<K, V> pNodoDerecha)
	{
		derecha = pNodoDerecha;
	}
	public void ponerValor(V pValor)
	{
		valor = pValor;
	}
	public void cambiarTamanio(Short pTamanio)
	{
		tamanio = pTamanio;
	}
}