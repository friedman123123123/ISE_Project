package geometries;

/**
 * @author Daniel & Yonathan
 *
 */
public abstract class RadialGeometry extends Geometry {
	private double _radius;


	/********** Constructors ***********/

	public RadialGeometry(double radius) {
		super();
		_radius = radius;
	}
	
	// Copy constructor
	public RadialGeometry(RadialGeometry other) {
		_radius = other._radius;
	}

	/************** Getters/Setters *******/

	public double get_radius() {
		return _radius;
	}
	/*************** Admin *****************/

	/************** Operations ***************/
}
