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
	private double _Kr;
	private double _Kt;

	/********** Constructors ***********/
	public Material(double Ks, double Kd, int nShininess, double Kr, double Kt) {
		_Ks = Ks;
		_Kd = Kd;
		_nShininess = nShininess;
		_Kr = Kr;
		_Kt = Kt;
	}

	public Material(Material other) {
		_Ks = other._Ks;
		_Kd = other._Kd;
		_nShininess = other._nShininess;
		_Kr = other._Kr;
		_Kt = other._Kt;
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

	public double get_Kr() {
		return _Kr;
	}

	public double get_Kt() {
		return _Kt;
	}

	public void set_Kr(double _Kr) {
		this._Kr = _Kr;
	}

	public void set_Kt(double _Kt) {
		this._Kt = _Kt;
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
