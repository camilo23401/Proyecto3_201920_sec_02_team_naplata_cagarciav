package model.logic;

public class Interseccion implements Comparable<Interseccion> {
double latin;
double lonin;
double latin1;
double lonin2;
public double getLatin() {
	return latin;
}
public void setLatin(double latin) {
	this.latin = latin;
}
public double getLonin() {
	return lonin;
}
public void setLonin(double lonin) {
	this.lonin = lonin;
}
public double getLatin1() {
	return latin1;
}
public void setLatin1(double latin1) {
	this.latin1 = latin1;
}
public double getLonin2() {
	return lonin2;
}
public void setLonin2(double lonin2) {
	this.lonin2 = lonin2;
}
public Interseccion(double latin, double lonin, double latin1, double lonin2) {
	super();
	this.latin = latin;
	this.lonin = lonin;
	this.latin1 = latin1;
	this.lonin2 = lonin2;
}
@Override
public int compareTo(Interseccion o) {
	// TODO Auto-generated method stub
	return 0;
}


	
	
}
