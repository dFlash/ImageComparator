package com.maliavin.imagecomparator.algorithm;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import com.maliavin.imagecomparator.exception.ComparationException;
import com.maliavin.imagecomparator.model.Cluster;
import com.maliavin.imagecomparator.model.Point;

/**
 * Image comparator, which uses dilatation algorithm: when the element in
 * 2-dimension array is 1, then all it neighbors should be 1. It will be useful
 * when we have 2 near located clusters of points (e.g. text letters) -
 * algorithm combine it to one cluster.
 * 
 * @author Dmitriy
 *
 */
public class DilatationImageComparator implements ImageComparator {

	private int extendDistance;

	private int diffPercent;

	/**
	 * Initializes newly created DilatationImageComparator
	 * 
	 * @param extendDistance
	 *            - distance to which clusters will be extended
	 * @param diffPercent
	 *            - percent of difference between pixels RGB
	 */
	public DilatationImageComparator(int extendDistance, int diffPercent) {
		this.extendDistance = extendDistance;
		this.diffPercent = diffPercent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.maliavin.imagecomparator.algorithm.ImageComparator#compareImages(java
	 * .awt.image.BufferedImage, java.awt.image.BufferedImage)
	 */
	@Override
	public Collection<Cluster> compareImages(BufferedImage first, BufferedImage second) {
		if (first.getHeight() != second.getHeight() || first.getWidth() != second.getWidth()) {
			throw new ComparationException("Mismatched sizes of images");
		}
		int[][] diffMatrix = getDifferenceMatrix(first, second);

		for (int i = 0; i < extendDistance; i++) {
			diffMatrix = dilatation(diffMatrix);
		}

		Collection<Cluster> clusters = getClusters(diffMatrix);

		return clusters;
	}

	private int[][] getDifferenceMatrix(BufferedImage first, BufferedImage second) {
		int width = first.getWidth();
		int height = first.getHeight();
		int[][] diffMatrix = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Color firstColor = new Color(first.getRGB(j, i));
				Color secondColor = new Color(second.getRGB(j, i));
				if (getColorDiff(firstColor.getRed(), secondColor.getRed()) > diffPercent
						|| getColorDiff(firstColor.getBlue(), secondColor.getBlue()) > diffPercent
						|| getColorDiff(firstColor.getGreen(), secondColor.getGreen()) > diffPercent) {
					diffMatrix[i][j] = 1;
				}
			}
		}
		return diffMatrix;
	}

	private int getColorDiff(int firstColor, int secondColor) {
		return (int) ((double) Math.abs(firstColor - secondColor) / 255 * 100);
	}

	private int[][] dilatation(int[][] diffMatrix) {
		int height = diffMatrix.length;
		int width = diffMatrix[0].length;
		int[][] afterDilatation = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (diffMatrix[i][j] == 1) {
					afterDilatation[i][j] = 1;
					if (toRight(j, width)) {
						afterDilatation[i][j + 1] = 1;
					}
					if (toLeft(j)) {
						afterDilatation[i][j - 1] = 1;
					}
					if (toTop(i)) {
						afterDilatation[i - 1][j] = 1;
					}
					if (toBottom(i, height)) {
						afterDilatation[i + 1][j] = 1;
					}
				}
			}
		}
		return afterDilatation;
	}

	private Collection<Cluster> getClusters(int[][] diffMatrix) {
		Collection<Cluster> clusters = new ArrayList<>();
		int height = diffMatrix.length;
		int width = diffMatrix[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (diffMatrix[i][j] == 1) {
					Cluster cluster = bfs(diffMatrix, i, j);
					clusters.add(cluster);
				}
			}
		}
		return clusters;
	}

	private Cluster bfs(int[][] diffMatrix, int y, int x) {
		int height = diffMatrix.length;
		int width = diffMatrix[0].length;
		Queue<Point> clusterPoints = new LinkedList<>();
		Point first = new Point(x, y);
		Point minPoint = new Point(first);
		Point maxPoint = new Point(first);
		Cluster cluster = new Cluster(minPoint, maxPoint);
		clusterPoints.add(first);
		while (!clusterPoints.isEmpty()) {
			Point point = clusterPoints.poll();
			int currX = point.getX();
			int currY = point.getY();
			// for checking repeated points
			if (diffMatrix[currY][currX] == 0) {
				continue;
			}
			if (toRight(currX, width)) {
				if (diffMatrix[currY][currX + 1] == 1) {
					clusterPoints.add(new Point(currX + 1, currY));
				}
			}
			if (toLeft(currX)) {
				if (diffMatrix[currY][currX - 1] == 1) {
					clusterPoints.add(new Point(currX - 1, currY));
				}
			}
			if (toTop(currY)) {
				if (diffMatrix[currY - 1][currX] == 1) {
					clusterPoints.add(new Point(currX, currY - 1));
				}
			}
			if (toBottom(currY, height)) {
				if (diffMatrix[currY + 1][currX] == 1) {
					clusterPoints.add(new Point(currX, currY + 1));
				}
			}
			cluster.addPoint(point);
			diffMatrix[currY][currX] = 0;
		}
		return cluster;
	}

	private boolean toRight(int j, int width) {
		return j != width - 1;
	}

	private boolean toLeft(int j) {
		return j > 0;
	}

	private boolean toTop(int i) {
		return i > 0;
	}

	private boolean toBottom(int i, int height) {
		return i != height - 1;
	}

}
