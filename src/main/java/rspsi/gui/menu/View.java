package rspsi.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import rspsi.Main;
import rspsi.RSInterfaceConstants;
import rspsi.client.*;
import rspsi.client.graphics.Sprite;
import rspsi.gui.FileChooser;
import rspsi.gui.Workspace;

public class View {

	private Workspace workspace;

	public View(Workspace workspace) {
    	this.workspace = workspace;
	}

	public void reference() {
    	JCheckBox jcb = ((JCheckBox) workspace.menu.getMenu(2).getMenuComponents()[0]);
    	if (Main.reference == null)
    		setReference();
    	else
    		Main.showReference = jcb.isSelected();
    	workspace.updateInterfaces();
	}

	public void setReference() {
		final JFileChooser fc = new FileChooser(workspace.frame, Main.referenceDir, "Browse Reference...");
        fc.addActionListener(new ActionListener() {

        	public void actionPerformed(ActionEvent evt) {
        		System.out.println(evt.getActionCommand());
				if (evt.getActionCommand().equalsIgnoreCase("ApproveSelection")) {
	            	String refDir = fc.getSelectedFile().getAbsolutePath();
	            	Main.referenceDir = refDir;
	            	Main.reference = new Sprite(refDir);
	            	((JCheckBox) workspace.menu.getMenu(2).getMenuComponents()[0]).setSelected(true);
	            	Main.showReference = true;

		        } else if (evt.getActionCommand().equalsIgnoreCase("CancelSelection")) {
	            	((JCheckBox) workspace.menu.getMenu(2).getMenuComponents()[0]).setSelected(false);
		        }
			}
		});
        workspace.updateInterfaces();
	}

	public void setRegion() {
		if (Main.topParent == null) {
			JOptionPane.showMessageDialog(workspace, "Nothing Loaded.");
			return;
		}
    	int region = JOptionPane.showOptionDialog(workspace, "Region ID:", "Set Region", 0, 3, null, RSInterfaceConstants.regions, 0);
    	if (region == -1)
    		return;

    	Main.updateRegion(region);
	}

	public void outline() {
		Main.outline = !Main.outline;
		workspace.updateInterfaces();
	}
	
	public void shaded() {
    	Main.shaded = !Main.shaded;
		workspace.updateInterfaces();
	}

}
