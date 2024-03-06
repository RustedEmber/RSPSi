package rspsi.gui;

import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.*;

import rspsi.Main;
import rspsi.client.Interface;

public class ValuesEditor extends JPanel implements ActionListener, TableModelListener, TreeSelectionListener {

	private Workspace workspace;

	private Interface editing;
	private String[][] tempValuesArray;
	private String[] tempValuesCompareType;
	private String[] tempRequiredValues;
	private JLabel idLabel;
	private JTree tree;
	private TreePath treeSelectionPath;
	private int treeSelectionRow;
	private JTable table;

	public ValuesEditor(Workspace workspace) {
//		super("RSPSi - Values Editor");
		this.workspace = workspace;
		initComponents();
		setVisible(false);
	}

	public void update() {
		if (Main.editing != null && Main.editing[0] != null) {
			editing = Main.editing[0];
			idLabel.setText("" + editing.id);
			load();
			updateTable(null);
			updateTree();
		}
	}

	public void load() {
		tempValuesArray = new String[0][];
		tempRequiredValues = new String[0];
		tempValuesCompareType = new String[0];

		if (editing != null) {
			if (editing.valueIndexArray != null) {
				int[][] array = editing.valueIndexArray;
				tempValuesArray = new String[array.length][];

				for (int abc = 0; abc < array.length; abc++) {
					tempValuesArray[abc] = new String[array[abc].length];

					for (int def = 0; def < array[abc].length; def++)
						tempValuesArray[abc][def] = "" + array[abc][def];
				}
			}

			if (editing.requiredValues != null) {
				int[] array = editing.requiredValues;
				tempRequiredValues = new String[array.length];

				for (int abc = 0; abc < array.length; abc++)
					tempRequiredValues[abc] = "" + array[abc];
			}

			if (editing.valueCompareType != null) {
				int[] array = editing.valueCompareType;
				tempValuesCompareType = new String[array.length];

				for (int abc = 0; abc < array.length; abc++)
					tempValuesCompareType[abc] = "" + array[abc];
			}
		}
	}

	public void save() {
		if (editing != null) {
			if (tempValuesArray != null) {
				editing.valueIndexArray = new int[tempValuesArray.length][];

				for (int abc = 0; abc < editing.valueIndexArray.length; abc++) {
					editing.valueIndexArray[abc] = new int[tempValuesArray[abc].length];

					for (int def = 0; def < editing.valueIndexArray[abc].length; def++)
						editing.valueIndexArray[abc][def] = Integer.parseInt(tempValuesArray[abc][def]);
				}
			}

			if (tempRequiredValues != null) {
				editing.requiredValues = new int[tempRequiredValues.length];

				for (int abc = 0; abc < editing.requiredValues.length; abc++)
					editing.requiredValues[abc] = Integer.parseInt(tempRequiredValues[abc]);
			}

			if (tempValuesCompareType != null) {
				editing.valueCompareType = new int[tempValuesCompareType.length];

				for (int abc = 0; abc < editing.valueCompareType.length; abc++)
					editing.valueCompareType[abc] = Integer.parseInt(tempValuesCompareType[abc]);
			}
			
		}
	}

	public void insert() {
		if (editing != null && treeSelectionPath != null) {
			int rowCount = table.getRowCount();

			String nodeString = treeSelectionPath.getLastPathComponent().toString();
			if (nodeString.startsWith("ValuesIndex")) {
				int childCount = ((MutableTreeNode) treeSelectionPath.getLastPathComponent()).getChildCount();

				String[][] newValueArray = new String[childCount + 1][];
				newValueArray[childCount] = new String[] { "0" };

				if (tempValuesArray != null)
					System.arraycopy(tempValuesArray, 0, newValueArray, 0, tempValuesArray.length);
				tempValuesArray = newValueArray;

				updateTree();
			}

			if (nodeString.startsWith("valueArray")) {
				String[] newValueArray = new String[rowCount + 1];
				newValueArray[rowCount] = "0";

				int selected = Integer.parseInt(nodeString.substring(nodeString.indexOf("[") + 1, nodeString.indexOf("]")));
				if (tempValuesArray != null)
					System.arraycopy(tempValuesArray[selected], 0, newValueArray, 0, rowCount);
				tempValuesArray[selected] = newValueArray;

				updateTable(tempValuesArray[selected]);
			}
	
			if (nodeString.equals("ValuesRequired")) {
				String[] newValuesRequired = new String[rowCount + 1];
				newValuesRequired[rowCount] = "0";

				if (tempRequiredValues != null)
					System.arraycopy(tempRequiredValues, 0, newValuesRequired, 0, rowCount);
				tempRequiredValues = newValuesRequired;

				updateTable(tempRequiredValues);
			}
	
			if (nodeString.equals("ValuesCompareType")) {
				String[] newValuesCompareType = new String[rowCount + 1];
				newValuesCompareType[rowCount] = "0";

				if (tempValuesCompareType != null)
					System.arraycopy(tempValuesCompareType, 0, newValuesCompareType, 0, rowCount);
				tempValuesCompareType = newValuesCompareType;

				updateTable(tempValuesCompareType);
			}
		}
	}

	public void remove() {
		if (editing != null && treeSelectionPath != null) {
			int rowCount = table.getRowCount();

			String nodeString = treeSelectionPath.getLastPathComponent().toString();
			if (nodeString.startsWith("ValuesIndex")) {
				int childCount = ((MutableTreeNode) treeSelectionPath.getLastPathComponent()).getChildCount();
				if (childCount == 0)
					return;

				String[][] newValueArray = new String[childCount - 1][];

				if (tempValuesArray != null)
					System.arraycopy(tempValuesArray, 0, newValueArray, 0, tempValuesArray.length - 1);
				tempValuesArray = newValueArray;

				updateTree();
			}

			if (nodeString.startsWith("valueArray")) {
				String[] newValueArray = new String[rowCount - 1];

				int selected = Integer.parseInt(nodeString.substring(nodeString.indexOf("[") + 1, nodeString.indexOf("]")));
				if (tempValuesArray != null)
					System.arraycopy(tempValuesArray[selected], 0, newValueArray, 0, rowCount - 1);
				tempValuesArray[selected] = newValueArray;

				updateTable(tempValuesArray[selected]);
			}

			if (nodeString.equals("ValuesRequired")) {
				String[] newValuesRequired = new String[rowCount - 1];

				if (tempRequiredValues != null)
					System.arraycopy(tempRequiredValues, 0, newValuesRequired, 0, rowCount - 1);
				tempRequiredValues = newValuesRequired;

				updateTable(tempRequiredValues);
			}
	
			if (nodeString.equals("ValuesCompareType")) {
				String[] newValuesCompareType = new String[rowCount - 1];

				if (tempValuesCompareType != null)
					System.arraycopy(tempValuesCompareType, 0, newValuesCompareType, 0, rowCount - 1);
				tempValuesCompareType = newValuesCompareType;

				updateTable(tempValuesCompareType);
			}
		}
	}

	private void initComponents() {

		JLabel titleLabel = new JLabel("Values Configuration Editor");
        idLabel = new JLabel("");
        JScrollPane treePane = new JScrollPane();
        tree = new JTree();
        JScrollPane tablePane = new JScrollPane();
        table = new JTable();

        JPanel actionsPanel = new JPanel();
        JLabel actionsLabel = new JLabel("Actions:");
        JSeparator separator1 = new JSeparator(), separator2 = separator1;

        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(this);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);

        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(this);

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(this);

//        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        setTitle("Values Editor - RSPSi");
//        setAlwaysOnTop(true);
        setMinimumSize(new Dimension(425, 250));

//        actionsPanel.setPreferredSize(new Dimension(100, 210));

        GroupLayout actionsPanelLayout = new GroupLayout(actionsPanel);
        actionsPanel.setLayout(actionsPanelLayout);
        actionsPanelLayout.setHorizontalGroup(
            actionsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(actionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(actionsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(separator1, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                    .addComponent(actionsLabel)
                    .addComponent(separator2, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                    .addComponent(applyButton, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                    .addComponent(cancelButton, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                    .addComponent(removeButton, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(insertButton, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE))
                .addContainerGap())
        );
        actionsPanelLayout.setVerticalGroup(
            actionsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(actionsPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(actionsLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(applyButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separator2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(insertButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removeButton)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Values Config")));
        tree.addTreeSelectionListener(this);
        treePane.setViewportView(tree);

        table.setModel(new DefaultTableModel(
            new String [] {
                "Values"
            }, 0
        ));
        table.getModel().addTableModelListener(this);
        tablePane.setViewportView(table);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(treePane, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(titleLabel)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(idLabel)
                    .addComponent(tablePane, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(actionsPanel, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(titleLabel)
                    .addComponent(idLabel))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(tablePane, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                    .addComponent(treePane, GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)))
            .addComponent(actionsPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
        );

//        pack();
    }

    public void updateTree() {
		DefaultMutableTreeNode topNode = new DefaultMutableTreeNode("Values Config");
		
    	DefaultMutableTreeNode indexNode = new DefaultMutableTreeNode("ValuesIndex");
		if (tempValuesArray != null)
    		for (int abc = 0; abc < tempValuesArray.length; abc++)
    			indexNode.add(new DefaultMutableTreeNode("valueArray[" + abc + "]"));

    	topNode.add(indexNode);
		topNode.add(new DefaultMutableTreeNode("ValuesRequired"));
		topNode.add(new DefaultMutableTreeNode("ValuesCompareType"));
		
		tree.setModel(new DefaultTreeModel(topNode));
		tree.expandRow(1);
		tree.setSelectionPath(treeSelectionPath);
		tree.setSelectionRow(treeSelectionRow);
    }

	public void updateTable(String[] values) {
		if (values == null)
			table.setModel(new DefaultTableModel(0, 0));

		else {
			table.setModel(new DefaultTableModel(new String[values.length][1], new String[] { "Values" }));
			for (int abc = 0; abc < values.length; abc++)
				table.getModel().setValueAt(values[abc], abc, 0);
	        table.getModel().addTableModelListener(this);
		}
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		System.out.println(cmd);
		if (cmd != null) {
			if (cmd.equals("Apply"))
				save();

			if (cmd.equals("Insert"))
				insert();
			
			if (cmd.equals("Remove"))
				remove();
		}
		
	}

	@Override
	public void valueChanged(TreeSelectionEvent evt) {
		if (editing != null && evt.getNewLeadSelectionPath() != null) {

			treeSelectionPath = evt.getNewLeadSelectionPath();
			if (tree.getMinSelectionRow() > -1)
				treeSelectionRow = tree.getMinSelectionRow();
			updateTable(null);

			String nodeString = treeSelectionPath.getLastPathComponent().toString();
			System.out.println(nodeString);
			System.out.println(treeSelectionRow);
			if (nodeString.startsWith("valueArray")) {
				int selected = Integer.parseInt(nodeString.substring(nodeString.indexOf("[") + 1, nodeString.indexOf("]")));
				if (tempValuesArray != null && tempValuesArray.length > selected && selected > -1)
					updateTable(tempValuesArray[selected]);
//					updateTable(editing.valueIndexArray[selected]);
			}
	
			if (nodeString.equals("ValuesRequired"))
				updateTable(tempRequiredValues);
//				updateTable(editing.requiredValues);
	
			if (nodeString.equals("ValuesCompareType"))
				updateTable(tempValuesCompareType);
//				updateTable(editing.valueCompareType);
		}
	}

	@Override
	public void tableChanged(TableModelEvent arg0) {
		if (arg0.getFirstRow() != -1 && tree.getSelectionPath() != null) {
			String nodeString = tree.getSelectionPath().getLastPathComponent().toString();
			int row = arg0.getFirstRow();
			String value = null;
			if (table.getValueAt(row, 0) != null)
				value = (String) table.getValueAt(row, 0);

			if (value != null) {
				if (nodeString.startsWith("valueArray")) {
					int abc = Integer.parseInt(nodeString.substring(
							nodeString.indexOf("[") + 1,
							nodeString.indexOf("]")));

					if (tempValuesArray[abc].length > row)
						tempValuesArray[abc][row] = value;
				}

				else if (nodeString.equals("ValuesRequired"))
					if (tempRequiredValues.length > row)
						tempRequiredValues[row] = value;

				else if (nodeString.equals("ValuesCompareType"))
					if (tempValuesCompareType.length > row)
						tempValuesCompareType[row] = value;

			}
		}
		
	}

	public JPanel getEditorPane() {
		return this;
	}

}
