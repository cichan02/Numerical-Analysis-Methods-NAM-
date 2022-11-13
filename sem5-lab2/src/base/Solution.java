package base;

import static java.lang.Math.abs;
import static java.lang.Math.max;

import java.util.LinkedList;
import java.util.List;

public class Solution {
	private static final double a = 0.5;
	private static final double b = 1;
	
	private static final double y10 = -1.75;
	private static final double y20 = 0;
	
	private static final double y11 = 0;
	private static final double y21 = 1;
	
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
		return 2 * y1 + (1 - x) * y2 + 2*(3 + x);
	}
	
	private static double phi120(double x, double y1, double y2, double h) {
		return phi020(x + h / 2, y1 + h * phi010(y2) / 2, y2 + h * phi020(x, y1, y2) / 2);
	}
	
	private static double phi021(double x, double y1, double y2) {
		return 2 * y1 + (1 - x) * y2;
	}
	
	private static double phi121(double x, double y1, double y2, double h) {
		return phi021(x + h / 2, y1 + h * phi010(y2) / 2, y2 + h * phi021(x, y1, y2) / 2);
	}
	
	private static void step1(double h, List<Double> u0, List<Double> v0) {
		double y1 = y10, y2 = y20, x = a;
		while(x - b <= 1e-6) {
			u0.add(y1);
			v0.add(y2);
			
			double newY1 = y1 + h * phi110(x, y1, y2, h);
			y2 = y2 + h * phi120(x, y1, y2, h);		
			y1 = newY1;
			
			x += h;
		}
	}
	
	private static void step2(double h, List<Double> u1, List<Double> v1) {
		double y1 = y11, y2 = y21, x = a;
		while(x - b <= 1e-6) {
			u1.add(y1);
			v1.add(y2);
			
			double newY1 = y1 + h * phi110(x, y1, y2, h);
			y2 = y2 + h * phi121(x, y1, y2, h);			
			y1 = newY1;
			
			x += h;
		}
	}	
	
	private static void constructAnswer(double c, List<Double> u, List<Double> u0, List<Double> u1) {
		for (int i = 0; i < u0.size(); i++) {
			u.add(u0.get(i) + c * u1.get(i));
		}
	}
	
	private static void norm(double h, List<Double> u) {
		double norm = 0, x = a;
		for (double yi : u) {
//			System.out.println(yi + " - " + u(x));
			norm = max(norm, abs(yi - u(x)));
			x += h;
		}
		System.out.println(norm + "\n");
	}
	
	private static void mainFrame(double h, List<Double> u, List<Double> v) {
		System.out.println("Метод средних прямоугольников с шагом: \nh = " +  h);
				
		List<Double> u0 = new LinkedList<>();
		List<Double> v0 = new LinkedList<>();
		step1(h, u0, v0);
		
		List<Double> u1 = new LinkedList<>();
		List<Double> v1 = new LinkedList<>();
		step2(h, u1, v1);
		
		int last = u0.size() - 1;
		
		double c = (-5 - u0.get(last) - v0.get(last)) / (u1.get(last) + v1.get(last));
		
		constructAnswer(c, u, u0, u1);	
		constructAnswer(c, v, v0, v1);
		
		norm(h, u);
	}
	
	private static double normRunge(List<Double> vector, List<Double> doubleVector) {
		int n = vector.size(), m = 1;
		int n1 = doubleVector.size();
		double norm  = 0;
		for(int i = 0; i < n; i++) {
			double x1 = vector.get(i);
			double x2 = doubleVector.get(2*i);
			norm = max(norm, abs(x1 - x2)) / ((2<<m) - 1);
		}
		return norm;
	}

	public static void main(String[] args) {
		List<Double> u1 = new LinkedList<>();
		List<Double> v1 = new LinkedList<>();
		mainFrame(0.01, u1, v1);
		
		List<Double> u2 = new LinkedList<>();
		List<Double> v2 = new LinkedList<>();
		mainFrame(0.005, u2, v2);
		
		double uMax = normRunge(u1, u2);
		double vMax = normRunge(v1, v2);
		System.out.println("Runge's norm: " + max(uMax, vMax));
	}
}	
