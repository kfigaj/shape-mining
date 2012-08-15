package shapemining.controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import sax.representation.primitives.Pair;
import shapemining.model.ImagesModel;
import shapemining.ui.ProcessDialog;
import shapemining.ui.SettingsDialogPanel;
import shapemining.ui.SkeletonPanel;
import shapemining.ui.TreePanel;
import utils.sax.representation.SAXEnhanced;


import discordfinder.algorithm.bruteforce.ImprovedBruteForce;
import discordfinder.algorithm.localsensivityhashing.LocalSensivityHashingAlgorithm;

/**
 * Main Controller class of the Shape Mining application.
 * Initializes and controls flow in the application.
 *
 */
public class ShapeMiningApp extends JFrame implements HiddenOnProccess {

	private static final long serialVersionUID = 1L;
	private static ShapeMiningApp thisClass;
	
	// Model
	private ImagesModel model;  //  @jve:decl-index=0:

	// UI
	private JPanel jContentPane = null;
	private SkeletonPanel skeletonPanel = null;
	private TreePanel fileTreePanel = null;

	private JMenuBar jMenuBar = null;
	private JMenuItem jMenuExitItem = null;
	private JMenuItem jNewListMenuItem = null;
	private JMenu jMenuTools = null;
	private JMenuItem jMenuKNNItem = null;
	private JMenu jMenuSettings = null;
	private JMenuItem jLocalHashingMenuItem = null;
	private JMenuItem jBruteForceMenuItem = null;
	
	private JDialog jSettingsDialog = null;  //  @jve:decl-index=0:visual-constraint="10,620"
	private JMenuItem jSettingsMenuItem = null;
	
	private ProcessDialog proccessDialog = null;
	
	private JMenu jMenuTest = null;
	private JMenuItem jKNNCrossTestMenuItem = null;
	private JMenuItem jLSHTestMenuItem = null;
	private JMenuItem jIBFTestMenuItem = null;
	
	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuExitItem() {
		if (jMenuExitItem == null) {
			jMenuExitItem = new JMenuItem("Exit");
			jMenuExitItem.setMnemonic(KeyEvent.VK_E);
			jMenuExitItem.setAccelerator(KeyStroke.getKeyStroke(
			        KeyEvent.VK_F10, ActionEvent.ALT_MASK));
			jMenuExitItem.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					if(thisClass != null)
						thisClass.setVisible(false);		
				}});
		}
		return jMenuExitItem;
	}

	/**
	 * This method initializes jNewListMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJNewListMenuItem() {
		if (jNewListMenuItem == null) {
			jNewListMenuItem = new JMenuItem("Create new SAX list");
			jNewListMenuItem.setMnemonic(KeyEvent.VK_C);
			jNewListMenuItem.setAccelerator(KeyStroke.getKeyStroke(
			        KeyEvent.VK_F1, ActionEvent.ALT_MASK));
			jNewListMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new java.io.File("."));
				    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				    // disable the "All files" option.
				    chooser.setAcceptAllFileFilterUsed(false); 
				    if (chooser.showOpenDialog(thisClass) == JFileChooser.APPROVE_OPTION) { 
				    	new CreateSAXListCommand(thisClass, chooser).execute();
				    	thisClass.setProcessing(true);
				    	if(model.getSaxRepresentation().isEmpty())
				    		JOptionPane.showMessageDialog(thisClass, "No image files have been found in selected directory.",
					    		    "Message", JOptionPane.INFORMATION_MESSAGE);
				    }
				}
			});
		}
		return jNewListMenuItem;
	}


	/**
	 * This method initializes jMenuTools	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuTools() {
		if (jMenuTools == null) {
			jMenuTools = new JMenu();
			jMenuTools.setText("Tools");
			jMenuTools.setMnemonic(KeyEvent.VK_T);
			jMenuTools.add(getJMenuKNNItem());
			jMenuTools.addSeparator();
			jMenuTools.add(getJLocalHashingMenuItem());
			jMenuTools.add(getJBruteForceMenuItem());
		}
		return jMenuTools;
	}
	
	private DefaultMutableTreeNode getSelectedNode(){
		if(fileTreePanel != null && fileTreePanel.getJImageTree() != null){
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) fileTreePanel.
			getJImageTree().getLastSelectedPathComponent();
			return node;
		}else
			return null;
	}
	
	/**
	 * This method initializes jMenuKNNItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuKNNItem() {
		if (jMenuKNNItem == null) {
			jMenuKNNItem = new JMenuItem();
			jMenuKNNItem.setText("K-Nearest Neighbour Algorithm");
			jMenuKNNItem.setMnemonic(KeyEvent.VK_K);
			jMenuKNNItem.setAccelerator(KeyStroke.getKeyStroke(
			        KeyEvent.VK_1, ActionEvent.ALT_MASK));
			jMenuKNNItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DefaultMutableTreeNode node = getSelectedNode();
					if (node != null){		
						Object nodeInfo = node.getUserObject();
						if (node.isLeaf()) {
							new KNNAlgorithmCommand(thisClass, nodeInfo).execute();
					    	thisClass.setProcessing(true); 
						} else {
							JOptionPane.showMessageDialog(thisClass, "Please select image to classify.",
					    		    "Message", JOptionPane.INFORMATION_MESSAGE);
						}
					}else{
						JOptionPane.showMessageDialog(thisClass, "Please create sax list first and/or select image to classify.",
				    		    "Message", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return jMenuKNNItem;
	}
	
	/**
	 * This method initializes jLocalHashingMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJLocalHashingMenuItem() {
		if (jLocalHashingMenuItem == null) {
			jLocalHashingMenuItem = new JMenuItem();
			jLocalHashingMenuItem.setText("Local Sensitivity Hashing Algorithm - Discord Finder");
			jLocalHashingMenuItem.setMnemonic(KeyEvent.VK_L);
			jLocalHashingMenuItem.setAccelerator(KeyStroke.getKeyStroke(
			        KeyEvent.VK_2, ActionEvent.ALT_MASK));
			jLocalHashingMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DefaultMutableTreeNode node = getSelectedNode();
					if (node != null){		
						Object nodeInfo = node.getUserObject();
						if (node.isLeaf()) {
							JOptionPane.showMessageDialog(thisClass, "Please select image group to find a discord within it.",
					    		    "Message", JOptionPane.INFORMATION_MESSAGE);
						} else {
							new LHDAlgorithmCommand(thisClass, nodeInfo).execute();
					    	thisClass.setProcessing(true); 
						}
					}else{
						JOptionPane.showMessageDialog(thisClass, "Please create sax list first and/or select image group to find a discord within it.",
				    		    "Message", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return jLocalHashingMenuItem;
	}
	
	/**
	 * This method initializes jBruteForceMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJBruteForceMenuItem() {
		if (jBruteForceMenuItem == null) {
			jBruteForceMenuItem = new JMenuItem();
			jBruteForceMenuItem.setText("Improved Brute Force Algorithm - Discord Finder");
			jBruteForceMenuItem.setMnemonic(KeyEvent.VK_I);
			jBruteForceMenuItem.setAccelerator(KeyStroke.getKeyStroke(
			        KeyEvent.VK_3, ActionEvent.ALT_MASK));
			jBruteForceMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DefaultMutableTreeNode node = getSelectedNode();
					if (node != null){		
						Object nodeInfo = node.getUserObject();
						if (node.isLeaf()) {
							JOptionPane.showMessageDialog(thisClass, "Please select image group to find a discord within it.",
					    		    "Message", JOptionPane.INFORMATION_MESSAGE);
						} else {
							new IBFDAlgorithmCommand(thisClass, nodeInfo).execute();
					    	thisClass.setProcessing(true); 
						}
					}else{
						JOptionPane.showMessageDialog(thisClass, "Please create sax list first and/or select image group to find a discord within it.",
				    		    "Message", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return jBruteForceMenuItem;
	}

	/**
	 * This method initializes jMenuSettings	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuSettings() {
		if (jMenuSettings == null) {
			jMenuSettings = new JMenu();
			jMenuSettings.setText("Settings");
			jMenuSettings.setMnemonic(KeyEvent.VK_S);
			jMenuSettings.add(getJSettingsMenuItem());
		}
		return jMenuSettings;
	}


	/**
	 * This method initializes jSettingsDialog	
	 * 	
	 * @return javax.swing.JDialog	
	 */
	private JDialog getJSettingsDialog() {
		if (jSettingsDialog == null) {
			jSettingsDialog = new JDialog(this, "Settings", true);
			jSettingsDialog.setSize(new Dimension(547, 324));
			jSettingsDialog.setResizable(false);
			jSettingsDialog.setContentPane(new SettingsDialogPanel(jSettingsDialog, this));
			jSettingsDialog.setLocationRelativeTo(this);
		}
		return jSettingsDialog;
	}

	/**
	 * This method initializes jSettingsMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJSettingsMenuItem() {
		if (jSettingsMenuItem == null) {
			jSettingsMenuItem = new JMenuItem();
			jSettingsMenuItem.setText("Set Settings");
			jSettingsMenuItem.setMnemonic(KeyEvent.VK_S);
			jSettingsMenuItem.setAccelerator(KeyStroke.getKeyStroke(
			        KeyEvent.VK_F2, ActionEvent.ALT_MASK));
			jSettingsMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					((SettingsDialogPanel) getJSettingsDialog().getContentPane())
					.setDefaultValues();
					getJSettingsDialog().setVisible(true);
				}
			});
		}
		return jSettingsMenuItem;
	}


	/**
	 * This method initializes jMenuTest	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuTest() {
		if (jMenuTest == null) {
			jMenuTest = new JMenu();
			jMenuTest.setText("Tests");
			jMenuTest.setMnemonic(KeyEvent.VK_E);
			jMenuTest.add(getJKNNCrossTestMenuItem());
			jMenuTest.addSeparator();
			jMenuTest.add(getJLSHTestMenuItem());
			jMenuTest.add(getJIBFTestMenuItem());
		}
		return jMenuTest;
	}


	/**
	 * This method initializes jKNNCrossTestMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJKNNCrossTestMenuItem() {
		if (jKNNCrossTestMenuItem == null) {
			jKNNCrossTestMenuItem = new JMenuItem();
			jKNNCrossTestMenuItem.setText("Cross Test K-Nearest Neighbour Algorithm");
			jKNNCrossTestMenuItem.setMnemonic(KeyEvent.VK_C);
			jKNNCrossTestMenuItem.setAccelerator(KeyStroke.getKeyStroke(
			        KeyEvent.VK_1, ActionEvent.CTRL_MASK));
			jKNNCrossTestMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(model != null){
						List<Pair<SAXEnhanced, String>> currentClassification = model.getCurrentClassification();
						DefaultMutableTreeNode root = model.getRoot();
						if (currentClassification != null && root != null && !currentClassification.isEmpty()){		
							new KNNCrossTestCommand(thisClass, currentClassification, root).execute();
						    thisClass.setProcessing(true); 
						} else {
							JOptionPane.showMessageDialog(thisClass, "Classification test needs appropriate data.",
					    		    "Message", JOptionPane.INFORMATION_MESSAGE);
						}
					}else{
						JOptionPane.showMessageDialog(thisClass, "Please create sax list first.",
				    		    "Message", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return jKNNCrossTestMenuItem;
	}

	/**
	 * This method initializes jLSHTestMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJLSHTestMenuItem() {
		if (jLSHTestMenuItem == null) {
			jLSHTestMenuItem = new JMenuItem();
			jLSHTestMenuItem.setText("LSH Discord Finder Algorithm - efficency test");
			jLSHTestMenuItem.setMnemonic(KeyEvent.VK_L);
			jLSHTestMenuItem.setAccelerator(KeyStroke.getKeyStroke(
			        KeyEvent.VK_2, ActionEvent.CTRL_MASK));
			jLSHTestMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(model != null){
						new DiscordTestCommand(thisClass, new LocalSensivityHashingAlgorithm()).execute();
						thisClass.setProcessing(true); 
					}else { 
						JOptionPane.showMessageDialog(thisClass, "Please create sax list first.",
				    		    "Message", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return jLSHTestMenuItem;
	}

	/**
	 * This method initializes jIBFMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJIBFTestMenuItem() {
		if (jIBFTestMenuItem == null) {
			jIBFTestMenuItem = new JMenuItem();
			jIBFTestMenuItem.setText("IBF Discord Finder Algorithm - efficency test");
			jIBFTestMenuItem.setMnemonic(KeyEvent.VK_I);
			jIBFTestMenuItem.setAccelerator(KeyStroke.getKeyStroke(
			        KeyEvent.VK_3, ActionEvent.CTRL_MASK));
			jIBFTestMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(model != null){
						new DiscordTestCommand(thisClass, new ImprovedBruteForce()).execute();
						thisClass.setProcessing(true); 
					}else { 
						JOptionPane.showMessageDialog(thisClass, "Please create sax list first.",
				    		    "Message", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return jIBFTestMenuItem;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				thisClass = new ShapeMiningApp();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}

	/**
	 * This is the default constructor
	 */
	public ShapeMiningApp() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {		
		//ui
		this.setPreferredSize(new Dimension(700, 600));
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setContentPane(getJContentPane());
		this.setTitle("Shape Mining");
		this.setJMenuBar(getNewJMenuBar());
		getJSettingsDialog();
		getProccessDialog();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getSkeletonPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}
	
	private JMenuBar getNewJMenuBar(){
		if(jMenuBar == null){
			jMenuBar = new JMenuBar();
			
			JMenu jMenuFile = new JMenu("File");
			jMenuFile.setMnemonic(KeyEvent.VK_F);
			jMenuFile.add(getJNewListMenuItem());
			jMenuFile.addSeparator();
			jMenuFile.add(getJMenuExitItem());
			
			jMenuBar.add(jMenuFile);
			jMenuBar.add(getJMenuTools());
			jMenuBar.add(getJMenuSettings());
			jMenuBar.add(getJMenuTest());
			
		}
		return jMenuBar;
	}
	
	public SkeletonPanel getSkeletonPanel(){
		if(skeletonPanel == null){
			skeletonPanel = new SkeletonPanel();
		}
		return skeletonPanel;
	}
	
	public void setSkeletonPanel(SkeletonPanel skeletonPanel) {
		this.skeletonPanel = skeletonPanel;
	}
	
	/**
	 * This method initializes jSettingsDialog	
	 * 	
	 * @return javax.swing.JDialog	
	 */
	private ProcessDialog getProccessDialog() {
		if (proccessDialog == null) {
			proccessDialog = new ProcessDialog(this, "Please Wait", ModalityType.APPLICATION_MODAL);
		}
		return proccessDialog;
	}
	
	
	@Override
	public void setProcessing(boolean processing) {
		if (proccessDialog != null) {
			proccessDialog.setVisible(processing);
		}
	}
	
	public ImagesModel getModel() {
		return model;
	}

	public void setModel(ImagesModel model) {
		this.model = model;
	}
	
	public void updateModel(){
		if(this.model != null){
			new UpdateSAXListCommand(thisClass).execute();
	    	thisClass.setProcessing(true);
		}
	}
	
	public TreePanel getFileTreePanel() {
		return fileTreePanel;
	}

	public void setFileTreePanel(TreePanel fileTreePanel) {
		this.fileTreePanel = fileTreePanel;
	}

	
}
