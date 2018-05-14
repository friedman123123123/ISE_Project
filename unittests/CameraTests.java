package unittests;

import static org.junit.Assert.*;

import java.awt.geom.Area;

import org.junit.Test;

import elements.Camera;
import junit.framework.Assert;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import primitives.Coordinate;

/**
 * @author Daniel & Yonathan
 *
 */
public class CameraTests {
	
	// Tests the camera for even and odd screen size.
	
	// Odd screen size.
	@Test
	public void test1RaysConstruction(){
		final int WIDTH = 3;
		final int HEIGHT = 3;
		Point3D[][] screen = new Point3D [HEIGHT][WIDTH];
		Camera camera = new Camera(new Point3D(0.0 ,0.0 ,0.0), new Vector (0.0, 1.0, 0.0), new Vector (0.0, 0.0, -1.0));
		System.out.println("Camera:\n" + camera);
		for (int j = 0; j < HEIGHT; j++)
		{
			for (int i = 0; i < WIDTH; i++)
			{
				Ray ray = camera.constructRayThroughPixel(WIDTH, HEIGHT, i, j, 1, 3 * WIDTH, 3 * HEIGHT);
				screen[i][j] = camera.advanceRayToViewPlane(WIDTH, HEIGHT, i, j, 1, 3 * WIDTH, 3 * HEIGHT);
				System.out.print(screen[i][j]);
				System.out.println(ray.get_direction());
				// Checking z-coordinate
				assertTrue(Double.compare(screen[i][j].getZ().get(), -1.0) == 0);
				// Checking all options
				double x = screen[i][j].getX().get();
				double y = screen[i][j].getY().get();
				if (Double.compare(x, 3) == 0 || Double.compare(x, 0) == 0 || Double.compare(x, -3) == 0)	{
					if (Double.compare(y, 3) == 0 || Double.compare(y, 0) == 0 || Double.compare(y, -3) == 0)
						assertTrue(true);
					else
						fail("Wrong y coordinate");
				}	else
						fail("Wrong x coordinate");
			}
			System.out.println("---");
		}

	}
	
	// Even screen size.	
	@Test
	public void test2RaysConstruction(){
		final int WIDTH = 3;
		final int HEIGHT = 3;
		Point3D[][] screen = new Point3D [HEIGHT + 1][WIDTH + 1];
		Camera camera = new Camera(new Point3D(0.0 ,0.0 ,0.0), new Vector (0.0, 1.0, 0.0), new Vector (0.0, 0.0, -1.0));
		System.out.println("Camera:\n" + camera);
		for (int j = 0; j < HEIGHT; j++)
		{
			for (int i = 0; i < WIDTH; i++)
			{
				Ray ray = camera.constructRayThroughPixel(WIDTH, HEIGHT, i, j, 1, 3 * WIDTH, 3 * HEIGHT);
				screen[i][j] = camera.advanceRayToViewPlane(WIDTH, HEIGHT, i, j, 1, 3 * WIDTH, 3 * HEIGHT);
				System.out.print(screen[i][j]);
				System.out.println(ray.get_direction());
				// Checking z-coordinate
				assertTrue(Double.compare(screen[i][j].getZ().get(), -1.0) == 0);
				// Checking all options
				double x = screen[i][j].getX().get();
				double y = screen[i][j].getY().get();
				if (Double.compare(x, 3) == 0 || Double.compare(x, 0) == 0 || Double.compare(x, -3) == 0)	{
					if (Double.compare(y, 3) == 0 || Double.compare(y, 0) == 0 || Double.compare(y, -3) == 0)
						assertTrue(true);
					else
						fail("Wrong y coordinate");
				}	else
						fail("Wrong x coordinate");
			}
			System.out.println("---");
		}

	}
}
