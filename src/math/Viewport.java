package math;

public class Viewport {
	
	private int x, y, width, height;
	
	/** Constructeur */
	public Viewport() {
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
	}
	
	/** Constructeur */
	public Viewport(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/** Retourne la coordonnee x du viewport */
	public int getX() {
		return x;
	}
	
	/** Retourne la coordonnee y du viewport */
	public int getY() {
		return y;
	}
	
	/** Retourne la largeur du viewport */
	public int getWidth() {
		return width;
	}
	
	/** Retourne la hauteur du viewport */
	public int getHeight() {
		return height;
	}
}
