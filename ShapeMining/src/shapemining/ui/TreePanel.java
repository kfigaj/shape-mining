package shapemining.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

/**
 * Left top sax Tree Panel. 
 */
public class TreePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTree jImageTree = null;

	public JTree getJImageTree() {
		return jImageTree;
	}


	/**
	 * This is the default constructor
	 */
	public TreePanel(DefaultMutableTreeNode root) {
		super();
		initialize(root);
	}
	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize(DefaultMutableTreeNode root) {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.gridx = 0;
		this.setLayout(new GridBagLayout());
		this.add(getJImageTree(root), gridBagConstraints);
	}

	/**
	 * This method initializes jImageTree	
	 * 	
	 * @return javax.swing.JTree	
	 */
	private JTree getJImageTree(DefaultMutableTreeNode root) {
		if (jImageTree == null) {
			jImageTree = new JTree(root);
			jImageTree.getSelectionModel().setSelectionMode(
					TreeSelectionModel.SINGLE_TREE_SELECTION);
		}
		
		return jImageTree;
	}


}
