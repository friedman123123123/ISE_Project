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
public class Triangle extends Plane{
	private Point3D _p1;
	private Point3D _p2;
	private Point3D _p3;
	/********** Constructors ***********/

	public Triangle(Point3D p1, Point3D p2, Point3D p3, Color emission, Material material) {
		super(p1, p2, p3, emission, material);
		_p1 = new Point3D(p1);
		_p2 = new Point3D(p2);
		_p3 = new Point3D(p3);
	}
	 
	// Copy constructor
	// The copy constructor gets three parameters, sends two to its predecessor and the other one is copied to the class' parameters.
	public Triangle(Triangle other) {
		super(other);
		_p1 = new Point3D(other._p1);
		_p2 = new Point3D(other._p2);
		_p3 = new Point3D(other._p3);
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
	
	@Override
	public Color get_emission() {
		return _emission;
	}
	
	/*************** Admin *****************/

	/************** Operations ***************/
		
	@Override
	public Map<Geometry, List<Point3D>> findIntersectionPoints(Ray r){
		findIntersections = new HashMap<Geometry, List<Point3D>>();
		pointsIntersections = new ArrayList<Point3D>();
		Point3D p0 = new Point3D(r.get_p00());
		super.findIntersectionPoints(r);
		if(pointsIntersections.isEmpty())
			return findIntersections;
		Vector p_p0 = new Vector(pointsIntersections.get(pointsIntersections.size()-1).subtract(p0));
		Vector v1 = new Vector(get_p1().subtract(p0));
		Vector v2 = new Vector(get_p2().subtract(p0));
		Vector v3 = new Vector(get_p3().subtract(p0));
		Vector n1 = new Vector(v1.crossProduct(v2));
		double s1 = p_p0.dotProduct(n1);
		Vector n2 = new Vector(v2.crossProduct(v3));
		double s2 = p_p0.dotProduct(n2);
		Vector n3 = new Vector(v3.crossProduct(v1));
		double s3 = p_p0.dotProduct(n3);
		if(!((s1<0 && s2<0 && s3<0) || (s1>0 && s2>0 && s3>0))){
			pointsIntersections.clear();
			findIntersections.put(this, pointsIntersections);
		}
		return findIntersections;
	}
}
