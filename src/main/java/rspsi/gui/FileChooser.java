package rspsi.gui;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class FileChooser extends JFileChooser {

	public static JDialog jd;

    public FileChooser(Frame parent, String dir, String title) {
    	super(dir);
        setDialogTitle(title);
        setMultiSelectionEnabled(false);
        setPreferredSize(new Dimension(750, 375));
        if (getDialogTitle().contains("Reference..."))
        	setAccessory(new ImagePreview(this));

        addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				fcActionPerformed(evt);
			}
		});
        setFileFilter(new FileFilter() {

            public boolean accept(File file) {
            	String extension = file.getName().toLowerCase().substring(file.getName().toLowerCase().lastIndexOf(".") + 1);

                if (getDialogTitle().contains("Reference"))
	            	return (extension.equals("bmp")
	            			|| extension.equals("jfif") || extension.equals("jpg")
	            			|| extension.equals("jpe") || extension.equals("jpeg")
							|| extension.equals("gif") || extension.equals("png")
							|| extension.equals("tif") || extension.equals("tiff")
							|| extension.equals("tga") || file.isDirectory());
                
                else if (getDialogTitle().contains("Export") || getDialogTitle().contains("Import"))
	            	return (extension.equals("rsi") || file.isDirectory());

                else if (getDialogTitle().contains("File"))
	            	return (extension.equals("dat") || file.isDirectory());

                else
                	return true;
            }

            public String getDescription() {
                if (getDialogTitle().contains("Reference"))
                	return "All Image Types (*.bmp, *.jfif, *.jpg, *.jpe, *.jpeg, *.gif, *.png, *.tif, *.tiff, *.tga)";

                else if (getDialogTitle().contains("Export") || getDialogTitle().contains("Import"))
                	return "Rspsi File Types (*.rsi)";

                else if (getDialogTitle().contains("File"))
                	return "Data File Types (*.dat)";

                else
                	return "All File Types (*.*)";
            }
        });

    	jd = new JDialog(parent);
        jd.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jd.setPreferredSize(new Dimension(750, 375));
        jd.setTitle(title);
        jd.getContentPane().add(this);
        jd.pack();
        jd.setLocationRelativeTo(null);
        jd.setVisible(true);
    }

    private void fcActionPerformed(ActionEvent evt) {                                            
		String event = evt.getActionCommand();
		if (event.equalsIgnoreCase("ApproveSelection")
				|| event.equalsIgnoreCase("CancelSelection")) {
			jd.setVisible(false);
            jd.dispose();
            
        }
    }

}

class ImagePreview extends JComponent implements PropertyChangeListener {
    ImageIcon thumbnail = null;
    File file = null;

    public ImagePreview(JFileChooser fc) {
        setPreferredSize(new Dimension(100, 50));
        fc.addPropertyChangeListener(this);
    }

    public void loadImage() {
        if (file == null) {
            thumbnail = null;
            return;
        }

        //Don't use createImageIcon (which is a wrapper for getResource)
        //because the image we're trying to load is probably not one
        //of this program's own resources.
        ImageIcon tmpIcon = new ImageIcon(file.getPath());
        if (tmpIcon != null)
            if (tmpIcon.getIconWidth() > 90)
                thumbnail = new ImageIcon(tmpIcon.getImage().
                                          getScaledInstance(90, -1,
                                                      Image.SCALE_DEFAULT));
            else //no need to miniaturize
                thumbnail = tmpIcon;
    }

    public void propertyChange(PropertyChangeEvent e) {
        boolean update = false;
        String prop = e.getPropertyName();

        //If the directory changed, don't show an image.
        if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(prop)) {
            file = null;
            update = true;

        //If a file became selected, find out which one.
        } else if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(prop)) {
            file = (File) e.getNewValue();
            update = true;
        }

        //Update the preview accordingly.
        if (update) {
            thumbnail = null;
            if (isShowing()) {
                loadImage();
                repaint();
            }
        }
    }

    protected void paintComponent(Graphics g) {
        if (thumbnail == null)
            loadImage();

        if (thumbnail != null) {
            int x = getWidth()/2 - thumbnail.getIconWidth()/2;
            int y = getHeight()/2 - thumbnail.getIconHeight()/2;

            if (y < 0) {
                y = 0;
            }

            if (x < 5) {
                x = 5;
            }
            thumbnail.paintIcon(this, g, x, y);
        }
    }

}

