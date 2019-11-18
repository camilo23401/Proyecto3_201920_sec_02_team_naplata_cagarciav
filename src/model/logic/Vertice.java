package model.logic;

public class Vertice implements Comparable<Vertice>
{
	private int id;
	private double longitud;
	private double latitud;
	private int movement_id;
	public Vertice(int pId, double pLongitud, double pLatitud, int pMovement_id)
	{
		id = pId;
		longitud = pLongitud;
		latitud = pLatitud;
		movement_id = pMovement_id;
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
	public int darMovementId()
	{
		return movement_id;
	}
	@Override
	public int compareTo(Vertice o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
