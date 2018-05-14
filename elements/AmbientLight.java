package elements;

import primitives.Color;
import primitives.Point3D;

/**
 * @author Daniel & Yonathan
 *
 */
public class AmbientLight {
	private Color _color;
	private double _Ka;
	private Color _intensity;

	/********** Constructors ***********/
	public AmbientLight() {
		_color = new Color(255, 255, 255);
		_Ka = 1;
		_intensity = new Color(_color).scale(_Ka);
	}

	public AmbientLight(Color color, double ka) {
		_color = color;
		_Ka = ka;
		_intensity = new Color(_color).scale(_Ka);
	}

	/************** Getters/Setters *******/

	public Color get_color() {
		return _color;
	}

	public double getKa() {
		return _Ka;
	}

	public void set_color(Color c) {
		_color = c;
	}

	public void setKa(double ka) {
		_Ka = ka;
	}

	/**
	 * @return final ambient light after multiply by _Ka
	 */
	public Color getIntensity()
	{
		return _intensity;
	}
}
