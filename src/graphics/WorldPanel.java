package graphics;

import geometry.Shape3D;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import world3d.World3D;

public class WorldPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JList<String> list;
	private World3D world;

	/** Consturcteur */
	public WorldPanel(World3D world) {
		super();
		this.world = world;
		
		setLayout(new GridLayout(3, 1));
		setName("World");
		
		
		DefaultListModel<String> model = new DefaultListModel<String>();
	    list = new JList<String>(model);
	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    list.addMouseListener(new ListMouseListener());
		
	    ArrayList<Shape3D> arrayShape = world.getListShape(); 
		for (int i = 0; i < arrayShape.size(); i++) {
			model.addElement(arrayShape.get(i).getName());
		}
		
		JPanel panelList = new JPanel(new BorderLayout());
		panelList.add(BorderLayout.WEST, new JLabel("Objets : "));
		panelList.add(BorderLayout.CENTER, new JScrollPane(list));
		panelList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		add(panelList);
	}
	
	/** Classe privee */
	private class ListMouseListener extends MouseAdapter {
		
		public void mousePressed(MouseEvent e) {
			int index = list.locationToIndex(e.getPoint());
			Shape3D shape = world.getShape(index);
			System.out.println(shape.getName());
		}
	}
}
