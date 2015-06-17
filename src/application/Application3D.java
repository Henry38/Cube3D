package application;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import math.Point3D;
import math.Vecteur3D;
import geometry.Cube;
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
			shape.rotationOz(Math.PI / 256);
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
			public void keyTyped(KeyEvent ev) {}
			
			public void keyReleased(KeyEvent ev) {}
			
			public void keyPressed(KeyEvent ev) {
				if (ev.getKeyCode() == KeyEvent.VK_ESCAPE) {
					frame.dispose();
					System.exit(0);
				}
			}
		});
		frame.setLocation(width / 6, height / 6);
		frame.setResizable(false);
		
		StlReader stlReader = new StlReader();
		ObjReader objReader = new ObjReader();
		
		// Creation des objets du monde
		Light light = new Light(new Point3D(10, 10, 10), new Vecteur3D(-1, -1, -1));
		light.setColor(new Color(200, 200, 200));
		Shape3D piece = stlReader.getShape("reine.stl");
		Shape3D teapot = objReader.getShape("teapot.obj");
		Cube cube1 = new Cube(null, 0, 0, 0);
		cube1.setTexture("./image/texture.png");
		teapot.setTexture("./image/texture.png");
		
		// Transformation
//		teapot.rotationOx(Math.PI/2);
//		teapot.rotationOy(Math.PI/2);
		cube1.translation(0, 2, 0);
		//cube1.rotationOz(Math.PI/4);
		
		// Creation du monde
		World3D world = new World3D();
		//world.addShape(piece);
		world.addShape(teapot);
		world.addShape(cube1);
		world.setLight(light);
		
		Camera camera1 = new Camera(new Point3D(2, 2, 2), new Point3D(0, 0, 0), PROJECTION.PERSPECTIVE);
		//Camera camera1 = new Camera(new Point3D(25, 25, 40), new Point3D(0, 0, 20), PROJECTION.PERSPECTIVE);
		//Camera camera2 = new Camera(new Point3D(-2, -2, 2), new Point3D(0.5, 0.5, 0.5), PROJECTION.ORTHOGRAPHIC);
		
		Observer observer1 = new Observer(world, camera1, 640, 480);
		//Observer observer2 = new Observer(world, camera2, 640, 480);
		
		JPanel panneau = new JPanel();
		panneau.add(observer1);
		//panneau.add(observer2);
		
		frame.add(panneau);
		frame.pack();
		frame.setVisible(true);
		
//		Application3D app = new Application3D(frame, piece);
//		app.start();
	}
}
