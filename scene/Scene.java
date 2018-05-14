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
	public Scene(String name) {
		_name = name;
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
	
	public void set_name(String name) {
		_name = name;
	}

	public void set_camera(Camera camera) {
		_camera = camera;
	}

	public void set_distance(double distance) {
		_distance = distance;
	}

	public void set_geometries(Geometries geometries) {
		_geometries = geometries;
	}

	public void set_ambientLight(AmbientLight ambientLight) {
		_ambientLight = ambientLight;
	}

	public void set_background(Color background) {
		_background = background;
	}

	/*************** Admin *****************/
	/************** Operations **************/

	public void addGeometry(Geometry g){
		_geometries.add(g);
	}
	
	
}
