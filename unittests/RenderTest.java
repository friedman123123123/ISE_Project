package unittests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.prefs.BackingStoreException;

import org.junit.Test;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

/**
 * @author Daniel&Yonathan
 *
 */
public class RenderTest {

	@Test
	public void basicRendering(){
		
		Date date = new Date();
		
		Scene scene = new Scene("Test scene");
		scene.set_camera(new Camera(new Point3D(0.0, 0.0, 0.0), new Vector(0.0, -1.0, 0.0), new Vector(0.0, 0.0, 1.0)));
		scene.set_distance(150);
		scene.set_background(new Color(0, 0, 0));
		Geometries geometries = new Geometries();
		scene.set_geometries(geometries);
		scene.set_ambientLight(new AmbientLight());
		geometries.add(new Sphere(50, new Point3D(0, 0, 150), new Color(111,111, 111)));
		
		geometries.add(new Triangle(new Point3D( 100, 0, 149), new Point3D( 0, 100, 149), new Point3D( 100, 100, 149), new Color(144,238,144)));
		
		geometries.add(new Triangle(new Point3D( 100, 0, 149), new Point3D( 0, -100, 149), new Point3D( 100,-100, 149), new Color(205,92,92)));
		
		geometries.add(new Triangle(new Point3D(-100, 0, 149), new Point3D( 0, 100, 149), new Point3D(-100, 100, 149), new Color(111,111, 111)));
		
		geometries.add(new Triangle(new Point3D(-100, 0, 149), new Point3D( 0,  -100, 149), new Point3D(-100, -100, 149), new Color(147,112,219)));
		
		ImageWriter imageWriter = new ImageWriter("test0", 500, 500, 500, 500);

		Render render = new Render(imageWriter, scene);
		
		render.renderImage();
		render.printGrid(50);
		render.printImage();
		
		System.out.println(new Date().getTime() - date.getTime());
	}

}
