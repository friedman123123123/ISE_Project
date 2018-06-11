package unittests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.*;
import java.util.prefs.BackingStoreException;

import org.junit.Test;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.LightSource;
import elements.PointLight;
import elements.SpotLight;
import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import javafx.scene.effect.Light.Spot;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

/**
 * @author Daniel & Yonathan
 *
 */
public class RenderTest {

	/*@Test
	public void basicRendering() {

		Date date = new Date();

		Scene scene = new Scene("Test scene");
		scene.set_camera(new Camera(new Point3D(0.0, 0.0, 0.0), new Vector(0.0, -1.0, 0.0), new Vector(0.0, 0.0, 1.0)));
		scene.set_distance(150);
		scene.set_background(new Color(0, 0, 0));
		Geometries geometries = new Geometries();
		scene.set_geometries(geometries);
		scene.set_ambientLight(new AmbientLight());

		geometries.add(new Sphere(50, new Point3D(0, 0, 150), new Color(111, 111, 111), new Material(1, 0.8, 5, 0, 0)));

		geometries.add(new Triangle(new Point3D(100, 0, 149), new Point3D(0, 100, 149), new Point3D(100, 100, 149),
				new Color(144, 238, 144), new Material(1, 0.8, 5,0,0)));

		geometries.add(new Triangle(new Point3D(100, 0, 149), new Point3D(0, -100, 149), new Point3D(100, -100, 149),
				new Color(205, 92, 92), new Material(1, 0.8, 5,0,0)));

		geometries.add(new Triangle(new Point3D(-100, 0, 149), new Point3D(0, 100, 149), new Point3D(-100, 100, 149),
				new Color(111, 111, 111), new Material(1, 0.8, 5,0,0)));

		geometries.add(new Triangle(new Point3D(-100, 0, 149), new Point3D(0, -100, 149), new Point3D(-100, -100, 149),
				new Color(147, 112, 219), new Material(1, 0.8, 5,0,0)));

		ImageWriter imageWriter = new ImageWriter("test0", 500, 500, 500, 500);

		Render render = new Render(imageWriter, scene);

		// render.renderImage();
		// render.printGrid(50);
		// render.printImage();

		System.out.println(new Date().getTime() - date.getTime());
	}

	@Test
	public void directionalLightRendering() {

		Date date = new Date();

		Scene scene = new Scene("Test scene directionalLight");
		scene.set_camera(new Camera(new Point3D(0.0, 0.0, 0.0), new Vector(0.0, -1.0, 0.0), new Vector(0.0, 0.0, 1.0)));
		scene.set_distance(350);
		scene.set_background(new Color(0, 0, 0));
		Geometries geometries = new Geometries();
		scene.set_geometries(geometries);
		scene.set_ambientLight(new AmbientLight());
		scene.get_lights().add(new DirectionalLight(new Vector(-1, 1, 1), new Color(230, 85, 90)));

		geometries.add(new Sphere(50, new Point3D(0, 0, 150), new Color(118, 90, 168), new Material(0.55, 0.95, 10,0,0)));

		ImageWriter imageWriter = new ImageWriter("testDirectional", 500, 500, 500, 500);

		Render render = new Render(imageWriter, scene);

		// render.renderImage();
		// render.printGrid(50);
		// render.printImage();

		System.out.println(new Date().getTime() - date.getTime());
	}

	@Test
	public void pointLightRendering() {

		Date date = new Date();

		Scene scene = new Scene("Test scene pointLight");
		scene.set_camera(new Camera(new Point3D(0.0, 0.0, 0.0), new Vector(0.0, 1.0, 0.0), new Vector(0.0, 0.0, -1.0)));
		scene.set_distance(120);
		scene.set_background(new Color(0, 0, 0));
		Geometries geometries = new Geometries();
		scene.set_geometries(geometries);
		scene.set_ambientLight(new AmbientLight(new Color(30, 30, 30), 1));

		scene.get_lights().add(new PointLight(new Point3D(3, 0, 0), 1, 0, 0.1, new Color(230, 200, 150)));
		geometries.add(new Sphere(8, new Point3D(0, 0, -10), new Color(143, 60, 185), new Material(0.9, 1, 20,0,0)));

		ImageWriter imageWriter = new ImageWriter("testPoint", 500, 500, 500, 500);

		Render render = new Render(imageWriter, scene);

		// render.renderImage();
		// render.printGrid(50);
		// render.printImage();

		System.out.println(new Date().getTime() - date.getTime());
	}

	@Test
	public void spotLightRendering() {

		Date date = new Date();

		Scene scene = new Scene("Test scene spotLight");
		scene.set_camera(new Camera(new Point3D(0.0, 0.0, 0.0), new Vector(0.0, -1.0, 0.0), new Vector(0.0, 0.0, 1.0)));
		scene.set_distance(350);
		scene.set_background(new Color(0, 0, 0));
		Geometries geometries = new Geometries();
		scene.set_geometries(geometries);
		scene.set_ambientLight(new AmbientLight());
		geometries.add(new Sphere(50, new Point3D(0, 0, 150), new Color(0, 0, 70), new Material(0.8, 0.7, 12,0,0)));
		Vector Dir = new Point3D(0, 0, 150).subtract(new Point3D(60, 60, 61));
		scene.get_lights().add(new SpotLight(new Point3D(35, -15, 1), 1, 0.7, 0.4, new Color(100, 100, 100),
				Dir.subtract(new Vector(9, 0, 3))));

		ImageWriter imageWriter = new ImageWriter("testSpot", 500, 500, 500, 500);

		Render render = new Render(imageWriter, scene);

		render.renderImage();
		// render.printGrid(50);
		render.printImage();

		System.out.println(new Date().getTime() - date.getTime());
	}

	@Test
	public void trianglesLightRendering() {

		Date date = new Date();

		Scene scene = new Scene("Test scene pointLight with triangle");
		scene.set_camera(new Camera(new Point3D(0.0, 0.0, 0.0), new Vector(0.0, -1.0, 0.0), new Vector(0.0, 0.0, 1.0)));
		scene.set_distance(150);
		scene.set_background(new Color(0, 0, 0));
		Geometries geometries = new Geometries();
		scene.set_geometries(geometries);
		scene.set_ambientLight(new AmbientLight(new Color(30, 30, 30), 1));

		// scene.get_lights().add(new PointLight(new Point3D(0, 0, -1), 1, 0, 0.1, new
		// Color(200, 80, 80)));
		scene.get_lights().add(new PointLight(new Point3D(3, 0, 0), 1, 0, 0.1, new Color(230, 200, 150)));
		// scene.get_lights().add(new DirectionalLight(new Vector(-1, 1, 1), new
		// Color(230, 85, 90)));

		// geometries.add(new Triangle(new Point3D(300, 300, 149), new Point3D(-180,
		// -200, 180), new Point3D(200, -200, 180),
		// new Color(20, 20, 20), new Material(1, 0.8, 5)));
		// geometries.add(new Triangle(new Point3D(297, 300, 149), new Point3D(-183,
		// -200, 180), new Point3D(-200, 300, 149),
		// new Color(20, 20, 20), new Material(1, 0.8, 5)));
		geometries.add(new Sphere(8, new Point3D(0, 0, -10), new Color(143, 60, 185), new Material(0.9, 1, 20,0,0)));

		ImageWriter imageWriter = new ImageWriter("testTrianglesPoint", 500, 500, 500, 500);

		Render render = new Render(imageWriter, scene);

		// render.renderImage();
		// render.printGrid(50);
		// render.printImage();

		System.out.println(new Date().getTime() - date.getTime());
	}

	
	 * @Test public void shadowRendering() {
	 * 
	 * Date date = new Date();
	 * 
	 * Scene scene = new Scene("Test scene shadow"); scene.set_camera(new Camera(new
	 * Point3D(0.0, 0.0, 0.0), new Vector(0.0, -1.0, 0.0), new Vector(0.0, 0.0,
	 * 1.0))); scene.set_distance(350); scene.set_background(new Color(0, 0, 0));
	 * Geometries geometries = new Geometries(); scene.set_geometries(geometries);
	 * scene.set_ambientLight(new AmbientLight()); geometries.add(new Sphere(50, new
	 * Point3D(0, 0, 150), new Color(0, 0, 70), new Material(0.8, 0.3, 20))); Vector
	 * Dir = new Point3D(0, 0, 150).subtract(new Point3D(60, 60, 61));
	 * scene.get_lights().add(new SpotLight(new Point3D(0, 0, 1), 1, 0.31, 0.7, new
	 * Color(100, 100, 100), Dir.subtract(new Vector(9, 0, 3))));
	 * 
	 * ImageWriter imageWriter = new ImageWriter("testShadow", 500, 500, 500, 500);
	 * 
	 * Render render = new Render(imageWriter, scene);
	 * 
	 * render.renderImage(); // render.printGrid(50); render.printImage();
	 * 
	 * System.out.println(new Date().getTime() - date.getTime()); }
	 */

	@Test
	public void shadowTest() {
		Scene scene = new Scene("Test shadow");
		scene.set_camera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, 1)));
		scene.set_distance(100);
		scene.set_background(new Color(0, 0, 0));
		scene.set_ambientLight(new AmbientLight());

		Geometries geometries = new Geometries();
		Triangle triangle1 = new Triangle(new Point3D(-250, -250, 120), new Point3D(-250, 250, 120),
				new Point3D(250, -250, 120), new Color(0, 0, 0), new Material(0.9, 0.8, 100,0,0));
		Triangle triangle2 = new Triangle(new Point3D(250, 250, 120), new Point3D(-250, 250, 120),
				new Point3D(250, -250, 120), new Color(0, 0, 0), new Material(0.9, 0.8, 100,0,0));

		Sphere sphere = new Sphere(60, new Point3D(0, 0, 80), new Color(0, 0, 70), new Material(0.9, 0.5, 30,0,0));

		

		geometries.add(triangle1);
		geometries.add(triangle2);
		geometries.add(sphere);

		scene.set_geometries(geometries);
		List<LightSource> lights = new ArrayList<LightSource>();
		SpotLight spotLight = new SpotLight(new Point3D(50, -1, -32), 1, 0, 0.69, new Color(200, 200, 200), new Vector(-25, 0, 80));
		lights.add(spotLight);
		scene.set_lights(lights);

		ImageWriter imageWriter = new ImageWriter("shadow test", 500, 500, 500, 500);
		Render testRender = new Render(imageWriter, scene);

		testRender.renderImage();
		testRender.printImage();

	}

	
}
