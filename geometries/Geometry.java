package geometries;

import java.util.*;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * @author Daniel & Yonathan
 *
 */
public abstract class Geometry {
	protected Color _emission;//represents the color of a geometry
	protected Material _material;
	protected double _xMin;
	protected double _yMin;
	protected double _zMin;
	protected double _xMax;
	protected double _yMax;
	protected double _zMax;
	
	protected ArrayList<Point3D> pointsIntersections;
	protected Map<Geometry, List<Point3D>> findIntersections;

	/********** Constructors ***********/
	// Default constructor and copy constructor that does nothing (for now).

	// Default constructor
	public Geometry() {
		
	}

	// Copy constructor
	public Geometry(Geometry geometry) {

	}

	/************** Getters/Setters *******/
	public Color get_emission() {
		return new Color(_emission);
	}
	
	public Material get_material() {
		return _material;
	}
	
	public double get_xMin() {
		return _xMin;
	}

	public double get_yMin() {
		return _yMin;
	}

	public double get_zMin() {
		return _zMin;
	}

	public double get_xMax() {
		return _xMax;
	}

	public double get_yMax() {
		return _yMax;
	}

	public double get_zMax() {
		return _zMax;
	}

	/************** Operations ***************/
	// Abstract function that gets a point on a body and returns the normal
	// vector to the body at that point.
	// The function gets a type Point3D since it's supposed to get a point on a
	// body which is in the space.
	// (also if it was a plane the normal vector would have been the same at
	// every point, and since it changes for every point, it must be a 3
	// dimensional body).

	/**
	 * Calculate the normal to the geometry
	 * @param Point3D
	 * @return Vector
	 */
	public abstract Vector getNormal(Point3D p);

	/**
	 * @param Ray
	 * @return Map<Geometry, List<Point3D>>
	 */
	public abstract Map<Geometry, List<Point3D>> findIntersectionPoints(Ray r);
}
