/** 
 * test Planet 
 */

public class TestPlanet {

	/**
	 * check whether the two doubles are euqal and print the result.
	 * 
	 * @param actual: it is what we get from the calc and it is a double.
	 * @param expected: it is what we want from the calc.
	 * @param label: label for the test case, here is calcForceExertedByX(), and calcForceExertedByY().
	 * @param eps: the tolerance for the double comparison.
	 */


	private static void checkEuqals(double actual, double expected, String label, double eps){
		if (Math.abs(expected - actual) > eps * Math.max(expected, actual)) {
			System.out.println("FAIL: " + label + ": Expected" + expected + " and you gave" + actual);
		}else{
			System.out.println("PASS: " + label + ": Expected" + expected + " and you gave" + actual);
		}
	}

	/** check Planet class and make sure the calcForceExertedByX(), and calcForceExertedByY() works
	 * 
	 */

	private static void checkCalForceExertedBy() {
		System.out.println("Checking calForceExertedBy...");

        Planet p1 = new Planet(1.0, 1.0, 3.0, 4.0, 5.0, "jupiter.gif");
        Planet p2 = new Planet(2.0, 1.0, 3.0, 4.0, 5.0, "jupiter.gif");
        Planet p3 = new Planet(4.0, 5.0, 3.0, 4.0, 5.0, "jupiter.gif");

        checkEuqals(p1.calcForceExertedByX(p2), 1.6675E-9, "cakForceExertedBy", 0.001);
	}

	public static void main(String[] args) {
		checkCalForceExertedBy();
	}



}