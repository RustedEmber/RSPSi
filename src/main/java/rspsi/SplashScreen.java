/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rspsi;

import java.awt.*;
import javax.swing.*;

import rspsi.gui.Workspace;

/**
 *
 * @author Benjamin
 */
public class SplashScreen extends JWindow {

    /** Creates new window SplashScreen */
    public SplashScreen(Workspace workspace) {
//    	super();
//        ImageIcon icon = getSplashIcon();
//        JLabel imageLabel = new JLabel(icon);
//
//        Image image = icon.getImage();
//        int width = image.getWidth(this);
//        int height = image.getHeight(this);
//
//        Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
//        int xPos = (screenRes.width - width) / 2;
//        int yPos = (screenRes.height - height) / 2;
//
//        setBounds(xPos, yPos, width, height);
//        add(imageLabel, BorderLayout.CENTER);
//
//        setVisible(true);
//
//        try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
        super(workspace.getFrame());
        JLabel imageLabel = new JLabel(getSplashIcon());
        getContentPane().add(imageLabel, BorderLayout.CENTER);
        pack();
        Dimension screenSize =
          Toolkit.getDefaultToolkit().getScreenSize();
        Dimension labelSize = imageLabel.getPreferredSize();
        setLocation(screenSize.width/2 - (labelSize.width/2),
                    screenSize.height/2 - (labelSize.height/2));
        setVisible(true);
        try {
//        	Thread.sleep(5000);
        } catch(Exception e) {
        	e.printStackTrace();
        }
        setVisible(false);
    }

    public ImageIcon getSplashIcon() {
        ImageIcon icon = new ImageIcon("FileStore/media/Splash.png");
        return icon;
    }

    public Image getIconImage() {
        ImageIcon icon = new ImageIcon("FileStore/media/Logo.png");
        Image image = icon.getImage();
        return image;
    }

}
