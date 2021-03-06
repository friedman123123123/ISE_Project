package geometries;

import java.util.*;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends RadialGeometry {
	private Ray _axisRay;

	/********** Constructors ***********/

	
	public Cylinder(double radius, Ray axisRay, Color emission, Material material) {
		super(radius);
		_axisRay = new Ray(axisRay);
		_emission = emission;
		_material = material;
	}

	// Copy constructor
	// The copy constructor gets two parameters, sends one to its predecessor and the other one is copied to the class' parameters.
	// The parameter r (which stands for the radius) is sent to the predecessor's copy constructor since the radius exists in the predecessor class and not in this class.
	public Cylinder(Cylinder other) {
		super(other);
		_axisRay = new Ray(other._axisRay);
		_emission = other._emission;
		_material = new Material(other._material);
	}
	
	/************** Getters/Setters *******/		
	public Ray get_axisRay() {
		return _axisRay;
	}
	
	/*************** Admin *****************/

	/************** Operations ***************/
	
	/** 
	 * @param Point3D
	 * @return Vector
	 */
	@Override
	public Vector getNormal(Point3D p) {
		// First we want to find the point on the ray of the cylinder in which the normal to the given point (of the function) goes throw.
		// To implement this, we use the initial point of the ray and the given point, and since we know the normal creates a right angle, we get a right triangle.
		// Then, with the length between the two points and the radius of the cylinder which function as one of the edges, we can find the last edge using the Pythagoraen theorem. 
		Vector v = new Vector(p.subtract(_axisRay.get_p00())); // The vector between the two points.
		double d1 = v.length();
		double d = Math.sqrt(Math.pow(d1, 2) - Math.pow(super.get_radius(), 2)); // Pythagorean theorem.
		
		// After finding the length of the edge, we want to find the third point of the triangle, and build the normal throw this point.
		Vector v1 = new Vector(_axisRay.get_direction().normalize().scale(d)); 
		Point3D p1 = new Point3D(_axisRay.get_p00().add(v1));
		
		// Finding the length between the point we found and the given point, and adding 1 so we can get a normalized vector from the given point (and not from the third point of the triangle).
		Vector v2 = new Vector(p.subtract(p1));
		double l = v2.length() + 1;
		
		// Finding the point on which the normalized normal to the cylinder ends, and returning ray (point and vector).
		Vector v3 = new Vector(v2.normalize().scale(l));
		Point3D p2 = new Point3D(p1.add(v3));
		return new Vector(p2.subtract(p));
	}

	/** 
	 * @param Ray
	 * @return Map<Geometry, List<Point3D>>
	 */
	@Override
	public Map<Geometry, List<Point3D>> findIntersectionPoints(Ray r){
		pointsIntersections = new ArrayList<Point3D>();
		findIntersections = new HashMap<Geometry, List<Point3D>>();
		//findIntersections.put(this, pointsIntersections);
		return findIntersections;
	}
}
