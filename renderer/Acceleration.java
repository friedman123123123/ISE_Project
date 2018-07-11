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
	// for every index (cell in the grid) stores the geometries in this cell
	private Map<Point3D, List<Geometry>> grid = new HashMap<Point3D, List<Geometry>>(); 

	private double Nx, Ny, Nz; // grid resolution in each side of the cube (how many squares in each height/width/depth)

	private double gridMinX, gridMinY, gridMinZ; // minimums of the grid
	private double gridMaxX, gridMaxY, gridMaxZ; // maximums of the grid

	private double _dx, _dy, _dz; // the total length of each side of the 3D cube

	private double _cellSizeX, _cellSizeY, _cellSizeZ; // size of the cell (in real sizes)

	/********** Constructors ***********/
	/**
	 * Ctor which initialize the parameters of the class and calls the buildGrid function
	 * @param scene
	 */
	public Acceleration(Scene scene) {
		scene.get_geometries().boundingBox();
		gridMaxX = scene.get_geometries().get_xFinalMax();
		gridMinX = scene.get_geometries().get_xFinalMin();
		gridMaxY = scene.get_geometries().get_yFinalMax();
		gridMinY = scene.get_geometries().get_yFinalMin();
		gridMaxZ = scene.get_geometries().get_zFinalMax();
		gridMinZ = scene.get_geometries().get_zFinalMin();

		
		_dx = gridMaxX - gridMinX + 1;
		_dy = gridMaxY - gridMinY + 1;
		_dz = gridMaxZ - gridMinZ + 1;
		
		double cubeRoot = Math.pow(4 * scene.get_geometries().countGeometries() / volume(scene), 1.0/3.0);
		
		/*Nx = _dx * Math.cbrt(4 * scene.get_geometries().countGeometries() / volume(scene));
		Ny = _dy * Math.cbrt(4 * scene.get_geometries().countGeometries() / volume(scene));
		Nz = _dz * Math.cbrt(4 * scene.get_geometries().countGeometries() / volume(scene));*/
		
		Nx = Math.floor(_dx * cubeRoot);
		Ny = Math.floor(_dy * cubeRoot);
		Nz = Math.floor(_dz * cubeRoot);
		
		if(Nx < 1)
			Nx = 1;
		else if(Nx > 128)
			Nx = 128;
		_cellSizeX = _dx / Nx;
		
		if(Ny < 1)
			Ny = 1;
		else if(Ny > 128)
			Ny = 128;
		_cellSizeY = _dy / Ny;
		
		if(Nz < 1)
			Nz = 1;
		else if(Nz > 128)
			Nz = 128;
		_cellSizeZ = _dz / Nz;

		buildGrid(scene);
	}

	/************** Operations ***************/
	
	/**
	 * Builds the grid
	 * @param scene
	 */
	public void buildGrid(Scene scene) {
		// since the bbox of each geometry is calculated in the geometry itself
		// we create the grid by inserting each geometry in the cell/s it belongs to
		// that, by looping on every geometry in the scene, and by its bbox, found the cell/s it belongs to
		for (Geometry geometry : scene.get_geometries().getGeometriesList()) {
			
			double minX = Math.floor((geometry.get_xMin() - gridMinX)/ _cellSizeX);
			double minY = Math.floor((geometry.get_yMin() - gridMinY)/ _cellSizeY);
			double minZ = Math.floor((geometry.get_zMin() - gridMinZ)/ _cellSizeZ);
			
			double cellMinX = pmalc(minX, 0, Nx - 1);
			double cellMinY = pmalc(minY, 0, Ny - 1);
			double cellMinZ = pmalc(minZ, 0, Nz - 1);

			Point3D cellMin = new Point3D(cellMinX, cellMinY, cellMinZ); // minimum point of the grid
			//Point3D cellMin = new Point3D(minX, minY, minZ);

			double maxX = Math.floor((geometry.get_xMax() - gridMinX)/ _cellSizeX);			
			double maxY = Math.floor((geometry.get_yMax() - gridMinY)/ _cellSizeY);
			double maxZ = Math.floor((geometry.get_zMax() - gridMinZ)/ _cellSizeZ);
			
			double cellMaxX = pmalc(maxX, 0, Nx - 1);
			double cellMaxY = pmalc(maxY, 0, Ny - 1);
			double cellMaxZ = pmalc(maxZ, 0, Nz - 1);

			Point3D cellMax = new Point3D(cellMaxX, cellMaxY, cellMaxZ); // maximum point of the grid
			//Point3D cellMax = new Point3D(maxX, maxY, maxZ);

			
			// is it ++z?
			// inserts every geometry to every cell
			for (double z = cellMin.getZ().get(); z <= cellMax.getZ().get(); ++z)
				for (double y = cellMin.getY().get(); y <= cellMax.getY().get(); ++y)
					for (double x = cellMin.getX().get(); x <= cellMax.getX().get(); ++x) {
						// since the class Geometries has already an implementation of a list of geometries, we can just use this form
						List<Geometry> cell = new ArrayList<Geometry>();						
						// the key of the map will be the index - the cell in which the geometry is stored
  						Point3D p = new Point3D(x, y, z);
						boolean g = grid.containsKey(p);
						if (grid.containsKey(p))
							cell = grid.get(p);	
						cell.add(geometry);
						grid.put(p, cell);
					}
		}
	}

	/**
	 * the function which founds the closest intersection with the ray
	 * @param r
	 * @return Map<Geometry, Point3D>
	 */
	public Map<Geometry, Point3D> intersect(Ray r) {
		Map<Geometry, Point3D> tHitPoint = new HashMap<Geometry, Point3D>();
		
		double Rx = r.get_direction().getHead().getX().get();
		double Ry = r.get_direction().getHead().getY().get();
		double Rz = r.get_direction().getHead().getZ().get();
		
		double BBoxInter = BBoxIntersect(r, Rx, Ry, Rz);
		if(BBoxInter == -1) // if the ray doesnt intersect the grid
			return tHitPoint;
		
		// if the ray doesn't intersect the grid return
		// if (!bbox.intersect(r)) return false;
		double t_x, t_y, t_z;
		double deltaTx, deltaTy, deltaTz;
		double x, y, z;
		//if (r.get_p00().getX().get() < gridMinX)
			//x = 
		double Ogridx = r.get_p00().getX().get() + r.get_direction().scale(BBoxInter).getHead().getX().get() - gridMinX;
		double Ogridy = r.get_p00().getY().get() + r.get_direction().scale(BBoxInter).getHead().getY().get() - gridMinY;
		double Ogridz = r.get_p00().getZ().get() + r.get_direction().scale(BBoxInter).getHead().getZ().get() - gridMinZ;

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
		
		double indexX = (double) Math.floor(Ocellx);
		double indexY = (double) Math.floor(Ocelly);
		double indexZ = (double) Math.floor(Ocellz);
		Point3D index;
		double tNextCrossing;
		double tHit = Double.MAX_VALUE; // maybe should be inside the loop so every loop it's reinitialized
		boolean t = true;
		// Loops until an intersection is found or we left the grid
		while (t) {
			// intersect |= cell[cellIndex[0]][cellIndex[1]]->intersect(r,
			// tHit);
			index = new Point3D(indexX, indexY, indexZ);
			Map<Geometry, List<Point3D>> intersectionsPoints = new HashMap<Geometry, List<Point3D>>();
			if (grid.get(index) != null) {
				// since we already implemented a type that loop over a list of geometries (in Geometries) we don't have to do it again
				// we just create the grid (in line 29) of type HashMap<Point3D, Geometries>.
				
				//intersectionsPoints = new HashMap<Geometry, List<Point3D>>(grid.get(index).findIntersectionPoints(r));
				
				for (Geometry geometry : grid.get(index)) {
					Map<Geometry, List<Point3D>> geometryIntersectionPoints = new HashMap<Geometry, List<Point3D>>(
							geometry.findIntersectionPoints(r));

					geometryIntersectionPoints.forEach((g, list) -> {
						if (list.size() > 0)
							intersectionsPoints.put(g, list);
					});
				}
				
				// we put the returned value as "new" because the function doesn't return a new object 
				tHitPoint= new HashMap<Geometry, Point3D>(getTHitPoint(intersectionsPoints, index));
				tHit = index.distance((Point3D) tHitPoint.values().toArray()[0]);
			}
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
	private double volume(Scene scene) {
		return _dx * _dy * _dz;
	}

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
					// if we found a point before, and now found a closer one, delete the first one
					closestPoint.clear();
					closestPoint.put(entry.getKey(), minp);	
				}
			}
		}		
		//tHit = Math.sqrt(minDistance); // first option to check if doesnt work!!!!!!!!!
		return closestPoint;
	}	
	
	private double BBoxIntersect (Ray r, double Rx, double Ry, double Rz) {
		
		Point3D sign = new Point3D((Rx < 0)? 1 : 0, (Ry < 0)? 1 : 0, (Rz < 0)? 1 : 0);
		
		double tmin, tmax, tymin, tymax, tzmin, tzmax; 
		if (sign.getX().get() == 0) {
			tmin  = (gridMinX - r.get_p00().getX().get()) / Rx; 
		    tmax  = (gridMaxX - r.get_p00().getX().get()) / Rx; 
		}
		else {
			tmin  = (gridMaxX - r.get_p00().getX().get()) / Rx; 
		    tmax  = (gridMinX - r.get_p00().getX().get()) / Rx;
		}
	    
		if (sign.getY().get() == 0) {
			tymin  = (gridMinY - r.get_p00().getY().get()) / Ry; 
		    tymax  = (gridMaxY - r.get_p00().getY().get()) / Ry; 
		}
		else {
			tymin  = (gridMaxY - r.get_p00().getY().get()) / Ry; 
		    tymax  = (gridMinY - r.get_p00().getY().get()) / Ry;
		}
	 
	    if ((tmin > tymax) || (tymin > tmax)) 
	        return -1; 
	 
	    if (tymin > tmin) 
	        tmin = tymin; 
	    if (tymax < tmax) 
	        tmax = tymax; 
	 
	    if (sign.getZ().get() == 0) {
			tzmin  = (gridMinZ - r.get_p00().getZ().get()) / Rz; 
		    tzmax  = (gridMaxZ - r.get_p00().getZ().get()) / Rz; 
		}
		else {
			tzmin  = (gridMaxZ - r.get_p00().getZ().get()) / Rz; 
		    tzmax  = (gridMinZ - r.get_p00().getZ().get()) / Rz;
		}
	 
	    if ((tmin > tzmax) || (tzmin > tmax)) 
	        return -1; 
	 
	    if (tzmin > tmin) 
	        tmin = tzmin; 
	    if (tzmax < tmax) 
	        tmax = tzmax; 
	 
	    return tmin; 
	}
	
	private double pmalc(double h, double low, double high) {
		return Math.max(low, Math.min(h, high));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
