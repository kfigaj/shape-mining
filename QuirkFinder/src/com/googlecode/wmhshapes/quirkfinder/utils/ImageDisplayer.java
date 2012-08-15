package com.googlecode.wmhshapes.quirkfinder.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

class ImagePanel extends JPanel {

	// image object
	private Image img;

	public ImagePanel(Image img) throws IOException {
		this.img = img;

	}

	// override paint method of panel
	public void paint(Graphics g) {
		// draw the image
		if (img != null)
			g.drawImage(img, 0, 0, this);
	}

}

public class ImageDisplayer {
	public static void display(Image image, String name) {

		try {

			// create frame
			JFrame f = new JFrame(name);

			// create panel with selected file
			ImagePanel panel = new ImagePanel(image);

			// add panel to pane
			f.getContentPane().add(panel);

			// show frame
			f.setSize(image.getWidth(null) + 10, image.getHeight(null) + 50);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//f.setBounds(0, 0, image.getWidth(null), image.getHeight(null));
			f.setVisible(true);
		} catch (Exception e) {
			System.out
					.println("Please verify that you selected a valid image file");
		}
	}

}
