package unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import elements.Camera;
import geometries.Plane;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class PlaneTests {

	/*** Plane test ***/
	@Test
	public void testIntersectionPoints() throws Exception{
		final int WIDTH = 3;
		final int HEIGHT = 3;
		Ray[][] rays = new Ray [HEIGHT][WIDTH];
		Camera camera = new Camera(new Point3D(0.0 ,0.0 ,0.0), new Vector (0.0, 1.0, 0.0), new Vector (0.0, 0.0, -1.0));
		// plane orthogonal to the view plane
		Plane plane = new Plane(new Point3D(0.0, 0.0, -3.0), new Vector(0.0, 0.0, -1.0));
		// 45 degrees to the view plane
		Plane plane2 = new Plane(new Point3D(0.0, 0.0, -3.0), new Vector(0.0, 0.25, -1.0));
		ArrayList<Point3D> intersectionPointsPlane = new ArrayList<Point3D>();
		ArrayList<Point3D> intersectionPointsPlane2 = new ArrayList<Point3D>();
		System.out.println("Camera:\n" + camera);
		for (int i = 0; i < HEIGHT; i++){
			for (int j = 0; j < WIDTH; j++){
				rays[i][j] = camera.constructRayThroughPixel(WIDTH, HEIGHT, j, i, 1, 3 * WIDTH, 3 * HEIGHT);
				ArrayList<Point3D> rayIntersectionPoints = plane.findIntersectionPoints(rays[i][j]);
				ArrayList<Point3D> rayIntersectionPoints2 = plane2.findIntersectionPoints(rays[i][j]);
				for (Point3D iPoint: rayIntersectionPoints)
					intersectionPointsPlane.add(iPoint);
				for (Point3D iPoint: rayIntersectionPoints2)
					intersectionPointsPlane2.add(iPoint);
			}
		}
		assertTrue(intersectionPointsPlane. size() == 9);
		assertTrue(intersectionPointsPlane2.size() == 9);
		for (Point3D iPoint: intersectionPointsPlane)
			System.out.println(iPoint);
		System.out.println("---");
		for (Point3D iPoint: intersectionPointsPlane2)
			System.out.println(iPoint);
	}

}
