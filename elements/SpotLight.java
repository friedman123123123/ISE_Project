/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * @author Daniel
 *
 */
public class SpotLight extends PointLight {
	
		private Vector _direction;
	
	
	/********** Constructors ***********/
	/**
	 * @param position
	 * @param Kc
	 * @param Kl
	 * @param Kq
	 * @param color
	 */
	public SpotLight(Point3D position, double Kc, double Kl, double Kq, Color color, Vector direction) {
		super(position, Kc, Kl, Kq, color);
		_direction  = direction;
	}

	/************** Getters/Setters *******/
	public Vector get_direction() {
		return _direction;
	}

	public void set_direction(Vector _direction) {
		this._direction = _direction;
	}
	
	/************** Operations ***************/
	/**
	 * @param Point3D
	 * @return Color
	 */
	@Override
	public Color getIntensity(Point3D p){
		//double l = _position.distance(p);
		double a = _direction.dotProduct(getL(p));
		//double d = (_Kc < 1 ? _Kc = 1 : _Kc) + _Kl * l + _Kq * l * l;
		//return new Color(getIntensity().scale(a).reduce(d));
		return super.getIntensity(p).scale(a);
	}

	/**
	 * @param Point3D
	 * @return Vector
	 */
	@Override
	public Vector getD(Point3D p) {
		return _direction;
	}
}
