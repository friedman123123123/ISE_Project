package primitives;

public class Color {
	private java.awt.Color _color;
	private double _red;
	private double _green;
	private double _blue;
	
	/********** Constructors ***********/
	
	/************** Getters/Setters *******/
	public java.awt.Color get_color() {
		return _color;
	}
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
	public void scale(double d){
		_red *= d;
		_green *= d;
		_blue *= d;
	}
	
	public void reduce(double d){
		scale(1/d);
	}
}
