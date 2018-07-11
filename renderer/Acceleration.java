/**
 * 
 */
package renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.sun.javafx.scene.paint.GradientUtils.Point;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xerces.internal.impl.dv.xs.DoubleDV;

import geometries.Geometries;
import geometries.Geometry;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;
import sun.net.www.content.audio.x_aiff;

/**
 * @author Daniel & Yonathan
 *
 */
public class Acceleration {
	// for every index (cell in the grid) stores a list of geometries in this
	// cell
	private Map<Point3D, Geometries> grid = new HashMap<Point3D, Geometries>();

	private double Nx, Ny, Nz; // grid resolution in each side of the cube (how
								// many squares in each height/width/depth)

	private double gridMinX, gridMinY, gridMinZ; // minimums of the grid
	private double gridMaxX, gridMaxY, gridMaxZ; // maximums of the grid

	private double _dx, _dy, _dz; // the total length of each side of the 3D
									// cube

	private double _cellSizeX, _cellSizeY, _cellSizeZ; // size of the cell (in
														// real sizes)

	/********** Constructors ***********/
	/**
	 * Ctor which initialize the parameters of the class and calls the buildGrid
	 * function
	 * 
	 * @param scene
	 */
	public Acceleration(Scene scene) {
		scene.get_geometries().boundingBox(); // builds the bounding box of the
												// whole scene
		// finds the minimum and maxmimum values of the grid
		gridMaxX = scene.get_geometries().get_xFinalMax();
		gridMinX = scene.get_geometries().get_xFinalMin();
		gridMaxY = scene.get_geometries().get_yFinalMax();
		gridMinY = scene.get_geometries().get_yFinalMin();
		gridMaxZ = scene.get_geometries().get_zFinalMax();
		gridMinZ = scene.get_geometries().get_zFinalMin();

		// finds the length of each side of the grid
		_dx = gridMaxX - gridMinX + 1;
		_dy = gridMaxY - gridMinY + 1;
		_dz = gridMaxZ - gridMinZ + 1;

		// Nx = dx * cubeSqrt(lambda * N / V)

		// this formula tries to establish some relation between the dimension
		// of the scene, the number of primitive it contains and the overall
		// volume of the scene. The parameter lambda is a user defined parameter
		// which allows to fine tweak the performance of the algorithm.

		// It has been showed that the grid acceleration structure gives optimum
		// results for values of lambda between 3 and 5 ("Ray Tracing Animated
		// Scenes
		// using Coherent Grid Traversal", Wald et al. 2006). therefore lambda =
		// 4
		double cubeRoot = Math.pow(4 * scene.get_geometries().countGeometries() / volume(scene), 1.0 / 3.0);

		Nx = Math.floor(_dx * cubeRoot);
		Ny = Math.floor(_dy * cubeRoot);
		Nz = Math.floor(_dz * cubeRoot);

		// if the resolution < 1 then since it cannot be smaller than 1,
		// we change its value to 1 if so
		// and to keep track we don't get to big values
		// we also put an upper boundary
		
		if (Nx < 1)
			Nx = 1;
		else if (Nx > 128)
			Nx = 128;
		_cellSizeX = _dx / Nx;

		if (Ny < 1)
			Ny = 1;
		else if (Ny > 128)
			Ny = 128;
		_cellSizeY = _dy / Ny;

		if (Nz < 1)
			Nz = 1;
		else if (Nz > 128)
			Nz = 128;
		_cellSizeZ = _dz / Nz;

		buildGrid(scene);
	}

	/************** Operations ***************/

	/**
	 * Builds the grid
	 * 
	 * @param scene
	 */
	public void buildGrid(Scene scene) {
		// since the bbox of each geometry is calculated in the geometry itself
		// we create the grid by inserting each geometry in the cell/s it
		// belongs to
		// that, by looping on every geometry in the scene, and by its bbox,
		// found the cell/s it belongs to
		for (Geometry geometry : scene.get_geometries().getGeometriesList()) {

			// to convert the minimum of each geometry
			// in regards of the minimum of the grid
			double minX = Math.floor((geometry.get_xMin() - gridMinX) / _cellSizeX);
			double minY = Math.floor((geometry.get_yMin() - gridMinY) / _cellSizeY);
			double minZ = Math.floor((geometry.get_zMin() - gridMinZ) / _cellSizeZ);

			double cellMinX = pmalc(minX, 0, Nx - 1);
			double cellMinY = pmalc(minY, 0, Ny - 1);
			double cellMinZ = pmalc(minZ, 0, Nz - 1);

			// minimum point of the grid
			Point3D cellMin = new Point3D(cellMinX, cellMinY, cellMinZ);

			// to convert the maximum of each geometry
			// in regards of the maximum of the grid
			double maxX = Math.floor((geometry.get_xMax() - gridMinX) / _cellSizeX);
			double maxY = Math.floor((geometry.get_yMax() - gridMinY) / _cellSizeY);
			double maxZ = Math.floor((geometry.get_zMax() - gridMinZ) / _cellSizeZ);

			double cellMaxX = pmalc(maxX, 0, Nx - 1);
			double cellMaxY = pmalc(maxY, 0, Ny - 1);
			double cellMaxZ = pmalc(maxZ, 0, Nz - 1);

			// maximum point of the grid
			Point3D cellMax = new Point3D(cellMaxX, cellMaxY, cellMaxZ);

			// inserts every geometry to every cell
			for (double z = cellMin.getZ().get(); z <= cellMax.getZ().get(); ++z)
				for (double y = cellMin.getY().get(); y <= cellMax.getY().get(); ++y)
					for (double x = cellMin.getX().get(); x <= cellMax.getX().get(); ++x) {

						// since the class Geometries has already an
						// implementation of a list of geometries, we can just
						// use this form
						Geometries cell = new Geometries();

						// the key of the map will be the index - the cell in
						// which the geometry is stored
						Point3D p = new Point3D(x, y, z);
						if (grid.containsKey(p))
							cell = grid.get(p);
						cell.add(geometry);
						grid.put(p, cell);
					}
		}
	}

	/**
	 * the function which founds the closest intersection with the ray and does
	 * the acceleration instead of trying every geometry in scene with every ray
	 * we try only specific geometries with specific rays which accelerate the
	 * program using the 3DDDA algorithm
	 * 
	 * @param r
	 * @return Map<Geometry, Point3D>
	 */
	public Map<Geometry, Point3D> intersect(Ray r) {
		// since we only know to cooperate with rays that intersect the grid,
		// we will advance the ray if its not intersecting the grid
		Map<Geometry, Point3D> tHitPoint = new HashMap<Geometry, Point3D>();

		// using similar triangles, we use the x/y/z components
		// to find the distance to the next intersection
		double Rx = r.get_direction().getHead().getX().get();
		double Ry = r.get_direction().getHead().getY().get();
		double Rz = r.get_direction().getHead().getZ().get();

		// call a function which advance the ray to the grid
		// and if the is no intersection, returns -1
		double BBoxInter = BBoxIntersect(r, Rx, Ry, Rz);
		if (BBoxInter == -1) // if the ray doesn't intersect the grid
			return tHitPoint;

		// to know how much the ray has to advance in the grid every time,
		// we will take the minimal distance it has to pass to achievce the next
		// grid
		// via choosing the smallest of the x/y/z components
		double t_x, t_y, t_z;
		double deltaTx, deltaTy, deltaTz;

		// to convert the position of the origin of the ray in regards to the
		// minimum of the grid
		// which we do by subtracting the grid minimum extent to the ray's first
		// intersection with the grid.
		double Ogridx = r.get_p00().getX().get() + r.get_direction().scale(BBoxInter).getHead().getX().get() - gridMinX;
		double Ogridy = r.get_p00().getY().get() + r.get_direction().scale(BBoxInter).getHead().getY().get() - gridMinY;
		double Ogridz = r.get_p00().getZ().get() + r.get_direction().scale(BBoxInter).getHead().getZ().get() - gridMinZ;

		// we "normalize" Ogrid by dividing it with the cell's dimensions.
		// the resulting value corresponds to the position of the origin of the
		// ray in terms of number of cells.
		double Ocellx = Ogridx / _cellSizeX;
		double Ocelly = Ogridy / _cellSizeY;
		double Ocellz = Ogridz / _cellSizeZ;

		// Check if the ray is in the opposite or the right direction
		// Then find the delta to find the next intersection
		// And find the intersection in this cell

		// Find for x (positivity or negativity)
		if (Rx < 0) {
			deltaTx = -_cellSizeX / Rx;
			t_x = (Math.floor(Ocellx) * _cellSizeX - Ogridx) / Rx;
		} else {
			deltaTx = _cellSizeX / Rx;
			t_x = ((Math.floor(Ocellx) + 1) * _cellSizeX - Ogridx) / Rx;
		}

		// Find for y (positivity or negativity)
		if (Ry < 0) {
			deltaTy = -_cellSizeY / Ry;
			t_y = (Math.floor(Ocelly) * _cellSizeY - Ogridy) / Ry;
		} else {
			deltaTy = _cellSizeY / Ry;
			t_y = ((Math.floor(Ocelly) + 1) * _cellSizeY - Ogridy) / Ry;
		}

		// Find for z (positivity or negativity)
		if (Rz < 0) {
			deltaTz = -_cellSizeZ / Rz;
			t_z = (Math.floor(Ocellz) * _cellSizeZ - Ogridz) / Rz;
		} else {
			deltaTz = _cellSizeZ / Rz;
			t_z = ((Math.floor(Ocellz) + 1) * _cellSizeZ - Ogridz) / Rz;
		}

		// to find where to store the geometry in the HashMap
		// we will use the index of the cell/s it is in as the key of it's value
		// to do that, we just take the floor value of Ocell,
		// since it's referring to the cell we're in.
		double indexX = (double) Math.floor(Ocellx);
		double indexY = (double) Math.floor(Ocelly);
		double indexZ = (double) Math.floor(Ocellz);
		Point3D index;

		// the distance to the next cell
		double tNextCrossing;
		// the distance from the beginning of a cell to an intersection with a
		// geometry
		double tHit = Double.MAX_VALUE;
		// boolean t = true;

		// Loops until an intersection is found or we left the grid
		while (true) {
			index = new Point3D(indexX, indexY, indexZ);
			Map<Geometry, List<Point3D>> intersectionsPoints = new HashMap<Geometry, List<Point3D>>();

			// if the index (this key) exists in the HashMap
			if (grid.get(index) != null) {
				// since we already implemented a type that loop over a list of
				// geometries (in Geometries) we don't have to do it again
				// we just create the grid (in line 29) of type HashMap<Point3D,
				// Geometries>.
				intersectionsPoints = new HashMap<Geometry, List<Point3D>>(grid.get(index).findIntersectionPoints(r));

				/*
				 * for (Geometry geometry : grid.get(index)) { Map<Geometry,
				 * List<Point3D>> geometryIntersectionPoints = new
				 * HashMap<Geometry, List<Point3D>>(
				 * geometry.findIntersectionPoints(r));
				 * 
				 * geometryIntersectionPoints.forEach((g, list) -> { if
				 * (list.size() > 0) intersectionsPoints.put(g, list); }); }
				 */

				// we put the returned value as "new" because the function
				// doesn't return a new object
				tHitPoint = new HashMap<Geometry, Point3D>(getTHitPoint(intersectionsPoints, index));
				if (!tHitPoint.values().isEmpty())
					tHit = index.distance((Point3D) tHitPoint.values().toArray()[0]);
			}

			// searches the smallest distance to the next cell

			if (t_x < t_y && t_x < t_z) {
				tNextCrossing = t_x; // current t, next intersection with
										// cell along ray
				t_x += deltaTx; // increment, next crossing along x
				if (Rx < 0)
					indexX -= 1;
				else
					indexX += 1;
			}

			else if (t_y < t_x && t_y < t_z) {
				tNextCrossing = t_y;
				t_y += deltaTy; // increment, next crossing along y
				if (Ry < 0)
					indexY -= 1;
				else
					indexY += 1;
			}

			else {
				tNextCrossing = t_z; // current t, next intersection with
										// cell along ray
				t_z += deltaTz; // increment, next crossing along z
				if (Rz < 0)
					indexZ -= 1;
				else
					indexZ += 1;
			}
			
			// if we have intersected geometry and tHit < tNextCrossing
			// break
			if (tHit < tNextCrossing)
				break;
			// if we exited the cell break from the loop
			if (indexX < 0 || indexY < 0 || indexZ < 0 || indexX > Nx - 1 || indexY > Ny - 1 || indexZ > Nz - 1)
				break;
		}

		return tHitPoint;
	}

	/************** Helpers ***************/
	/**
	 * @param scene
	 * @return double
	 */
	private double volume(Scene scene) {
		return _dx * _dy * _dz;
	}

	
	/**
	 * finds the (first) closest (point) intersection of the ray with the grid
	 * @param points
	 * @param index
	 * @return Map<Geometry, Point3D>
	 */
	private Map<Geometry, Point3D> getTHitPoint(Map<Geometry, List<Point3D>> points, Point3D index) {
		double minDistance = Double.MAX_VALUE;
		Point3D minp = index;
		Map<Geometry, Point3D> closestPoint = new HashMap<Geometry, Point3D>();
		Point3D p0 = index;
		double d;
		for (Map.Entry<Geometry, List<Point3D>> entry : points.entrySet()) {
			for (Point3D p : entry.getValue()) {
				d = p0.distanceSqrt(p);
				if (d < minDistance) {
					minDistance = d;
					minp = new Point3D(p);
					// if we found a point before, and now found a closer one,
					// delete the first one
					closestPoint.clear();
					closestPoint.put(entry.getKey(), minp);
				}
			}
		}
		return closestPoint;
	}

	/**
	 * finds the distance of the first intersection of the ray with the grid
	 * @param Ray
	 * @param double
	 * @param double
	 * @param double
	 * @return double
	 */
	private double BBoxIntersect(Ray r, double Rx, double Ry, double Rz) {

		// to know in which direction are the rays
		// we check for every component (x/y/z) of the ray whether it is smaller then 0 or not
		Point3D sign = new Point3D((Rx < 0) ? 1 : 0, (Ry < 0) ? 1 : 0, (Rz < 0) ? 1 : 0);

		// minimum and maximum distances from the ray beginning
		// to the minimum and maximum of the grid
		double tmin, tmax, tymin, tymax, tzmin, tzmax;
		
		// if the sign is positive
		if (sign.getX().get() == 0) {
			tmin = (gridMinX - r.get_p00().getX().get()) / Rx;
			tmax = (gridMaxX - r.get_p00().getX().get()) / Rx;
		} else {
			tmin = (gridMaxX - r.get_p00().getX().get()) / Rx;
			tmax = (gridMinX - r.get_p00().getX().get()) / Rx;
		}

		// if the sign is positive
		if (sign.getY().get() == 0) {
			tymin = (gridMinY - r.get_p00().getY().get()) / Ry;
			tymax = (gridMaxY - r.get_p00().getY().get()) / Ry;
		} else {
			tymin = (gridMaxY - r.get_p00().getY().get()) / Ry;
			tymax = (gridMinY - r.get_p00().getY().get()) / Ry;
		}

		// if the minimum value of one component is bigger then the maximum of another one
		// or in the opposite way, return -1
		if ((tmin > tymax) || (tymin > tmax))
			return -1;

		// find the minimum and maximum distance to the minimum and maximum of the grid
		if (tymin > tmin)
			tmin = tymin;
		if (tymax < tmax)
			tmax = tymax;

		// if the sign is positive
		if (sign.getZ().get() == 0) {
			tzmin = (gridMinZ - r.get_p00().getZ().get()) / Rz;
			tzmax = (gridMaxZ - r.get_p00().getZ().get()) / Rz;
		} else {
			tzmin = (gridMaxZ - r.get_p00().getZ().get()) / Rz;
			tzmax = (gridMinZ - r.get_p00().getZ().get()) / Rz;
		}

		// if the minimum value of one component is bigger then the maximum of another one
		// or in the opposite way, return -1
		if ((tmin > tzmax) || (tzmin > tmax))
			return -1;

		// find the minimum and maximum distance to the minimum and maximum of the grid
		if (tzmin > tmin)
			tmin = tzmin;
		if (tzmax < tmax)
			tmax = tzmax;

		return tmin;
	}

	/**
	 * since we want to be sure we don't pass minimal value
	 * we take the maximum between our minimal value
	 * and the minimum between two other ones (to be sure that even their minimum is bigger than the minimal value)
	 * @param h
	 * @param low
	 * @param high
	 * @return
	 */
	private double pmalc(double h, double low, double high) {
		return Math.max(low, Math.min(h, high));
	}

	/*************** Admin *****************/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(Nx);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(Ny);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(Nz);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(_cellSizeX);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(_cellSizeY);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(_cellSizeZ);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(_dx);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(_dy);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(_dz);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((grid == null) ? 0 : grid.hashCode());
		temp = Double.doubleToLongBits(gridMaxX);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(gridMaxY);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(gridMaxZ);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(gridMinX);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(gridMinY);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(gridMinZ);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Acceleration)) {
			return false;
		}
		Acceleration other = (Acceleration) obj;
		if (Double.doubleToLongBits(Nx) != Double.doubleToLongBits(other.Nx)) {
			return false;
		}
		if (Double.doubleToLongBits(Ny) != Double.doubleToLongBits(other.Ny)) {
			return false;
		}
		if (Double.doubleToLongBits(Nz) != Double.doubleToLongBits(other.Nz)) {
			return false;
		}
		if (Double.doubleToLongBits(_cellSizeX) != Double.doubleToLongBits(other._cellSizeX)) {
			return false;
		}
		if (Double.doubleToLongBits(_cellSizeY) != Double.doubleToLongBits(other._cellSizeY)) {
			return false;
		}
		if (Double.doubleToLongBits(_cellSizeZ) != Double.doubleToLongBits(other._cellSizeZ)) {
			return false;
		}
		if (Double.doubleToLongBits(_dx) != Double.doubleToLongBits(other._dx)) {
			return false;
		}
		if (Double.doubleToLongBits(_dy) != Double.doubleToLongBits(other._dy)) {
			return false;
		}
		if (Double.doubleToLongBits(_dz) != Double.doubleToLongBits(other._dz)) {
			return false;
		}
		if (grid == null) {
			if (other.grid != null) {
				return false;
			}
		} else if (!grid.equals(other.grid)) {
			return false;
		}
		if (Double.doubleToLongBits(gridMaxX) != Double.doubleToLongBits(other.gridMaxX)) {
			return false;
		}
		if (Double.doubleToLongBits(gridMaxY) != Double.doubleToLongBits(other.gridMaxY)) {
			return false;
		}
		if (Double.doubleToLongBits(gridMaxZ) != Double.doubleToLongBits(other.gridMaxZ)) {
			return false;
		}
		if (Double.doubleToLongBits(gridMinX) != Double.doubleToLongBits(other.gridMinX)) {
			return false;
		}
		if (Double.doubleToLongBits(gridMinY) != Double.doubleToLongBits(other.gridMinY)) {
			return false;
		}
		if (Double.doubleToLongBits(gridMinZ) != Double.doubleToLongBits(other.gridMinZ)) {
			return false;
		}
		return true;
	}
}
