package unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import elements.SpotLight;
import geometries.*;
import primitives.*;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

public class RecursiveTest {

	@Test
	public void recursiveTest() {

		Scene scene = new Scene("recursive1");
		scene.set_camera(new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1)));
		scene.set_distance(300);
		scene.set_background(new Color(0, 0, 0));
		scene.set_ambientLight(new AmbientLight());

		Sphere sphere = new Sphere(500, new Point3D(0.0, 0.0, -1000), new Color(0, 0, 100),
				new Material(0.5, 0.3, 10, 0, 0.5));

		scene.addGeometry(sphere);
		Sphere sphere2 = new Sphere(250, new Point3D(0.0, 0.0, -1000), new Color(100, 20, 20),
				new Material(0.4, 0.4, 10, 0.5, 0));
		scene.addGeometry(sphere2);
		
		List<LightSource> lights = new ArrayList<LightSource>();
		lights.add(new SpotLight(new Point3D(-200, -200, -150), 0.1, 0.01, 0.0025, new Color(255, 100, 100),
				new Vector(2, 2, -3)));
		scene.set_lights(lights);
		
		ImageWriter imageWriter = new ImageWriter("Recursive Test", 500, 500, 500, 500);

		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.printImage();
		;

	}

	@Test
	public void recursiveTest2() {

		Scene scene = new Scene("recursive2");
		scene.set_camera(new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1)));
		scene.set_distance(300);
		scene.set_background(new Color(0, 0, 0));
		scene.set_ambientLight(new AmbientLight());

		Sphere sphere = new Sphere(300, new Point3D(-550, -500, -1000), new Color(0, 0, 100),
				new Material(0.4, 0.6, 10, 0, 0.5));
		scene.addGeometry(sphere);

		Sphere sphere2 = new Sphere(150, new Point3D(-550, -500, -1000), new Color(100, 20, 20),
				new Material(0.3, 0.4, 10, 0.5, 0));
		scene.addGeometry(sphere2);

		Triangle triangle = new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
				new Point3D(200, 200, -375), new Color(20, 20, 20), new Material(0.4, 0.6, 10, 1, 0));

		Triangle triangle2 = new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
				new Point3D(-1500, -1500, -1500), new Color(20, 20, 20), new Material(0.4, 0.6, 10, 0.5, 0));

		scene.addGeometry(triangle);
		scene.addGeometry(triangle2);

		List<LightSource> lights = new ArrayList<LightSource>();
		lights.add(new SpotLight(new Point3D(200, 200, -150), 0, 0.0001, 0.005, new Color(255, 100, 100),
				new Vector(-2, -2, -3)));
		scene.set_lights(lights);

		ImageWriter imageWriter = new ImageWriter("Recursive Test 2", 500, 500, 500, 500);

		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.printImage();

	}

	@Test
	public void recursiveTest3() {

		Scene scene = new Scene("recursive3");
		scene.set_camera(new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1)));
		scene.set_distance(300);
		scene.set_background(new Color(0, 0, 0));
		scene.set_ambientLight(new AmbientLight());
		
		Sphere sphere = new Sphere(300, new Point3D(0, 0, -1500), new Color(0, 0, 100), new Material(0.5, 0.3, 10, 0, 0.5));
		scene.addGeometry(sphere);

		Sphere sphere2 = new Sphere(150, new Point3D(0, 0, -1500), new Color(100, 20, 20), new Material(0.4, 0.6, 10, 0.5, 0));
		scene.addGeometry(sphere2);

		Triangle triangle = new Triangle(new Point3D(2000, -1000, -2000), new Point3D(-1000, 2000, -2000),
				new Point3D(700, 700, -875), new Color(20, 20, 20), new Material(0.5, 0.3, 10, 1, 0));

		Triangle triangle2 = new Triangle(new Point3D(2000, -1000, -2000), new Point3D(-1000, 2000, -2000),
				new Point3D(-1000, -1000, -2000), new Color(20, 20, 20), new Material(0.55, 0.25, 10, 0.5, 0));

		scene.addGeometry(triangle);
		
		scene.addGeometry(triangle2);


		List<LightSource> lights = new ArrayList<LightSource>();
		lights.add(new SpotLight(new Point3D(200, 200, -650), 0, 0.0001, 0.005, new Color(255, 100, 100),
				new Vector(-2, -2, -3)));
		scene.set_lights(lights);

		ImageWriter imageWriter = new ImageWriter("Recursive Test 3", 500, 500, 500, 500);

		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.printImage();

	}
	
}
