package base;

import static java.lang.Math.abs;
import static java.lang.Math.max;

import java.util.LinkedList;
import java.util.List;

public final class Solver {
	private static final double a = 0.5;
	private static final double b = 1;
	
	private static final double firstU0 = -1.75;
	private static final double firstV0 = 0;
	
	private static final double firstU1 = 0;
	private static final double firstV1 = 1;
	
	private static double u(double x) {
		return x * (x - 4);
	}
	
	private static double phi010(double y2) {
		return y2;
	}
	
	private static double phi110(double x, double y1, double y2, double h) {
		return phi010(y2 + h * phi020(x, y1, y2) / 2);
	}
	
	private static double phi020(double x, double y1, double y2) {
		return 2 * y1 + (1 - x) * y2 + 2 * (3 + x);
	}
	
	private static double phi120(double x, double y1, double y2, double h) {
		return phi020(x + h / 2, y1 + h * phi010(y2) / 2, y2 + h * phi020(x, y1, y2) / 2);
	}
	
	private static double phi111(double x, double y1, double y2, double h) {
		return phi010(y2 + phi021(x, y1, y2) * h / 2);
	}
	
	private static double phi021(double x, double y1, double y2) {
		return 2 * y1 + (1 - x) * y2;
	}
	
	private static double phi121(double x, double y1, double y2, double h) {
		return phi021(x + h / 2, y1 + h * phi010(y2) / 2, y2 + h * phi021(x, y1, y2) / 2);
	}
	
	private static void middlePointMethod(double h, double firstU, double firstV, List<Double> u, List<Double> v,
							 ToDoubleThreeDoubleFunction<Double> funcU, ToDoubleThreeDoubleFunction<Double> funcV) {
		double x = a, iterU = firstU, iterV = firstV;	
		while(x - b <= 1e-6) {
			u.add(iterU);
			v.add(iterV);
			
			double temp = funcU.applyAsDouble(x, iterU, iterV);
			iterV = funcV.applyAsDouble(x, iterU, iterV);
			iterU = temp;
			
			x += h;
		}
	}
	
	private static List<Double> constructAnswer(double c, List<Double> u0, List<Double> u1) {
		List<Double> u = new LinkedList<>();
		for (int i = 0; i < u0.size(); i++) {
			u.add(u0.get(i) + c * u1.get(i));
		}
		return u;
	}
	
	public static double norm(double h, List<Double> y) {
		double norm = 0, x = a;
		for (double yi : y) {
			norm = max(norm, abs(yi - u(x)));
			x += h;
		}
		return norm;
	}
	
	public static List<Double> solve(double h) {
		List<Double> u0 = new LinkedList<>();
		List<Double> v0 = new LinkedList<>();
		middlePointMethod(h, firstU0, firstV0, u0, v0, (x, u, v) -> u + h * phi110(x, u, v, h), (x, u, v) -> v + h * phi120(x, u, v, h));
		
		List<Double> u1 = new LinkedList<>();
		List<Double> v1 = new LinkedList<>();
		middlePointMethod(h, firstU1, firstV1, u1, v1, (x, u, v) -> u + h * phi111(x, u, v, h), (x, u, v) -> v + h * phi121(x, u, v, h));
		
		int last = u0.size() - 1;
		
		double c = (-5 - u0.get(last) - v0.get(last)) / (u1.get(last) + v1.get(last));
		
		return constructAnswer(c, u0, u1);
	}
	
	public static double normRunge(List<Double> doubleVector, List<Double> vector) {
		double norm  = 0;
		for(int i = 0; i < vector.size(); i++) {
			norm = max(norm, abs(doubleVector.get(2*i) - vector.get(i))) / ((1<<2) - 1);
		}
		return norm;
	}
}	
