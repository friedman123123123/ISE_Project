package primitives;

public class Color {
	private double _red;
	private double _green;
	private double _blue;
	
	/********** Constructors ***********/
	
	public Color(double r, double g, double b) {
		_red = r;
		_green = g;
		_blue = b;
	}
	
	/************** Getters/Setters *******/
	public double getRed() {
		return _red;
	}
	public double getGreen() {
		return _green;
	}
	public double getBlue() {
		return _blue;
	}
	/************** Operations ***************/
	public Color add(Color c) {
		
		_red += c.getRed();
		_green += c.getGreen();
		_blue += c.getBlue();
		return new Color(_red, _green, _blue);
	}
	
	public Color scale(double d){
		_red *= d;
		_green *= d;
		_blue *= d;
		return new Color(_red, _green, _blue);
	}
	
	public Color reduce(double d){
		return scale(1/d);
	}
	
	public java.awt.Color getColor() {
		java.awt.Color color = new java.awt.Color((int)_red, (int)_green, (int)_blue);
		return color;
	}
}
