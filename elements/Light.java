/**
 * 
 */
package elements;


import primitives.Color;

/**
 * @author Daniel
 *
 */
public abstract class Light {

	private Color _color;

	/********** Constructors ***********/
	public Light(Color color) {
		_color = color;
	}

	/************** Getters/Setters *******/
	public Color get_color() {
		return _color;
	}

	public void set_color(Color c) {
		_color = c;
	}

	/************** Operations ***************/
	
	/**return the intensity of the light
	 * @return Color
	 */
	public Color getIntensity() {
		return new Color(_color);
	}

}
