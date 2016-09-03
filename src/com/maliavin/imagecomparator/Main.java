package com.maliavin.imagecomparator;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import com.maliavin.imagecomparator.algorithm.DilatationImageComparator;
import com.maliavin.imagecomparator.algorithm.ImageComparator;
import com.maliavin.imagecomparator.exception.ComparationException;
import com.maliavin.imagecomparator.model.Cluster;

/**
 * Main class - input point in program
 * 
 * @author Dmitriy
 *
 */
public class Main {

	public static void main(String[] args) {
		
		 if (args.length < 2){
		 throw new ComparationException("You have to specify 2 images");
		 }
		BufferedImage image1 = null;
		BufferedImage image2 = null;
		try {
			image1 = ImageIO.read(new File(args[0]));
			image2 = ImageIO.read(new File(args[1]));
		} catch (Exception e) {
			throw new ComparationException("Error in reading images", e);
		}

		ImageComparator comparator = new DilatationImageComparator(3, 10);
		Collection<Cluster> clusters = comparator.compareImages(image1, image2);

		drawClusters(image2, clusters);

		JFrame editorFrame = new ImageFrame(image2, "Image");
		editorFrame.setVisible(true);

	}

	private static void drawClusters(BufferedImage image, Collection<Cluster> clusters) {
		Graphics2D g = image.createGraphics();
		g.setColor(Color.RED);
		g.setStroke(new BasicStroke(1));

		for (Cluster cluster : clusters) {
			int width = cluster.getMaxPoint().getX() - cluster.getMinPoint().getX();
			int height = cluster.getMaxPoint().getY() - cluster.getMinPoint().getY();
			g.drawRect(cluster.getMinPoint().getX(), cluster.getMinPoint().getY(), width, height);
		}
	}

	/**
	 * Frame for image rendering
	 * 
	 * @author Dmitriy
	 *
	 */
	private static class ImageFrame extends JFrame {

		private static final long serialVersionUID = -8703290519354421407L;

		/**
		 * Initializes newly created ImageFrame object
		 * 
		 * @param image
		 * @param title
		 */
		public ImageFrame(BufferedImage image, String title) {
			super(title);
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

			ImageIcon imageIcon = new ImageIcon(image);
			JLabel jLabel = new JLabel();
			jLabel.setIcon(imageIcon);
			getContentPane().add(jLabel, BorderLayout.CENTER);

			pack();
			setLocationRelativeTo(null);
		}
	}

}
