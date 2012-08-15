package shapemining.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import sax.util.test.Settings;
import shapemining.controller.ShapeMiningApp;


/**
 * Settings Panel in Dialog.
 *
 */
public class SettingsDialogPanel extends JPanel implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	private final JDialog parent;
	private final ShapeMiningApp shapeMiningApp;
	
	private JPanel jSettingsPanel = null;
	private JTabbedPane jSettingsTabbedPane = null;
	private JPanel jButtonPanel = null;
	private JPanel jMainSettingsPanel = null;
	private JButton jSaveSettingsButton = null;
	private JButton jCancelSettingsButton = null;
	private JPanel jAlgorithmsPanel = null;

	private JLabel jTimeSeriesLabel = null;
	private JFormattedTextField jTimeSeriesTextField = null;
	private JLabel jPaaLabel = null;
	private JFormattedTextField jPaaTextField = null;
	private JLabel jAlphabetLabel = null;
	private JFormattedTextField jAlphabetTextField = null;
	private JLabel jCircularLabel = null;
	private JFormattedTextField jCircularTextField = null;
	private JCheckBox jErrorOptionCheckBox = null;
	
	private JPanel jPanel = null;
	private JPanel jPanel3 = null;
	private JPanel jPanel2 = null;
	private JLabel jKValueLabel = null;
	private JFormattedTextField jKValueTextField = null;
	private JLabel jBruteSaxResultsLabel = null;
	private JFormattedTextField jBruteSaxResultsTextField = null;
	private JLabel jIterationNumberLabel = null;
	private JFormattedTextField jIterationNumberTextField = null;
	private JLabel jHashSizeLabel = null;
	private JFormattedTextField jHashSizeTextField = null;

	/**
	 * This is the default constructor
	 */
	public SettingsDialogPanel(JDialog parent,ShapeMiningApp shapeMiningApp) {
		super();
		this.parent = parent;
		this.shapeMiningApp = shapeMiningApp;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setPreferredSize(new Dimension(550, 300));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(getJSettingsPanel(), null);
		this.setOpaque(true);
	}
	
	/**
	 * This method initializes jSettingsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJSettingsPanel() {
		if (jSettingsPanel == null) {
			jSettingsPanel = new JPanel();
			jSettingsPanel.setLayout(new BorderLayout());
			jSettingsPanel.add(getJSettingsTabbedPane(), BorderLayout.CENTER);
			jSettingsPanel.add(getJButtonPanel(), BorderLayout.SOUTH);
		}
		return jSettingsPanel;
	}

	/**
	 * This method initializes jSettingsTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJSettingsTabbedPane() {
		if (jSettingsTabbedPane == null) {
			jSettingsTabbedPane = new JTabbedPane();
			jSettingsTabbedPane.setBounds(new Rectangle(5, 4, 529, 235));
			jSettingsTabbedPane.addTab("Main", null, getJMainSettingsPanel(), null);
			jSettingsTabbedPane.addTab("Algorithms", null, getJAlgorithmsPanel(), null);
		}
		return jSettingsTabbedPane;
	}

	/**
	 * This method initializes jButtonPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJButtonPanel() {
		if (jButtonPanel == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(1);
			gridLayout.setVgap(0);
			gridLayout.setHgap(30);
			jButtonPanel = new JPanel();
			jButtonPanel.setLayout(gridLayout);
			jButtonPanel.setBounds(new Rectangle(6, 244, 529, 40));
			jButtonPanel.add(getJSaveSettingsButton(), null);
			jButtonPanel.add(getJCancelSettingsButton(), null);
		}
		return jButtonPanel;
	}

	/**
	 * This method initializes jMainSettingsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJMainSettingsPanel() {
		if (jMainSettingsPanel == null) {
			jCircularLabel = new JLabel();
			jCircularLabel.setBounds(new Rectangle(15, 135, 136, 31));
			jCircularLabel.setText("Circular threshold [%]:");
			jAlphabetLabel = new JLabel();
			jAlphabetLabel.setBounds(new Rectangle(210, 75, 151, 31));
			jAlphabetLabel.setText("Number of letters in SAX:");
			jPaaLabel = new JLabel();
			jPaaLabel.setBounds(new Rectangle(285, 15, 136, 31));
			jPaaLabel.setText("PAA elements number:");
			jTimeSeriesLabel = new JLabel();
			jTimeSeriesLabel.setText("Time series elements number:");
			jTimeSeriesLabel.setBounds(new Rectangle(15, 15, 180, 30));
			jMainSettingsPanel = new JPanel();
			jMainSettingsPanel.setLayout(null);
			jMainSettingsPanel.add(jTimeSeriesLabel, null);
			jMainSettingsPanel.add(getJTimeSeriesTextField(), null);
			jMainSettingsPanel.add(jPaaLabel, null);
			jMainSettingsPanel.add(getJPaaTextField(), null);
			jMainSettingsPanel.add(jAlphabetLabel, null);
			jMainSettingsPanel.add(getJAlphabetTextField(), null);
			jMainSettingsPanel.add(jCircularLabel, null);
			jMainSettingsPanel.add(getJCircularTextField(), null);
			jMainSettingsPanel.add(getJErrorOptionCheckBox(), null);
		}
		return jMainSettingsPanel;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTimeSeriesTextField() {
		if (jTimeSeriesTextField == null) {
			NumberFormatter nf = new NumberFormatter();
			nf.setMinimum(0);
			nf.setValueClass(Integer.class);
			jTimeSeriesTextField = new JFormattedTextField(nf);
			jTimeSeriesTextField.setColumns(4);
			jTimeSeriesTextField.addPropertyChangeListener("value", this);
			jTimeSeriesTextField.setBounds(new Rectangle(195, 15, 61, 31));
		}
		return jTimeSeriesTextField;
	}

	
	/**
	 * This method initializes jPaaTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJPaaTextField() {
		if (jPaaTextField == null) {
			NumberFormatter nf = new NumberFormatter();
			nf.setMinimum(0);
			nf.setMaximum((Integer)jTimeSeriesTextField.getValue());
			nf.setValueClass(Integer.class);
			jPaaTextField = new JFormattedTextField(nf);
			jPaaTextField.setColumns(3);
			jPaaTextField.setBounds(new Rectangle(420, 15, 61, 31));
		}
		return jPaaTextField;
	}

	/**
	 * This method initializes jAlphabetTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJAlphabetTextField() {
		if (jAlphabetTextField == null) {
			NumberFormatter nf = new NumberFormatter();
			nf.setMinimum(3); // see Settings.java - ALPHABET_NUMBER / breakpoints
			nf.setMaximum(10);
			nf.setValueClass(Integer.class);
			jAlphabetTextField = new JFormattedTextField(nf);
			jAlphabetTextField.setColumns(1);
			jAlphabetTextField.setBounds(new Rectangle(360, 75, 61, 31));
		}
		return jAlphabetTextField;
	}

	/**
	 * This method initializes jCircularTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJCircularTextField() {
		if (jCircularTextField == null) {
			jCircularTextField = new JFormattedTextField();
			jCircularTextField.setBounds(new Rectangle(150, 135, 61, 31));
		}
		return jCircularTextField;
	}

	/**
	 * This method initializes jErrorOptionCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJErrorOptionCheckBox() {
		if (jErrorOptionCheckBox == null) {
			jErrorOptionCheckBox = new JCheckBox("Use Reconstruction Error");
			jErrorOptionCheckBox.setBounds(new Rectangle(15, 75, 181, 31));
			jErrorOptionCheckBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					NumberFormatter nf = (NumberFormatter) jAlphabetTextField.getFormatter();
					if(e.getStateChange() == ItemEvent.SELECTED){
						nf.setMaximum(5);
						if((Integer)jAlphabetTextField.getValue() > 5)
							jAlphabetTextField.setValue(new Integer(5));
					}else{
						nf.setMaximum(10);
					}
				}
			});
		}
		return jErrorOptionCheckBox;
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		 Object source = e.getSource();
		    if (source == jTimeSeriesTextField) {
		       Integer amount = ((Number)jTimeSeriesTextField.getValue()).intValue();
		       ((NumberFormatter)jPaaTextField.getFormatter()).setMaximum(amount);
		    }		    		
	}

	/**
	 * Set default settings values
	 */
	public void setDefaultValues(){
		//main
		jTimeSeriesTextField.setValue(new Integer(Settings.TIME_SERIES_NUMBER));
		jPaaTextField.setValue(new Integer(Settings.PAA_ELEMENTS_NUMBER));
		jAlphabetTextField.setValue(new Integer(Settings.ALPHABET_NUMBER));
		jCircularTextField.setValue(new Double(Settings.CIRCULAR_THRESHOLD));
		jErrorOptionCheckBox.setSelected(Settings.USE_RECONSTRUCTION_ERROR);
		
		//algorithms
		jKValueTextField.setValue(new Integer(Settings.K_VALUE));
		jBruteSaxResultsTextField.setValue(new Integer(Settings.BRUTE_SAX_RESULTS));
		jIterationNumberTextField.setValue(new Integer(Settings.ITERATIONS_NUMBER));
		jHashSizeTextField.setValue(new Integer(Settings.HASH_SIZE));
	}
	
	private void saveValues(){
		//main
		Settings.TIME_SERIES_NUMBER = (Integer)jTimeSeriesTextField.getValue();
		Settings.PAA_ELEMENTS_NUMBER = (Integer)jPaaTextField.getValue();
		Settings.ALPHABET_NUMBER = (Integer)jAlphabetTextField.getValue();
		Settings.CIRCULAR_THRESHOLD = (Double)jCircularTextField.getValue();
		Settings.USE_RECONSTRUCTION_ERROR = jErrorOptionCheckBox.isSelected();
		
		//algorithms
		Settings.K_VALUE = (Integer)jKValueTextField.getValue();
		Settings.BRUTE_SAX_RESULTS = (Integer)jBruteSaxResultsTextField.getValue();
		Settings.ITERATIONS_NUMBER = (Integer)jIterationNumberTextField.getValue();
		Settings.HASH_SIZE = (Integer)jHashSizeTextField.getValue();
	}
	
	/**
	 * This method initializes jSaveSettingsButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJSaveSettingsButton() {
		if (jSaveSettingsButton == null) {
			jSaveSettingsButton = new JButton();
			jSaveSettingsButton.setText("Save Settings");
			jSaveSettingsButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveValues();
					parent.setVisible(false);
					shapeMiningApp.updateModel();
				}
			});
		}
		return jSaveSettingsButton;
	}

	/**
	 * This method initializes jCancelSettingsButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJCancelSettingsButton() {
		if (jCancelSettingsButton == null) {
			jCancelSettingsButton = new JButton();
			jCancelSettingsButton.setText("Cancel");
			jCancelSettingsButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					parent.setVisible(false);
				}
			});
		}
		return jCancelSettingsButton;
	}

	/**
	 * This method initializes jAlgorithmsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJAlgorithmsPanel() {
		if (jAlgorithmsPanel == null) {
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(3);
			jAlgorithmsPanel = new JPanel();
			jAlgorithmsPanel.setLayout(gridLayout1);
			jAlgorithmsPanel.add(getJPanel(), null);
			jAlgorithmsPanel.add(getJPanel2(), null);
			jAlgorithmsPanel.add(getJPanel3(), null);
		}
		return jAlgorithmsPanel;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BoxLayout(getJPanel(), BoxLayout.X_AXIS));
			jPanel.setBorder(BorderFactory.createTitledBorder("K Nearest Neighbour"));
			jKValueLabel = new JLabel();
			jKValueLabel.setText("K Value: ");
			jPanel.add(jKValueLabel, null);
			jPanel.add(Box.createHorizontalStrut(30));
			jPanel.add(getJKValueTextField(), null);
			jPanel.add(Box.createHorizontalStrut(300));
			
		}
		return jPanel;
	}
	
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jHashSizeLabel = new JLabel();
			jHashSizeLabel.setText("Hash Size: ");
			jIterationNumberLabel = new JLabel();
			jIterationNumberLabel.setText("Number of Iterations: ");
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BoxLayout(getJPanel2(), BoxLayout.X_AXIS));
			jPanel2.setBorder(BorderFactory.createTitledBorder("Local Hasing Algorithm - Discord Finder"));
			jPanel2.add(jIterationNumberLabel, null);
			jPanel2.add(Box.createHorizontalStrut(30));
			jPanel2.add(getJIterationNumberTextField(), null);
			jPanel2.add(Box.createHorizontalStrut(30));
			jPanel2.add(jHashSizeLabel, null);
			jPanel2.add(Box.createHorizontalStrut(30));
			jPanel2.add(getJHashSizeTextField(), null);
			jPanel2.add(Box.createHorizontalStrut(100));
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jBruteSaxResultsLabel = new JLabel();
			jBruteSaxResultsLabel.setText("Sax Resutls Number: ");
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BoxLayout(getJPanel3(), BoxLayout.X_AXIS));
			jPanel3.setBorder(BorderFactory.createTitledBorder("Improved Brute Froce - Discord Finder"));
			jPanel3.add(jBruteSaxResultsLabel, null);
			jPanel3.add(Box.createHorizontalStrut(30));
			jPanel3.add(getJBruteSaxResultsTextField(), null);
			jPanel3.add(Box.createHorizontalStrut(300));
		}
		return jPanel3;
	}

	/**
	 * This method initializes jKValueTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJKValueTextField() {
		if (jKValueTextField == null) {
			NumberFormatter nf = new NumberFormatter();
			nf.setMinimum(1);
			nf.setValueClass(Integer.class);
			jKValueTextField = new JFormattedTextField(nf);
			jKValueTextField.setMaximumSize(new Dimension(50, 30));
		}
		return jKValueTextField;
	}

	/**
	 * This method initializes jBruteSaxResultsTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJBruteSaxResultsTextField() {
		if (jBruteSaxResultsTextField == null) {
			NumberFormatter nf = new NumberFormatter();
			nf.setMinimum(1);
			nf.setValueClass(Integer.class);
			jBruteSaxResultsTextField = new JFormattedTextField(nf);
			jBruteSaxResultsTextField.setMaximumSize(new Dimension(50, 30));
		}
		return jBruteSaxResultsTextField;
	}

	/**
	 * This method initializes jIterationNumberTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJIterationNumberTextField() {
		if (jIterationNumberTextField == null) {
			NumberFormatter nf = new NumberFormatter();
			nf.setMinimum(1);
			nf.setValueClass(Integer.class);
			jIterationNumberTextField = new JFormattedTextField(nf);
			jIterationNumberTextField.setMaximumSize(new Dimension(50, 30));
		}
		return jIterationNumberTextField;
	}

	/**
	 * This method initializes jHashSizeTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJHashSizeTextField() {
		if (jHashSizeTextField == null) {
			NumberFormatter nf = new NumberFormatter();
			nf.setMinimum(1);
			nf.setValueClass(Integer.class);
			jHashSizeTextField = new JFormattedTextField(nf);
			jHashSizeTextField.setMaximumSize(new Dimension(50, 30));
		}
		return jHashSizeTextField;
	}

}
