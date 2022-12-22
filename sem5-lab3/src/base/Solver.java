package base;

import static java.lang.Math.abs;
import static java.lang.Math.max;

import java.util.LinkedList;
import java.util.List;

public class Solver {
	private static final double a = 0.5;
	private static final double b = 1;
	
	private static double u(double x) {
		return x * (x - 4);
	}
	
	private static double a(double h, double x) {
		return 2 - h * (x - 1);
	}
	
	private static double c(double h) {
		return 4 * (h*h + 1);
	} 
	
	private static double b(double h, double x) {
		return 2 + h * (x - 1);
	}
	
	private static double f(double h, double x) {
		return - 4 * h*h * (x + 3);
	}

	private static List<Double> constructAlpha(double h) {
		List<Double> alpha = new LinkedList<>();
		alpha.add(0.0);
		double x = a + h;
		double c = c(h);
		int N = (int) ((b - a) / h);
		for(int i = 1; i < N; i++, x += h) {
			double alphai = b(h, x) / (c - a(h, x) * alpha.get(i - 1));
			alpha.add(alphai);
		}
		return alpha;
	}
	
	private static List<Double> constructBeta(double h, List<Double> alpha) {
		List<Double> beta = new LinkedList<>();
		beta.add(-1.75);
		double x = a + h;
		double c = c(h);
		int N = (int) ((b - a) / h);
		int i = 1;
		for(; i < N; i++, x += h) {
			double betai = (f(h, x) + a(h, x) * beta.get(i - 1)) / (c - a(h, x) * alpha.get(i - 1));
			beta.add(betai);
		}
		double fN = -h * (4 * h + 5);
		double aN = 1;
		double cN = h*h + h + 1;
		double betaN = (fN + aN * beta.get(i - 1)) / (cN - aN * alpha.get(i - 1));
		beta.add(betaN);
		return beta;
	}
	
	private static List<Double> constuctAnswer(List<Double> alpha, List<Double> beta) {
		LinkedList<Double> answer = new LinkedList<>();
		int i = beta.size() - 1;
		answer.push(beta.get(i--));
		for(; i >= 0; i--) {
			double yi = alpha.get(i) * answer.get(0) + beta.get(i);
			answer.push(yi);
		}
		return answer;
	}
	
	public static List<Double> solve(double h) {
		List<Double> alpha = constructAlpha(h);
		List<Double> beta = constructBeta(h, alpha);	
		
		return constuctAnswer(alpha, beta);
	}

	public static double norm1(double h, List<Double> y) {
		double norm = 0, x = a;
		for (double yi : y) {
			norm = max(norm, abs(yi - u(x)));
			x += h;
		}
		return norm;
	}
	
	public static double norm2(List<Double> doubleVector, List<Double> vector) {
		double norm  = 0;
		for(int i = 0; i < vector.size(); i++) {
			norm = max(norm, abs(doubleVector.get(2*i) - vector.get(i)));
		}
		return norm / 3;
	}
}