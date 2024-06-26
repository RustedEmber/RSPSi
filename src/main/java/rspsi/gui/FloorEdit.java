package rspsi.gui;

import rspsi.Gui;
import rspsi.Main;
import rspsi.gui.propertyeditors.SpinnerCellEditor;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import rspsi.io.Archive;
import rspsi.io.ImageArchive;
import rspsi.io.configloaders.FloorConfig;
import rspsi.io.util.exceptions.ConfigException;
import com.l2fprod.common.propertysheet.*;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * @author Tom
 */
public class FloorEdit extends JInternalFrame {
	private JList floorsList;
	private JButton saveConfigButton;
	private JPanel texPreview;
	private JPanel main;
	private PropertySheetPanel properties;
	private Archive jagArchive;
	private ImageArchive textures;
	private boolean hasEdited = false;
	private String title;
	private static FloorEdit instance;

	public FloorEdit(Archive textureSet) {
		instance = this;
		add(main);
		pack();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				if (hasEdited) {
					int response = JOptionPane.showConfirmDialog(Gui.logic.getSwingComponent(), "The floor configuration has been modified.\n" +
							"Are you sure you wish to exit without saving changes?", "Exit?", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.YES_OPTION) {
						dispose();
					}
				} else {
					dispose();
				}
			}
		});
		textures = new ImageArchive(textureSet);
		try {
			this.jagArchive = new Archive(Gui.logic.getCurrentCache().getIndice(0).getFile(2));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(Gui.logic.getSwingComponent(), "An error occurred whilst loading archive:\n" + e);
			e.printStackTrace();
		}
		try {
			FloorConfig.loadData(jagArchive.getFile("flo.dat"));
		} catch (ConfigException e) {
			JOptionPane.showMessageDialog(Gui.logic.getSwingComponent(), "An error occurred whilst unpacking configloaders:\n" + e);
			e.printStackTrace();
		}
		updateFloorList();

		floorsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				int i = floorsList.getSelectedIndex();
				if (i != -1) {
					FloorConfig currentFloorConfig = FloorConfig.getFloor(i);
					properties.readFromObject(currentFloorConfig);
					properties.setEnabled(true);
				} else {
					properties.setEnabled(false);
				}
				updatePreviews();
			}
		});
		saveConfigButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					byte[] newFloorData = FloorConfig.repack();
					jagArchive.updateFile(jagArchive.indexOf("flo.dat"), newFloorData);
					byte[] newJagData = jagArchive.recompile();
					Gui.logic.addOrEditFile(0, 2, newJagData);
					hasEdited = false;
					setTitle(title);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(Gui.logic.getSwingComponent(), "Error repacking floor configloaders:\n" + e);
					e.printStackTrace();
				}
			}
		});
	}

	private void updateFloorList() {
		String[] values = new String[FloorConfig.getFloorCount()];
		for (int i = 0; i < FloorConfig.getFloorCount(); i++) {
			values[i] = FloorConfig.getFloor(i).getFloorName();
		}
		floorsList.setListData(values);
	}

	private void updatePreviews() {
		int i = floorsList.getSelectedIndex();
		int texture = -1;
		if (i != -1) {
			FloorConfig currentFloorConfig = FloorConfig.getFloor(i);
			texture = currentFloorConfig.getTexture();
		}
		Graphics g = texPreview.getGraphics();
		if (texture != -1) {
			g.drawImage(textures.getImage(texture).getImage(0), 1, 1, texPreview.getWidth() - 2, texPreview.getHeight() - 2, this);
		} else {
			g.clearRect(1, 1, texPreview.getWidth() - 2, texPreview.getHeight() - 2);
		}
	}

	{
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
		$$$setupUI$$$();
	}

	/**
	 * Method generated by IntelliJ IDEA GUI Designer
	 * >>> IMPORTANT!! <<<
	 * DO NOT edit this method OR call it in your code!
	 *
	 * @noinspection ALL
	 */
	private void $$$setupUI$$$() {
		final JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
		final JScrollPane scrollPane1 = new JScrollPane();
		panel1.add(scrollPane1, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(125, 400), new Dimension(147, 128), new Dimension(150, 500), 0, false));
		floorsList = new JList();
		scrollPane1.setViewportView(floorsList);
		final JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));
		panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		panel2.setBorder(BorderFactory.createTitledBorder("Operations"));
		final JLabel label1 = new JLabel();
		label1.setText("Colour:");
		label1.setDisplayedMnemonic('C');
		label1.setDisplayedMnemonicIndex(0);
		panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final Spacer spacer1 = new Spacer();
		panel2.add(spacer1, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		final JLabel label2 = new JLabel();
		label2.setText("Map colour:");
		label2.setDisplayedMnemonic('M');
		label2.setDisplayedMnemonicIndex(0);
		panel2.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JLabel label3 = new JLabel();
		label3.setText("Texture ID:");
		label3.setDisplayedMnemonic('T');
		label3.setDisplayedMnemonicIndex(0);
		panel2.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		texPreview = new JPanel();
		texPreview.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		panel2.add(texPreview, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(25, 25), null, new Dimension(25, 25), 0, false));
		texPreview.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null));
		saveConfigButton = new JButton();
		saveConfigButton.setText("Save Config");
		saveConfigButton.setMnemonic('S');
		saveConfigButton.setDisplayedMnemonicIndex(0);
		panel1.add(saveConfigButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
	}

	private void createUIComponents() {

		PropertySheetTableModel model = new PropertySheetTableModel();

		DefaultProperty nameProperty = new DefaultProperty();
		nameProperty.setName("floorName");
		nameProperty.setDisplayName("FloorConfig name");
		nameProperty.setType(String.class);

		DefaultProperty colourProperty = new DefaultProperty();
		colourProperty.setName("actualColor");
		colourProperty.setDisplayName("Colour");
		colourProperty.setType(Color.class);

		DefaultProperty mapColourProperty = new DefaultProperty();
		mapColourProperty.setName("mapColor");
		mapColourProperty.setDisplayName("Minimap Colour");
		mapColourProperty.setType(Color.class);

		DefaultProperty textureProperty = new DefaultProperty();
		textureProperty.setName("texture");
		textureProperty.setDisplayName("Texture");
		textureProperty.setType(Integer.class);

		DefaultProperty occludeProperty = new DefaultProperty();
		occludeProperty.setName("occluded");
		occludeProperty.setDisplayName("Occlude?");
		occludeProperty.setType(Boolean.class);

		model.addProperty(nameProperty);
		model.addProperty(colourProperty);
		model.addProperty(mapColourProperty);
		model.addProperty(textureProperty);
		model.addProperty(occludeProperty);

		model.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
				int i = floorsList.getSelectedIndex();
				if (i != -1) {
					FloorConfig currentFloorConfig = FloorConfig.getFloor(i);
					Property prop = (Property) propertyChangeEvent.getSource();
					prop.writeToObject(currentFloorConfig);
					System.out.println(prop);
					if (prop.getName().equals("floorName")) {
						updateFloorList();
					} else if (prop.getName().equals("texture")) {   // Why do we have to update it ourselves??
						currentFloorConfig.setTexture((Integer) prop.getValue());
					}
					updatePreviews();
					setEdited();
				}
			}
		});

		PropertySheetTable table = new PropertySheetTable(model);
		properties = new PropertySheetPanel(table);
		PropertyEditorRegistry editorRegistry = (PropertyEditorRegistry) properties.getEditorFactory();
		SpinnerCellEditor e = new SpinnerCellEditor(new SpinnerNumberModel(0, -1, 49, 1));
		editorRegistry.registerEditor(textureProperty, e);
		properties.setEnabled(false);
	}

	private void setEdited() {
		if (!hasEdited) {
			hasEdited = true;
			title = getTitle();
			setTitle(title + " (*)");
		}
	}

	@Override
	public void dispose() {
		instance = null;
		super.dispose();
	}
}
