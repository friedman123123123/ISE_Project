/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * @author Daniel & Yonathan
 *
 */
public class DirectionalLight extends Light implements LightSource{
	private Vector _direction;
	
	/********** Constructors ***********/
	public DirectionalLight(Vector direction,Color color) {
		super(color);
		_direction = direction;
	}

	/************** Getters/Setters *******/
	public Vector get_direction() {
		return _direction;
	}

	public void set_direction(Vector direction) {
		_direction = direction;
	}
	
	/************** Operations ***************/
	@Override
	public Color getIntensity(Point3D p){
		return getIntensity();
	}

	@Override
	public Vector getL(Point3D p) {
		return getD(p);
	}

	@Override
	public Vector getD(Point3D p) {
		return _direction;
	}
	
	
}
