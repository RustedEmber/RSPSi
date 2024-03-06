package rspsi.gui.menu;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;

import com.l2fprod.common.propertysheet.Property;

import rspsi.Main;
import rspsi.Rspsi;
import rspsi.client.Interface;
import rspsi.gui.Workspace;

public class Edit {

	private Workspace workspace;

	public Edit(Workspace workspace) {
    	this.workspace = workspace;
	}

	public boolean doChecks(String action, int type) {
        if (Main.topParent == null) {
    		workspace.promptForNotice("Nothing Loaded.");
			return false;
    	}
    	if (Main.editingLayers == null) {
    		workspace.promptForNotice("No layer selected.");
			return false;
    	}
        for (int abc = 0; abc < Main.editingLayers.length; abc++) {
			boolean flag1 = false;
			switch (type) {
			case 0:
				if (Main.editingLayers[abc] < 0)
					flag1 = true;
				break;
			case 1:
				if (Main.editing[abc] == Main.editingParents[abc] || Main.editing[abc] == Main.topParent)
					flag1 = true;
				break;
			}
			if (flag1) {
				workspace.promptForNotice("Cannot " + action + " Top Parent.");
				return false;
			}
        }

        return true;
	}

	public void undo() {
//		RSInterface.interfaceCache = RSInterface.lastCache;
//		workspace.getTree().updateTree(-1);

        try {
            File file = new File("interfaceCache DUMP.rsi");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(Interface.interfaceCache.toString());

            bw.flush();
            bw.close();

            //Rspsi.setInterfaceSaved(true);
        } catch(Exception e) {
            e.printStackTrace();
        }
	}

	public void move() {
    	if (!doChecks("move", 0))
    		return;

    	TreePath[] paths = workspace.getTree().getTree().getSelectionPaths();
    	int[] rows = workspace.getTree().getTree().getSelectionRows();

    	int[] layers = Main.editingLayers;
        String[] coords = workspace.promptForInput("Move", new String[] { "X: (use +/- for relative movement)", "Y: (use +/- for relative movement)" }, 2, 1);
        if (coords == null || coords[0] == null || coords[1] == null)
        	return;

        for (int abc = 0; abc < layers.length; abc++) {
        	Interface rsi = Main.editingParents[abc];
        	int x = rsi.childX[layers[abc]];
        	int y = rsi.childY[layers[abc]];

        	if (coords[0].startsWith("+"))
                rsi.childX[layers[abc]] += Integer.parseInt(coords[0].replace("+", ""));
            else if (coords[0].startsWith("-"))
                rsi.childX[layers[abc]] -= Integer.parseInt(coords[0].replace("-", ""));
            else
                rsi.childX[layers[abc]] = Integer.parseInt(coords[0]);

            if (coords[1].startsWith("+"))
                rsi.childY[layers[abc]] += Integer.parseInt(coords[1].replace("+", ""));
            else if (coords[1].startsWith("-"))
                rsi.childY[layers[abc]] -= Integer.parseInt(coords[1].replace("-", ""));
            else
                rsi.childY[layers[abc]] = Integer.parseInt(coords[1]);
//            System.out.println("1: " + paths[abc]);
            
//          [0: [Top Parent], Layer 10: [11, 100, 260] - Sprite]
//          [0: [Top Parent], Layer 11: [12, 188, 80] - Sprite]
//            int length = 0;
//            for (int i = 0; i < paths.length; i++)
//            	if (paths[i].getPathCount() > length)
//            		length = paths[i].getParentPath().;
//
//            int[] expandRows = new int[length];

            TreePath path = paths[abc];
            String newPath = path.toString()
					.replace(
							"[" + rsi.id + ", " + x + ", " + y + "]",
							"[" + rsi.id + ", " + rsi.childX[layers[abc]] + ", " + rsi.childY[layers[abc]] + "]");

            paths[abc] = new TreePath(newPath);
            System.out.println("2: " + paths[abc]);

        }

//        int[] parentLayers = Main.parentLayers;
//        int[] editingLayers = Main.editingLayers;
        
        workspace.getPropSheet().update();
    	workspace.getTree().setSelectionPaths(paths, rows);
    	workspace.getTree().updateTree(-1);
//    	workspace.getTree().getTree().setSelectionPaths(paths);
//    	workspace.getTree().getTree().setSelectionRows(rows);
//    	workspace.getTree().setSelectionPaths(parentLayers, editingLayers);
    }

    public void resize() {
    	if (!doChecks("resize", -1))
    		return;

    	int[] layers = Main.editingLayers;
        String[] coords = workspace.promptForInput("Resize", new String[] { "Width: (use +/- for relative change)", "Height: (use +/- for relative change)" }, 2, 1);
        if (coords == null || coords[0] == null || coords[1] == null)
        	return;

        for (int abc = 0; abc < layers.length; abc++) {
            Interface rsi = Main.editing[abc];
            if (coords[0].startsWith("+"))
                rsi.width += Integer.parseInt(coords[0].replace("+", ""));
            else if (coords[0].startsWith("-"))
                rsi.width -= Integer.parseInt(coords[0].replace("-", ""));
            else
                rsi.width = Integer.parseInt(coords[0]);

            if (coords[1].startsWith("+"))
                rsi.height += Integer.parseInt(coords[1].replace("+", ""));
            else if (coords[1].startsWith("-"))
                rsi.height -= Integer.parseInt(coords[1].replace("-", ""));
            else
                rsi.height = Integer.parseInt(coords[1]);
        }
    	workspace.getPropSheet().update();
    }

    public void bounds() {
    	if (!doChecks("set bounds of", -1))
    		return;

    	for (Interface rsi : Main.editing)
    		if (rsi != null)
    			Rspsi.setBounds(rsi.id, rsi.type);

    	workspace.getPropSheet().update();
    }

    public void font() {
    	if (!doChecks("change font of", 1))
    		return;

        String[] input = workspace.promptForInput("Set Font", new String[] { "Font ID:" }, 1, 1);
        if (input == null || input[0] == null )
        	return;

        for (int abc = 0; abc < Main.editing.length; abc++)
            Main.editing[abc].setFontId(Integer.parseInt(input[0]));

    	workspace.getPropSheet().update();
    	
    }

    public void color() {
    	if (!doChecks("change color of", 1))
    		return;

		int colorType = JOptionPane.showOptionDialog(workspace, "Color Type:",
				"Set Color", 0, 3, null, new String[] { "disabledColor",
						"enabledColor", "mouseOverDisabledColor", "mouseOverEnabledColor" }, 0);
    	if (colorType == -1)
    		return;

        Color color = JColorChooser.showDialog(workspace, "Choose Color", new Color(0));
        if (color == null)
        	return;

        switch (colorType) {
        case 0:
	        for (int abc = 0; abc < Main.editing.length; abc++)
	            Main.editing[abc].setDisabledColor(color);
	        break;
        case 1:
	        for (int abc = 0; abc < Main.editing.length; abc++)
	            Main.editing[abc].setEnabledColor(color);
	        break;
        case 2:
	        for (int abc = 0; abc < Main.editing.length; abc++)
	            Main.editing[abc].setMouseOverDisabledColor(color);
	        break;
        case 3:
	        for (int abc = 0; abc < Main.editing.length; abc++)
	            Main.editing[abc].setMouseOverEnabledColor(color);
	        break;
        }

        workspace.getPropSheet().update();
    	
    }

    public void moveToTop() {
    	if (!doChecks("move", 0))
    		return;

    	int stage = 0;
    	for (int abc = 0; abc < Main.editingLayers.length; abc++) {
        	int to = 0, from = Main.editingLayers[abc];
	    	if (to == from)
	    		return;

	    	if (abc > 0 && Main.editingParents[abc] != Main.editingParents[abc - 1])
				stage = 0;

			Rspsi.moveChild(from, to + stage, Main.editingParents[abc]);
    		stage++;
    	}

    	workspace.getTree().updateTree(0);
    }

    public void moveTo() {
    	if (!doChecks("move", 0))
    		return;

        String[] string = workspace.promptForInput("Move Layer To", new String[] { "Layer: (use +/- for relative movement)" }, 1, 1);
    	if (string == null || string[0] == null)
    		return;

    	int to = Integer.parseInt(string[0]);
    	int stage = 0, stage2 = 0;
    	for (int abc = 0; abc < Main.editingLayers.length; abc++) {
	    	Interface rsi = Main.editingParents[abc];
	        int from = Main.editingLayers[abc];
	    	if (to == from)
	    		return;
	    	if (to < 0) {
	    		workspace.promptForNotice("Destination layer must be greater than -1.");
	    		return;
	    	}
	    	if (to >= rsi.children.length) {
	    		workspace.promptForNotice("Destination layer must not exceed container size.");
	    		return;
			}

	    	if (abc > 0
					&& Main.editingParents[abc] != Main.editingParents[abc - 1])
				stage = 0;

			if (Main.editingLayers.length > abc + 1
					&& Main.parentLayers[abc] != Main.parentLayers[abc + 1])
				stage2++;

			if (to > from && Main.editingLayers.length > 1)
				stage2--;

			if (to < from)
    			Rspsi.moveChild(from, to + stage, rsi);
    		else
    			Rspsi.moveChild(from - stage, to - stage2, rsi);
    		stage++;
    	}

    	workspace.getTree().updateTree(to);
    }

    public void moveToBottom() {
    	if (!doChecks("move", 0))
    		return;

    	int stage = 0;
    	for (int abc = 0; abc < Main.editingLayers.length; abc++) {
        	int to = Main.editingParents[abc].children.length - 1;
	        int from = Main.editingLayers[abc];
	    	if (to == from)
	    		return;

	    	if (abc > 0 && Main.editingParents[abc] != Main.editingParents[abc - 1])
				stage = 0;

			Rspsi.moveChild(from - stage, to, Main.editingParents[abc]);
    		stage++;
    	}

    	workspace.getTree().updateTree(Main.editingParents[0].children.length - 1);
    }

    public void duplicate() {
    	if (!doChecks("duplicate self /", 1))
    		return;
    	else {
    		for (int abc = 0; abc < Main.editing.length; abc++) {
    	        String[] input = workspace.promptForInput("Duplicate",
    	        		new String[] { "New Duplicate ID: (Next free interface: " + Rspsi.getNextFree() + ")" }, 1, 1);
    	    	if (input == null)
    	    		return;

    	    	int id = Integer.parseInt(input[0]);
	    		int layer = Main.editingLayers[abc] + 1;

	    		Interface parent = Main.editingParents[abc];
	    		Interface dup = Main.editing[abc];

//	    		Rspsi.create(id, dup.type);
//			        BeanInfo beanInfo = Introspector.getBeanInfo(RSInterface.class);
		
	    		Interface rsi = Interface.interfaceCache[id] = new Interface();

				for (Property property : workspace.getPropSheet().getPropSheet().getProperties()) {
					property.readFromObject(dup);
					if (property != null)
						property.writeToObject(rsi);
				}

	    		rsi.id = id;
//	    		rsi.parentId = id;
//		    		RSInterface.interfaceCache[id].type = dup.type;
	
				Rspsi.appendChild(layer, new int[] { id, parent.childX[layer - 1 + abc], parent.childY[layer - 1 + abc] }, parent);

    		}
    		
    	}
	
		workspace.getTree().updateTree(Main.editingLayers[0]);
    }

    public void remove() {
    	if (!doChecks("remove self /", 1))
    		return;

    	for (int abc = 0; abc < Main.editing.length; abc++)
			if (Main.editing[abc] == Main.editingParents[abc] || Main.editing[abc] == Main.topParent) {
				workspace.promptForNotice("Cannot remove self / top parent.");
				return;
			}
    	int stage = 0;
		for (int abc = 0; abc < Main.editingLayers.length; abc++) {
			if (stage != 0 && Main.editingParents[abc] != Main.editingParents[abc - 1])
				stage = 0;
			Rspsi.removeChild(Main.editingLayers[abc] - stage, Main.editingParents[abc]);
			stage++;
		}
    	workspace.getTree().updateTree(Main.editingLayers[0] - 1);
    }
}
