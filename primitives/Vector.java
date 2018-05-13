package primitives;

/**
 * @author Daniel & Yonathan
 *
 */
public class Vector {
	private Point3D _head;
	
	/********** Constructors ***********/
	public Vector(Coordinate x, Coordinate y, Coordinate z) {
		_head = new Point3D(x, y, z);
		if (Point3D.ZERO.equals(_head))
			throw new IllegalArgumentException("asdasdfsdf ");
	}
	
	public Vector(double x, double y, double z) {
		_head = new Point3D(x, y, z);
	}
	
	public Vector(Point3D p) {
		_head = new Point3D(p);
	}
	
	public Vector(Vector other) {
		_head = new Point3D(other._head);
	}

	/************** Getters/Setters *******/
	public Point3D getHead() {
		return _head;
	}

	/*************** Admin *****************/
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Vector)) 
			return false;
		Vector other = (Vector) obj;
		return _head.equals(other._head);
	}

	@Override
	public String toString() {
		return "" + _head;
	}
	
	/************** Operations ***************/
	//For every operation returns a new object to prevent any problems.
	
	public Vector add(Vector v) {
		return new Vector(_add(v));
	}
	
	public Vector subtract(Vector v) {
		return new Vector(_subtract(v));
	}
	
	public Vector scale(double s) {
		return new Vector(_scale(s));
	}
	
	public double dotProduct(Vector v) {
		return _dotProduct(v);
	}
	
	public Vector crossProduct(Vector v) {
		 return new Vector(_crossProduct(v));
	}
	
	public double length() {
		return _length();
	}
	
	/************** Helpers ***************/	
	/**
	 * Calling Coordinate's add method to economize re-checking addition of coordinations.
	 * @param v
	 * @return
	 */
	private Vector _add(Vector v) {
		Coordinate x = _head.getX().add(v.getHead().getX());
		Coordinate y = _head.getY().add(v.getHead().getY());
		Coordinate z = _head.getZ().add(v.getHead().getZ());
		return new Vector(x, y, z);
	}
	
	/**
	 * Calling Coordinate's subtract method to economize re-checking subtraction of coordinations.
	 * @param v
	 * @return
	 */
	private Vector _subtract(Vector v) {
		Coordinate x = _head.getX().subtract(v.getHead().getX());
		Coordinate y = _head.getY().subtract(v.getHead().getY());
		Coordinate z = _head.getZ().subtract(v.getHead().getZ());
		return new Vector(x, y, z);
	}
	
	/**
	 * Calling Coordinate's scale method to economize re-checking multiplication by scalar of coordinations.
	 * @param s
	 * @return
	 */
	private Vector _scale(double s) {
		Coordinate x = _head.getX().scale(s);
		Coordinate y = _head.getY().scale(s);
		Coordinate z = _head.getZ().scale(s);
		return new Vector(x, y, z);
	}
	
	
	/**Calling Coordinate's multiply method to economize re-checking multiplication of coordinations.
	 * Using the linear equation to calculate dot product:
	 * u * v = u1*v1 + u2*v2 + u3*v3
	 * @param v
	 * @return
	 */
	private double _dotProduct(Vector v) {
		Coordinate x = _head.getX().multiply(v.getHead().getX());
		Coordinate y = _head.getY().multiply(v.getHead().getY());
		Coordinate z = _head.getZ().multiply(v.getHead().getZ());
		return x.get() + y.get() + z.get();
	}
	
	/**Calling Coordinate's multiply and subtract methods to economize re-checking multiplication and subtraction of coordinations.
	 * Using the linear equation to calculate cross product:
	 * u x v = (u2*v3 - u3*v2, u3*v1 - u1*v3, u1*v2 - u2*v1)
	 * @param v
	 * @return
	 */
	private Vector _crossProduct(Vector v) {
		Coordinate x = (_head.getY().multiply(v.getHead().getZ())).subtract(_head.getZ().multiply(v.getHead().getY()));
		Coordinate y = (_head.getZ().multiply(v.getHead().getX())).subtract(_head.getX().multiply(v.getHead().getZ()));
		Coordinate z = (_head.getX().multiply(v.getHead().getY())).subtract(_head.getY().multiply(v.getHead().getX()));
		return new Vector(x, y, z);
	}
	
	/**The length of a vector v, ||v||, is the equation:
	 * sqrt((x)^2 + (y)^2 + (z)^2)
	 *  since every vector starts from the origin (0, 0, 0).
	 * (Elsewhere, it would have used the normal distance equation, as shown in class Point3D.)
	 * @return
	 */
	private double _length() {
		return Math.sqrt(this.dotProduct(this));
	}
	
	/**The normalization of a vector v, is the vector itself divided by its length, as in the equation:
	 * nor(v) = v / ||v||
	 * @return
	 */
	public Vector normalize() {
		double l = this.length();
		double x = _head.getX().get()/l;
		double y = _head.getY().get()/l;
		double z = _head.getZ().get()/l;
		_head = new Point3D(x, y, z);
		return this;
	}

}
