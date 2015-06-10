package math;

public class Coord {
	
	private double coordX;
	private double coordY;
	
	public Coord() {
		this.coordX = 0.0;
		this.coordY = 0.0;
	}
	
	public Coord(double coordX, double coordY) {
		this.coordX = coordX;
		this.coordY = coordY;
	}
	
	public double getX() {
		return coordX;
	}
	
	public double getY() {
		return coordY;
	}
}
