package rspsi.gui.menu;

import javax.swing.JOptionPane;

import rspsi.Main;
import rspsi.Rspsi;
import rspsi.client.Interface;
import rspsi.gui.Workspace;

public class Insert {

	private Workspace workspace;

	public Insert(Workspace workspace) {
    	this.workspace = workspace;
	}

	private Interface doChecks() {
    	if (Main.topParent == null) {
			JOptionPane.showMessageDialog(workspace, "Nothing Loaded.");
			return null;
		}
		if (Main.editing.length > 1) {
			JOptionPane.showMessageDialog(workspace, "Cannot insert to multiple containers.");
			return null;
		} else if (Main.editing[0].type == 0 && Main.editingLayers != Main.parentLayers) {
			int option = workspace.promptForConfirm("Do you want to insert to selected child?", JOptionPane.YES_NO_CANCEL_OPTION);
			if (option == 2)
				return null;
			else
				return option == 1 ? Main.editingParents[0] : Main.editing[0];
		} else
			return Main.editingParents[0];
	}

	public void existingObject() {
		Interface parent = doChecks();
		if (parent == null)
			return;

    	String[] input = workspace.promptForInput("Insert Existing Interface",
				new String[] { "Insert ID:" }, 1, 1);
    	if (input == null)
    		return;

    	int id = Integer.parseInt(input[0]);
		if (id == Main.topParent.id || id == Main.editingParents[0].id
				|| id == Main.topParent.parentId || id == Main.editingParents[0].parentId) {
			workspace.promptForNotice("Cannot insert self / top parent.");
			return;
		}

		int layer;
		if (Main.editing[0] == parent)
			layer = 0;
		else
			layer = Main.editingLayers[0] == -1 ? parent.children.length : Main.editingLayers[0];

		Interface rsi = Interface.interfaceCache[id];
		if (rsi != null) {
			Rspsi.appendChild(layer, new int[] { id, 0, 0 }, parent);
			workspace.getTree().updateTree(layer);
		} else
			workspace.promptForNotice("Interface is null, cannot load.");
	}

	public void newObject() {
		Interface parent = doChecks();
		if (parent == null)
			return;

    	int id, type;
    	Interface rsi;
		stages:
    	do {
			String[] idType = ((String[]) workspace.promptForInput("Insert New Object",
					new String[] {
							"New ID: (Next free interface: " + Rspsi.getNextFree() + ")",
							"Type: (0 - 8)" }, 2, 1));
			if (idType == null)
				return;
			id = Integer.parseInt(idType[0]);
			type = Integer.parseInt(idType[1]);
			rsi = Interface.interfaceCache[id];
			if (rsi == Main.topParent || rsi == parent) {
				workspace.promptForNotice("Cannot overwrite self / top parent.");
				return;
			}
			if (rsi == null)
				break;
			else {
				int option = workspace.promptForConfirm("Interface is in use, are you sure you want to use this ID?", JOptionPane.YES_NO_CANCEL_OPTION);
				if (option == 2)
					return;
				else if (option == 1)
					continue stages;
				else if (option == 0)
					break;
			}
    	} while (true);

		switch (type) {
		case 0:
			Rspsi.container(id, 512, 334);
			break;
		case 2:
			Rspsi.inventory(id);
			break;
		case 3:
			Rspsi.box(id);
			break;
		case 4:
			Rspsi.text(id);
			break;
		case 5:
			Rspsi.sprite(id);
			break;
		case 6:
			Rspsi.model(id);
			break;
		case 7:
			Rspsi.itemList(id);
			break;
		case 8:
			Rspsi.tooltip(id);
			break;
		default:
			Rspsi.create(id, type);
			break;
		}

		int layer;
		if (Main.editing[0] == parent)
			layer = 0;
		else
			layer = Main.editingLayers[0] == -1 ? parent.children.length : Main.editingLayers[0];

		Rspsi.appendChild(layer, new int[] { id, 0, 0 }, parent);
		workspace.getTree().updateTree(layer);
		
	}

}
