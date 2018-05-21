package primitives;

public class Color {
	private double _red;
	private double _green;
	private double _blue;

	/********** Constructors ***********/
	/**
	 * default constructor
	 */
	public Color() {
		_red = 0.0;
		_green = 0.0;
		_blue = 0.0;
	}

	public Color(double r, double g, double b) {
		_red = r < 0 ? 0 : r;
		_green = g < 0 ? 0 : g;
		_blue = b < 0 ? 0 : b;
	}

	public Color(Color other) {
		_red = other._red;
		_green = other._green;
		_blue = other._blue;
	}

	public Color(java.awt.Color color) {
		_red = color.getRed();
		_green = color.getGreen();
		_blue = color.getBlue();
	}

	/**
	 * Getter for the color - produces final java.awt.Color
	 * 
	 * @return color in java.awt type
	 */
	public java.awt.Color getColor() {
		int r = (int) _red;
		int g = (int) _green;
		int b = (int) _blue;
		return new java.awt.Color(r > 255 || r < 0 ? 255 : r, g > 255 || g < 0 ? 255 : g, b > 255 || b < 0 ? 255 : b);
	}

	public Color setColor() {
		_red = 0.0;
		_green = 0.0;
		_blue = 0.0;
		return this;
	}

	public Color setColor(double red, double green, double blue) {
		_red = red;
		_green = green;
		_blue = blue;
		return this;
	}

	public Color setColor(Color other) {
		_red = other._red;
		_green = other._green;
		_blue = other._blue;
		return this;
	}

	public Color setColor(java.awt.Color color) {
		_red = color.getRed();
		_green = color.getGreen();
		_blue = color.getBlue();
		return this;
	}

	/************** Operations ***************/
	/**
	 * @param colors
	 *            to add to our color
	 * @return our color with adding colors
	 */
	public Color add(Color... colors) {
		for (Color color : colors) {
			_red += color._red;
			_green += color._green;
			_blue += color._blue;
		}
		return this;
	}

	/**
	 * Updates the color by scaling its intensity by d - scaling coefficient
	 * @param double
	 * @return Color
	 */
	public Color scale(double d) {
		//if (d < 1)
			//throw new ArithmeticException("Scaling color with a value smaller than 1");
		_red *= d;
		_green *= d;
		_blue *= d;
		return new Color(_red, _green, _blue);
	}

	/**
	 * Updates the color by reducing its intensity by d - scaling coefficient
	 * @param double
	 * @return Color
	 */
	public Color reduce(double d) {
		if (d <= 0)
			throw new ArithmeticException("Reducing color with a value out of 0 to 1 range");
		else if (d > 1)
			return scale(1 / d);
		else
			return scale(d);
	}

}
