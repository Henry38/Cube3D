package graphics;

import geometry.Shape3D;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import world3d.World3D;

public class WorldPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JList<String> list;
	private World3D world;
	
	private Shape3D selectedShape;
	private JCheckBox wireframe;
	
	/** Consturcteur */
	public WorldPanel(World3D world) {
		super();
		this.world = world;
		
		//setLayout(new GridLayout(3, 1));
		setLayout(new BorderLayout());
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
		panelList.add(new JLabel("Objets : "), BorderLayout.WEST);
		panelList.add(new JScrollPane(list), BorderLayout.CENTER);
		panelList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		selectedShape = null;
		wireframe = new JCheckBox("Mode wireframe", false);
		wireframe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedShape != null) {
					selectedShape.toggleRenderingMode();
					world.needRepaint();
				}
			}
		});
		
		JPanel panelFeatures = new JPanel();
		panelFeatures.setLayout(new GridLayout(3, 1));
		panelFeatures.add(wireframe);
		
		add(panelList, BorderLayout.NORTH);
		add(panelFeatures, BorderLayout.CENTER);
	}
	
	/** Classe privee */
	private class ListMouseListener extends MouseAdapter {
		
		public void mousePressed(MouseEvent e) {
			int index = list.locationToIndex(e.getPoint());
			Shape3D shape = world.getShape(index);
			selectedShape = world.getShape(index);
			wireframe.setSelected(shape.isWireframeMode());
		}
	}
}
