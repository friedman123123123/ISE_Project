package geometries;

import java.util.*;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Geometries extends Geometry {

	private List<Geometry> geometriesList = new ArrayList<Geometry>();

	/************** Operations ***************/

	public void add(Geometry g) {
		geometriesList.add(g);
	}

	public void remove(Geometry g) {
		geometriesList.remove(g);
	}

	@Override
	public Vector getNormal(Point3D p) {
		return null;
	}

	@Override
	public Map<Geometry, List<Point3D>> findIntersectionPoints(Ray r) {
		findIntersections = new HashMap<Geometry, List<Point3D>>();
		pointsIntersections = new ArrayList<Point3D>();
		List<Point3D> intersections = new ArrayList<Point3D>();
		Map<Geometry, List<Point3D>> map = new HashMap<Geometry, List<Point3D>>();
		for (Geometry g : geometriesList) {
			map = g.findIntersectionPoints(r);
			intersections = map.get(g);
			if (intersections != null) {
				if (!intersections.isEmpty()){
					pointsIntersections.addAll(intersections);
					findIntersections.put(g, pointsIntersections);
				}
			}
		}
		return findIntersections;
	}

}
