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
	
	public Ray constructRayThroughPixel(int Nx, int Ny, int i, int j, double screenDistance, double	screenWidth, double	screenHeight)
	{
		Point3D pC = get_p0().add(get_vTo().scale(screenDistance));
		// Point3D p1 = new Point3D(pC).add(get_vUp().scale(0.5*screenHeight));
		// Point3D p2 = new Point3D(pC).add(get_vUp().scale(-0.5*screenHeight));
		double Rx = screenWidth / Nx;
		double Ry = screenHeight / Ny;
		//double y = (j + 0.5)*Ry; // remember: try catch
		//double x = (i + 0.5)*Rx;
		double y = (j - Ny/2.0 + 0.5)*Ry;
		double x = (i - Nx/2.0 + 0.5)*Rx;

		Point3D p = pC;
		if (x != 0.0)
			p = p.add(get_vRight().scale(x));
		if (y != 0.0)
			p = p.add(get_vUp().scale(-y));
		
		return new Ray(_p0, p.subtract(get_p0())); 
	}
	
}
