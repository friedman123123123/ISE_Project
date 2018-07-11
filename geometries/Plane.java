package geometries;

import java.util.*;

import primitives.Color;
import primitives.Coordinate;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Plane extends Geometry {
	protected Point3D _p; // A point in the plain.
	protected Vector _normal; // The normal vector to the plain.

	/********** Constructors ***********/
	public Plane(Point3D p, Vector normal, Color emission, Material material) {
		super();
		_p = new Point3D(p);
		_normal = new Vector(normal).normalize();
		_emission = new Color(emission);
		_material = new Material(material);
	}

	// Constructor that gets 3 points and founds the normal vector.
	public Plane(Point3D p1, Point3D p2, Point3D p3, Color emission, Material material) {
		Vector u = new Vector(p2.subtract(p1)); // The vector between p1 and p2
		Vector v = new Vector(p3.subtract(p1)); // The vector between p1 and p3
		Vector n = new Vector(u.crossProduct(v)); // The normal vector
		_normal = n.normalize(); // Normalized vector.
		_p = new Point3D(p1); // Saving a point.
		_emission = new Color(emission);
		_material = new Material(material);
	}

	// Copy constructor
	// The copy constructor gets two parameters, sends one to its predecessor
	// and the other one is copied to the class' parameters.
	public Plane(Plane other) {
		super(other);
		_p = new Point3D(other._p);
		_normal = new Vector(other._normal);
		_emission = other._emission;
		_material = new Material(other._material);
	}

	/************** Getters/Setters *******/

	public Point3D get_p() {
		return _p;
	}
	
	public Vector get_normal() {
		return _normal;
	}
	
	@Override
	public Vector getNormal(Point3D p) {
		return new Vector(_normal);
	}

	/*************** Admin *****************/

	/************** Operations ***************/

	/**
	 * @param Ray
	 * @return Map<Geometry, List<Point3D>>
	 */
	@Override
	public Map<Geometry, List<Point3D>> findIntersectionPoints(Ray r) {
		findIntersections = new HashMap<Geometry, List<Point3D>>();
		pointsIntersections = new ArrayList<Point3D>();
		Point3D p0 = new Point3D(r.get_p00());
		Point3D q0 = new Point3D(_p);
		if(p0.equals(q0)) {
			return findIntersections;
		}
		
		Vector n = new Vector(_normal);
		Vector d = new Vector(r.get_direction());
		Vector v = new Vector(q0.subtract(p0));

		// if the ray is contained in the plane then don't search for
		// intersection point.
		double d1 = n.dotProduct(d);
		if (d1 == 0)
			return findIntersections;

		double t = n.dotProduct(v) / n.dotProduct(d);
		d.normalize();
		Point3D p;
		Coordinate x = new Coordinate(t);
		if (x.get() > 0) {
			p = new Point3D(p0.add(d.scale(t)));
			pointsIntersections.add(p);
			findIntersections.put(this, pointsIntersections);
		}
		return findIntersections;
	}

}
