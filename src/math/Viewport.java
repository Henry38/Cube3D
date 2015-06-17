package math;

public class Viewport {
	
	private int x, y, width, height;
	private int near, far;
	
	/** Constructeur */
	public Viewport() {
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
		this.near = -1;
		this.far = 1;
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
	
	public Mat4 screenMat() {
		int w = getWidth();
		int h = getHeight();
		int n = near;
		int f = far;
		Mat4 m = new Mat4(new double[][] {
				{w/2,   0,       0,   x+w/2},
				{  0, h/2,       0,   y+h/2},
				{  0,   0, (f-n)/2, (f+n)/2},
				{  0,   0,       0,       1}
		});
		return m;
	}
}
