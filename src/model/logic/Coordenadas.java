package model.logic;

public class Coordenadas implements Comparable <Coordenadas>
{
	private double latitud;
	private double longitud;
	private int movement_id;

	public Coordenadas(double pLatitud,double pLongitud, int pId) {
		latitud=pLatitud;
		longitud=pLongitud;
		movement_id = pId;
	}
	public void cambiarLatitud(double latitud) {
		this.latitud = latitud;
	}
	public double darLongitud() {
		return longitud;
	}
	public double darLatitud() {
		return latitud;
	}
	public int darId()
	{
		return movement_id;
	}
	public void cambiarLongitud(double longitud) {
		this.longitud = longitud;
	}

	@Override
	public int compareTo(Coordenadas arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}

