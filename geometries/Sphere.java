package geometries;

import java.util.*;

import primitives.Color;
import primitives.Coordinate;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Sphere extends RadialGeometry {
	private Point3D _center;
	
	/********** Constructors ***********/

	public Sphere(double radius, Point3D center, Color emission, Material material) {
		super(radius);
		_center = new Point3D(center);
		_emission = new Color(emission);
		_material = new Material(material);
	}

	// Copy constructor
	// The copy constructor gets two parameters, sends one to its predecessor and the other one is copied to the class' parameters.	
	// The parameter r (which stands for the radius) is sent to the predecessor's copy constructor since the radius exists in the predecessor class and not in this class.
	public Sphere(Sphere other) {
		super(other);
		_center = new Point3D(other._center);
		_emission = new Color(other._emission);
		_material = new Material(other._material);
	}
	
	/************** Getters/Setters *******/
	public Point3D get_center() {
		return _center;
	}
	
	/*************** Admin *****************/

	/************** Operations ***************/
	
	/**
	 * Calculate the normal to the geometry
	 * @param Point3D
	 * @return Vector
	 */
	@Override
	public Vector getNormal(Point3D p) {
		Vector n = new Vector(p.subtract(get_center()).normalize());  // The (normalized) vector from the center of the sphere to the given point.
		Point3D p1 = new Point3D(p.add(n)); // The head of the normalized normal vector to the given point.
		Vector v = new Vector(p1.subtract(p)); // The direction (vector) of the normal to the given point.
		return new Vector(v); // Returns the normalized vector from the given point.
	}
	
	@Override
	public Map<Geometry, List<Point3D>> findIntersectionPoints(Ray r){
		findIntersections = new HashMap<Geometry, List<Point3D>>();
		pointsIntersections = new ArrayList<Point3D>();
		Point3D p0 = new Point3D(r.get_p00());
		Vector u = new Vector(get_center().subtract(p0));
		Vector v = new Vector(r.get_direction());
		double tm = v.dotProduct(u);
		double d = Math.sqrt(u.dotProduct(u) - tm * tm);
		if (d > get_radius())
			return findIntersections;
		double th = Math.sqrt(get_radius() * get_radius() - d * d);
		if (Coordinate.ZERO.equals(th)) {
			Point3D p = p0.add(v.scale(tm));
			pointsIntersections.add(p);
			findIntersections.put(this, pointsIntersections);
			return findIntersections;
		}
		double t1 = tm - th;
		double t2 = tm + th;
		Coordinate x = new Coordinate(t1);
		Coordinate y = new Coordinate(t2);
		if (x.get() > 0) {
			Point3D p1 = p0.add(v.scale(t1));
			pointsIntersections.add(p1);
		}
		if (y.get() > 0) {
			Point3D p2 = p0.add(v.scale(t2));
			pointsIntersections.add(p2);
		}
		findIntersections.put(this, pointsIntersections);
		return findIntersections;
	}
}
