package rspsi;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * @author tom
 */
public class MenuActionListener implements ActionListener {
	public void actionPerformed(ActionEvent actionEvent) {
		SuiteLogic logic = Gui.logic;
		if (actionEvent.getActionCommand().equals("loadcache")) {
			try {
				logic.loadNewCache();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(Gui.logic.getSwingComponent(), "An error occurred whilst loading cache:\n" + e);
				e.printStackTrace();
			}
		}
	}
}
