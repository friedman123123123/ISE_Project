
/**
 * @author Daniel
 *
 */

package geometries;

import java.util.List;
import java.util.Map;

import com.sun.org.apache.regexp.internal.recompile;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Rectangle extends Plane {
	private Ray _r1;
	private Ray _r2;
	
	private double _length1;
	private double _length2;
	
	/***************** Constructors **********************/
	/**
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param emission
	 * @param material
	 */
	public Rectangle(Point3D _p1, Point3D _p2, Point3D _p3, Color emission, Material material) {
		super(_p1, _p2, _p3, emission, material);
		_r1 = new Ray(_p1, _p2.subtract(_p1));
		_r2 = new Ray(_p1, _p3.subtract(_p1));
		_length1 = _p1.distance(_p2);
		_length2 = _p1.distance(_p3);
		boundingBox();
	}

	/**
	 * Copy constructor
	 * @param other
	 */
	public Rectangle(Rectangle other) {
		super(other._p, other._normal, other._emission, other._material);
		_r1 = new Ray(other._r1);
		_r2 = new Ray(other._r2);
		_length1 = other._length1;
		_length2 = other._length2;
		boundingBox();
	}

	/***************** Getters/Setters **********************/

	public Ray get_r1() {
		return _r1;
	}

	public Ray get_r2() {
		return _r2;
	}

	/***************** Admin ********************/
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rectangle other = (Rectangle) obj;
		if (_r1 == null) {
			if (other._r1 != null)
				return false;
		} else if (!_r1.equals(other._r1))
			return false;
		if (_r2 == null) {
			if (other._r2 != null)
				return false;
		} else if (!_r2.equals(other._r2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return super.toString() + "Rectangle [_r1=" + _r1 + ", _r2=" + _r2 + "]";
	}

	/***************** Operations ********************/
	/**
	 * @param Point3D
	 * @return Vector
	 */
	@Override
	public Vector getNormal(Point3D p) {
		return super.getNormal(p);
	}

	/**
	 * @param Ray
	 * @return Map<Geometry, List<Point3D>>
	 */
	@Override
	public Map<Geometry, List<Point3D>> findIntersectionPoints(Ray r) {
		Map<Geometry, List<Point3D>> intersections = super.findIntersectionPoints(r);
		for (int i = 0; i < intersections.size(); i++) {

			Point3D p = intersections.get(this).get(i);
			Vector v;
			double x, y, z;
			if (p.equals(this._p)) {
				x = p.getX().get() - _p.getX().get();
				y = p.getY().get() - _p.getY().get();
				z = p.getZ().get() - _p.getZ().get();

				double d1 = x * _r1.get_direction().getHead().getX().get()
						+ y * _r1.get_direction().getHead().getY().get()
						+ z * _r1.get_direction().getHead().getZ().get();

				if (d1 < 0 || d1 > _length1) {
					intersections.clear();
					return intersections;
				}

				double d2 = x * _r2.get_direction().getHead().getX().get()
						+ y * _r2.get_direction().getHead().getY().get()
						+ z * _r2.get_direction().getHead().getZ().get();

				if (d2 < 0 || d2 > _length2) {
					intersections.clear();
					return intersections;
				}
			} else {
				v = p.subtract(this._p);

				double d1 = v.dotProduct(_r1.get_direction());

				if (d1 < 0 || d1 > _length1) {
					intersections.clear();
					return intersections;
				}

				double d2 = v.dotProduct(_r2.get_direction());

				if (d2 < 0 || d2 > _length2) {
					intersections.clear();
					return intersections;
				}
			}
		}

		return intersections;
		/*for (int i = 0; i < intersections.size(); i++) {

			Point3D p = intersections.get(this).get(i);
			Vector v = p.subtract(this._p);

			double d1 = v.dotProduct(_r1.get_direction());

			if (d1 < 0 || d1 > _length1) {
				intersections.clear();
				return intersections;
			}

			double d2 = v.dotProduct(_r2.get_direction());

			if (d2 < 0 || d2 > _length2) {
				intersections.clear();
				return intersections;
			}

		}

		return intersections;*/
	}
	
	/************** Helpers ***************/
	public void boundingBox() {
		_xMin = Double.MAX_VALUE;
		_xMax = Double.MIN_VALUE;
		_yMin = Double.MAX_VALUE;
		_yMax = Double.MIN_VALUE;
		_zMin = Double.MAX_VALUE;
		_zMax = Double.MIN_VALUE;
		
		Point3D _p1 = new Point3D(_r1.get_p00());
		Point3D _p2 = new Point3D(_r1.get_p00().add(_r1.get_direction().scale(_length1)));
		Point3D _p3 = new Point3D(_r2.get_p00());
		Point3D _p4 = new Point3D(_r2.get_p00().add(_r2.get_direction().scale(_length2)));

		
		if (_p1.getX().get() < _xMin)
			_xMin = _p1.getX().get();
		if (_p1.getY().get() < _yMin)
			_yMin = _p1.getY().get();
		if (_p1.getZ().get() < _zMin)
			_zMin = _p1.getZ().get();
		
		if (_xMax < _p1.getX().get())
			_xMax = _p1.getX().get();
		if (_yMax < _p1.getY().get())
			_yMax = _p1.getY().get();
		if (_zMax < _p1.getZ().get())
			_zMax = _p1.getZ().get();
		
		if (_p2.getX().get() < _xMin)
			_xMin = _p2.getX().get();
		if (_p2.getY().get() < _yMin)
			_yMin = _p2.getY().get();
		if (_p2.getZ().get() < _zMin)
			_zMin = _p2.getZ().get();
		
		if (_xMax < _p2.getX().get())
			_xMax = _p2.getX().get();
		if (_yMax < _p2.getY().get())
			_yMax = _p2.getY().get();
		if (_zMax < _p2.getZ().get())
			_zMax = _p2.getZ().get();
		
		if (_p3.getX().get() < _xMin)
			_xMin = _p3.getX().get();
		if (_p3.getY().get() < _yMin)
			_yMin = _p3.getY().get();
		if (_p3.getZ().get() < _zMin)
			_zMin = _p3.getZ().get();
		
		if (_xMax < _p3.getX().get())
			_xMax = _p3.getX().get();
		if (_yMax < _p3.getY().get())
			_yMax = _p3.getY().get();
		if (_zMax < _p3.getZ().get())
			_zMax = _p3.getZ().get();
		
		if (_p4.getX().get() < _xMin)
			_xMin = _p4.getX().get();
		if (_p4.getY().get() < _yMin)
			_yMin = _p4.getY().get();
		if (_p4.getZ().get() < _zMin)
			_zMin = _p4.getZ().get();
		
		if (_xMax < _p4.getX().get())
			_xMax = _p4.getX().get();
		if (_yMax < _p4.getY().get())
			_yMax = _p4.getY().get();
		if (_zMax < _p4.getZ().get())
			_zMax = _p4.getZ().get();
	}
}
