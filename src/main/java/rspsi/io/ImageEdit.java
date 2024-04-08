package rspsi.io;


import com.l2fprod.common.propertysheet.PropertySheet;
import com.l2fprod.common.propertysheet.PropertySheetPanel;
import rspsi.Gui;
import rspsi.Main;
import rspsi.util.DataUtils;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ImageEdit extends JInternalFrame {
	public JList files;
	public JList images;
	public JButton addGroupButton;
	public JButton removeGroupButton;
	public JButton addImageButton;
	public JButton removeImageButton;
	public JButton importButton;
	public JButton exportButton;
	public JButton repackButton;
	public JLabel imageIDLabel;
	public JLabel widthLabel;
	public JLabel heightLabel;
	public JPanel main;
	public JPanel imagePanel;
	private PropertySheetPanel properties;
	private ArrayList<String> knownImages;
	private int[] knownHashes;
	private Archive jagArchive;
	private ImageArchive imageArchive;
	private String title;
	private boolean hasEdited = false;

	public ImageEdit(final int cacheFile, final ArrayList<String> knownImages) {
		add(main);
		setSize(600, 550);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				if (hasEdited) {
					int response = JOptionPane.showConfirmDialog(Gui.logic.getSwingComponent(), "This archive has been modified.\n" +
							"Are you sure you wish to exit without saving changes?", "Exit?", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.YES_OPTION) {
						dispose();
					}
				} else {
					dispose();
				}
			}
		});
		this.knownImages = knownImages;
		try {
			this.jagArchive = new Archive(Gui.logic.getCurrentCache().getIndice(0).getFile(cacheFile));
			this.imageArchive = new ImageArchive(jagArchive);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(Gui.logic.getSwingComponent(), "An error occurred whilst loading archive:\n" + e);
			e.printStackTrace();
		}
		reloadKnownHashes();
		populateFilesList();
		files.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				if (files.getSelectedIndex() != -1) {
					String selected = (String) files.getSelectedValue();
					if (selected != null) {
						populateImagesList();
						if (imageArchive.getImage(files.getSelectedIndex()).countImages() != 0) {
							images.setSelectedIndex(0);
						}
						removeGroupButton.setEnabled(true);
						addImageButton.setEnabled(true);
					} else {
						updateDisplayedImage();
						populateImagesList();
					}
				} else {
					removeGroupButton.setEnabled(false);
					addImageButton.setEnabled(false);
				}
			}
		});
		images.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				if (images.getSelectedIndex() != -1) {
					ImageBean i = imageArchive.getImage(files.getSelectedIndex()).getImageBean(images.getSelectedIndex());
					properties.readFromObject(i);
					properties.setEnabled(true);
					updateDisplayedImage();
					exportButton.setEnabled(true);
					importButton.setEnabled(true);
					removeImageButton.setEnabled(true);
				} else {
					properties.setEnabled(false);
					exportButton.setEnabled(false);
					importButton.setEnabled(false);
					removeImageButton.setEnabled(false);
				}
			}
		});
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				ImageGroup arch = imageArchive.getImage(files.getSelectedIndex());
				Image thisSprite = arch.getImage(images.getSelectedIndex());
				try {
					if (Gui.logic.saveImageToFile(getImageBytes(thisSprite))) {
						JOptionPane.showMessageDialog(Gui.logic.getSwingComponent(), "Image dumped sucessfully");
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(Gui.logic.getSwingComponent(), "An unknown error occurred!\n" + e);
					e.printStackTrace();
				}
			}
		});
		importButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					byte[] newImage = Gui.logic.loadImageFromFile();
					ImageGroup arch = imageArchive.getImage(files.getSelectedIndex());
					arch.replaceImage(images.getSelectedIndex(), newImage, Gui.logic.getSwingComponent());
					updateDisplayedImage();
					setEdited();
					JOptionPane.showMessageDialog(Gui.logic.getSwingComponent(), "Image replaced sucessfully");
				} catch (Exception e) {
					if (!(e instanceof NullPointerException)) {
						JOptionPane.showMessageDialog(Gui.logic.getSwingComponent(), "An unknown error occurred:\n" + e);
						e.printStackTrace();
					}
				}
			}
		});
		repackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					byte[] newData = imageArchive.repackArchive();
					Gui.logic.addOrEditFile(0, cacheFile, newData);
					hasEdited = false;
					setTitle(title);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(Gui.logic.getSwingComponent(), "An error occurred whilst repacking:\n" + e);
					e.printStackTrace();
				}
			}
		});
		addGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				ImageGroup a = new ImageGroup();
				String s = JOptionPane.showInputDialog("Enter a name for the new image group:");
				imageArchive.addImage(DataUtils.getHash(s + ".dat"), a);
				knownImages.add(s + ".dat");
				setEdited();
				reloadKnownHashes();
				populateFilesList();
			}
		});
		removeGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				int i = files.getSelectedIndex();
				imageArchive.removeImage(i);
				setEdited();
				populateFilesList();
				files.setSelectedIndex((i == 0) ? 0 : i - 1);
			}
		});
		imagePanel.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent componentEvent) {
				if (images.getSelectedIndex() != -1) {
					updateDisplayedImage();
				}
			}

			public void componentMoved(ComponentEvent componentEvent) {
				if (images.getSelectedIndex() != -1) {
					updateDisplayedImage();
				}
			}

			public void componentShown(ComponentEvent componentEvent) {
				if (images.getSelectedIndex() != -1) {
					updateDisplayedImage();
				}
			}

			public void componentHidden(ComponentEvent componentEvent) {
				if (images.getSelectedIndex() != -1) {
					updateDisplayedImage();
				}
			}
		});
		addImageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					byte[] newImage = Gui.logic.loadImageFromFile();
					ImageGroup arch = imageArchive.getImage(files.getSelectedIndex());
					if (newImage != null) {
						arch.addSprite(newImage, Gui.logic.getSwingComponent());
						populateImagesList();
						images.setSelectedIndex(arch.countImages() - 1);
						updateDisplayedImage();
						setEdited();
						JOptionPane.showMessageDialog(Gui.logic.getSwingComponent(), "Image added successfully.");
					} else {
						JOptionPane.showMessageDialog(Gui.logic.getSwingComponent(), "An error occurred whilst adding image.");
					}
				} catch (Exception e) {
					if (!(e instanceof NullPointerException)) {
						JOptionPane.showMessageDialog(Gui.logic.getSwingComponent(), "An unknown error occurred:\n" + e);
						e.printStackTrace();
					}
				}
			}
		});
		removeImageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				ImageGroup arch = imageArchive.getImage(files.getSelectedIndex());
				arch.removeSprite(images.getSelectedIndex());
				updateDisplayedImage();
				setEdited();
				JOptionPane.showMessageDialog(Gui.logic.getSwingComponent(), "Image removed successfully.");
			}
		});
		updateDisplayedImage();
	}

	private byte[] getImageBytes(Image image) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (image instanceof RenderedImage) {
			javax.imageio.ImageIO.write((RenderedImage) image, "PNG", baos);
		} else {
			throw new IOException("Image could not be rendered.");
		}
		return baos.toByteArray();
	}

	private void setEdited() {
		if (!hasEdited) {
			hasEdited = true;
			setTitle(title + "*");
		}
	}

	private void updateDisplayedImage() {
		if (images.getSelectedIndex() != -1) {
			Image image = imageArchive.getImage(files.getSelectedIndex()).getImage(images.getSelectedIndex());
			if (image != null) {
				BufferedImage scaled = ImageUtils.toBufferedImage(image.getScaledInstance(imagePanel.getWidth(), imagePanel.getHeight(), Image.SCALE_SMOOTH));
				imagePanel.getGraphics().drawImage(scaled, 0, 0, imagePanel);
			}
		}
	}

	private void reloadKnownHashes() {
		knownHashes = new int[knownImages.size()];
		for (int i = 0; i < knownHashes.length; i++) {
			knownHashes[i] = DataUtils.getHash(knownImages.get(i));
		}
	}

	private void populateFilesList() {
		DefaultListModel lm = new DefaultListModel();
		for (String s : knownImages) {
			lm.addElement(s);
		}
		files.setModel(lm);
		images.setModel(new DefaultListModel());
	}

	private void populateImagesList() {
		ImageGroup a = imageArchive.getImage(files.getSelectedIndex());
		DefaultListModel lm = new DefaultListModel();
		for (int i = 0; i < a.countImages(); i++) {
			lm.addElement("Image " + i);
		}
		images.setModel(lm);
	}

	private void createUIComponents() {
		properties = new PropertySheetPanel();
		properties.setMode(PropertySheet.VIEW_AS_FLAT_LIST);
		properties.setDescriptionVisible(true);
		properties.setSortingCategories(true);
		properties.setSortingProperties(true);
		properties.setEnabled(false);
	}
}
