package rspsi.gui;

import java.awt.Dimension;
import java.beans.*;

import javax.swing.JScrollPane;
import javax.swing.tree.TreePath;

import rspsi.Main;
import rspsi.RSInterfaceConstants;
import rspsi.client.Interface;

import com.l2fprod.common.propertysheet.*;

public class PropSheet extends JScrollPane implements PropertyChangeListener {

	private Workspace workspace;
	private PropertySheetPanel propsheet;

	public PropSheet(final Workspace workspace) {
		super();
		this.workspace = workspace;
		try {
			propsheet = new PropertySheetPanel();
			propsheet.setPreferredSize(new Dimension(260, 245));
			propsheet.addPropertySheetChangeListener(this);
			setPreferredSize(new Dimension(265, 248));
			setViewportView(propsheet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void resetSheet() {
		propsheet = new PropertySheetPanel();
		propsheet.addPropertySheetChangeListener(this);
		propsheet.setPreferredSize(new Dimension(260, 245));
		setViewportView(propsheet);
	}

    public void update() {
		try {
			TreePath[] paths = Main.getWorkspace().getTree().getTree().getSelectionPaths();
	        if (paths != null && paths.length > 1) {
	        	setVisible(false);
	        	return;
	        }
	        if (Main.editing != null && Main.editing[0] != null) {
        		setVisible(true);
		        BeanInfo beanInfo = Introspector.getBeanInfo(Interface.class);
		        propsheet.setBeanInfo(beanInfo);
		
				Property[] properties = propsheet.getProperties();
				for (Property property : properties)
						property.readFromObject(Main.editing[0]);
		
				propsheet.setProperties(properties);
				filterEditableProperties(properties);
				System.out.println("propsheet update: " + Main.editingID);
	        }

		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void filterEditableProperties(Property[] properties) {
    	int type = Main.editing[0].type;
    	String[][] values = RSInterfaceConstants.editableValues;
    	for (Property property : properties) {
        	String name = property.getName();
    		boolean editable = false;

    		for (int abc = 0; abc < values[0].length; abc++)
    			if (name.equalsIgnoreCase(values[0][abc]))
    				editable = true;

    		for (int abc = 0; abc < values[type + 1].length; abc++)
    			if (name.equalsIgnoreCase(values[type + 1][abc]))
    				editable = true;

    		if (!editable)
    			propsheet.removeProperty(property);

    	}
    }

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Property property = (Property) evt.getSource();
		try {
			if (property.getName().equals("alpha"))
				/*if (Integer.parseInt("" + property.getValue()) > 127)
					property.setValue(127);
				else */if (Integer.parseInt("" + property.getValue()) < -128)
					property.setValue(-128);

			if (property.getName().contains("Rotation")) {
				if (Integer.parseInt("" + property.getValue()) < 0)
					property.setValue(0);
				if (Integer.parseInt("" + property.getValue()) > 2047)
					property.setValue(2047);
//				int rot = (Integer) property.getValue() * 2048 / 360;
//				System.out.println(rot);
			}

			property.writeToObject(Interface.interfaceCache[Main.editingID[0]]);
			workspace.updateInterfaces();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public JScrollPane getPropPane() {
		return this;
    }

    public PropertySheet getPropSheet() {
		return propsheet;
    }

}
