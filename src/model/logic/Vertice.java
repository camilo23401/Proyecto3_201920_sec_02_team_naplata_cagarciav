package model.logic;

public class Vertice implements Comparable<Vertice>
{
	private int id;
	private double longitud;
	private double latitud;

	public Vertice(int pId, double pLongitud, double pLatitud)
	{
		id = pId;
		longitud = pLongitud;
		latitud = pLatitud;

	}
	public int darId()
	{
		return id;
	}
	public double darLongitud()
	{
		return longitud;
	}
	public double darLatitud()
	{
		return latitud;
	}

	@Override
	public int compareTo(Vertice o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
