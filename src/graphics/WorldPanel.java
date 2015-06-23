package graphics;

import geometry.Shape3D;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import world3d.World3D;

public class WorldPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JComboBox list;
	private World3D world;

	/** Consturcteur */
	public WorldPanel(World3D world) {
		super();
		this.world = world;
		
		setName("World");
		
		ArrayList<Shape3D> arrayShape = world.getListShape(); 
		String[] elements = new String[arrayShape.size()];
		for (int i = 0; i < arrayShape.size(); i++) {
			elements[i] = arrayShape.get(i).getName();
		}
		
		list = new JComboBox<String>(elements);
		
		add(list);
	}
}
