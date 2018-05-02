package geometries;

import java.awt.List;
import java.util.ArrayList;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * @author Daniel & Yonathan
 *
 */
public abstract class Geometry {
	
	ArrayList<Point3D> findIntersections;
	
	/********** Constructors ***********/
	// Default constructor and copy constructor that does nothing (for now).
	
	// Default constructor
	public Geometry() {
		
	}

	// Copy constructor
	public Geometry(Geometry geometry) {
		
		
	}

	/************** Operations 
	 * @throws Exception ***************/
	// Abstract function that gets a point on a body and returns the normal vector to the body at that point.
	// The function gets a type Point3D since it's supposed to get a point on a body which is in the space.
	// (also if it was a plain the normal vector would have been the same at every point, and since it changes for every point, it must be a 3 dimensional body).
	
	// Returns a ray since it's supposed to return a normal to the plain at a specific point (ray is a point and a direction - a vector).
	public abstract Ray getNormal(Point3D p);
	
	public abstract ArrayList<Point3D> findIntersectionPoints(Ray r);
}
