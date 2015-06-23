package application;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import math.Point3D;
import math.Vecteur3D;
import geometry.Cube;
import geometry.Damier;
import geometry.Light;
import geometry.Shape3D;
import graphics.MainWindow;
import world3d.Camera;
import world3d.Camera.TYPE;
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
			shape.rotationOz(Math.PI / 256);
			frame.repaint();
		}
	}
	
	public static void main(String[] args) throws AWTException {
		
		StlReader stlReader = new StlReader();
		ObjReader objReader = new ObjReader();
		
		// Creation de la lumiere du monde
		Light light = new Light(new Point3D(10, 10, 10), new Vecteur3D(-1, -1, -1));
		light.setColor(new Color(200, 200, 200));
		
		// Creation des objets du monde
		Shape3D piece = stlReader.readFromSTL("sphere.stl");
		Shape3D teapot = objReader.readFromOBJ("teapot.obj");
		Shape3D cube1 = new Cube(null, 0, 0, 0);
		Shape3D damier = new Damier(null);
		
		piece.setTexture("./image/cube1.jpg");
		teapot.setRenderingMode(Shape3D.WIREFRAME);
		teapot.setName("Teapot");
		cube1.setTexture("./image/cube1.jpg");
		cube1.setName("Cube");
		damier.setTexture("./image/cube1.jpg");
		
		// Transformation
		teapot.rotationOx(Math.PI/2);
		teapot.translation(0, 1, 0);
		teapot.rotationOy(Math.PI/2);
		cube1.translation(0.1, 0.1, 0);
		
		// Creation du monde
		World3D world = new World3D();
//		world.addShape(piece);
		world.addShape(teapot);
		world.addShape(cube1);
//		world.addShape(damier);
		world.setLight(light);
		
//		Miroir m = new Miroir(null, 1, 2, 0, world);
//		m.rotationOz(Math.PI/2);
//		world.addShape(m);
		
		Camera camera = new Camera(new Point3D(2, 2, 1), new Point3D(0, 0, 0), TYPE.PERSPECTIVE);
		//Observer observer = new Observer(world, camera, 640, 480);
		
		final JFrame fen = new MainWindow(world, camera, 640, 480);
		fen.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent ev) {}
			
			public void keyReleased(KeyEvent ev) {}
			
			public void keyPressed(KeyEvent ev) {
				if (ev.getKeyCode() == KeyEvent.VK_ESCAPE) {
					fen.dispose();
					System.exit(0);
				}
			}
		});
		fen.setVisible(true);
		
//		Camera camera1 = new Camera(new Point3D(2, 2, 1), new Point3D(0, 0, 0), TYPE.PERSPECTIVE);
//		//Camera camera1 = new Camera(new Point3D(25, 25, 40), new Point3D(0, 0, 20), PROJECTION.PERSPECTIVE);
//		//Camera camera2 = new Camera(new Point3D(-2, -2, 2), new Point3D(0.5, 0.5, 0.5), PROJECTION.ORTHOGRAPHIC);
//		
//		Observer observer1 = new Observer(world, camera1, 640, 480);
//		//Observer observer2 = new Observer(world, camera2, 640, 480);
//		
//		JPanel panneau = new JPanel();
//		panneau.add(observer1);
//		//panneau.add(observer2);
//		
//		frame.add(panneau);
//		frame.pack();
//		frame.setVisible(true);
		
//		Application3D app = new Application3D(frame, piece);
//		app.start();
	}
}
