package primitives;

/**
 * @author Daniel & Yonathan
 *
 */
public class Ray {
	private Point3D _p00;
	private Vector _direction;
	
	/********** Constructors ***********/
	// Constructors based on every possible combination of Point3D's constructors and Vector's constructors.
	
	public Ray(double x1, double y1, double z1, double x2, double y2, double z2) {
		_p00 = new Point3D(x1,y1,z1);
		_direction = new Vector(x2,y2,z2);
	}
	
	public Ray(double x, double y, double z, Vector v) {
		_p00 = new Point3D(x,y,z);
		_direction = new Vector(v);
	}
	
	public Ray(double x, double y, double z, Point3D p) {
		_p00 = new Point3D(x,y,z);
		_direction = new Vector(p);
	}
	
	public Ray(Point3D p, double x, double y, double z) {
		_p00 = new Point3D(p);
		_direction = new Vector(x,y,z);
	}
	
	public Ray(Point3D p, Vector v) {
		_p00 = new Point3D(p);
		_direction = new Vector(v);
	}
	
	public Ray(Point3D p1, Point3D p2) {
		_p00 = new Point3D(p1);
		_direction = new Vector(p2);
	}
	public Ray(Ray other) {
		_p00 = other._p00;
		_direction = other._direction;
	}

	/************** Getters/Setters *******/
	public Point3D get_p00() {
		return _p00;
	}
	
	public Vector get_direction() {
		return _direction;
	}
	
	/*************** Admin *****************/
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Ray)) 
			return false;
		Ray other = (Ray) obj;
		return _p00.equals(other._p00) && _direction.equals(other._direction);
	}

	@Override
	public String toString() {
		return "" + _p00 + ", " + _direction;
	}
		
	
}
