// bla bla bla
import java.awt.event.MouseWheelEvent;

import javax.swing.ProgressMonitorInputStream;

import geometries.Cylinder;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Coordinate;
import primitives.Point2D;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

// Main that tests the classes.
public class Main {

	public static void main(String[] args) {
		Coordinate c = new Coordinate(4);
		Coordinate c1 = new Coordinate(2);
		Point2D p2 = new Point2D(3, 4);
		Point3D p3 = new Point3D(1, 2, 3);
		Ray ray = new Ray(7,8,8,2,3,1);
		Vector v = new Vector(4, 5, 6);
		
		Point3D p4 = new Point3D(p3);
		p4 = new Point3D(1 , 1, 1);
		Point3D p5 = new Point3D(9, 9, 8);
		
		Vector v1 = new Vector(p4);
		
		Ray ray1 = new Ray(4, 4, 1, v1);
		Ray ray2 = new Ray(p4, 2, 2, 3);
		
		Cylinder cy = new Cylinder(3, ray);
		Plane pl = new Plane(p3, v);
		Sphere s = new Sphere(8, p4);
		Triangle t = new Triangle(p3, p4, p5);
		
		// Checking the Coordinate class
		System.out.println(c.add(c1));
		System.out.println(c.multiply(c1));
		System.out.println(c.scale(3));
		System.out.println(c.subtract(c1));
		
		// Checking the Point3D class
		System.out.println(p3.distance(p5));
		System.out.println(p4.add(v));
		System.out.println(p5.subtract(p3));
		
		// Checking the Vector class
		System.out.println((v.add(v1)).toString());
		System.out.println((v.subtract(v1)).toString());
		System.out.println((v.scale(4)).toString());
		System.out.println((v.dotProduct(v1)));
		System.out.println((v.crossProduct(v1)).toString());
		System.out.println(v.length());
		System.out.println(v1.normalize().toString());
	
		Point3D p6 = new Point3D(-1.8,2.7,7.55);
		Plane p10 = new Plane(p3,p4,p5);
		System.out.println(p10.getNormal(p6));
		
		System.out.println(t.getNormal(p6));
		
		Point3D p7 = new Point3D(7.85, 1.56, 5.09);
		System.out.println(s.getNormal(p7));
		
		Ray ray3 = new Ray(0, -8, 0, 3.97, 2.34, 0);
		Cylinder cy1 = new Cylinder(3, ray3);
		Point3D p8 = new Point3D(-0.13, -1.09, 1.5);
		System.out.println(cy1.getNormal(p8));
	}

}
