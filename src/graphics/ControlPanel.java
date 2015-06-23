package graphics;

import java.awt.Dimension;

import javax.swing.JTabbedPane;

import world3d.Camera;
import world3d.World3D;

public class ControlPanel extends JTabbedPane {
	
	private static final long serialVersionUID = 1L;
	
	/** Constructeur */
	public ControlPanel(World3D world, Camera camera) {
		super();
		setPreferredSize(new Dimension(192, 480));
		
		WorldPanel worldPanel = new WorldPanel(world);
		
		CameraPanel cameraPanel = new CameraPanel();
		camera.addObserver(cameraPanel);
		
		add(worldPanel);
		add(cameraPanel);
	}
}
