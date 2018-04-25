package unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import elements.Camera;
import geometries.Sphere;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class SphereTests {

	/**** Sphere test ***/
	@Test
	public void testIntersectionPoints() throws Exception{
		final int WIDTH = 3;
		final int HEIGHT = 3;
		Ray[][] rays = new Ray [HEIGHT][WIDTH];
		Camera camera = new Camera(new Point3D(0.0 ,0.0 ,0.0), new Vector (0.0, 1.0, 0.0),new Vector (0.0, 0.0, -1.0));
		Sphere sphere = new Sphere(1, new Point3D(0.0, 0.0, -3.0));
		Sphere sphere2 = new Sphere(10, new Point3D(0.0, 0.0, -3.0));
		Sphere sphere3 = new Sphere(1, new Point3D(0, 1, -4));// sphere for boundry test with one intersection(tangent)
		// Only the center ray intersect the sphere in two locations
		ArrayList<Point3D> intersectionPointsSphere = new ArrayList<Point3D>();
		// The sphere encapsulates the view plane - all rays intersect with the sphere once
		ArrayList<Point3D> intersectionPointsSphere2 = new ArrayList<Point3D>();
		// Only one ray intersect the sphere in one point(tangent)
		ArrayList<Point3D> intersectionPointsSphere3 = new ArrayList<Point3D>();
		System.out.println("Camera:\n" + camera);
		for (int i = 0; i < HEIGHT; i++){
			for (int j = 0; j < WIDTH; j++)	{
				rays[i][j] = camera.constructRayThroughPixel(WIDTH, HEIGHT, j, i, 1, 3 * WIDTH, 3 * HEIGHT);
				ArrayList<Point3D> rayIntersectionPoints = sphere.findIntersectionPoints(rays[i][j]);
				ArrayList<Point3D> rayIntersectionPoints2 = sphere2.findIntersectionPoints(rays[i][j]);
				ArrayList<Point3D> rayIntersectionPoints3 = sphere3.findIntersectionPoints(rays[i][j]);
				for (Point3D iPoint: rayIntersectionPoints)
					intersectionPointsSphere.add(iPoint);
				for (Point3D iPoint: rayIntersectionPoints2)
					intersectionPointsSphere2.add(iPoint);
				for (Point3D iPoint: rayIntersectionPoints3)
					intersectionPointsSphere3.add(iPoint);
			}	
			/*assertTrue(intersectionPointsSphere. size() == 2);
			assertTrue(intersectionPointsSphere2.size() == 9);
			System.out.println("Intersection Points:");
			for (Point3D iPoint: intersectionPointsSphere) {
				assertTrue(iPoint.equals(new Point3D(0.0, 0.0, -2.0)) || iPoint.equals(new Point3D(0.0, 0.0, -4.0)));
				System.out.println(iPoint);*/
			}
		assertTrue(intersectionPointsSphere. size() == 2);
		assertTrue(intersectionPointsSphere2.size() == 9);
		assertTrue(intersectionPointsSphere3.size() == 1);
		System.out.println("Intersection Points with sphere:");
		for (Point3D iPoint: intersectionPointsSphere) {
			assertTrue(iPoint.equals(new Point3D(0.0, 0.0, -2.0)) || iPoint.equals(new Point3D(0.0, 0.0, -4.0)));
			System.out.println(iPoint);
		}
		System.out.println("Intersection Points with sphere2:");
		for (Point3D jPoint: intersectionPointsSphere2) {
				System.out.println(jPoint);
		}
		System.out.println("Intersection Points with sphere3:");
		for (Point3D jPoint: intersectionPointsSphere3) {
				System.out.println(jPoint);
		}
	}
}
