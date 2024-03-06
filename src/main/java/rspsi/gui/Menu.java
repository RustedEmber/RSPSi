package rspsi.gui;

import java.awt.event.*;

import javax.swing.*;


import rspsi.*;
import rspsi.client.Interface;
import rspsi.client.sign.signlink;
import rspsi.gui.menu.*;

public class Menu extends JMenuBar implements ActionListener {

	public Menu(Workspace workspace) {
		super();
		for (int abc = 0; abc < menuNames.length; abc++)
			if (!menuNames[abc].contains("/")) {
				String menuName = menuNames[abc].substring(6);
				JMenu menu = setMenu(new JMenu(menuName), abc);
				menu = setItems(menu, abc);
				add(menu);
			}

		setFocusable(true);
		this.workspace = workspace;
    	file = new File(workspace);
    	edit = new Edit(workspace);
    	view = new View(workspace);
    	insert = new Insert(workspace);
    }

	private JMenu setMenu(JMenu menu, int pos) {
		for (int abc = 0; abc < menuNames.length; abc++) {
			String menuPath = menuNames[abc].substring(6);
			String parentPath = menuNames[pos].substring(6);

			if ((menuPath.contains("/") ? menuPath.substring(0, menuPath.lastIndexOf("/")).equals(parentPath) : menuPath.startsWith(parentPath))
					&& menuPath.length() > parentPath.length()) {

				String menuName = menuPath.substring(menuPath.lastIndexOf("/") + 1);
				JMenu childMenu = setMenu(new JMenu(menuName), abc);
				childMenu = setItems(childMenu, abc);

				menu.add(childMenu);
			}
		}
		return menu;
	}

	private JMenu setItems(JMenu menu, int pos) {
		for (int abc = 0; abc < menuItems.length; abc++) {
			String itemPath = menuItems[abc].substring(6);
			String parentPath = menuNames[pos].substring(6);

			if (itemPath.substring(0, itemPath.lastIndexOf("/")).equals(parentPath) && itemPath.length() > parentPath.length()) {

	    		String itemName = itemPath.substring(itemPath.lastIndexOf("/") + 1);
				String itemType = menuItems[abc].substring(0, 6);
	    		if (itemType.equals("[CBOX]")) {
	    			JCheckBox menuItem = new JCheckBox(itemName);
	    			menuItem.addActionListener(this);
	    			menu.add(menuItem, itemIndexes[abc]);
	    		} else if (itemType.equals("[ITEM]")) {
	    			JMenuItem menuItem = new JMenuItem(itemName);
	    			menuItem.addActionListener(this);
	    			menu.add(menuItem, itemIndexes[abc]);
	    		} else if (itemType.equals("[SEPE]"))
	    			menu.insertSeparator(itemIndexes[abc]);
			}
		}
		return menu;
	}

    @Override
    public void actionPerformed(ActionEvent evt) {
        String cmd = evt.getActionCommand();
        System.out.println(cmd);

        if (cmd != null) {
            /**File menu**/
            if (cmd.equals("New"))
            	file.newInterface();

            if (cmd.equals("From Cache"))
            	file.fromCache();

            if (cmd.equals("From Cache File"))
            	file.fromFile();

            if (cmd.equals("Import"))
            	file.importRsi();

            if (cmd.equals("To Cache"))
            	file.toCache();

            if (cmd.equals("To Cache File"))
            	file.toFile();

            if (cmd.equals("Export"))
            	file.exportRsi(0);

            if (cmd.equals("Exit"))
            	file.exit();

            /**Edit menu**/
            if (cmd.equals("Undo"))
            	edit.undo();

            if (cmd.equals("Move"))
            	edit.move();

            if (cmd.equals("Resize"))
            	edit.resize();

            if (cmd.equals("Bounds()"))
            	edit.bounds();

            if (cmd.equals("Font"))
            	edit.font();

            if (cmd.equals("Color"))
            	edit.color();

            if (cmd.equals("Move To Top"))
            	edit.moveToTop();

            if (cmd.equals("Move To..."))
            	edit.moveTo();

            if (cmd.equals("Move To Bottom"))
            	edit.moveToBottom();

            if (cmd.equals("Duplicate"))
            	edit.duplicate();

            if (cmd.equals("Remove"))
            	edit.remove();

            /**View menu**/
            if (cmd.equals("Reference"))
            	view.reference();

            if (cmd.equals("Outline"))
            	view.outline();

            if (cmd.equals("    - Shaded"))
            	view.shaded();

            if (cmd.equals("Set Reference"))
            	view.setReference();

            if (cmd.equals("Set Region"))
            	view.setRegion();

            /**Interface menu**/
            if (cmd.equals("Existing Object"))
            	insert.existingObject();

            if (cmd.equals("New Object"))
            	insert.newObject();

            if (cmd.equals("Fix IDs()"))
            	Rspsi.fixIDs();

            if (cmd.equals("Export Selected"))
            	file.exportRsi(1);

            /**Tools menu**/
            if (cmd.equals("Magnifier")) {
            	JCheckBox jcb = ((JCheckBox) getMenu(4).getMenuComponents()[0]);
            	JPanel pane = workspace.getMagnifier().getMagnifier();
            	Main.magnifierOpen = jcb.isSelected();
            	pane.setVisible(jcb.isSelected());
            	workspace.getFrame().pack();

//            	if (!Main.magnifierOpen)
//            		workspace.setMagnifier(new Magnifier());
//            	else
//                	Main.getWorkspace().setMagnifier(null);
//            		workspace.promptForNotice("Magnifier is already open.");

            }

            if (cmd.equals("Values Editor")) {
            	JCheckBox jcb = ((JCheckBox) getMenu(4).getMenuComponents()[1]);
            	JPanel pane = workspace.getValuesEditor().getEditorPane();
            	pane.setVisible(jcb.isSelected());
            	workspace.getFrame().pack();
            }

            if (cmd.equals("Options"))
                new Options();

//            if (cmd.equals("Pack Media"))
//                SpriteCache.packData();

//            /**Dump menu**/
//            if (cmd.equals("Dump Objects"))
//                Dump.ObjectDef();

//            /**Pack menu**/
//            if (cmd.equals("Pack Media"))
//                SpriteCache.writeCache();

	        /**List menu**/
//			if (cmd.equals("Interfaces"))
//                new Xml(signlink.findcachedir() + "list/Interfaces.xml", "item");
//
//			if (cmd.equals("Items"))
//                new Xml(signlink.findcachedir() + "list/Items.xml", "item");
//
//			if (cmd.equals("NPCs"))
//                new Xml(signlink.findcachedir() + "list/Npcs.xml", "entity");
//
//            if (cmd.equals("Objects"))
//                new Xml(signlink.findcachedir() + "list/Objects.xml", "object");

          /**Help menu**/
          if (cmd.equals("Help"))
              workspace.promptForNotice("RSPSi [BETA]!" +
              		"\n" +
              		"\nThis is the first release for the interface editor" +
              		"\ncreated by Benjamin Rea of Rune-Server.org." +
              		"\n" +
              		"\nIf you need help with the program, please post on" +
              		"\nthe release thread. Hopefully somebody will help." +
              		"\n" +
              		"\nPlease note that this IS a beta of the editor, and" +
              		"\ntherefore not all features may work properly or some" +
              		"\nmay be missing comepletely!" +
              		"\n" +
              		"\nIf you find a bug (which nobody else hase mentioned)," +
              		"\nplease report it on the program thread - along with" +
              		"\nyour personal thoughts of it, and any suggestions" +
              		"\n that you may have." +
              		"\n" +
              		"\nThanks for using RSPSi ;)");
        }
        
    }

    public JMenuBar getMenuBar() {
    	return this;
    }

    protected Workspace workspace;
    public File file;
    private Edit edit;
    private View view;
    private Insert insert;

    private static String[] menuNames = new String[] {
    	"[MENU]File", "[MENU]File/Load", "[MENU]File/Save",
    	"[MENU]Edit", "[MENU]Edit/Set", "[MENU]Edit/Layer",
    	"[MENU]View",
    	"[MENU]Interface", "[MENU]Interface/Insert",/* "[MENU]Interface/Insert/Preset Object",*/
    	"[MENU]Tools",/* "[MENU]Tools/Lists",*/
    	"[MENU]Help" };

    private static String[] menuItems = new String[] {
    	"[ITEM]File/New",
    	"[SEPE]File/Seperator",
    			"[ITEM]File/Load/From Cache", "[ITEM]File/Load/From Cache File", "[ITEM]File/Load/Import",
    			"[ITEM]File/Save/To Cache", "[ITEM]File/Save/To Cache File", "[ITEM]File/Save/Export",
    	"[SEPE]File/Seperator",
    	"[ITEM]File/Exit",

//    	"[ITEM]Edit/Undo",
//    	"[SEPE]Edit/Seperator",
    	"[ITEM]Edit/Move",
    	"[ITEM]Edit/Resize",
    	"[SEPE]Edit/Seperator",

				"[ITEM]Edit/Set/Bounds()",
				"[ITEM]Edit/Set/Font",
				"[ITEM]Edit/Set/Color",

		    	"[ITEM]Edit/Layer/Move To Top",
		    	"[ITEM]Edit/Layer/Move To...",
		    	"[ITEM]Edit/Layer/Move To Bottom",
		    	"[SEPE]Edit/Layer/Seperator",
		    	"[ITEM]Edit/Layer/Duplicate",
		    	"[ITEM]Edit/Layer/Remove",

    	"[CBOX]View/Reference",
    	"[CBOX]View/Outline",
    	"[CBOX]View/    - Shaded",
    	"[SEPE]View/Seperator",
    	"[ITEM]View/Set Reference",
    	"[ITEM]View/Set Region",

    			"[ITEM]Interface/Insert/Existing Object",
		    	"[ITEM]Interface/Insert/New Object",
//		    	"[SEPE]Interface/Insert/Seperator",
//		    	"[ITEM]Interface/Insert/Manage Presets",

		"[ITEM]Interface/Fix IDs()",
		"[SEPE]Interface/Seperator",
		"[ITEM]Interface/Export Selected",

		"[CBOX]Tools/Magnifier",
		"[CBOX]Tools/Values Editor",
		"[SEPE]Tools/Seperator",
		"[ITEM]Tools/Options",
//				"[ITEM]Tools/Lists/Interfaces",
//				"[ITEM]Tools/Lists/Items",
//				"[ITEM]Tools/Lists/NPCs",
//				"[ITEM]Tools/Lists/Objects",
//		"[ITEM]Tools/Pack Media",

    	"[ITEM]Help/Help",
//		"[SEPE]Help/Seperator",
//    	"[ITEM]Help/About",
//    	"[ITEM]Help/Docs",
//    	"[ITEM]Help/Examples",
    };

    private static int[] itemIndexes = new int[] {
    	0, 1, 0, 1, 2, 0, 1, 2, 4, 5, //File

    	0, 1, 2,/* 3, 4,*/ 0, 1, 2, 0, 1, 2, 3, 4, 5, //Edit

    	0, 1, 2, 3, 4, 5, //View

    	0, 1,/* 2, 4,*/ 1, 2, 3, //Interface

    	0, 1, 2, 3,/* 0, 1, 2, 3, 3,*/ //Tools

    	0,/* 1, 2, 3, 4,*/ //Help
    };

}
