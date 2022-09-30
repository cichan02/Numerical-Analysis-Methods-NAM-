package base;

import static java.lang.Math.max;
import static java.lang.Math.sqrt;
import static java.lang.Math.abs;

public final class Solution {
	private static final double a = 1;
	private static final double b = 2;
	private static final double y0 = 2;
		
	private static double phi0(double x, double y) {
		return  x * y / (y + 1) - x;
	}
	
	private static double dphi0(double x, double y) {
		return x / ((y + 1) * (y + 1));
	}
	
	private static double phi1t(double x, double y, double h) {
		return phi0(x + h, y + h * phi0(x, y));
	}
	
	private static double phi1r(double x, double y, double h) {
		return phi0(x + h / 2, y + h * phi0(x, y) / 2);
	}
	
	private static double phi2r(double x, double y, double h) {
		return phi0(x + h / 2, y + h * phi1r(x, y, h) / 2);
	}
	
	private static double phi3r(double x, double y, double h) {
		return phi0(x + h, y + h * phi2r(x, y, h));
	}
	
	private static double u(double x) {
		return sqrt(10 - x*x) - 1;
	}
	
	private static double newtonIteration(double x, double y, double prevIter, double h) {
		return prevIter - ((prevIter - y - h * (phi0(x, y) + phi0(x+h, prevIter)) / 2) / (1 - h * dphi0(x+h, prevIter) / 2));
	}
	
	private static double newtonMethod(double x, double y, double h) {
		double prevIter = y;
		double curIter = newtonIteration(x, y, prevIter, h);
		while(abs(curIter - prevIter) > 1e-6) {
			prevIter = curIter;
			curIter = newtonIteration(x, y, prevIter, h);
		}
		return curIter;
	}
	
	private static double rungeKuttaIteration(double x, double y, double h) {
		return y + h * (phi0(x, y) + 2 * phi1r(x, y, h) + 2 * phi2r(x, y, h) + phi3r(x, y, h)) / 6;
	}
	
	private static void methodsFrame(double h, String name, ToDoubleThreeFunction<Double, Double, Double> functionToY) {
		System.out.println(name + " с шагом: \nh = " +  h);
		double y = y0, x = a, norm = 0;
		while(x - b <= 1e-6) {
			norm = max(norm, abs(y - u(x)));
//			System.out.println(x + " " + y + " " + u(x));
			y = functionToY.applyAsDouble(x, y, h);
			x += h;
		}
		System.out.println(norm + "\n");
	}
	
	public static void explicitTrapezoidMethod(double h) {
		methodsFrame(h, "Явный метод трапеции", (x, y, lambdaH) -> y + lambdaH * (phi0(x, y) + phi1t(x, y, lambdaH)) / 2);
	}
	
	public static void implicitTrapezoidMethod(double h) {
		methodsFrame(h, "Неявный метод трапеции", Solution::newtonMethod);
	}
	
	public static void rungeKuttaMethod4thOrder(double h) {
		methodsFrame(h, "Метод Рунге-Кутта 4 порядка", Solution::rungeKuttaIteration);
	}
	
	public static void main(String[] args) {
		explicitTrapezoidMethod(0.1);
		explicitTrapezoidMethod(0.05);
		implicitTrapezoidMethod(0.1);
		implicitTrapezoidMethod(0.05);
		rungeKuttaMethod4thOrder(0.1);
		rungeKuttaMethod4thOrder(0.05);
	}
}
