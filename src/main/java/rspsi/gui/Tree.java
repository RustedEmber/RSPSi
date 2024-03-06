package rspsi.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import rspsi.Main;
import rspsi.RSInterfaceConstants;
import rspsi.client.Interface;

public class Tree extends JScrollPane implements TreeSelectionListener {

    private Workspace workspace;
    private TreePath[] paths;
    private int[] rows;
    
	public Tree(Workspace workspace) {
    	super();
    	this.workspace = workspace;
    	tree = new JTree(new DefaultMutableTreeNode("Nothing loaded"));
    	tree.addTreeSelectionListener(this);
		setPreferredSize(new Dimension(265, 248));
        setViewportView(tree);
    }
	
	public void resetTree() {
    	tree = new JTree(new DefaultMutableTreeNode("Nothing loaded"));
    	tree.addTreeSelectionListener(this);
        setViewportView(tree);
	}

    public void updateTree(int layer) {
		selected.clear();
		expanded.clear();
		getNodeSequances("-0", (TreeNode) tree.getModel().getRoot());//needs to go before any actions r actually carried out

    	Interface topParent = Main.topParent;
        String topName = (topParent.id == topParent.parentId)
    			? topParent.id + ": [Top Parent]"
    			: topParent.id + ": [Parent Container]";

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(topName);

		if (topParent.children != null)
			for (int abc = 0; abc < topParent.children.length; abc++) {
				Interface child = Interface.interfaceCache[topParent.children[abc]];
				DefaultMutableTreeNode treeNode;
				if (child == null)
					break;
				else if (child.type == 0 && child.children != null && child.children.length > 0)
						treeNode = parseContainer(abc, topParent);
				else
					treeNode = new DefaultMutableTreeNode(tooltipForChild(abc, topParent));
	            root.add(treeNode);
			}

        tree.setModel(new DefaultTreeModel(root));
		selectNodeSequances("-0", (TreeNode) tree.getModel().getRoot());
//        setSelectionPaths(paths, rows);

        //        setSelectionPaths(paths, rows);
//        if (Main.editingLayers[0] > -1) {
//		    int goTo = 1;
//
//		    if (Main.parentLayers[0] > -1) {
//	        	goTo += Main.parentLayers[0];
//		    	tree.expandRow(goTo);the 
//		    	goTo++;
//	        }
//	
//	        if (layer != -1)
//	            goTo += layer;
//	        else
//	        	goTo += Main.editingLayers[0];
//	
//	       		tree.setSelectionRow(goTo);
//        } else
//       		tree.setSelectionRow(0);

		System.out.println("tree update: " + Main.editingID);
    }
	
	public void setSelectionPaths(TreePath[] paths, int[] rows) {
		tree.setSelectionPaths(paths);
		tree.setSelectionRows(rows);
	}
	
	public void setSelectionPaths(int[] expandRows, int[] selectRows) {
		for (int i = expandRows.length - 1; i > -1; i--)
			tree.expandRow(expandRows[i]);

		tree.setSelectionRows(selectRows);

//		if (expandRows == null || selectRows == null) {
//			tree.setSelectionRow(0);
//			return;
//		}
//
//		int goTo = 1;
//
//	    if (expandRows.length == 1) {
//	        if (selectRows[0] > -1) {
//
//			    if (expandRows[0] > -1) {
//		        	goTo += expandRows[0];
//			    	tree.expandRow(goTo);
//			    	goTo++;
//		        }
//		
//	        	goTo += selectRows[0];
//	
//	       		tree.setSelectionRow(goTo);
//	        } else
//	       		tree.setSelectionRow(0);
//	    }
//	    else {
//		    for (int i = 0; i < expandRows.length; i++) {
//			    if (expandRows[i] > -1 && (i > 0 && expandRows[i] != expandRows[i - 1])) {
//	//		    	tree.expandRow(expandRows[i] + 1);
//		        	goTo += expandRows[i];
//			    	tree.expandRow(goTo);
//			    	goTo++;
//			    }
//	//		    if (expandRows[i] > -1) {
//	//	        	goTo += expandRows[i];
//	//		    	goTo+=i;
//	//	        }
//				if (selectRows[i] > -1) {
//		    		goTo += selectRows[i];
//		
//		    		selectRows[i] = goTo;
//	//	    		if (expandRows[i] != expandRows[i + 1])
//	//	    			goTo += tree.getPathForRow(goTo).getPathCount() + 1;
//		        } else
//		        	selectRows[i] = 0;
//		    }
//			tree.setSelectionRows(selectRows);
//	    }

//		    int goTo = 1;
//		    for (int i = expandRows.length - 1; i > -1; i--) {
//			    if (expandRows[i] > -1) {
//			    	tree.expandRow(expandRows[i] + 1);
////		        	goTo += expandRows[i];
////			    	tree.expandRow(goTo);
////			    	goTo++;
//		        }
//		    }
//		    for (int i = 0; i < selectRows.length; i++) {
//			    if (expandRows[i] > -1) {
//		        	goTo += expandRows[i];
//			    	goTo+=i;
//		        }
//				if (selectRows[i] > -1) {
//	        		goTo += selectRows[i];
//	
//	        		selectRows[i] = goTo;
//		        	goTo += tree.getPathForRow(goTo).getPathCount() + 1;
//		        } else
//		        	selectRows[i] = 0;
//		    }
//	
//       		tree.setSelectionRows(selectRows);

       		
//	    		int offset = 1;
//	    		for (int i = 0; i < expandRows.length; i++) {
//	    			offset += expandRows[i];
//	    			tree.expandRow(offset);
//	    			offset++;
//	    		}
//	    
//	    		for (int j = 0; j < selectRows.length; j++)
//	    			selectRows[j] += offset;
//	    		tree.setSelectionRows(selectRows);

	}

	public DefaultMutableTreeNode parseContainer(int id, Interface parent) {
		Interface subParent = Interface.interfaceCache[parent.children[id]];
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(tooltipForChild(id, parent));

		if (subParent.children != null)
			for (int abc = 0; abc < subParent.children.length; abc++) {
				Interface child = Interface.interfaceCache[subParent.children[abc]];
				DefaultMutableTreeNode childT = new DefaultMutableTreeNode(tooltipForChild(abc, subParent));
	
				if (child != null && child.children != null && child.type == 0 && child.children.length > 0) {
					DefaultMutableTreeNode childTl = parseContainer(abc, subParent);
					root.add(childTl);
				}
				root.add(childT);
			}
		return root;
	}


	ArrayList<String> selected = new ArrayList<String>();
	ArrayList<String> expanded = new ArrayList<String>();
	
	public void getNodeSequances(String curSeq, TreeNode n)
	{
		for(int i = 0;  i < n.getChildCount(); i++)
		{
			TreeNode node = n.getChildAt(i);
			if(tree.isPathSelected(new TreePath(((DefaultTreeModel)tree.getModel()).getPathToRoot(node))))
				selected.add(curSeq+"-"+i);
			if(tree.isExpanded(new TreePath(((DefaultTreeModel)tree.getModel()).getPathToRoot(node))))
				expanded.add(curSeq+"-"+i);
			getNodeSequances(curSeq+"-"+i, node);
		}
	}
	
	public void selectNodeSequances(String curSeq, TreeNode n)
	{
		for(int i = 0;  i < n.getChildCount(); i++)
		{
			TreeNode node = n.getChildAt(i);
			if(selected.contains(curSeq+"-"+i))
				tree.addSelectionPath(new TreePath(((DefaultTreeModel)tree.getModel()).getPathToRoot(node)));
			if(expanded.contains(curSeq+"-"+i))
				tree.expandPath(new TreePath(((DefaultTreeModel)tree.getModel()).getPathToRoot(node)));
			selectNodeSequances(curSeq+"-"+i, node);
		}
	}
	
	public void valueChanged(TreeSelectionEvent evt) { //this fires when i select something on the tree

		System.out.println("valueChanged()");
		TreePath[] paths =  tree.getSelectionPaths();
		if (paths == null)
			return;

//		tree.setSel
//		Arrays.sort(paths, new AlphaNumericalComparator());

//		String[] pathStrings = new String[paths.length];
//		for (int i = 0; i < paths.length; i++) {
//			pathStrings[i] = paths[i].toString();
//		}
//
//		String[] nodeStrings = new String[paths.length];
//		for (int i = 0; i < paths.length; i++) {
//			nodeStrings[i] = paths[i].getLastPathComponent().toString();
//		}


		Main.resetArrays(paths.length);
		for (int abc = 0; abc < paths.length; abc++) {
//			System.out.println("selectionRows[" + abc + "] = " + tree.getSelectionRows()[abc]);
			String nodeString = paths[abc].getLastPathComponent().toString();
			if (nodeString.startsWith("["))
				nodeString = nodeString.substring(1, nodeString.length() - 1);

			System.out.println(nodeString);

            if (nodeString != null && !(nodeString.equalsIgnoreCase("Nothing loaded"))) {
//            	Main.getWorkspace().getValuesEditor().promptSave();

            	int id, layer = -1;
		        if (nodeString.contains("Parent"))
		        	id = Integer.parseInt(nodeString.substring(0, nodeString.indexOf(":")));
		        else {
		        	id = Integer.parseInt(nodeString.substring(nodeString.indexOf(": [") + 3, nodeString.indexOf("]")).split(", ")[0]);
		        	layer = Integer.parseInt(nodeString.substring(6, nodeString.indexOf(": [")));
		        }

		        Main.editingID[abc] = id;
		        Main.editingLayers[abc] = layer;
		        Main.editing[abc] = Interface.interfaceCache[id];
				try {
					String line = paths[abc].getParentPath().toString();
//					System.out.println("line: " + line);

					if (line.endsWith("[Top Parent]]")
							|| line.endsWith("[Parent Container]]")) {
						Main.parentLayers[abc] = -1;
				        Main.editingParents[abc] = Main.topParent;
					} else {
						Main.parentLayers[abc] = Integer.parseInt(line.substring(line.lastIndexOf("Layer ") + 6, line.lastIndexOf(":")));

						String line1 = line.substring(line.lastIndexOf("Layer "), line.lastIndexOf(" - "));
						String line2 = line1.substring(line1.indexOf("[") + 1, line1.indexOf(", "));

//						System.out.println("line1: " + line1);
//						System.out.println("line2: " + line2);

				        Main.editingParents[abc] = Interface.interfaceCache[Integer.parseInt(line2)];
					}
				} catch (NullPointerException npe) {
//					npe.printStackTrace();
				}
	        }
		}
//		setSelectionPaths(tree.getSelectionPaths(), tree.getSelectionRows());
		
	}

	public String tooltipForChild(int id, Interface parent) {
		Interface child = Interface.interfaceCache[parent.children[id]];

		if (child == null)
			return "null";

		String tooltip = "";
		tooltip += "Layer " + id + ": [" + parent.children[id] + ", " + parent.childX[id] + ", " + parent.childY[id] + "]";
		tooltip += " - ";

		String[] types = RSInterfaceConstants.types;
		if (child.type > -1 && child.type < types.length)
			tooltip += types[child.type];
		else
			tooltip += "Unknown Type";

		return tooltip;
	}

	public JTree getTree() {
		return tree;
	}

	public JScrollPane getTreePane() {
		return this;
	}

	private JTree tree;

}

class AlphaNumericalComparator implements Comparator<TreePath> {

    public int compare(TreePath pathA, TreePath pathB) {
//    	[0: [Top Parent], Layer 0: [1, 12, 20] - Sprite]
//    	[0: [Top Parent], Layer 100: [101, 310, 250] - Box]

//    	[0: [Top Parent], Layer 0: [1, 12, 20] - Sprite]
//    	[0: [Top Parent], Layer 1: [2, 12, 80] - Sprite]

    	String pathAString = pathA.getPath().toString().toLowerCase(); //path A to string
    	String pathBString = pathB.getPath().toString().toLowerCase(); //path B to string

    	char[] pathAChars = pathAString.toCharArray(); //path A to char array
    	char[] pathBChars = pathBString.toCharArray(); //path B to char array

    	int pathALength = pathAString.length(); //path A char array to length
    	int pathBLength = pathBString.length(); //path B char array to length

    	int length = pathALength > pathBLength ? pathALength : pathBLength; //greatest length of A & B

    	for (int i = 0; i < length; i++) { //loop through biggest length
    		if (pathALength < i || pathBLength < i) //if length A / B is shorter than current cycle
    			return pathALength < i ? 1 : -1; //return A / B as lesser value

    		char charA = pathAChars[i];
    		char charB = pathBChars[i];

			if (isCharInt(charA) && isCharInt(charB)) { // if both chars are integers
				int result = -(new Integer("" + charA).compareTo(new Integer("" + charB)));
    			if (result != 0)
    				return result;
    			else
    				continue;
    		}

    		if ((isCharInt(charA) && !isCharInt(charB)) || (!isCharInt(charA) && isCharInt(charB))) {
    			
    		}


    		
    		int result = compareNextChar("" + pathAChars[i], "" + pathBChars[i]);
    		System.out.println("char[" + i + "] result = " + result);
    		if (result != 0)
    			return result;
    		else
    			continue;
    	}
//    		String charA = "" + pathAChars[i]; //charA at current index
//    		String charB = "" + pathBChars[i]; //charB at current index
//
//    		Integer a = null, b = null; //this block checks if A || B is a number - if not, nfe is thrown
//			try {
//				a = Integer.parseInt(charA);
//			} catch (NumberFormatException nfe) {
//			}
//
//			try {
//				b = Integer.parseInt(charB);
//			} catch (NumberFormatException nfe) {
//			}
//
//			if (a == null || b == null) { //if char A || B is NOT a number
//				int result = (a == null ? charA : charB)
//						.compareToIgnoreCase("" + (a == null ? b : a)); //compare A/a & B/b
//				if (result != 0)
//					return result; //if chars are NOT the same, return result.
//				else
//					continue; //else, continue next loop cycle
//
//			} else if (a != null && b != null) { //if char A && B are both numbers
//				int result = -(a.compareTo(b)); //compare a & b
//				if (result != 0)
//					return result; //if chars are NOT the same, return result.
//				else
//					continue; //else, continue next loop cycle
//
//			} else {
//	    		int result = charA.compareToIgnoreCase(charB); //compare chars A & B
//				if (result != 0)
//					return result; //if chars are NOT the same, return result
//				else
//					continue; //else, continue next loop cycle
//			}
//
//    	}

    	return 0;
    }

    public int compareNextChar(String charA, String charB) {

		Integer a = null, b = null; //this block checks if A || B is a number - if not, nfe is thrown
		try {
			a = Integer.parseInt(charA);
		} catch (NumberFormatException nfe) {
		}

		try {
			b = Integer.parseInt(charB);
		} catch (NumberFormatException nfe) {
		}

		if (a == null || b == null) { //if char A || B is NOT a number
			return (a == null ? charA : charB)
					.compareToIgnoreCase("" + (a == null ? b : a)); //return result.

		} else if (a != null && b != null) { //if char A && B are both numbers
			return -(a.compareTo(b)); //return result.

		} else {
			return charA.compareToIgnoreCase(charB); //return result
		}

    }
    
    public boolean isCharInt(char c) {
    	try {
        	return true;
    	} catch (NumberFormatException nfe) {
        	return false;
    	}
    }

}
