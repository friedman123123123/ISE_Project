package primitives;

public class Color {
	private double _red;
	private double _green;
	private double _blue;
	
	/********** Constructors ***********/
	
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
		
	}
	
	public void scale(double d){
		_red *= d;
		_green *= d;
		_blue *= d;
	}
	
	public void reduce(double d){
		scale(1/d);
	}
}
