package math;

public class Viewport {
	
	private int x, y, width, height;
	
	public Viewport() {
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
	}
	
	public Viewport(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
