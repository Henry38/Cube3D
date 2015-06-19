package application;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import math.Mat4;
import math.Point3D;
import math.Vec4;
import math.Vecteur3D;
import geometry.Cube;
import geometry.Damier;
import geometry.Light;
import geometry.Shape3D;
import geometry.Triangle;
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
		
//		Camera camera = new Camera(new Point3D(1, 1, 1), new Point3D(0, 0, 0), PROJECTION.PERSPECTIVE);
//		Shape3D cube = new Cube(null, 0, 0, 0);
//		
//		Triangle t = cube.getListTriangle().get(0);
//		Vecteur3D normal = t.getNormal();
//		
//		Mat4 modelMat = cube.modelMat();
//		Mat4 viewMat = camera.viewMat();
//		
//		System.out.println("Repere cube : " + normal);
//		System.out.println("Repere monde : " + modelMat.mult(new Vec4(normal)).toVecteur3D());
//		System.out.println("Repere camera : " + viewMat.mult(modelMat.mult(new Vec4(normal))).toVecteur3D());
		
		
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
		
		// Creation de la lumiere du monde
		Light light = new Light(new Point3D(10, 10, 10), new Vecteur3D(-1, -1, -1));
		light.setColor(new Color(200, 200, 200));
		
		// Creation des objets du monde
		Shape3D piece = stlReader.readFromSTL("sphere.stl");
		Shape3D teapot = objReader.readFromOBJ("teapot.obj");
		Cube cube1 = new Cube(null, 0, 0, 0);
		Damier damier = new Damier(null);

		piece.setTexture("./image/cube1.jpg");
		//teapot.setRenderingMode(Shape3D.WIREFRAME);
		teapot.setTexture("./image/environment_map.jpg");
		teapot.setRenderingMode(Shape3D.WIREFRAME);
		cube1.setTexture("./image/cube1.jpg");
		damier.setTexture("./image/cube1.jpg");
		
		// Transformation
		teapot.rotationOx(Math.PI/2);
//		teapot.rotationOy(Math.PI/2);
//		cube1.translation(0, 1, 0);
		
		// Creation du monde
		World3D world = new World3D();
//		world.addShape(piece);
		world.addShape(teapot);
//		world.addShape(cube1);
//		world.addShape(damier);
		world.setLight(light);
		
		Camera camera1 = new Camera(new Point3D(2, 2, 1), new Point3D(0, 0, 0), PROJECTION.PERSPECTIVE);
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
