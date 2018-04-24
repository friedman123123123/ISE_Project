package geometries;

import java.awt.List;
import java.util.ArrayList;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Plane extends Geometry {
	private Point3D _p; // A point in the plain.
	private Vector _normal; // The normal vector to the plain.
	

	/********** Constructors ***********/
	
	public Plane(Point3D _p, Vector _normal) {
		super();
		this._p = new Point3D(_p);
		this._normal = new Vector(_normal);
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
	// The copy constructor gets two parameters, sends one to its predecessor and the other one is copied to the class' parameters.
	public Plane(Geometry g, Plane p) {
		super(g);
		this._p = p.get_p();
		this._normal = p.get_normal();
	}
	
	/************** Getters/Setters *******/	
	
	public Point3D get_p() {
		return _p;
	}

	public Vector get_normal() {
		return _normal;
	}

	/*************** Admin *****************/

	/************** Operations ***************/
	
	// Returns a ray since it's supposed to return a normal to the plain at a specific point (ray is a point and a direction - a vector).
	@Override
	public Ray getNormal(Point3D p) {
		
		/* // To find the ray we added the normal vector to the plain (which starts at (0, 0, 0) and ends at its head) to the point we received in the parameters of the function.
		// Since the normal has only a head (since it starts at the origin), the calculation gave us the parallel normal vector which starts at that point, as needed.
		 Vector n = new Vector(p.add(get_normal())); 
		 return new Ray(p, n); */
		
		// Since we receive a point and we already have the normal to the plain, all left to do is return the ray (the point and the direction (vector)).
		Vector n = new Vector(get_normal());
		return new Ray(p, n);
	}

	@Override
	public ArrayList<Point3D> findIntersectionPoints(Ray r) {
		findIntersections = new ArrayList<Point3D>();
		Point3D p0 = new Point3D(r.get_p00());
		Point3D q0 = new Point3D(get_p());
		Vector n = new Vector(get_normal());
		Vector d = new Vector(r.get_direction());
		Vector v = new Vector(q0.subtract(p0));
		double t = n.dotProduct(v) / n.dotProduct(d);
		d.normalize();
		Point3D p;
		if(t >= 0){
			p = new Point3D(p0.add(d.scale(t)));
			findIntersections.add(p);
		}  
		return findIntersections;
	}

}
