package rspsi.gui;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;

import rspsi.SplashScreen;
import rspsi.client.*;
import rspsi.client.sign.signlink;

public class Workspace extends client implements ActionListener {

	public Menu menu = new Menu(this);
	public PropSheet propSheet = new PropSheet(this);
	public Tree tree = new Tree(this);
	public SplashScreen splashScreen = new SplashScreen(this);
	public Magnifier magnifier = new Magnifier(this);
	public ValuesEditor valuesEditor = new ValuesEditor(this);

    public URL getDocumentBase() {
        return getCodeBase();
    }
 
    public URL getCodeBase() {
        try {
            return new URL("http://127.0.0.1/cache");
        } catch (Exception e) {
            return super.getCodeBase();
        }
    }

    public static void main(String[] args) {
        new Workspace();
    }

    public Workspace() {
        super();
        try {
            initUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    JPanel clientPanel;

    private void initUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setPreferredSize(new Dimension(765, 503));
            signlink.startpriv(java.net.InetAddress.getLocalHost());
            signlink.mainapp = this;

            clientPanel = new JPanel(new BorderLayout());
            clientPanel.setPreferredSize(new Dimension(765, 503));
//            clientPanel.setPreferredSize(new Dimension(519, 341));
            clientPanel.add(this, BorderLayout.CENTER);

            JPanel westPanel = new JPanel(new BorderLayout());
            westPanel.add(clientPanel, BorderLayout.NORTH);
            westPanel.add(magnifier.getMagnifier(), BorderLayout.CENTER);
            westPanel.add(valuesEditor.getEditorPane(), BorderLayout.EAST);

            JPanel eastPanel = new JPanel(new BorderLayout());
            eastPanel.setPreferredSize(new Dimension(265, 503));
            eastPanel.add(menu.getMenuBar(), BorderLayout.NORTH);
            eastPanel.add(tree.getTreePane(), BorderLayout.CENTER);
            eastPanel.add(propSheet.getPropPane(), BorderLayout.SOUTH);

            frame = new JFrame(frameTitle);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        	frame.setIconImage(splashScreen.getIconImage());
            frame.setMinimumSize(new Dimension(800, 380));
            frame.setResizable(true);

            frame.setLayout(new BorderLayout());
            frame.getContentPane().add(westPanel, BorderLayout.LINE_START);
            frame.getContentPane().add(eastPanel, BorderLayout.CENTER);

            frame.pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void promptForNotice(String subTitle) {
		JOptionPane.showMessageDialog(this, subTitle);
    }

    public int promptForConfirm(String subTitle, int optionType) {
		int option = JOptionPane.showConfirmDialog(this, subTitle, "", optionType);
		return option;
    }

    public String[] promptForInput(String frameTitle, String[] subTitles, int howMany, int type) {
		String[] values = new String[howMany];
        int stage = 0;
		stages: do {
			String input = JOptionPane.showInputDialog(this, subTitles[stage], frameTitle, 3);
			if (input == null)
				return null;
			else if (input.equalsIgnoreCase("")) {
				promptForNotice("Input is required");
				continue stages;
			} else
				try {
					if (type == 0)
						values[stage] = "" + Integer.parseInt(input);
					else if (type == 1) {
						if (input.startsWith("+"))
							values[stage] = "+" + Integer.parseInt(input.replace("+", ""));
						else if (input.startsWith("-"))
							values[stage] = "-" + Integer.parseInt(input.replace("-", ""));
						else
							values[stage] = "" + Integer.parseInt(input);
					}
					stage++;
					continue stages;
				} catch (NumberFormatException nfe) {
					promptForNotice("Numeral characters only");
					continue stages;
				}
		} while (stage != howMany);
		return values;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String cmd = evt.getActionCommand();
        if (cmd != null) {
        	//
        }
        
    }

	public JFrame getFrame() {
		return frame;
	}

	public Tree getTree() {
		return tree;
	}

	public PropSheet getPropSheet() {
		return propSheet;
	}

	public Magnifier getMagnifier() {
		return magnifier;
	}

	public void setMagnifier(Magnifier magnifier) {
		this.magnifier = magnifier;
	}

	public ValuesEditor getValuesEditor() {
		return valuesEditor;
	}

    private String frameTitle = "RSPSi - Thee Interface Editor";
    public static JFrame frame;
}
