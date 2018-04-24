package geometries;

/**
 * @author Daniel & Yonathan
 *
 */
public abstract class RadialGeometry extends Geometry {
	private double _radius;


	/********** Constructors ***********/

	public RadialGeometry(double _radius) {
		super();
		this._radius = _radius;
	}
	
	// Copy constructor
	public RadialGeometry(RadialGeometry r) {
		this._radius = r._radius;
	}

	/************** Getters/Setters *******/

	public double get_radius() {
		return _radius;
	}
	/*************** Admin *****************/

	/************** Operations ***************/
}
