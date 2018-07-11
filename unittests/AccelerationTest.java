package unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import elements.SpotLight;
import geometries.Geometries;
import geometries.Rectangle;
import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.Acceleration;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

public class AccelerationTest {

	@Test
	public void test() {
		Scene scene = new Scene("Test acceleration");
		scene.set_camera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, 1)));
		scene.set_distance(100);
		scene.set_background(new Color(0, 0, 0));
		scene.set_ambientLight(new AmbientLight());

		Geometries geometries = new Geometries();

		Material material = new Material(0.9, 0.8, 100, 0, 0);
		int z = 200;
		/*for (int i = 1000; i >= 0; i -= 50) {
			int k = 150;
			for (int j = 1000; j >= 0; j -= 50) {
				Color color = new Color(i / 4, j / 4, k);
				Triangle t1 = new Triangle(new Point3D(i - 500, j - 500, z), new Point3D(i - 550, j - 500, z),
						new Point3D(i - 500, j - 550, z), color, material);
				Triangle t2 = new Triangle(new Point3D(i - 550, j - 550, z), new Point3D(i - 550, j - 500, z),
						new Point3D(i - 500, j - 550, z), color, material);
				k -= 2;
				geometries.add(t1);
				geometries.add(t2);
			}
		}*/
		
		for (int i = 1000; i >= 0; i -= 25) {
			int k = 150;
			for (int j = 1000; j >= 0; j -= 25) {
				Color color = new Color(i / 4, j / 4, k);
				Rectangle rectangle = new Rectangle(new Point3D(i - 500, j - 500, z), new Point3D(i - 525, j - 500, z),
						new Point3D(i - 500, j - 525, z), color, material);
				k -= 2;
				geometries.add(rectangle);
			}
		}
		scene.set_geometries(geometries);
		List<LightSource> lights = new ArrayList<LightSource>();
		SpotLight spotLight = new SpotLight(new Point3D(50, -1, -32), 1, 0, 0.69, new Color(200, 200, 200),
				new Vector(-25, 0, 80));
		lights.add(spotLight);
		scene.set_lights(lights);

		Acceleration acceleration = new Acceleration(scene);
		ImageWriter imageWriter = new ImageWriter("acceleration test", 500, 500, 500, 500);
		Render testRender = new Render(imageWriter, scene, acceleration);

		testRender.renderImage();
		testRender.printImage();

	}

}
