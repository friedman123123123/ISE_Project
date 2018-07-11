package scene;

import java.util.ArrayList;
import java.util.List;

import elements.AmbientLight;
import elements.Camera;
import elements.Light;
import elements.LightSource;
import elements.SpotLight;
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
	private List<LightSource> _lights;

	
	/********** Constructors ***********/
	public Scene(String name) {
		_name = name;
		_lights = new ArrayList<LightSource>();
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
	
	public List<LightSource> get_lights() {
		return _lights;
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

	public void set_lights(List<LightSource> lights) {
		_lights = lights;
	}

	/*************** Admin *****************/
	/************** Operations **************/

	/**
	 * @param Geometry
	 */
	public void addGeometry(Geometry g){
		_geometries.add(g);
	}
	
	
}
