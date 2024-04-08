/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rspsi;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.*;
import javax.swing.tree.TreePath;

import com.intellij.uiDesigner.core.GridLayoutManager;
import rspsi.client.*;
import rspsi.client.graphics.Sprite;

import rspsi.gui.ToolsUI;
import rspsi.gui.Workspace;
import rspsi.io.FileOperations;
import rspsi.io.RSModel;
import rspsi.io.util.VersionChecker;
import rspsi.gui.ToolsUI;


/**
 *
 * @author Benjamin
 */
public class Main extends JFrame {
	//public static SuiteLogic logic;
	public static Workspace workspace;
	public static final float VERSION = 0.17f;
	private JDesktopPane mainPane;
	private static MenuActionListener menuListener = new MenuActionListener();
	private ToolsUI toolsUI;
	public JPanel mainPanel;
	public static String browseDir = "./";
	public static String referenceDir = "./";
	public static Sprite reference = null;
	public static boolean showReference = false;

	public static boolean outline = false;
	public static boolean shaded = false;
	public static boolean emulating;
	public static boolean magnifierOpen;
	public static int region = 0; /** Screen * Inventory * Chatbox * Fullscreen **/

	public static int[] editingID = new int[] { -1 };
	public static int[] editingLayers = new int[] { -1 };
	public static int[] parentLayers = new int[] { -1 };
	public static Interface topParent = null;
	public static Interface[] editing = null;
	public static Interface[] editingParents = null;

	public static Workspace getWorkspace() {
		return workspace;
	}

	public static void main(String[] args) {
		new Main();
	}

	public ToolsUI getTools() {
		return toolsUI;
	}



	public void createStaticFrame(String title, JPanel framePanel) {
		JInternalFrame frame = new JInternalFrame(title, false, false, false, false);
		frame.setVisible(true);
		frame.add(framePanel);
		frame.pack();
		mainPane.add(frame);
	}

	public void addFrame(JInternalFrame frame) {
		frame.setClosable(true);
		frame.setResizable(true);
		frame.setMaximizable(true);
		frame.setIconifiable(true);
		frame.setVisible(true);
		frame.setLocation(250, 120);
		mainPane.add(frame);
	}
	public ToolsActionListener action = new ToolsActionListener();
	public Main() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					workspace = new Workspace();
					workspace.getFrame().addWindowListener(new java.awt.event.WindowAdapter() {
						@Override
						public void windowClosing(java.awt.event.WindowEvent e) {
							workspace.menu.file.exit();
						}
					});
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void load(int id) {
		Interface rsi = Interface.interfaceCache[id];

		editingID = new int[] { id };
		editingLayers = new int[] { -1 };
		parentLayers = new int[] { -1 };
		topParent = rsi;
		editing = new Interface[] { rsi };
		editingParents = new Interface[] { rsi };

		updateRegion(region);
		workspace.getTree().updateTree(0);
	}

	public static void resetArrays(int length) {
		editingID = new int[length];
		editingLayers = new int[length];
		parentLayers = new int[length];
		editing = new Interface[length];
		editingParents = new Interface[length];
	}

	public static void setMedia(String dir) {
		if (dir.equals(""))
			Interface.archive_rsi = workspace.streamLoaderForName(4, "2d graphics", "media");

		else if (new File(dir).exists())
			try {
				System.out.println("Setting media: " + dir);
				Interface.archive_rsi = new com.jagex.cache.Archive(FileOperations.readFile(dir));
			} catch (Exception e) {
				e.printStackTrace();
			}

		else
			workspace.promptForNotice("Could not find the file: " + dir);
	}
	public static void updateRegion(int newRegion) {
		region = newRegion;
		workspace.clearAllInterfaces();
		switch (newRegion) {
			case 0:
				workspace.openInterfaceID = topParent.parentId;
				break;
			case 1:
				workspace.invInterfaceID = topParent.parentId;
				break;
			case 2:
				workspace.backDialogID = topParent.parentId;
				break;
			case 3:
				String[] s = workspace.promptForInput("Set Region",
						new String[] { "Set Overlay Interface: (not needed)" }, 1, 1);

				if (s != null) {
					Interface rsi = Interface.interfaceCache[Integer.parseInt(s[0])];
					if (rsi == null)
						workspace.promptForNotice("Overlay Interface is null, cannot load.");

					else if (rsi.type != 0) {
						workspace.promptForNotice("Must load a parent/container.\nLoaded parent of " + rsi.parentId + ".");
						workspace.openInterfaceID = rsi.parentId;

					} else
						workspace.openInterfaceID = rsi.parentId;
				}
				workspace.fullscreenInterfaceID = topParent.parentId;
				break;
			default:
				Main.workspace.openInterfaceID = topParent.parentId;
				break;
		}
		Main.workspace.updateInterfaces();
	}
}
