package com.maliavin.imagecomparator.model;

/**
 * Describes cluster of points and group it in rectangular area
 * 
 * @author Dmitriy
 *
 */
public class Cluster {

	private Point minPoint;

	private Point maxPoint;

	/**
	 * Initializes newly created Cluster using coordinates
	 * 
	 * @param minPoint
	 *            left top point
	 * @param maxPoint
	 *            right bottom point
	 */
	public Cluster(Point minPoint, Point maxPoint) {
		this.minPoint = minPoint;
		this.maxPoint = maxPoint;
	}
	
	/**
	 * Adds point to cluster
	 * 
	 * @param point
	 */
	public void addPoint(Point point){
		if (point.getX() < minPoint.getX()){
			minPoint.setX(point.getX());
		}
		if (point.getY() < minPoint.getY()){
			minPoint.setY(point.getY());
		}
		if (point.getX() > maxPoint.getX()){
			maxPoint.setX(point.getX());
		}
		if (point.getY() > maxPoint.getY()){
			maxPoint.setY(point.getY());
		}
	}

	public Point getMinPoint() {
		return minPoint;
	}

	public void setMinPoint(Point minPoint) {
		this.minPoint = minPoint;
	}

	public Point getMaxPoint() {
		return maxPoint;
	}

	public void setMaxPoint(Point maxPoint) {
		this.maxPoint = maxPoint;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maxPoint == null) ? 0 : maxPoint.hashCode());
		result = prime * result + ((minPoint == null) ? 0 : minPoint.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cluster other = (Cluster) obj;
		if (maxPoint == null) {
			if (other.maxPoint != null)
				return false;
		} else if (!maxPoint.equals(other.maxPoint))
			return false;
		if (minPoint == null) {
			if (other.minPoint != null)
				return false;
		} else if (!minPoint.equals(other.minPoint))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cluster [minPoint=" + minPoint + ", maxPoint=" + maxPoint + "]";
	}

}
