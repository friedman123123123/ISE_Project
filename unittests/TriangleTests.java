package unittests;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import elements.Camera;
import geometries.Triangle;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class TriangleTests {

	/*** Triangle test  ***/
	@Test
	public void TriangleIntersectionPointsTest(){
		final int WIDTH = 3;
		final int HEIGHT = 3;
		Ray[][] rays = new Ray [HEIGHT][WIDTH];
		Camera camera = new Camera(new Point3D(0.0 ,0.0	 ,0.0), new Vector (0.0, 1.0, 0.0), new Vector (0.0, 0.0, -1.0));
		Triangle triangle = new Triangle(new Point3D( 0, 1, -2), new Point3D( 1, -1, -2), new Point3D(-1, -1, -2));
		Triangle triangle2 = new Triangle(new Point3D( 0, 10, -2), new Point3D( 1, -1, -2), new Point3D(-1, -1, -2));
		// Triangle that a ray intersects in the vertex
		Triangle triangle3 = new Triangle(new Point3D(3, -3, -1), new Point3D( 2.93, -2.58, -2), new Point3D(5.89, -4.34, 0));
		// Triangle that a ray intersects in the edge
		Triangle triangle4 = new Triangle(new Point3D(-0.84, -6.78, -2.41), new Point3D(1.2, -9.18, -2.85), new Point3D(1.38, -4.62, -2));
		List<Point3D> intersectionPointsTriangle = new ArrayList<Point3D>();
		List<Point3D> intersectionPointsTriangle2 = new ArrayList<Point3D>();
		List<Point3D> intersectionPointsTriangle3 = new ArrayList<Point3D>();
		List<Point3D> intersectionPointsTriangle4 = new ArrayList<Point3D>();
		System.out.println("Camera:\n" + camera);
		for (int i = 0; i < HEIGHT; i++)
		{
			for (int j = 0; j < WIDTH; j++)
			{
				rays[i][j] = camera.constructRayThroughPixel(WIDTH, HEIGHT, j, i, 1, 3 * WIDTH, 3 * HEIGHT);
				List<Point3D> rayIntersectionPoints = triangle.findIntersectionPoints(rays[i][j]);
				List<Point3D> rayIntersectionPoints2 = triangle2.findIntersectionPoints(rays[i][j]);
				List<Point3D> rayIntersectionPoints3 = triangle3.findIntersectionPoints(rays[i][j]);
				List<Point3D> rayIntersectionPoints4 = triangle4.findIntersectionPoints(rays[i][j]);
				for (Point3D iPoint: rayIntersectionPoints)
					intersectionPointsTriangle.add(iPoint);
				for (Point3D iPoint: rayIntersectionPoints2)
					intersectionPointsTriangle2.add(iPoint);
				for (Point3D iPoint: rayIntersectionPoints3)
					intersectionPointsTriangle3.add(iPoint);
				for (Point3D iPoint: rayIntersectionPoints4)
					intersectionPointsTriangle4.add(iPoint);
			}
		}
		assertTrue(intersectionPointsTriangle.size() == 1);
		assertTrue(intersectionPointsTriangle2.size() == 2);
		assertTrue(intersectionPointsTriangle3.size() == 0);
		assertTrue(intersectionPointsTriangle4.size() == 0);
		System.out.println("Intersection Points triangle:");
		for (Point3D iPoint: intersectionPointsTriangle)
			System.out.println(iPoint);
		System.out.println("--");
		System.out.println("Intersection Points triangle2:");
		for (Point3D iPoint: intersectionPointsTriangle2)
			System.out.println(iPoint);
		System.out.println("--");
		System.out.println("Intersection Points triangle3:");
		for (Point3D iPoint: intersectionPointsTriangle3)
		System.out.println(iPoint);
		System.out.println("--");
		System.out.println("Intersection Points triangle4:");
		for (Point3D iPoint: intersectionPointsTriangle4)
		System.out.println(iPoint);
	}		
}
	