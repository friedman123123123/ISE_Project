/**
 * 
 */
package renderer;

import java.util.*;
import java.util.Map.Entry;

import org.junit.validator.PublicClassValidator;

import elements.LightSource;
import primitives.Vector;
import geometries.Geometry;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;
import sun.management.counter.Variability;

/**
 * @author Daniel & Yonathan
 *
 */
public class Render {
	private Scene _scene;
	private ImageWriter _imageWriter;

	private static class GeoPoint {
		public Geometry geometry;
		public Point3D point;
	}

	/***************** Constructors **********************/
	public Render(ImageWriter image, Scene scene) {
		_imageWriter = image;
		_scene = scene;
	}

	/***************** Getters/Setters **********************/
	public Scene get_scene() {
		return _scene;
	}

	public ImageWriter get_imageWriter() {
		return _imageWriter;
	}

	/***************** Operations ********************/
	/**
	 * prints a grid
	 * @param int
	 */
	public void printGrid(int interval) {
		for (int i = 0; i < _imageWriter.getNy() - 1; i++) {
			for (int j = 0; j < _imageWriter.getNx() - 1; j++) {
				if (((i + 1) % interval == 0) || (((j + 1) % interval) == 0))
					_imageWriter.writePixel(i, j, 255, 255, 255);
			}
		}
		// _imageWriter.writeToimage();
	}

	/**
	 * write to image
	 */
	public void printImage() {
		_imageWriter.writeToimage();
	}

	public void renderImage() {
		for (int i = 0; i < _imageWriter.getNx(); i++) {
			for (int j = 0; j < _imageWriter.getNy(); j++) {
				Ray ray = _scene.get_camera().constructRayThroughPixel(_imageWriter.getNx(), _imageWriter.getNy(), i, j,
						_scene.get_distance(), _imageWriter.getWidth(), _imageWriter.getHeight());
				Map<Geometry, List<Point3D>> intersectionsPoints = new HashMap<Geometry, List<Point3D>>(
						_scene.get_geometries().findIntersectionPoints(ray));
				Map<Geometry, Point3D> closestPoint = getClosestPoint(intersectionsPoints);
				if (intersectionsPoints.values().isEmpty() || intersectionsPoints == null || closestPoint == null)
					_imageWriter.writePixel(i, j, _scene.get_background().getColor());
				else {
					// System.out.println(intersectionsPoints);
					// System.out.println(closestPoint);
					// System.out.println("(" + i + "," + j + ")");
					// System.exit(0);
					Geometry geometry = (Geometry) closestPoint.keySet().toArray()[0];
					Point3D point3d = (Point3D) closestPoint.values().toArray()[0];
					_imageWriter.writePixel(i, j, calcColor(geometry, point3d).getColor()); // calcColor(closestPpoint)
					// _imageWriter.writePixel(i, j, 255,255,255);
				}
			}
			System.err.println(i + "/" + _imageWriter.getNx());
		}

	}

	/** Calculates the color
	 * @param Geometry
	 * @param Point3D
	 * @return Color
	 */
	private Color calcColor(Geometry geometry, Point3D p) {
		Color color = new Color(_scene.get_ambientLight().getIntensity());
		color = color.add(geometry.get_emission());

		Vector v = p.subtract(_scene.get_camera().get_p0());
		Vector n = geometry.getNormal(p);
		int nShininess = geometry.get_material().get_nShininess();
		double kd = geometry.get_material().get_Kd();
		double ks = geometry.get_material().get_Ks();
		
		for (LightSource lightSource : _scene.get_lights()) {
			Vector l = new Vector(lightSource.getL(p).normalize());
			if (n.dotProduct(l) * n.dotProduct(v) > 0) {
				Color lightIntensity = lightSource.getIntensity(p);
				color.add(calcDiffusive(kd, l, n, lightIntensity), calcSpecular(ks, l, n, v, nShininess, lightIntensity));
			}
		}

		return color;
	}

	
	/** Calculates the specular light
	 * @param double ks
	 * @param Vector l
	 * @param Vector n
	 * @param Vector v
	 * @param int nShininess
	 * @param Color lightIntensity
	 * @return Color
	 */
	private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
		Vector r = l.add(n.scale(2*(l.dotProduct(n))));
		return (lightIntensity.scale(ks*Math.pow(Math.abs(r.dotProduct(v)), nShininess)));
	}

	
	/** Calculates the diffusive light
	 * @param double kd
	 * @param Vector l
	 * @param Vector n
	 * @param Color lightIntensity
	 * @return Color
	 */
	private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
		return lightIntensity.scale(kd*Math.abs(l.dotProduct(n)));
	}

	/** Finds the closest point on a geometry from list of points of ray intersection
	 * @param Map<Geometry, List<Point3D>> 
	 * @return Map<Geometry, Point3D>
	 */
	private Map<Geometry, Point3D> getClosestPoint(Map<Geometry, List<Point3D>> points) {
		double minDistance = Double.MAX_VALUE;
		Point3D minp = _scene.get_camera().get_p0();
		Map<Geometry, Point3D> closestPoint = new HashMap<Geometry, Point3D>();
		Point3D p0 = _scene.get_camera().get_p0();
		double d;
		for (Map.Entry<Geometry, List<Point3D>> entry : points.entrySet()) {
			for (Point3D p : entry.getValue()) {
				d = p0.distanceSqrt(p);
				if (d < minDistance) {
					// if (_scene.get_camera().get_p0().distanceSqrt(p) <
					// minDistance) {
					// minDistance =
					// _scene.get_camera().get_p0().distanceSqrt(p);
					minDistance = d;
					minp = new Point3D(p);
					closestPoint.clear();
					closestPoint.put(entry.getKey(), minp);
				}
			}
		}

		return closestPoint;
	}
}
