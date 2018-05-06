package elements;

import java.awt.Color;

/**
 * @author Daniel & Yonathan
 *
 */
public class AmbientLight {
	Color _color;
	double Ka;
	
	
	/********** Constructors ***********/
	
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

}
