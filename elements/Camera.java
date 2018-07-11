package elements;

import java.awt.Point;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Camera {
	private Point3D _p0;
	private Vector _vUp;
	private Vector _vTo;
	private Vector _vRight;
	
	/********** Constructors ***********/
	public Camera(Point3D p, Vector vUp, Vector vTo){
		Vector vRight;
		
		if(vUp.dotProduct(vTo) != 0)
			throw new IllegalArgumentException("non-orthogonal");
		else 
			vRight = new Vector(vUp.crossProduct(vTo));
		
		_p0 = new Point3D(p);
		_vUp = new Vector(vUp);
		_vTo = new Vector(vTo);
		_vRight = new Vector(vRight);
	}

	/************** Getters/Setters *******/
	
	public Point3D get_p0() {
		return _p0;
	}

	public Vector get_vUp() {
		return _vUp;
	}

	public Vector get_vTo() {
		return _vTo;
	}

	public Vector get_vRight() {
		return _vRight;
	}

	
	/*************** Admin *****************/
	@Override
	public String toString() {
		return _p0  + ", vUp: " + _vUp  + " vTo: "+ _vTo + " vRight: " +  _vRight ;
	}

	
	/************** Operations ***************/
	
	/**
	 * @param int
	 * @param int
	 * @param int
	 * @param int
	 * @param double
	 * @param double
	 * @param double
	 * @return Ray
	 */
	public Ray constructRayThroughPixel(int Nx, int Ny, int i, int j, double screenDistance, double	screenWidth, double	screenHeight)
	{
		Point3D p = advanceRayToViewPlane(Nx, Ny, i, j, screenDistance, screenWidth, screenHeight);
		return new Ray(_p0, p.subtract(_p0)); 
	}
	
	/**
	 * @param int
	 * @param int
	 * @param int
	 * @param int
	 * @param double
	 * @param double
	 * @param double
	 * @return Point3D
	 */
	public Point3D advanceRayToViewPlane(int Nx, int Ny, int i, int j, double screenDistance, double screenWidth, double screenHeight) {
		Point3D pC = _p0.add(get_vTo().scale(screenDistance));
		double Rx = screenWidth / Nx;
		double Ry = screenHeight / Ny;
		double y = (j - Ny/2.0 + 0.5)*Ry;
		double x = (i - Nx/2.0 + 0.5)*Rx;

		Point3D p = pC;
		if (x != 0.0)
			p = p.add(get_vRight().scale(x));
		if (y != 0.0)
			p = p.add(get_vUp().scale(-y));
		return new Point3D(p);
	}
}
