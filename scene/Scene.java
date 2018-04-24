package scene;

import elements.Camera;
import geometries.Geometries;
import geometries.Geometry;
import primitives.Point3D;
import primitives.Vector;

public class Scene {
	
	private String _name;
	//private color
	private Geometries _geometries;
	private Camera _camera;
	private double _distance;
	
	/********** Constructors ***********/
	public Scene(String name) throws Exception {
		_name = name;
		_camera =  new Camera(new Point3D(0.0 ,0.0 ,0.0), new Vector (0.0, 1.0, 0.0), new Vector (0.0, 0.0, -1.0));
		_distance = 1;
		_geometries = new Geometries();
	}

	/************** Getters/Setters *******/
	public String get_name() {
		return _name;
	}

	public Camera get_camera() {
		return _camera;
	}

	public double get_distance() {
		return _distance;
	}

	/*public void set_name(String _name) {
		this._name = _name;
	}

	public void set_camera(Camera _camera) {
		this._camera = _camera;
	}

	public void set_distance(double _distance) {
		this._distance = _distance;
	}*/
	/*************** Admin *****************/
	/************** Operations ***************/

	public void addGeometry(Geometry g){
		_geometries.add(g);
	}
}
