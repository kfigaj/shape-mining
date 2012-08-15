package shapemining.ui;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Bottom result panel.
 *
 */
public class ResultPanel extends JPanel {
	
	public static final String K_NEAREST_NEIGHBOUR = "Results for K Nearest Neighbour Algorithm";  //  @jve:decl-index=0:
	public static final String LOCAL_HASHING = "Results for Local Sensivity Hashing Algorithm";  //  @jve:decl-index=0:
	public static final String IMPROVED_BRUTE_FORCE = "Results Improved Brute Force Algorithm";
	public static final String K_NEAREST_NEIGHBOUR_TEST = "Test results for K Nearest Neighbour Algorithm"; 
	public static final String LOCAL_HASHING_TEST = "Test results for Local Sensivity Hashing Algorithm";
	public static final String IMPROVED_BRUTE_FORCE_TEST = "Test results for Improved Brute Force Algorithm";
	
	public static final String K_NEAREST_NEIGHBOUR_SHORT = "K-NN Algorithm";  //  @jve:decl-index=0:
	public static final String LOCAL_HASHING_SHORT = "LSH Algorithm";  //  @jve:decl-index=0:
	public static final String IMPROVED_BRUTE_FORCE_SHORT = "IBForce Algorithm";  //  @jve:decl-index=0:
	public static final String K_NEAREST_NEIGHBOUR_TEST_SHORT = "K-NN Algorithm Test";  //  @jve:decl-index=0:
	public static final String LOCAL_HASHING_TEST_SHORT = "LSH Algorithm Test";
	public static final String IMPROVED_BRUTE_FORCE_TEST_SHORT = "IBForce Algorithm Test";
	
	private static final long serialVersionUID = 1L;
	private JLabel jResultTypeLabel = null;
	private JTextArea jResultTextArea = null;
	private JScrollPane jScrollPane = null;

	public ResultPanel(String resultLabel) {
		super();
		initialize(resultLabel);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize(String resultLabel) {
		jResultTypeLabel = new JLabel();
		jResultTypeLabel.setText(resultLabel);
		this.setSize(772, 305);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(Box.createRigidArea(new Dimension(5, 5)));
		this.add(jResultTypeLabel, null);
		this.add(Box.createVerticalGlue());
		this.add(getJScrollPane(), null);
		this.add(Box.createRigidArea(new Dimension(5, 5)));
	}

	/**
	 * This method initializes jResultTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	public JTextArea getJResultTextArea() {
		if (jResultTextArea == null) {
			jResultTextArea = new JTextArea();
			jResultTextArea.setEditable(false);
			jResultTextArea.setLineWrap(true);
			jResultTextArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		}
		return jResultTextArea;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			
			this.add(getJResultTextArea(), null);
			jScrollPane.setViewportView(getJResultTextArea());
		}
		return jScrollPane;
	}

}  //  @jve:decl-index=0:visual-constraint="1,14"
