package unittests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.prefs.BackingStoreException;

import org.junit.Test;

import com.sun.java.swing.plaf.windows.WindowsInternalFrameTitlePane.ScalableIconUIResource;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.LightSource;
import elements.PointLight;
import elements.SpotLight;
import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;
import sun.print.resources.serviceui;

/**
 * @author Daniel&Yonathan
 *
 */
public class RenderTest {

	/*
	 * @Test public void basicRendering(){
	 * 
	 * Date date = new Date();
	 * 
	 * Scene scene = new Scene("Test scene"); scene.set_camera(new Camera(new
	 * Point3D(0.0, 0.0, 0.0), new Vector(0.0, -1.0, 0.0), new Vector(0.0, 0.0,
	 * 1.0))); scene.set_distance(150); scene.set_background(new Color(0, 0,
	 * 0)); Geometries geometries = new Geometries();
	 * scene.set_geometries(geometries); scene.set_ambientLight(new
	 * AmbientLight());
	 * 
	 * 
	 * 
	 * geometries.add(new Sphere(50, new Point3D(0, 0, 150), new Color(111,111,
	 * 111), new Material(1, 0.8, 5)));
	 * 
	 * geometries.add(new Triangle(new Point3D( 100, 0, 149), new Point3D( 0,
	 * 100, 149), new Point3D( 100, 100, 149), new Color(144,238,144), new
	 * Material(1, 0.8, 5)));
	 * 
	 * geometries.add(new Triangle(new Point3D( 100, 0, 149), new Point3D( 0,
	 * -100, 149), new Point3D( 100,-100, 149), new Color(205,92,92), new
	 * Material(1, 0.8, 5)));
	 * 
	 * geometries.add(new Triangle(new Point3D(-100, 0, 149), new Point3D( 0,
	 * 100, 149), new Point3D(-100, 100, 149), new Color(111,111, 111), new
	 * Material(1, 0.8, 5)));
	 * 
	 * geometries.add(new Triangle(new Point3D(-100, 0, 149), new Point3D( 0,
	 * -100, 149), new Point3D(-100, -100, 149), new Color(147,112,219), new
	 * Material(1, 0.8, 5)));
	 * 
	 * ImageWriter imageWriter = new ImageWriter("test0", 500, 500, 500, 500);
	 * 
	 * Render render = new Render(imageWriter, scene);
	 * 
	 * render.renderImage(); render.printGrid(50); render.printImage();
	 * 
	 * System.out.println(new Date().getTime() - date.getTime()); }
	 */

	@Test
	public void directionalLightRendering() {

		Date date = new Date();

		Scene scene = new Scene("Test scene1");
		scene.set_camera(new Camera(new Point3D(0.0, 0.0, 0.0), new Vector(0.0, -1.0, 0.0), new Vector(0.0, 0.0, 1.0)));
		scene.set_distance(350);
		scene.set_background(new Color(0, 0, 0));
		Geometries geometries = new Geometries();
		scene.set_geometries(geometries);
		scene.set_ambientLight(new AmbientLight());
		scene.get_lights().add(new DirectionalLight(new Vector(-1, 1, 1), new Color(230, 85, 90)));

		geometries.add(new Sphere(50, new Point3D(0, 0, 150), new Color(118, 90, 168), new Material(0.55, 0.95, 10)));

		ImageWriter imageWriter = new ImageWriter("testDirectional", 500, 500, 500, 500);

		Render render = new Render(imageWriter, scene);

		render.renderImage();
		//render.printGrid(50);
		render.printImage();

		System.out.println(new Date().getTime() - date.getTime());
	}

	@Test
	public void pointLightRendering() {

		Date date = new Date();

		Scene scene = new Scene("Test scene2");
		scene.set_camera(new Camera(new Point3D(0.0, 0.0, 0.0), new Vector(0.0, 1.0, 0.0), new Vector(0.0, 0.0, -1.0)));
		scene.set_distance(50);
		scene.set_background(new Color(0, 0, 0));
		Geometries geometries = new Geometries();
		scene.set_geometries(geometries);
		scene.set_ambientLight(new AmbientLight(new Color(30, 30, 30), 1));

		scene.get_lights().add(new PointLight(new Point3D(3, 0, 0), 1, 0, 0.1, new Color(0, 255, 0)));
		geometries.add(new Sphere(8, new Point3D(0, 0, -10), new Color(200, 30, 70), new Material(0.8, 1, 20)));

		ImageWriter imageWriter = new ImageWriter("testPoint", 500, 500, 500, 500);

		Render render = new Render(imageWriter, scene);

		render.renderImage();
		// render.printGrid(50);
		render.printImage();

		System.out.println(new Date().getTime() - date.getTime());
	}

	@Test
	public void spotLightRendering() {

		Date date = new Date();

		Scene scene = new Scene("Test scene3");
		scene.set_camera(new Camera(new Point3D(0.0, 0.0, 0.0), new Vector(0.0, -1.0, 0.0), new Vector(0.0, 0.0, 1.0)));
		scene.set_distance(350);
		scene.set_background(new Color(0, 0, 0));
		Geometries geometries = new Geometries();
		scene.set_geometries(geometries);
		scene.set_ambientLight(new AmbientLight());
		geometries.add(new Sphere(50, new Point3D(0, 0, 150), new Color(0, 0, 70), new Material(1, 1, 12)));
		Vector Dir = new Point3D(0, 0, 150).subtract(new Point3D(60, 60, 61));
		scene.get_lights().add(new SpotLight(new Point3D(0, 0, 1), 1, 0.31, 0.7, new Color(100, 100, 100),
				Dir.subtract(new Vector(9, 0, 3))));

		ImageWriter imageWriter = new ImageWriter("testSpot", 500, 500, 500, 500);

		Render render = new Render(imageWriter, scene);

		render.renderImage();
		// render.printGrid(50);
		render.printImage();

		System.out.println(new Date().getTime() - date.getTime());

		/*
		 * Date date = new Date();
		 * 
		 * Scene scene = new Scene("Test scene3"); scene.set_camera(new
		 * Camera(new Point3D(0.0, 0.0, 0.0), new Vector(0.0, 1.0, 0.0), new
		 * Vector(0.0, 0.0, -1.0))); scene.set_distance(50);
		 * scene.set_background(new Color(0, 0, 0)); Geometries geometries = new
		 * Geometries(); scene.set_geometries(geometries);
		 * scene.set_ambientLight(new AmbientLight(new Color(20, 20, 20), 0.1));
		 * 
		 * scene.get_lights().add(new SpotLight(new Point3D(-2,2,3), 1, 5, 5,
		 * new Color(30,100,95), new Vector(2,-2,-30))); geometries.add(new
		 * Sphere(50, new Point3D(0, 0, -50), new Color(0, 0, 70), new
		 * Material(1, 2, 20)));
		 * 
		 * ImageWriter imageWriter = new ImageWriter("testspot", 500, 500, 500,
		 * 500);
		 * 
		 * Render render = new Render(imageWriter, scene);
		 * 
		 * render.renderImage(); // render.printGrid(50); render.printImage();
		 * 
		 * System.out.println(new Date().getTime() - date.getTime());
		 */
		/*
		 * Date date = new Date();
		 * 
		 * Scene scene = new Scene("Test scene3"); scene.set_camera(new
		 * Camera(new Point3D(0.0, 0.0, 0.0), new Vector(0.0, -1.0, 0.0), new
		 * Vector(0.0, 0.0, -1.0))); scene.set_distance(50);
		 * scene.set_background(new Color(0, 0, 0)); Geometries geometries = new
		 * Geometries(); scene.set_geometries(geometries);
		 * scene.set_ambientLight(new AmbientLight(new Color(20,20,20), 0.1));
		 * scene.get_lights().add(new SpotLight(new Point3D(-2,2,3), 1, 5, 5,
		 * new Color(255,255,255), new Vector(2,-2,30)));
		 * 
		 * 
		 * geometries.add(new Sphere(50, new Point3D(0, 0, -50), new Color(0, 0,
		 * 70), new Material(1, 2, 20)));
		 * 
		 * ImageWriter imageWriter = new ImageWriter("testSpot", 500, 500, 500,
		 * 500);
		 * 
		 * Render render = new Render(imageWriter, scene);
		 * 
		 * render.renderImage(); //render.printGrid(50); render.printImage();
		 * 
		 * System.out.println(new Date().getTime() - date.getTime());
		 */
	}

}
