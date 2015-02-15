package application;

import java.awt.AWTException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import math.Point3D;
import geometry.Cube;
import graphics.Observer;
import world3d.World3D;

public class Application3D extends Thread {
	
	private JFrame frame;
	private Observer observer;
	private boolean shouldRun = true;
	
	/** Constructeur */
	public Application3D(JFrame frame, World3D world) {
		this.frame = frame;
		this.frame.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent arg0) {}
			
			public void keyReleased(KeyEvent arg0) {}
			
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
					shouldRun = false;
				}
			}
		});
		this.observer = new Observer(world, new Point3D(2, 2, 2), new Point3D(.5, .5, .5), 640, 480);
		this.shouldRun = true;
	}
	
	public void run() {
		
		while (shouldRun) {
			observer.repaint();
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		frame.dispose();
		System.exit(0);
	}
	
	public static void main(String[] args) throws AWTException {
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		int width = (int) tk.getScreenSize().getWidth();
		int height = (int) tk.getScreenSize().getHeight();
		
		JFrame frame = new JFrame("3D Application");
		JPanel panneau = new JPanel();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLocation(width / 6, height / 6);
		frame.setResizable(false);
		
		World3D world = new World3D();
		world.addShape(new Cube(0, 0, 0));
		//world.addShape(new Cube(1, 1, 1));
		//world.addShape(new Cube(2, 2, 0));
		
		Application3D app = new Application3D(frame, world);
		app.start();
		
		panneau.add(app.observer);
		frame.add(panneau);
		frame.pack();
		frame.setVisible(true);
	}
	
}
