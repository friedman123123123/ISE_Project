package unittests;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import elements.Camera;
import geometries.Geometry;
import geometries.Triangle;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class TriangleTests {

	/*** Triangle test ***/
	@Test
	public void TriangleIntersectionPointsTest() {
		final int WIDTH = 3;
		final int HEIGHT = 3;
		Ray[][] rays = new Ray[HEIGHT][WIDTH];
		Camera camera = new Camera(new Point3D(0.0, 0.0, 0.0), new Vector(0.0, 1.0, 0.0), new Vector(0.0, 0.0, -1.0));
		Triangle triangle = new Triangle(new Point3D(0, 1, -2), new Point3D(1, -1, -2), new Point3D(-1, -1, -2),
				new Color(111, 111, 111));
		Triangle triangle2 = new Triangle(new Point3D(0, 10, -2), new Point3D(1, -1, -2), new Point3D(-1, -1, -2),
				new Color(111, 111, 111));
		// Triangle that a ray intersects in the vertex
		Triangle triangle3 = new Triangle(new Point3D(3, -3, -1), new Point3D(2.93, -2.58, -2),
				new Point3D(5.89, -4.34, 0), new Color(111, 111, 111));
		// Triangle that a ray intersects in the edge
		Triangle triangle4 = new Triangle(new Point3D(-0.84, -6.78, -2.41), new Point3D(1.2, -9.18, -2.85),
				new Point3D(1.38, -4.62, -2), new Color(111, 111, 111));
		List<Point3D> intersectionPointsTriangle = new ArrayList<Point3D>();
		List<Point3D> intersectionPointsTriangle2 = new ArrayList<Point3D>();
		List<Point3D> intersectionPointsTriangle3 = new ArrayList<Point3D>();
		List<Point3D> intersectionPointsTriangle4 = new ArrayList<Point3D>();
		System.out.println("Camera:\n" + camera);
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				rays[i][j] = camera.constructRayThroughPixel(WIDTH, HEIGHT, j, i, 1, 3 * WIDTH, 3 * HEIGHT);
				Map<Geometry, List<Point3D>> rayIntersectionPoints = triangle.findIntersectionPoints(rays[i][j]);
				Map<Geometry, List<Point3D>> rayIntersectionPoints2 = triangle2.findIntersectionPoints(rays[i][j]);
				Map<Geometry, List<Point3D>> rayIntersectionPoints3 = triangle3.findIntersectionPoints(rays[i][j]);
				Map<Geometry, List<Point3D>> rayIntersectionPoints4 = triangle4.findIntersectionPoints(rays[i][j]);
				rayIntersectionPoints.forEach((k, v) -> {
					intersectionPointsTriangle.addAll(v);
				});
				rayIntersectionPoints2.forEach((k, v) -> {
					intersectionPointsTriangle2.addAll(v);
				});
				rayIntersectionPoints3.forEach((k, v) -> {
					intersectionPointsTriangle3.addAll(v);
				});
				rayIntersectionPoints4.forEach((k, v) -> {
					intersectionPointsTriangle4.addAll(v);
				});
			}
		}
		assertTrue(intersectionPointsTriangle.size() == 1);
		assertTrue(intersectionPointsTriangle2.size() == 2);
		assertTrue(intersectionPointsTriangle3.size() == 0);
		assertTrue(intersectionPointsTriangle4.size() == 0);
		System.out.println("Intersection Points triangle:");
		for (Point3D iPoint : intersectionPointsTriangle)
			System.out.println(iPoint);
		System.out.println("--");
		System.out.println("Intersection Points triangle2:");
		for (Point3D iPoint : intersectionPointsTriangle2)
			System.out.println(iPoint);
		System.out.println("--");
		System.out.println("Intersection Points triangle3:");
		for (Point3D iPoint : intersectionPointsTriangle3)
			System.out.println(iPoint);
		System.out.println("--");
		System.out.println("Intersection Points triangle4:");
		for (Point3D iPoint : intersectionPointsTriangle4)
			System.out.println(iPoint);
	}

	@Test
	public void test_triangle() {

		Point3D p1 = new Point3D(0, 4, 3);
		Point3D p2 = new Point3D(4, 0, 3);
		Point3D p3 = new Point3D(-4, 0, 3);

		Triangle triangle = new Triangle(p1, p2, p3, new Color(111, 111, 111));
		Vector normal = new Vector(0, 0, 1);

		assertEquals(normal, triangle.get_normal());

	}
}
