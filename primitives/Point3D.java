package primitives;

/**
 * 
 * @author Daniel & Yonathan
 *
 */
public class Point3D extends Point2D {
	private Coordinate _z;

	final static Point3D ZERO = new Point3D(0, 0, 0);

	/********** Constructors ***********/
	public Point3D(Coordinate x, Coordinate y, Coordinate z) {
		super(x, y);
		_z = new Coordinate(z);
	}

	public Point3D(double x, double y, double z) {
		super(x, y);
		_z = new Coordinate(z);
	}

	public Point3D(Point3D other) {
		super(other);
		_z = new Coordinate(other._z);
	}

	/************** Getters/Setters *******/
	public Coordinate getZ() {
		return _z;
	}

	/*************** Admin *****************/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((_z == null) ? 0 : _z.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Point3D))
			return false;
		Point3D oth = (Point3D) obj;
		return super.equals(oth) && _z.equals(oth._z);
	}
	
	@Override
	public String toString() {
		return "(" + getX() + "," + getY() + "," + _z + ")";
	}

	/************** Operations ***************/
	// For every operation returns a new object to prevent any problems.

	/**
	 * @param Point3D
	 * @return Vector
	 */
	public Vector subtract(Point3D p) {
		return new Vector(_subtract(p));
	}

	/**
	 * @param Vector
	 * @return Point3D
	 */
	public Point3D add(Vector v) {
		return new Point3D(_add(v));
	}

	/**Distance between two points
	 * @param Point3D
	 * @return double
	 */
	public double distance(Point3D p) {
		return _distance(p);
	}

	/**Sqrt distance between two points
	 * @param Point3D
	 * @return double
	 */
	public double distanceSqrt(Point3D p) {
		double x = (p._x.subtract(_x)).get() * (p._x.subtract(_x)).get();
		double y = (p._y.subtract(_y)).get() * (p._y.subtract(_y)).get();
		double z = ((p._z.subtract(_z)).get()) * ((p._z.subtract(_z)).get());
		return (x + y + z);
	}

	/************** Helpers ***************/
	/**
	 * Calling Coordinate's subtract method to economize re-checking subtraction
	 * of coordinations.
	 * 
	 * @param p
	 * @return
	 */
	private Point3D _subtract(Point3D p) {
		Coordinate x = this.getX().subtract(p.getX());
		Coordinate y = this.getY().subtract(p.getY());
		Coordinate z = _z.subtract(p.getZ());
		return new Point3D(x, y, z);
	}

	/**
	 * Calling Coordinate's add method to economize re-checking addition of
	 * coordinations.
	 * 
	 * @param v
	 * @return
	 */
	private Point3D _add(Vector v) {
		Coordinate x = this.getX().add(v.getHead().getX());
		Coordinate y = this.getY().add(v.getHead().getY());
		Coordinate z = _z.add(v.getHead().getZ());
		return new Point3D(x, y, z);
	}

	/**
	 * Using the Math library to calculate the power and square root according
	 * to the distance equation: sqrt((x1 - x2)^2 + (y1 - y2)^2 + (z1 - z2)^2)
	 * 
	 * @param p
	 * @return
	 */
	private double _distance(Point3D p) {
		double x = (p._x.subtract(_x)).get() * (p._x.subtract(_x)).get();
		double y = (p._y.subtract(_y)).get() * (p._y.subtract(_y)).get();
		double z = ((p._z.subtract(_z)).get()) * ((p._z.subtract(_z)).get());
		return Math.sqrt(x + y + z);
	}
}
