/**
 * 
 */
package renderer;

import java.util.*;
import java.util.Map.Entry;

import org.junit.validator.PublicClassValidator;

import elements.Light;
import elements.LightSource;
import primitives.Vector;
import geometries.Geometry;
import primitives.Color;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

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
	 * 
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
				GeoPoint closestPoint = getClosestPoint(intersectionsPoints);
				if (intersectionsPoints.values().isEmpty() || intersectionsPoints == null || closestPoint == null)
					_imageWriter.writePixel(i, j, _scene.get_background().getColor());
				else {
					// System.out.println(intersectionsPoints);
					// System.out.println(closestPoint);
					// System.out.println("(" + i + "," + j + ")");
					// System.exit(0);
					// Geometry geometry = (Geometry)
					// closestPoint.keySet().toArray()[0];
					// Point3D point3d = (Point3D)
					// closestPoint.values().toArray()[0];
					_imageWriter.writePixel(i, j, calcColor(closestPoint, ray).getColor()); // calcColor(closestPpoint)
					// _imageWriter.writePixel(i, j, 255,255,255);
				}
			}
			System.err.println(i + "/" + _imageWriter.getNx());
		}

	}

	private Color calcColor(GeoPoint geopoint, Ray inRay) {
		return calcColor(geopoint, inRay, 3, 1.0);
	}

	/**
	 * Calculates the color
	 * 
	 * @param Geometry
	 * @param Point3D
	 * @return Color
	 */
	private Color calcColor(GeoPoint geopoint, Ray inRay, int level, double k) {
		if (level == 0 || Coordinate.ZERO.equals(k) || geopoint.geometry == null)
			return new Color(0, 0, 0);

			Color color = new Color(_scene.get_ambientLight().getIntensity());
			color = new Color(color.add(geopoint.geometry.get_emission()));


			Vector v = inRay.get_direction();

			// Vector v =
			// p.subtract(_scene.get_camera().get_p0()).normalize();//
			// normalize
			Vector n = geopoint.geometry.getNormal(geopoint.point);
			int nShininess = geopoint.geometry.get_material().get_nShininess();
			double kd = geopoint.geometry.get_material().get_Kd();
			double ks = geopoint.geometry.get_material().get_Ks();

			for (LightSource lightSource : _scene.get_lights()) {
				Vector l = new Vector(lightSource.getL(geopoint.point).normalize());
				if (n.dotProduct(l) * n.dotProduct(v) > 0) {
					double o = occluded(l, geopoint);
					if (!Coordinate.ZERO.equals(o * k)) {
						Color lightIntensity = new Color(lightSource.getIntensity(geopoint.point)).scale(o);
						color.add(calcDiffusive(kd, l, n, lightIntensity),
								calcSpecular(ks, l, n, v, nShininess, lightIntensity));
					}
				}
			}

			// Recursive call for a reflected ray
			Ray reflectedRay = constructReflectedRay(n, geopoint.point, inRay);
			GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
			double kr = geopoint.geometry.get_material().get_Kr();
			Color reflectedLight = calcColor(reflectedPoint, reflectedRay, level - 1, k * kr).scale(kr);
			// Recursive call for a refracted ray
			Ray refractedRay = constructRefractedRay(geopoint.point, inRay);
			GeoPoint refractedPoint = findClosestIntersection(refractedRay);
			double kt = geopoint.geometry.get_material().get_Kt();
			Color refractedLight = calcColor(refractedPoint, refractedRay, level - 1, k * kt).scale(kt);
			return color.add(reflectedLight, refractedLight);
		}
		


	/**
	 * Calculates the specular light
	 * 
	 * @param double
	 *            ks
	 * @param Vector
	 *            l
	 * @param Vector
	 *            n
	 * @param Vector
	 *            v
	 * @param int
	 *            nShininess
	 * @param Color
	 *            lightIntensity
	 * @return Color
	 */
	private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
		Vector r = l.add(n.scale(-2 * (l.dotProduct(n)))).normalize();

		if (v.dotProduct(r) > 0)
			return new Color(0, 0, 0);
		return new Color(lightIntensity).scale(ks * Math.pow(Math.abs(r.dotProduct(v)), nShininess));

	}

	/**
	 * Calculates the diffusive light
	 * 
	 * @param double
	 *            kd
	 * @param Vector
	 *            l
	 * @param Vector
	 *            n
	 * @param Color
	 *            lightIntensity
	 * @return Color
	 */
	private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
		return new Color(lightIntensity).scale(kd * Math.abs(l.dotProduct(n)));
	}

	/**
	 * @param Vector
	 *            n
	 * @param Point3D
	 *            p
	 * @param Ray
	 *            inRay
	 * @return Ray
	 */
	private Ray constructReflectedRay(Vector n, Point3D p, Ray inRay) {
		Vector v = inRay.get_direction();
		Vector r = v.subtract(n.scale(2 * (v.dotProduct(n)))).normalize();
		return new Ray(p, r);
	}

	private Ray constructRefractedRay(Point3D p, Ray inRay) {
		Vector v = inRay.get_direction();
		return new Ray(p, v);
		// return new Ray(inRay);
	}

	/**
	 * Finds the closest point on a geometry from list of points of ray
	 * intersection
	 * 
	 * @param Map<Geometry,
	 *            List<Point3D>>
	 * @return Map<Geometry, Point3D>
	 */
	private GeoPoint getClosestPoint(Map<Geometry, List<Point3D>> points) {
		double minDistance = Double.MAX_VALUE;
		Point3D minp = _scene.get_camera().get_p0();
		GeoPoint closestPoint = new GeoPoint();
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
					closestPoint.point = p;
					closestPoint.geometry = entry.getKey();
					// closestPoint.clear();
					// closestPoint.put(entry.getKey(), minp);
				}
			}
		}

		return closestPoint;
	}

	private GeoPoint findClosestIntersection(Ray ray) {
		Map<Geometry, List<Point3D>> intersections = new HashMap<Geometry, List<Point3D>>(
				_scene.get_geometries().findIntersectionPoints(ray));
		GeoPoint closestIntersection = getClosestPoint(intersections);
		return closestIntersection;
	}

	private double occluded(Vector l, GeoPoint geopoint) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Vector normal = geopoint.geometry.getNormal(geopoint.point);
		Vector epsVector = normal.scale(normal.dotProduct(lightDirection) > 0 ? 2 : -2);
		Point3D geometryPoint = geopoint.point.add(epsVector);
		Ray lightRay = new Ray(geometryPoint, lightDirection);
		Map<Geometry, List<Point3D>> intersectionPoints = _scene.get_geometries().findIntersectionPoints(lightRay);

		double shadowK = 1;
		for (Map.Entry<Geometry, List<Point3D>> entry : intersectionPoints.entrySet())
			shadowK *= entry.getKey().get_material().get_Kt();
		return shadowK;
	}
}
