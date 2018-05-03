package primitives;

/**
 * @author Daniel & Yonathan
 *
 */
public class Point3D extends Point2D {
private Coordinate _z;
	
	/**
	 * @param x - Coordinate value for x
	 * @param y
	 * @param z
	 */
	
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
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Point3D))
			return false;
		Point3D oth = (Point3D)obj;
		return super.equals(oth) && _z.equals(oth._z);
	}

	@Override
	public String toString() {
		return "(" + getX() + "," + getY() + "," + _z + ")";
	}
	
	/************** Operations ***************/
	//For every operation returns a new object to prevent any problems.
	
	public Vector subtract(Point3D p) {
		return new Vector(_subtract(p));
	}

	public Point3D add(Vector v) {
		return new Point3D(_add(v));
	}
	
	public double distance(Point3D p) {
		return _distance(p);
	}
	
	/************** Helpers ***************/
	/**
	 * Calling Coordinate's subtract method to economize re-checking subtraction of coordinations.
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
	 * Calling Coordinate's add method to economize re-checking addition of coordinations.
	 * @param v
	 * @return
	 */
	private Point3D _add(Vector v) {
		Coordinate x = this.getX().add(v.getHead().getX());
		Coordinate y = this.getY().add(v.getHead().getY());
		Coordinate z = _z.add(v.getHead().getZ());
		return new Point3D(x, y, z);
	}
	
	/**Using the Math library to calculate the power and square root according to the distance equation:
	 * sqrt((x1 - x2)^2 + (y1 - y2)^2 + (z1 - z2)^2)
	 * @param p
	 * @return
	 */
	private double _distance(Point3D p) {
		double x = Math.pow((p.getX().subtract(this.getX())).get(),2);
		double y = Math.pow((p.getY().subtract(this.getY())).get(),2);
		double z = Math.pow((p.getZ().subtract(_z)).get(),2);
		return Math.sqrt(x + y +z);
	}
}
