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
	}
	
	public Vector(double x, double y, double z) {
		_head = new Point3D(x, y, z);
	}
	
	public Vector(Point3D other) {
		_head = new Point3D(other);
	}
	
	public Vector(Vector other) {
		_head = other._head;
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
	
	public Vector normalize() {
		return new Vector(_normalize());
	}
	
	/************** Helpers ***************/	
	//Calling Coordinate's add method to economize re-checking addition of coordinations.
	private Vector _add(Vector v) {
		Coordinate x = this.getHead().getX().add(v.getHead().getX());
		Coordinate y = this.getHead().getY().add(v.getHead().getY());
		Coordinate z = this.getHead().getZ().add(v.getHead().getZ());
		return new Vector(x, y, z);
	}
	
	//Calling Coordinate's subtract method to economize re-checking subtraction of coordinations.
	private Vector _subtract(Vector v) {
		Coordinate x = this.getHead().getX().subtract(v.getHead().getX());
		Coordinate y = this.getHead().getY().subtract(v.getHead().getY());
		Coordinate z = this.getHead().getZ().subtract(v.getHead().getZ());
		return new Vector(x, y, z);
	}
	
	//Calling Coordinate's scale method to economize re-checking multiplication by scalar of coordinations.
	private Vector _scale(double s) {
		Coordinate x = this.getHead().getX().scale(s);
		Coordinate y = this.getHead().getY().scale(s);
		Coordinate z = this.getHead().getZ().scale(s);
		return new Vector(x, y, z);
	}
	
	
	//Calling Coordinate's multiply method to economize re-checking multiplication of coordinations.
	//Using the linear equation to calculate dot product:
	//u * v = u1*v1 + u2*v2 + u3*v3
	private double _dotProduct(Vector v) {
		Coordinate x = this.getHead().getX().multiply(v.getHead().getX());
		Coordinate y = this.getHead().getY().multiply(v.getHead().getY());
		Coordinate z = this.getHead().getZ().multiply(v.getHead().getZ());
		return x.get() + y.get() + z.get();
	}
	
	//Calling Coordinate's multiply and subtract methods to economize re-checking multiplication and subtraction of coordinations.
	//Using the linear equation to calculate cross product:
	//u x v = (u2*v3 - u3*v2, u3*v1 - u1*v3, u1*v2 - u2*v1)
	private Vector _crossProduct(Vector v) {
		Coordinate x = (this.getHead().getY().multiply(v.getHead().getZ())).subtract(this.getHead().getZ().multiply(v.getHead().getY()));
		Coordinate y = (this.getHead().getZ().multiply(v.getHead().getX())).subtract(this.getHead().getX().multiply(v.getHead().getZ()));
		Coordinate z = (this.getHead().getX().multiply(v.getHead().getY())).subtract(this.getHead().getY().multiply(v.getHead().getX()));
		return new Vector(x, y, z);
	}
	
	//The length of a vector v, ||v||, is the equation:
	// sqrt((x)^2 + (y)^2 + (z)^2)
	// since every vector starts from the origin (0, 0, 0).
	//(Elsewhere, it would have used the normal distance equation, as shown in class Point3D.)
	private double _length() {
		return Math.sqrt(Math.pow(this.getHead().getX().get(),2) + Math.pow(this.getHead().getY().get(),2) + Math.pow(this.getHead().getZ().get(),2));
	}
	
	//The normalization of a vector v, is the vector itself divided by its length, as in the equation:
	//nor(v) = v / ||v||
	private Vector _normalize() {
		double l = this.length();
		if (l == 0) throw new ArithmeticException("divide by zero"); // Throws exception if divided by zero.
		Coordinate x = new Coordinate((this.getHead().getX().get())/(l));
		Coordinate y = new Coordinate((this.getHead().getY().get())/(l));
		Coordinate z = new Coordinate((this.getHead().getZ().get())/(l));
		return new Vector(x, y, z);
	}

}
