package primitives;

/**
 * @author Daniel & Yonathan
 *
 */
public class Coordinate {
	// private static final double EPSILON = 0.0000001;

	public static final Coordinate ZERO = new Coordinate(0);
	private double _coord;

	/********** Constructors ***********/
	public Coordinate(double coord) {
		// if it too close to zero make it zero
		_coord = (getExp(coord) < ACCURACY) ? 0.0 : coord;
	}

	public Coordinate(Coordinate other) {
		_coord = other._coord;
	}

	/************** Getters/Setters *******/
	public double get() {
		return _coord;
	}

	/*************** Admin *****************/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(_coord);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;

		// ... if (Coordinate.ZERO.equals(some_number)) ...
		if (obj instanceof Double)
			return _subtract((double) obj) == 0.0;

		if (!(obj instanceof Coordinate))
			return false;

		Coordinate other = (Coordinate) obj;
		return _subtract(other._coord) == 0.0;
	}

	@Override
	public String toString() {
		return "" + _coord;
	}

	/************** Operations ***************/
	// For every operation returns a new object to prevent any problems.

	/**
	 * @param Coordinate
	 * @return Coordinate
	 */
	public Coordinate subtract(Coordinate other) {
		return new Coordinate(_subtract(other._coord));
	}

	/**
	 * @param Coordinate
	 * @return Coordinate
	 */
	public Coordinate add(Coordinate other) {
		return new Coordinate(_add(other._coord));
	}

	/**
	 * @param double
	 * @return Coordinate
	 */
	public Coordinate scale(double num) {
		return new Coordinate(_scale(num));
	}

	/**
	 * @param Coordinate
	 * @return Coordinate
	 */
	public Coordinate multiply(Coordinate other) {
		return new Coordinate(_scale(other._coord));
	}

	/************** Helpers ***************/
	// It is binary, equivalent to ~1/1,000,000 in decimal (6 digits)
	private static final int ACCURACY = -20;

	// double store format (bit level): seee eeee eeee (1.)mmmm mmmm
	// 1 bit sign, 11 bits exponent, 53 bits (52 stored) normalized mantissa
	// the number is m+2^e where 1<=m<2
	// NB: exponent is stored "normalized" (i.e. always positive by adding 1023)
	private int getExp(double num) {
		// 1. doubleToRawLongBits: "convert" the stored number to set of bits
		// 2. Shift all 52 bits to the right (removing mantissa)
		// 3. Zero the sign of number bit by mask 0x7FF
		// 4. "De-normalize" the exponent by subtracting 1023
		return (int) ((Double.doubleToRawLongBits(num) >> 52) & 0x7FFL) - 1023;
	}

	private double _subtract(double other) {
		int otherExp = other == 0.0 ? 0 : getExp(other);
		int thisExp = _coord == 0.0 ? 0 : getExp(_coord);

		// if other is too small relatively to our coordinate
		// return the original coordinate
		if (otherExp - thisExp < ACCURACY)
			return _coord;

		// if our coordinate is too small relatively to other
		// return negative of other coordinate
		if (thisExp - otherExp < ACCURACY)
			return -other;

		double result = _coord - other;
		// if the result is too small tell that it is zero
		int resultExp = getExp(result);
		return resultExp < ACCURACY ? 0.0 : result;
	}

	private double _add(double other) {
		int otherExp = getExp(_coord);
		int thisExp = getExp(_coord);

		// if other is too small relatively to our coordinate
		// return the original coordinate
		if (otherExp - thisExp < ACCURACY)
			return _coord;

		// if our coordinate is too small relatively to other
		// return other coordinate
		if (thisExp - otherExp < ACCURACY)
			return other;

		double result = _coord + other;
		// if the result is too small tell that it is zero
		int resultExp = getExp(result);
		return resultExp < ACCURACY ? 0.0 : result;
	}

	private double _scale(double num) {
		int deltaExp = getExp(num - 1);
		return deltaExp < ACCURACY ? _coord : _coord * num;
	}

}
