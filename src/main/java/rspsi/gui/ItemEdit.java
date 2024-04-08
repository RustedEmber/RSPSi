package rspsi.gui;

import rspsi.Gui;
import rspsi.Main;
import rspsi.io.Archive;
import rspsi.io.configloaders.ItemConfig;
import rspsi.io.configloaders.beans.ItemBean;
import com.l2fprod.common.beans.editor.ComboBoxPropertyEditor;
import com.l2fprod.common.propertysheet.Property;
import com.l2fprod.common.propertysheet.PropertyEditorRegistry;
import com.l2fprod.common.propertysheet.PropertySheetPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * @author tom
 */
public class ItemEdit extends JInternalFrame {
	private JList itemsList;
	private PropertySheetPanel properties;
	private JPanel main;
	private JButton saveConfigButton;
	private JButton addItemButton;
	private JButton removeItemButton;
	private Archive configArchive;

	public ItemEdit() {
		try {
			configArchive = new Archive(Gui.logic.getCurrentCache().getIndice(0).getFile(2));
			ItemConfig.unpackConfig(configArchive.getFile("obj.dat"), configArchive.getFile("obj.idx"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(Gui.logic.getSwingComponent(), "An error occurred whilst loading archive:\n" + e);
			e.printStackTrace();
		}
		add(main);
		pack();
		setVisible(true);
		rebuildItemsList();
		try {
			properties.setBeanInfo(Introspector.getBeanInfo(ItemBean.class));
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		PropertyEditorRegistry reg = (PropertyEditorRegistry) properties.getEditorFactory();
		for (Property p : properties.getProperties()) {
			if (p.getName().endsWith("ModelColors") || p.getName().equals("groundActions")
					|| p.getName().equals("actions") || p.getName().startsWith("stack")) {
				ComboBoxPropertyEditor e = new ComboBoxPropertyEditor();
				((JComboBox)e.getCustomEditor()).setEditable(true);
				//reg.registerEditor(p, e);
			}
		}
		properties.addPropertySheetChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
				if (itemsList.getSelectedIndex() != -1) {
					Property p = (Property) propertyChangeEvent.getSource();
					p.writeToObject(ItemConfig.getItem(itemsList.getSelectedIndex()));
				}
			}
		});
		itemsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				if (itemsList.getSelectedIndex() != -1) {
					properties.readFromObject(ItemConfig.getItem(itemsList.getSelectedIndex()));
				}
			}
		});
		saveConfigButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					byte[][] repacked = ItemConfig.recompile();
					configArchive.updateFile(configArchive.indexOf("obj.idx"), repacked[0]);
					configArchive.updateFile(configArchive.indexOf("obj.dat"), repacked[1]);
					byte[] newJagData = configArchive.recompile();
					Gui.logic.addOrEditFile(0, 2, newJagData);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(Gui.logic.getSwingComponent(), "An error occurred whilst repacking archive:\n" + e);
					e.printStackTrace();
				}
			}
		});
		addItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				ItemBean newItem = new ItemBean();
				newItem.setName("New Item");
				ItemConfig.addItem(newItem);
				rebuildItemsList();
				itemsList.setSelectedIndex(ItemConfig.countItems()-1);
			}
		});
		removeItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				int i;
				if ((i = itemsList.getSelectedIndex()) != -1) {
					ItemConfig.removeItem(i);
					rebuildItemsList();
					if (i == 0) {
						itemsList.setSelectedIndex(i-1);
					} else {
						itemsList.setSelectedIndex(i-1);
					}
				}
			}
		});
	}

	private void rebuildItemsList() {
		String[] values = new String[ItemConfig.countItems()];
		for (int i = 0; i < values.length; i++) {
			values[i] = String.valueOf(i);
		}
		itemsList.setListData(values);
	}
}
