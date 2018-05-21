package elements;

import primitives.Color;
import primitives.Point3D;

/**
 * @author Daniel & Yonathan
 *
 */
public class AmbientLight extends Light{
	private double _Ka;
	private Color _intensity;

	/********** Constructors ***********/
	public AmbientLight() {
		super(new Color(0,0,0));
		_Ka = 1;
		_intensity = new Color(0,0,0).scale(_Ka);
		}

	public AmbientLight(Color color, double ka) {
		super(color);
		_Ka = ka;
		_intensity = new Color(color.scale(_Ka));
	}

	/************** Getters/Setters *******/


	public double getKa() {
		return _Ka;
	}

	public void setKa(double ka) {
		_Ka = ka;
	}

	/************** Operations ***************/
	/**
	 * final ambient light after multiply by _Ka
	 * @return Color
	 */
	@Override
	public Color getIntensity() {
		return new Color(_intensity);
	}
}
