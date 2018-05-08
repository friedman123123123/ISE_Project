package scene;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometries;
import geometries.Geometry;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class Scene {
	
	private String _name;
	private Color _background;
	private Geometries _geometries;
	private Camera _camera;
	private double _distance;
	private AmbientLight _ambientLight;
	
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

	public Geometries get_geometries() {
		return _geometries;
	}

	public AmbientLight get_ambientLight() {
		return _ambientLight;
	}
	
	public Color get_background() {
		return _background;
	}
	
	public void set_name(String _name) {
		this._name = _name;
	}

	public void set_camera(Camera _camera) {
		this._camera = _camera;
	}

	public void set_distance(double _distance) {
		this._distance = _distance;
	}

	public void set_geometries(Geometries _geometries) {
		this._geometries = _geometries;
	}

	public void set_ambientLight(AmbientLight _ambientLight) {
		this._ambientLight = _ambientLight;
	}

	public void set_background(Color _background) {
		this._background = _background;
	}

	/*************** Admin *****************/
	/************** Operations ***************/

	public void addGeometry(Geometry g){
		_geometries.add(g);
	}
	
	
}
