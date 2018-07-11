/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import primitives.*;
import renderer.Acceleration;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import org.junit.Test;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.LightSource;
import elements.SpotLight;
import geometries.Geometries;
import geometries.Sphere;

/**
 * @author yonathan friedman & Daniel Bazar
 *
 */
public class PigTest {

	@Test
	public void test() {
		
		Scene scene = new Scene("Test Pig");
		scene.set_camera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, 1)));
		scene.set_distance(100);
		scene.set_background(new Color(0, 0, 0));
		scene.set_ambientLight(new AmbientLight());

		Geometries geometries = new Geometries();
		
		float radius = 350;
		Point3D p = new Point3D(1,7,444);
		Material material = new Material(0, 1, 3, 0, 0);
		Color pink = new Color(255,192,203);
		Color white = new Color(255,255,255);
		Color eyeblue = new Color(65,105,225);
		Color black = new Color(0,0,0);

		Sphere body = new Sphere(radius, p, pink, material);
		Sphere head = new Sphere(0.75*radius, new Point3D(1, 7, 350), new Color(255,182,193), material);
		Sphere leftEyeWhite = new Sphere(0.1*radius, new Point3D(30, -20, 121), white, material);
		Sphere rightEyeWhite = new Sphere(0.1*radius, new Point3D(-30, -20, 121), white, material);
		Sphere leftEyeBlue = new Sphere(0.05*radius, new Point3D(32, -22, 100), eyeblue, material);
		Sphere rightEyeBlue = new Sphere(0.05*radius, new Point3D(-32, -22, 100), eyeblue, material);
		Sphere leftEyeBlack = new Sphere(0.01*radius, new Point3D(30, -21, 80), black, material);
		Sphere rightEyeBlack = new Sphere(0.01*radius, new Point3D(-30, -21, 80), black, material);
		Sphere mainNose = new Sphere(0.08*radius, new Point3D(0, 35, 80), new Color(219,112,147), material);
		Sphere leftNose = new Sphere(0.02*radius, new Point3D(-10, 30, 55), new Color(128,0,0), material);
		Sphere rightNose = new Sphere(0.02*radius, new Point3D(10, 30, 55), new Color(128,0,0), material);


		
		geometries.add(body);
		geometries.add(head);
		geometries.add(leftEyeWhite);
		geometries.add(rightEyeWhite);
		geometries.add(leftEyeBlue);
		geometries.add(rightEyeBlue);
		geometries.add(leftEyeBlack);
		geometries.add(rightEyeBlack);
		geometries.add(mainNose);
		geometries.add(leftNose);
		geometries.add(rightNose);

		
		scene.set_geometries(geometries);
		List<LightSource> lights = new ArrayList<LightSource>();
		DirectionalLight directionalLight = new DirectionalLight(new Vector(-1,1,0), white);
	//	lights.add(directionalLight);
		scene.set_lights(lights);

		Acceleration acceleration = new Acceleration(scene);
		ImageWriter imageWriter = new ImageWriter("pig test", 500, 500, 500, 500);
		Render testRender = new Render(imageWriter, scene, acceleration);

		testRender.renderImage2();
		testRender.printImage();
		
		
	}

}
