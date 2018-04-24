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
	public Camera(Point3D p, Vector vUp, Vector vTo) throws Exception {
		Vector vRight;
		
		if(vUp.dotProduct(vTo) != 0)
			throw new Exception("non-orthogonal");
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
		Point3D pC = new Point3D(get_p0().add(get_vTo().scale(screenDistance)));
		// Point3D p1 = new Point3D(pC).add(get_vUp().scale(0.5*screenHeight));
		// Point3D p2 = new Point3D(pC).add(get_vUp().scale(-0.5*screenHeight));
		double Rx = screenWidth / Nx;
		double Ry = screenHeight / Ny;
		//double y = (j + 0.5)*Ry; // remember: try catch
		//double x = (i + 0.5)*Rx;
		double y = (j - Ny/2.0 + 0.5)*Ry;
		double x = (i - Nx/2.0 + 0.5)*Rx;
		Point3D _p = new Point3D((pC.add(get_vRight().scale(((x-Nx/2.0)*Rx)+Rx/2.0))));
		Point3D p1 = new Point3D(_p.add((get_vUp().scale( - (((y-Ny/2)*screenHeight/Ny)+screenHeight/2.0*Ny)))));  
		//Point3D p = new Point3D((pC.add(get_vRight().scale(Rx*(x + 0.5 - Nx/2.0)).add((get_vUp().scale(-Ry*(y+0.5-Ny/2.0)))))));
		Point3D p = new Point3D((pC.add(get_vRight().scale(x).add((get_vUp().scale(-y))))));
		
		Vector _v = new Vector(p.subtract(get_p0()));
		Vector v = new Vector(_v.normalize());
		return new Ray(p, v); 
	}
	
}
