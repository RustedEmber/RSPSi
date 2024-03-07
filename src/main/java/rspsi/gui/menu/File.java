package rspsi.gui.menu;

import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import com.jagex.cache.Archive;
import rspsi.*;
import rspsi.client.*;
import rspsi.client.stream.Stream;
import rspsi.gui.*;
import rspsi.io.FileOperations;

public class File {

	private Workspace workspace;

	public File(Workspace workspace) {
    	this.workspace = workspace;
	}

    public void newInterface() {
    	stages:
    	do {
    		String[] input = workspace.promptForInput("New Interface",
					new String[] { "New ID: (Next free interface: " + Rspsi.getNextFree() + ")" }, 1, 0);
        	if (input == null)
        		return;

    		int id = Integer.parseInt(input[0]);
    		if (id < 0) {
    			workspace.promptForNotice("New ID must be > -1.");
				continue stages;
    		}

    		Interface rsi = Interface.interfaceCache[id];
			if (rsi == null) {
				Rspsi.container(id, 512, 334);
				Main.load(id);
				return;
			} else {
				int option = workspace.promptForConfirm("Interface is in use, are you sure you want to use this ID?", JOptionPane.YES_NO_CANCEL_OPTION);
				if (option == 2)
					return;
				else if (option == 1)
					continue stages;
				else if (option == 0) {
					Rspsi.container(id, 512, 334);
					Main.load(id);
					return;
				}
			}
    	} while (true);
    }

    public void fromCache() {
		String[] input = workspace.promptForInput("Load From Cache",
    			new String[] { "Interface ID:" }, 1, 0);
    	if (input == null)
    		return;

    	int id = Integer.parseInt(input[0]);
    	if (id == -1) {
    		Main.load(-1);
    		return;
    	}

    	Interface rsi = Interface.interfaceCache[id];
    	if (rsi == null)
			workspace.promptForNotice("Interface is null, cannot load.");
    	else if (rsi.type != 0) {
    		workspace.promptForNotice("Must load a parent/container.\nLoaded parent of " + id + ".");
			Main.load(rsi.parentId);
    	} else
    		Main.load(id);
    }

    public void fromFile() {
		final JFileChooser fc = new FileChooser(workspace.frame, Main.referenceDir, "Open File...");
        fc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				if (evt.getActionCommand().equalsIgnoreCase("ApproveSelection")) {
					String path = fc.getSelectedFile().getAbsolutePath();

					workspace.getTree().resetTree();
					workspace.getPropSheet().resetSheet();
					workspace.clearAllInterfaces();
					byte[] data = FileOperations.readFile(path);
					//do some checks?
					Rspsi.unpack(new Stream(data), Rspsi.textDrawingAreas_rsi, Rspsi.archive_rsi);
					Main.browseDir = fc.getSelectedFile().getParentFile().getAbsolutePath();
				}

				else if (evt.getActionCommand().equalsIgnoreCase("CancelSelection"))
					return;
			}
		});
    }

    public void importRsi() {
		final JFileChooser fc = new FileChooser(workspace.frame, Main.referenceDir, "Import Interface...");
        fc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				if (evt.getActionCommand().equalsIgnoreCase("ApproveSelection")) {
					String path = fc.getSelectedFile().getAbsolutePath();

					workspace.clearAllInterfaces();
					byte[] data = FileOperations.readFile(path);
					//do some checks?

					Stream stream = new Stream(data);
					int id = stream.readUnsignedWord();
					Rspsi.unpack(stream);

					Main.load(id);
					Main.browseDir = fc.getSelectedFile().getParentFile().getAbsolutePath();
				}

				else if (evt.getActionCommand().equalsIgnoreCase("CancelSelection"))
					return;
			}
		});
    }

    public void toCache() {
    	if (Main.topParent == null) {
			JOptionPane.showMessageDialog(workspace, "Nothing Loaded.");
			return;
		}
		try {
			Archive archive = new Archive(workspace.cache.getFile(0, 3));
			archive.updateEntry("data", Rspsi.toData());
			workspace.cache.putFile(0, 3, archive.recompile());
		} catch (IOException e) {
			workspace.promptForNotice("Error: " + e.getMessage());
			e.printStackTrace();
		}
    }

    public void toFile() {
    	if (Main.topParent == null) {
			JOptionPane.showMessageDialog(workspace, "Nothing Loaded.");
			return;
		}

    	final JFileChooser fc = new FileChooser(workspace.frame, Main.referenceDir, "Save File...");
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
        fc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				if (evt.getActionCommand().equalsIgnoreCase("ApproveSelection")) {
					String path = fc.getSelectedFile().getAbsolutePath();
					Rspsi.packToData(path + (!path.toLowerCase().endsWith(".dat") ? ".dat" : ""));
					Main.browseDir = fc.getSelectedFile().getParentFile().getAbsolutePath();
				}

				else if (evt.getActionCommand().equalsIgnoreCase("CancelSelection"))
					return;
			}
		});
    }
	public void toMedia() {
		if (Main.topParent == null) {
			JOptionPane.showMessageDialog(workspace, "Nothing Loaded.");
			return;
		} 	try {

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public void toSave() {
		if (Main.topParent == null) {
			JOptionPane.showMessageDialog(workspace, "Nothing Loaded.");
			return;
		} 	try {

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void toTexture() {
		if (Main.topParent == null) {
			JOptionPane.showMessageDialog(workspace, "Nothing Loaded.");
			return;
		} 	try {

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
    public void exportRsi(final int type) {
    	if (Main.topParent == null) {
			JOptionPane.showMessageDialog(workspace, "Nothing Loaded.");
			return;
		}

    	final JFileChooser fc = new FileChooser(workspace.frame, Main.referenceDir, "Export Interface...");
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
        fc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				if (evt.getActionCommand().equalsIgnoreCase("ApproveSelection")) {
					String path = fc.getSelectedFile().getAbsolutePath();
					Rspsi.export(path + (!path.toLowerCase().endsWith(".rsi") ? ".rsi" : ""),
							type == 0 ? new Interface[] { Main.topParent } : Main.editing);
					Main.browseDir = fc.getSelectedFile().getParentFile().getAbsolutePath();
				}

				else if (evt.getActionCommand().equalsIgnoreCase("CancelSelection"))
					return;
			}
		});
    }

    public void exit() {
    	int option = workspace.promptForConfirm("Are you sure you want to exit?\nAll unsaved editing will be lost.", JOptionPane.YES_NO_CANCEL_OPTION);
    	if (option == 0)
    		System.exit(0);
    }
}
