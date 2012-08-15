package shapemining.ui;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel to display images.
 */
public class ImagePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public ImagePanel(String path) throws IOException {
		this();
		Image image = ImageIO.read(new File(path));
		ImageIcon icon = new ImageIcon(image);
		JLabel label = new JLabel( icon );
		this.add( label );

	}

	/**
	 * This is the default constructor
	 */
	public ImagePanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}

}
