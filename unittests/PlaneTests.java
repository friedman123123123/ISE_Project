package unittests;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import elements.Camera;
import geometries.Geometry;
import geometries.Plane;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class PlaneTests {

	/*** Plane test ***/
	@Test
	public void testIntersectionPoints(){
		final int WIDTH = 3;
		final int HEIGHT = 3;
		Ray[][] rays = new Ray [HEIGHT][WIDTH];
		Camera camera = new Camera(new Point3D(0.0 ,0.0 ,0.0), new Vector (0.0, 1.0, 0.0), new Vector (0.0, 0.0, -1.0));
		// plane orthogonal to the view plane
		Plane plane = new Plane(new Point3D(0.0, 0.0, -3.0), new Vector(0.0, 0.0, -1.0), new Color(111,111, 111));
		// 45 degrees to the view plane
		Plane plane2 = new Plane(new Point3D(0.0, 0.0, -3.0), new Vector(0.0, 0.25, -1.0), new Color(111,111, 111));
		// plane with contained rays
		Plane plane3 = new Plane(new Point3D(3,3,-1), new Point3D(5.14,5.14,-1.71), new Point3D(7,0,0), new Color(111,111, 111));
		
		
		
		List<Point3D> intersectionPointsPlane = new ArrayList<Point3D>();
		List<Point3D> intersectionPointsPlane2 = new ArrayList<Point3D>();
		List<Point3D> intersectionPointsPlane3 = new ArrayList<Point3D>();
		
		System.out.println("Camera:\n" + camera);
		for (int i = 0; i < HEIGHT; i++){
			for (int j = 0; j < WIDTH; j++){
				rays[i][j] = camera.constructRayThroughPixel(WIDTH, HEIGHT, j, i, 1, 3 * WIDTH, 3 * HEIGHT);
				Map<Geometry, List<Point3D>> rayIntersectionPoints = plane.findIntersectionPoints(rays[i][j]);
				Map<Geometry, List<Point3D>> rayIntersectionPoints2 = plane2.findIntersectionPoints(rays[i][j]);
				Map<Geometry, List<Point3D>> rayIntersectionPoints3 = plane3.findIntersectionPoints(rays[i][j]);
				
				rayIntersectionPoints.forEach((k, v) -> {
					intersectionPointsPlane.addAll(v);
				});
				rayIntersectionPoints2.forEach((k, v) -> {
					intersectionPointsPlane2.addAll(v);
				});
				rayIntersectionPoints3.forEach((k, v) -> {
					intersectionPointsPlane3.addAll(v);
				});
			}
		}
		
		assertTrue(intersectionPointsPlane.size() == 9);		
		assertTrue(intersectionPointsPlane2.size() == 9);		
		assertTrue(intersectionPointsPlane3.size() == 0);
		
		for(Point3D iPoint: intersectionPointsPlane)
			System.out.println(iPoint);
		System.out.println("---");
		for(Point3D iPoint: intersectionPointsPlane2)
			System.out.println(iPoint);
		System.out.println("---");
		for(Point3D iPoint: intersectionPointsPlane3)
			System.out.println(iPoint);
	}



		/*Map<Geometry, List<Point3D>> intersectionPointsPlane = new HashMap<Geometry, List<Point3D>>();
		Map<Geometry, List<Point3D>> intersectionPointsPlane2 = new HashMap<Geometry, List<Point3D>>();
		Map<Geometry, List<Point3D>> intersectionPointsPlane3 = new HashMap<Geometry, List<Point3D>>();*/


		/*rayIntersectionPoints.forEach((k, v) -> { 
			v.addAll(intersectionPointsPlane.get(k) == null ? new ArrayList<Point3D>() : intersectionPointsPlane.get(k));
			intersectionPointsPlane.put(k, v);
		});*/

		//assertTrue(intersectionPointsPlane.get(plane).size() == 9);
		/*intersectionPointsPlane.forEach((k,v) -> { 
			System.out.println(k + " " + v);
		});*/

	@Test
	public void test_plane() {

		Vector normal = new Vector(0, 0, 1);
		Point3D point = new Point3D(0, 0, 0);
		Plane plane = new Plane(point, normal,new Color(111,111, 111));

		Vector vector0 = new Vector(0, 0, 1);
		assertEquals(vector0, plane.get_normal());
	}
}