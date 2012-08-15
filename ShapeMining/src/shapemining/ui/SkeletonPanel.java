package shapemining.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import sax.util.test.Settings;
import shapemining.controller.ShapeMiningApp;
import utils.sax.representation.SAXEnhanced;


/**
 * Panel that initializes empty skeleton of application.
 *
 */
public class SkeletonPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JSplitPane jTopSplitPane = null;
	private JPanel jBottomPanel = null;  //  @jve:decl-index=0:visual-constraint="31,229"
	private JScrollPane jTopLeftScrollPane = null;
	private JScrollPane jTopRightScrollPane = null;
	private JTabbedPane jBottomTabbedPane = null;
	/**
	 * This is the default constructor
	 */
	public SkeletonPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(632, 566);
		this.setLayout(new GridLayout(2,1));
		this.add(getJTopSplitPane(), null);
		this.add(getJBottomPanel(), null);
	}

	/**
	 * This method initializes jTopSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJTopSplitPane() {
		if (jTopSplitPane == null) {
			jTopSplitPane = new JSplitPane();
			jTopSplitPane.setLeftComponent(getJTopLeftScrollPane());
			jTopSplitPane.setRightComponent(getJTopRightScrollPane());
			
			jTopSplitPane.setOneTouchExpandable(true);
			jTopSplitPane.setDividerLocation(200);
		}
		return jTopSplitPane;
	}

	/**
	 * This method initializes jBottomPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJBottomPanel() {
		if (jBottomPanel == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			jBottomPanel = new JPanel();
			jBottomPanel.setLayout(new GridBagLayout());
			jBottomPanel.setSize(new Dimension(8, 8));
			jBottomPanel.add(getJBottomTabbedPane(), gridBagConstraints);
		}
		return jBottomPanel;
	}

	/**
	 * This method initializes jTopLeftScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	public JScrollPane getJTopLeftScrollPane() {
		if (jTopLeftScrollPane == null) {
			jTopLeftScrollPane = new JScrollPane();
			jTopLeftScrollPane.setMinimumSize(new Dimension(100, 50));
		}
		return jTopLeftScrollPane;
	}

	/**
	 * This method initializes jTopRightScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	public JScrollPane getJTopRightScrollPane() {
		if (jTopRightScrollPane == null) {
			jTopRightScrollPane = new JScrollPane();
			jTopRightScrollPane.setMinimumSize(new Dimension(100, 50));
		}
		return jTopRightScrollPane;
	}

	/**
	 * This method initializes jBottomTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	public JTabbedPane getJBottomTabbedPane() {
		if (jBottomTabbedPane == null) {
			jBottomTabbedPane = new JTabbedPane();
		}
		return jBottomTabbedPane;
	}
	
	public void createResultPanel(String title, String shortTitle, int type, String resultString){
		ResultPanel resultPanel = new ResultPanel(title);
		resultString += Settings.getParametersAsString(type);
		resultPanel.getJResultTextArea().setText(resultString);
		this.getJBottomTabbedPane().insertTab(shortTitle,
				null, resultPanel, shortTitle, 0);
		this.getJBottomTabbedPane().setTabComponentAt(0, 
				new ButtonTabComponent(this.getJBottomTabbedPane()));
		this.getJBottomTabbedPane().setSelectedIndex(0);
  		this.getJBottomTabbedPane().repaint();
	}
	
	public void createImagePanel(SAXEnhanced sax, ShapeMiningApp shapeMiningApp){
		ImagePanel imagePanel;
		try {
			imagePanel = new ImagePanel( sax.getImagePath() );
	  		this.getJTopRightScrollPane().setViewportView(imagePanel);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(shapeMiningApp, "Image could not be loaded from given file.",
	    		    "Message", JOptionPane.INFORMATION_MESSAGE);
		}
	}



}  //  @jve:decl-index=0:visual-constraint="51,10"
