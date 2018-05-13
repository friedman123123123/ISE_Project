package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import primitives.Vector;

/**
 * @author Daniel & Yonthan
 *
 */
public class VectorTests {

	// Tests all the functions in class Vector.
	// For each test, checks if the current result (of a specific example) is equal to the expected result.
	
	// Tests the add method.
	@Test
	public void testAdd() {
		Vector v1 = new Vector(2, 1, 3);
		Vector v2 = new Vector(6, 6, 9);
		Vector v3 = new Vector(v1.add(v2));
		Vector v4 = new Vector(8, 7, 12);
		assertEquals(v4, v3);
	}

	// Tests the subtract method.
	@Test
	public void testSubtract() {
		Vector v1 = new Vector(2, 1, 3);
		Vector v2 = new Vector(6, 6, 9);
		Vector v3 = new Vector(v1.subtract(v2));
		Vector v4 = new Vector(-4, -5, -6);
		assertEquals(v4, v3);
	}

	// Tests the scale method.
	@Test
	public void testScale() {
		Vector v1 = new Vector(2, 1, 3);
		Vector v2 = new Vector(v1.scale(5));
		Vector v3 = new Vector(10, 5, 15);
		assertEquals(v3, v2);
	}

	// Tests the dotProduct method.
	@Test
	public void testDotProduct() {
		Vector v1 = new Vector(2, 1, 3);
		Vector v2 = new Vector(6, 6, 9);
		double d1 = v1.dotProduct(v2);
		assertEquals(45, d1, 1e-20);
	}

	// Tests the crossProduct method.
	@Test
	public void testCrossProduct() {
		Vector v1 = new Vector(2, 1, 3);
		Vector v2 = new Vector(6, 6, 9);
		Vector v3 = new Vector(v1.crossProduct(v2));
		Vector v4 = new Vector(-9, 0, 6);
		assertEquals(v4, v3);
	}

	// Tests the length method.
	@Test
	public void testLength() {
		Vector v1 = new Vector(2, 4, 4);
		double l = v1.length();
		assertEquals(6, l, 1e-20);
	}

	// Tests the normalize method.
	@Test
	public void testNormalize(){
		Vector v1 = new Vector(0, 3, 4);
		Vector v2 = v1.normalize();
		Vector v3 = new Vector(0, 0.6, 0.8);
		assertEquals(v3, v2);
		
		
		// Checks if throws exception in divided by zero (when trying to normalize when the length of the vector is zero).
		
		try {
			Vector v4 = new Vector(0,0,0);
			fail("Didn't throw divide by zero exception!");// if we threw exception, we not reach that line, and if we reach that line we have a problem
		} 
		catch (ArithmeticException e) {
			assertTrue(true);
		}  
	}

}
