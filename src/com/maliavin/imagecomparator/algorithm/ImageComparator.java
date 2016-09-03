package com.maliavin.imagecomparator.algorithm;

import java.awt.image.BufferedImage;
import java.util.Collection;

import com.maliavin.imagecomparator.model.Cluster;

/**
 * Interface for comparing images
 * 
 * @author Dmitriy
 *
 */
public interface ImageComparator {

	/**
	 * Compares 2 images and returns collection of clusters where differences
	 * were found
	 * 
	 * @param first
	 *            first image
	 * @param second
	 *            second image
	 * @return collection of clusters where differences were found
	 */
	Collection<Cluster> compareImages(BufferedImage first, BufferedImage second);

}
