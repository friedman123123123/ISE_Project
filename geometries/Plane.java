package geometries;

import java.util.*;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Plane extends Geometry {
	private Point3D _p; // A point in the plain.
	private Vector _normal; // The normal vector to the plain.

	/********** Constructors ***********/

	public Plane(Point3D p, Vector normal) {
		super();
		_p = new Point3D(p);
		_normal = new Vector(normal).normalize();
	}

	// Constructor that gets 3 points and founds the normal vector.
	public Plane(Point3D p1, Point3D p2, Point3D p3) {
		Vector u = new Vector(p2.subtract(p1)); // The vector between p1 and p2
		Vector v = new Vector(p3.subtract(p1)); // The vector between p1 and p3
		Vector n = new Vector(u.crossProduct(v)); // The normal vector
		_normal = n.normalize(); // Normalized vector.
		_p = new Point3D(p1); // Saving a point.
	}

	// Copy constructor
	// The copy constructor gets two parameters, sends one to its predecessor
	// and the other one is copied to the class' parameters.
	public Plane(Plane other) {
		super(other);
		_p = new Point3D(other._p);
		_normal = new Vector(other._normal);
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
		return _normal;
	}

	/*************** Admin *****************/

	/************** Operations ***************/

	@Override
	public Map<Geometry, List<Point3D>> findIntersectionPoints(Ray r) {
		findIntersections = new HashMap<Geometry, List<Point3D>>();
		pointsIntersections = new ArrayList<Point3D>();
		Point3D p0 = new Point3D(r.get_p00());
		Point3D q0 = new Point3D(_p);
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
		if (t > 0) {
			p = new Point3D(p0.add(d.scale(t)));
			pointsIntersections.add(p);
			findIntersections.put(this, pointsIntersections);
		}
		return findIntersections;
	}

}
