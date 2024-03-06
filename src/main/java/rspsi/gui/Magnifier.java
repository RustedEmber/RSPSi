package rspsi.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

import rspsi.Main;

public class Magnifier extends JPanel implements ActionListener {
	public Workspace workspace;
	public JPanel menuBar;
	public JPanel lensPanel;
	public JCheckBox lockImage;
//	public JCheckBox alwaysOnTop;
	public JButton zoomOut;
	public JButton zoomIn;

	boolean locked;
	int lockX = 0, lockY = 0;
	int scaleFactor = 2;

	public Magnifier(Workspace workspace) {
		this.workspace = workspace;
//	}
//
//	public Magnifier() {
//		super("Magnifier");
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
//        addWindowListener(new java.awt.event.WindowAdapter() {
//            @Override
//            public void windowClosing(java.awt.event.WindowEvent e) {
//            	Main.magnifierOpen = false;
//            	workspace.setMagnifier(null);
//            }
//        });

		lockImage = new JCheckBox("Lock Image");
		lockImage.addActionListener(this);

//		alwaysOnTop = new JCheckBox("AlwaysOnTop");
//		alwaysOnTop.addActionListener(this);

		zoomOut = new JButton("-");
		zoomOut.addActionListener(this);

		zoomIn = new JButton("+");
		zoomIn.addActionListener(this);

		menuBar = new JPanel();
		menuBar.add(lockImage);
		menuBar.add(zoomOut);
		menuBar.add(zoomIn);

		lensPanel = new JPanel();
		lensPanel.setPreferredSize(new Dimension(200, 200));
		lensPanel.setVisible(true);

		/*getContentPane().*/add(menuBar, BorderLayout.NORTH);
		/*getContentPane().*/add(lensPanel, BorderLayout.CENTER);

		setVisible(false);
//        pack();

        Main.magnifierOpen = false;
	}

	public void paint() {
		try {
			if (this == null)
				return;

			Point framePos = workspace.clientPanel.getLocationOnScreen();
			int mouseX = (locked ? lockX : workspace.mouseX);
			int mouseY = (locked ? lockY : workspace.mouseY);
			Rectangle rectangle = new Rectangle(
					framePos.x + mouseX - lensPanel.getWidth() / scaleFactor,
					framePos.y + mouseY - lensPanel.getHeight() / scaleFactor,
					lensPanel.getWidth() * scaleFactor,
					lensPanel.getHeight() * scaleFactor);

			Robot robot;
			robot = new Robot();
			BufferedImage image = robot.createScreenCapture(rectangle);
			lensPanel.getGraphics().drawImage(image,
					-(lensPanel.getWidth() / 2),
					-(lensPanel.getHeight() / 2),
					rectangle.width * scaleFactor,
					rectangle.height * scaleFactor, this);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

    @Override
    public void actionPerformed(ActionEvent evt) {
        String cmd = evt.getActionCommand();
        System.out.println(cmd);
        if (cmd != null) {
        	if (cmd.equals("Lock Image")) {
        		setLocked(!locked, -1, -1);
        	}
        	if (cmd.equals("-")) {
        		if (scaleFactor > 2)
        			scaleFactor -= 1;
        	}
        	if (cmd.equals("+")) {
        		if (scaleFactor < 10)
        			scaleFactor += 1;
        	}
        }
        
    }

	public Magnifier getMagnifier() {
		return this;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked, int lockX, int lockY) {
		this.locked = locked;
		this.lockX = lockX;
		this.lockY = lockY;
		lockImage.setSelected(locked);
	}
}
