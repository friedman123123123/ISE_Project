package geometries;

import java.util.ArrayList;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Sphere extends RadialGeometry {
	private Point3D _center;
	
	/********** Constructors ***********/

	public Sphere(double _radius, Point3D _center) {
		super(_radius);
		this._center = new Point3D(_center);
	}

	// Copy constructor
	// The copy constructor gets two parameters, sends one to its predecessor and the other one is copied to the class' parameters.	
	// The parameter r (which stands for the radius) is sent to the predecessor's copy constructor since the radius exists in the predecessor class and not in this class.
	public Sphere(RadialGeometry r, Sphere s) {
		super(r);
		this._center = s.get_center();
	}
	
	/************** Getters/Setters *******/	
	
	public Point3D get_center() {
		return _center;
	}
	
	/*************** Admin *****************/

	/************** Operations ***************/
	
	// Returns a ray since it's supposed to return a normal to the plain at a specific point (ray is a point and a direction - a vector).
	@Override
	public Ray getNormal(Point3D p) {
		/* Vector n = new Vector(p.subtract(get_center()).normalize());  // The (normalized) vector from the center of the sphere to the given point.
		Vector v = new Vector(p.add(n)); // The vector from the origin point (0,0,0) to the head of the normal to the given point.
		return new Ray(p, v); // Returns the normalized vector (which is a ray) from the given point. */
		
		// Since the function is supposed to return a ray which means a point and a direction, we have to find the point where the normal vector ends and find its direction based on that.
		Vector n = new Vector(p.subtract(get_center()).normalize());  // The (normalized) vector from the center of the sphere to the given point.
		Point3D p1 = new Point3D(p.add(n)); // The head of the normalized normal vector to the given point.
		Vector v = new Vector(p1.subtract(p)); // The direction (vector) of the normal to the given point.
		return new Ray(p, v); // Returns the normalized vector (which is a ray) from the given point.
	}
	
	@Override
	public ArrayList<Point3D> findIntersectionPoints(Ray r){
		findIntersections = new ArrayList<Point3D>();
		Point3D p0 = new Point3D(r.get_p00());
		Vector u = new Vector(get_center().subtract(p0));
		Vector v = new Vector(r.get_direction());
		double tm = v.dotProduct(u);
		double d = Math.sqrt(u.dotProduct(u) - Math.pow(tm, 2));
		if (d > get_radius())
			return findIntersections;
		if (d == get_radius()) {
			Point3D p = p0.add(v.scale(tm));
			findIntersections.add(p);
			return findIntersections;
		}
		double th = Math.sqrt(Math.pow(get_radius(), 2) - Math.pow(d, 2));
		double t1 = tm - th;
		double t2 = tm + th;
		if (t1 >= 0) {
			Point3D p1 = p0.add(v.scale(t1));
			findIntersections.add(p1);
		}
		if (t2 >= 0) {
			Point3D p2 = p0.add(v.scale(t2));
			findIntersections.add(p2);
		}
		return findIntersections;
	}
}
