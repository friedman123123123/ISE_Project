package geometries;

import java.util.*;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Geometries extends Geometry {

	private List<Geometry> geometriesList = new ArrayList<Geometry>();
	private double _xFinalMin;
	private double _yFinalMin;
	private double _zFinalMin;
	private double _xFinalMax;
	private double _yFinalMax;
	private double _zFinalMax;

	/************** Getters/Setters *******/
	public List<Geometry> getGeometriesList() {
		return geometriesList;
	}
	
	public double get_xFinalMin() {
		return _xFinalMin;
	}

	public double get_yFinalMin() {
		return _yFinalMin;
	}

	public double get_zFinalMin() {
		return _zFinalMin;
	}

	public double get_xFinalMax() {
		return _xFinalMax;
	}

	public double get_yFinalMax() {
		return _yFinalMax;
	}

	public double get_zFinalMax() {
		return _zFinalMax;
	}
	
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
		/*findIntersections = new HashMap<Geometry, List<Point3D>>();
		pointsIntersections = new ArrayList<Point3D>();
		List<Point3D> intersections = new ArrayList<Point3D>();
		Map<Geometry, List<Point3D>> map = new HashMap<Geometry, List<Point3D>>();
		for (Geometry g : geometriesList) {
			map = g.findIntersectionPoints(r);
			intersections = map.get(g);
			if (intersections != null) {
				if (!intersections.isEmpty()){
					pointsIntersections.clear();
					pointsIntersections.addAll(intersections);
					findIntersections.put(g, pointsIntersections);
				}
			}
		}
		return findIntersections;*/
		
		Map<Geometry, List<Point3D>> intersectionPoints = new HashMap<Geometry, List<Point3D>>();
		for (Geometry geometry : geometriesList) {
			Map<Geometry, List<Point3D>> geometryIntersectionPoints = new HashMap<Geometry, List<Point3D>>(
					geometry.findIntersectionPoints(r));

			geometryIntersectionPoints.forEach((g, list) -> {
				if (list.size() > 0)
					intersectionPoints.put(g, list);
			});
		}
		return intersectionPoints;
	}
	
	public int countGeometries() {
		return geometriesList.size();
	}
	
	/************** Helpers ***************/
	public void boundingBox() {
		_xFinalMin = Double.MAX_VALUE;
		_xFinalMax = Double.MIN_VALUE;
		_yFinalMin = Double.MAX_VALUE;
		_yFinalMax = Double.MIN_VALUE;
		_zFinalMin = Double.MAX_VALUE;
		_zFinalMax = Double.MIN_VALUE;
		for(Geometry geometry: geometriesList) {
			if (geometry._xMin < _xFinalMin)
				_xFinalMin = geometry._xMin;
			if (_xFinalMax < geometry._xMax)
				_xFinalMax = geometry._xMax;
			
			if (geometry._yMin < _xFinalMin)
				_yFinalMin = geometry._yMin;
			if (_yFinalMax < geometry._yMax)
				_yFinalMax = geometry._yMax;
			
			if (geometry._zMin < _zFinalMin)
				_zFinalMin = geometry._zMin;
			if (_zFinalMax < geometry._zMax)
				_zFinalMax = geometry._zMax;
		}
	}
}
