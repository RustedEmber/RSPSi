/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewJFrame.java
 *
 * Created on Feb 20, 2011, 2:07:07 PM
 */
package rspsi.gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 *
 * @author Benjamin
 */
public final class Xml extends javax.swing.JFrame {

    /** Creates new form Xml */
    public Xml(String dir, String tag) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            initComponents();
            setVisible(true);
            initList(dir, tag);
        } catch (Exception ex) {
            Logger.getLogger(Xml.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initList(String dir, String tag) {
        loadXML(dir, tag);
        dataList.setListData(listData);
        dataList.setCellRenderer(new XmlCellRenderer());
    }

    public void loadXML(String dir, String tag) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(dir));
            doc.getDocumentElement().normalize();

            NodeList nodeLst = doc.getElementsByTagName(tag);

            listData = new String[nodeLst.getLength()][2];

            for (int abc = 0; abc < listData.length; abc++) {

                Node node = nodeLst.item(abc);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    NodeList nameElementList = element.getElementsByTagName("name");
                    Element nameElement = (Element) nameElementList.item(0);
                    NodeList name = nameElement.getChildNodes();
                    listData[abc][0] = ((Node) name.item(0)).getNodeValue();

                    NodeList typeElementList = element.getElementsByTagName("id");
                    Element typeElement = (Element) typeElementList.item(0);
                    NodeList type = typeElement.getChildNodes();
                    listData[abc][1] = ((Node) type.item(0)).getNodeValue();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void search() {
        String label = searchField.getText();
        if (label.equals(""))
            dataList.setListData(listData);
        else {
            String s = "";
            for (int abc = 0; abc < listData.length; abc++) {
                if (listData[abc][0].toLowerCase().contains(label.toLowerCase())
                        || listData[abc][1].toLowerCase().contains(label.toLowerCase()))
                    s += listData[abc][0] + "\t" + listData[abc][1] + "\n";
            }
            String[][] newData;
            if (s.equals("")) {
                newData = new String[1][2];
                newData[0] = new String[] {"No matches", "-1"};
            } else {
                String[] lines = s.split("\n");
                newData = new String[lines.length][1];
                for (int abc = 0; abc < newData.length; abc++) {
                    newData[abc] = lines[abc].split("\t");
                }
            }
            dataList.setListData(newData);
        }
    }

    private void initComponents() {

        xmlPanel = new javax.swing.JPanel();
        dataListPane = new javax.swing.JScrollPane();
        dataList = new javax.swing.JList();
        searchLabel = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        dataList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        dataListPane.setViewportView(dataList);

        searchLabel.setText("Search:");

        searchField.setText("");
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchFieldKeyPressed(evt);
            }
        });

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout xmlPanelLayout = new javax.swing.GroupLayout(xmlPanel);
        xmlPanel.setLayout(xmlPanelLayout);
        xmlPanelLayout.setHorizontalGroup(
            xmlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, xmlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchField, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchButton)
                .addContainerGap())
            .addComponent(dataListPane, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
        );
        xmlPanelLayout.setVerticalGroup(
            xmlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, xmlPanelLayout.createSequentialGroup()
                .addComponent(dataListPane, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(xmlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchButton)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchLabel))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(xmlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(xmlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    private void searchFieldKeyPressed(java.awt.event.KeyEvent evt) {
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER)
                search();
    }

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        search();
    }

	static class XmlCellRenderer extends JPanel implements ListCellRenderer {

        JLabel nameColumnLabel, idColumnLabel;

        XmlCellRenderer() {
            setLayout(new GridLayout(1, 2));
            nameColumnLabel = new JLabel();
            idColumnLabel = new JLabel();
            nameColumnLabel.setOpaque(true);
            idColumnLabel.setOpaque(true);
            add(nameColumnLabel);
            add(idColumnLabel);
        }

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            String nameData = ((String[]) value)[0];
            String idData = ((String[]) value)[1];
            nameColumnLabel.setText(nameData);
            idColumnLabel.setText(idData);
            if (isSelected) {
                nameColumnLabel.setBackground(list.getSelectionBackground());
                nameColumnLabel.setForeground(list.getSelectionForeground());
                idColumnLabel.setBackground(list.getSelectionBackground());
                idColumnLabel.setForeground(list.getSelectionForeground());
            } else {
                nameColumnLabel.setBackground(list.getBackground());
                nameColumnLabel.setForeground(list.getForeground());
                idColumnLabel.setBackground(list.getBackground());
                idColumnLabel.setForeground(list.getForeground());
            }
            setEnabled(list.isEnabled());
            setFont(list.getFont());
            return this;
        }
    }

    private String[][] listData;
    private javax.swing.JList dataList;
    private javax.swing.JScrollPane dataListPane;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JPanel xmlPanel;
}
