package math;

public class Coord {
	
	private double coordU;
	private double coordV;
	
	/** Constructeur */
	public Coord() {
		this.coordU = 0.0;
		this.coordV = 0.0;
	}
	
	/** Constructeur */
	public Coord(double coordX, double coordY) {
		this.coordU = coordX;
		this.coordV = coordY;
	}
	
	/** Retourne la composante u */
	public double getU() {
		return coordU;
	}
	
	/** Retourne la composante v */
	public double getV() {
		return coordV;
	}
}
