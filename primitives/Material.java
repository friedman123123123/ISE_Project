/**
 * 
 */
package primitives;

/**
 * @author Daniel
 *
 */
public class Material {
	private double _Ks;
	private double _Kd;
	private int _nShininess;
	
	/********** Constructors ***********/
	public Material(double Ks, double Kd, int nShininess) {
		_Ks = Ks;
		_Kd = Kd;
		_nShininess = nShininess;
	}
	
	public Material(Material other) {
		_Ks = other._Ks;
		_Kd = other._Kd;
		_nShininess = other._nShininess;
	}

	/************** Getters/Setters *******/
	public double get_Ks() {
		return _Ks;
	}
	public double get_Kd() {
		return _Kd;
	}
	public int get_nShininess() {
		return _nShininess;
	}
	public void set_Ks(double _Ks) {
		this._Ks = _Ks;
	}
	public void set_Kd(double _Kd) {
		this._Kd = _Kd;
	}
	public void set_nShininess(int _nShininess) {
		this._nShininess = _nShininess;
	}
	
	
}
