package geometries;

import java.util.ArrayList;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * @author Daniel & Yonathan
 *
 */
public class Triangle extends Plane{
	private Point3D _p1;
	private Point3D _p2;
	private Point3D _p3;

	
	/********** Constructors ***********/

	public Triangle(Point3D _p1, Point3D _p2, Point3D _p3) {
		super(_p1, _p2, _p3);
		this._p1 = new Point3D(_p1);
		this._p2 = new Point3D(_p2);
		this._p3 = new Point3D(_p3);
	}
	 
	// Copy constructor
	// The copy constructor gets three parameters, sends two to its predecessor and the other one is copied to the class' parameters.
	public Triangle(Geometry g, Plane p, Triangle t) {
		super(g, p);
		this._p1 = t.get_p1();
		this._p2 = t.get_p2();
		this._p3 = t.get_p3();
	}

	/************** Getters/Setters *******/

	public Point3D get_p1() {
		return _p1;
	}

	public Point3D get_p2() {
		return _p2;
	}

	public Point3D get_p3() {
		return _p3;
	}
	
	/*************** Admin *****************/

	/************** Operations ***************/
	
	// Returns a ray since it's supposed to return a normal to the plain at a specific point (ray is a point and a direction - a vector).
	@Override
	public Ray getNormal(Point3D p) {
		// Inherits from the father.
		return super.getNormal(p);
	}
	
	@Override
	public ArrayList<Point3D> findIntersectionPoints(Ray r){
		findIntersections = new ArrayList<Point3D>();
		Point3D p0 = new Point3D(r.get_p00());
		super.findIntersectionPoints(r);
		if(findIntersections == null)
			return findIntersections;
		Vector p_p0 = new Vector(findIntersections.get(findIntersections.size()-1).subtract(p0));
		Vector v1 = new Vector(get_p1().subtract(p0));
		Vector v2 = new Vector(get_p2().subtract(p0));
		Vector v3 = new Vector(get_p3().subtract(p0));
		Vector n1 = new Vector((v1.crossProduct(v2)).normalize());
		double s1 = p_p0.dotProduct(n1);
		Vector n2 = new Vector((v2.crossProduct(v3)).normalize());
		double s2 = p_p0.dotProduct(n2);
		Vector n3 = new Vector((v3.crossProduct(v1)).normalize());
		double s3 = p_p0.dotProduct(n3);
		if(!((s1<0 && s2<0 && s3<0) || (s1>0 && s2>0 && s3>0))){
			findIntersections.remove(findIntersections.size()-1);
		}
		return findIntersections;
	}
}
