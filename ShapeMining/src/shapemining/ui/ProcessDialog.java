package shapemining.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * "Please Wait" dialog - shown during main application process time consuming commands.
 *
 */
public class ProcessDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	/***
	 * Constructor.
	 * @param parent - main app class
	 * @param title - of dialog
	 * @param modalityType - type of modality
	 */
	public ProcessDialog(Frame parent, String title, ModalityType modalityType) {
		super(parent, title, modalityType);
		this.getContentPane().add(new ProcessDialogPanel().getPanel());
	    this.pack();
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

	}
}

/*
 * Panel for Process Dialog.
 */
class ProcessDialogPanel {
	private static final Dimension DIALOG_SIZE = new Dimension(500, 75);
	private JPanel panel = new JPanel();
 
	public ProcessDialogPanel() {
		panel.setPreferredSize(DIALOG_SIZE);
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		panel.add(new JLabel("<html><body align='center'><b>Please wait...</b> Operation is in progress.<br>" +
				"Depending on given " +
                "parameters it might take several seconds or minutes." +
                "</body></html>"), BorderLayout.CENTER);
	}
  
	public JPanel getPanel() {
		return panel;
	}
}