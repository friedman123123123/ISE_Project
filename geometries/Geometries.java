package geometries;

import java.util.ArrayList;

import primitives.Point3D;
import primitives.Ray;

public class Geometries extends Geometry{
	
	private ArrayList<Geometry> geometriesList = new ArrayList<Geometry>();
	
	/************** Operations ***************/
	
	public void add(Geometry g){
		geometriesList.add(g);
	}
	
	public void remove(Geometry g){
		geometriesList.remove(g);
	}

	@Override
	public Ray getNormal(Point3D p) {
		return null;
	}

	@Override
	public ArrayList<Point3D> findIntersectionPoints(Ray r){
		findIntersections = new ArrayList<Point3D>();
		ArrayList<Point3D> intersections = new ArrayList<Point3D>();
		for(Geometry g: geometriesList){
			intersections = g.findIntersectionPoints(r);
			for(Point3D p: intersections)
				findIntersections.add(p);
		}
		return findIntersections;
	}
	

}
