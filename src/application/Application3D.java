package application;

import java.awt.AWTException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import math.Point3D;
import math.Vecteur3D;
import geometry.Cube;
import geometry.Damier;
import geometry.Light;
import geometry.Shape3D;
import graphics.Observer;
import world3d.Camera;
import world3d.Camera.PROJECTION;
import world3d.World3D;

public class Application3D extends Thread {
	
	private JFrame frame;
	private Shape3D shape;
	
	public Application3D(JFrame frame, Shape3D shape) {
		this.frame = frame;
		this.shape = shape;
	}
	
	public void run() {
		while (true) {
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			shape.rotationOx(Math.PI / 64);
			frame.repaint();
		}
	}
	
	public static void main(String[] args) throws AWTException {

		Toolkit tk = Toolkit.getDefaultToolkit();
		int width = (int) tk.getScreenSize().getWidth();
		int height = (int) tk.getScreenSize().getHeight();
		
		JFrame frame = new JFrame("3D Application");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent arg0) {}
			
			public void keyReleased(KeyEvent arg0) {}
			
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
					frame.dispose();
					System.exit(0);
				}
			}
		});
		frame.setLocation(width / 6, height / 6);
		frame.setResizable(false);
		
		Light light = new Light(new Point3D(10, 10, 10), new Vecteur3D(-1, -1, -1));
		
		Cube cube1 = new Cube(null, 0, 0, 0);
		Cube cube2 = new Cube(null, -1.1, -1.1, 0);
		
		STLReader read = new STLReader();
		Shape3D pion = read.getShape();
		//pion.rotationOx(Math.PI/4);
		
		World3D world = new World3D();
		world.addShape(pion);
		//world.addShape(new Damier(null));
//		world.addShape(cube1);
//		world.addShape(cube2);
		world.setLight(light);
		
		//Camera camera1 = new Camera(new Point3D(10, 10, 10), new Point3D(0, 0, 5), PROJECTION.PERSPECTIVE);
		Camera camera1 = new Camera(new Point3D(2, 2, 2), new Point3D(.5, .5, .5), PROJECTION.PERSPECTIVE);
		//Camera camera2 = new Camera(new Point3D(-2, -2, 2), new Point3D(0.5, 0.5, 0.5), PROJECTION.ORTHOGRAPHIC);
		
		Observer observer1 = new Observer(world, camera1, 1280, 768);
		//Observer observer2 = new Observer(world, camera2, 640, 480);
		
		JPanel panneau = new JPanel();
		panneau.add(observer1);
		//panneau.add(observer2);
		
		frame.add(panneau);
		frame.pack();
		frame.setVisible(true);
		
//		Application3D app = new Application3D(frame, cube);
//		app.start();
	}
}
