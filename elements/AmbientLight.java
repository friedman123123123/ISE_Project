package elements;


import primitives.Color;
import primitives.Point3D;

/**
 * @author Daniel & Yonathan
 *
 */
public class AmbientLight {
	Color _color;
	double Ka;
	
	
	/********** Constructors ***********/
	public AmbientLight() {
		_color = new Color(255, 255, 255);
		Ka = 1;
	}
	
	/************** Getters/Setters *******/
	
	public Color get_color() {
		return _color;
	}
	public double getKa() {
		return Ka;
	}
	public void set_color(Color _color) {
		this._color = _color;
	}
	public void setKa(double ka) {
		Ka = ka;
	}
	
	/************** Operations ***************/
	public Color getIntensity() {
		//Color I;
		Color ambientLight = _color.scale(Ka);
		//I = ambientLight;
		return ambientLight;
	}
}
