package base;

import static java.lang.Math.*;

import java.util.function.IntToDoubleFunction;
import java.util.stream.IntStream;

public class Solution {
	public static final double a = 1;
	public static final double b = 2;
	public static final int k = 5;
	public static final double epsilon = 1e-7;
	
	public static double f(double x) {
		return x / (1 + log(x));
	}
	
	public static double h(int N) {
		return (b - a) / N;
	}
	
	public static double trapezoidQ(int N) {
		double h = h(N);
		double sum = IntStream.range(1, N)
								.parallel()
								.mapToDouble(i -> a + i*h)
								.map(x -> f(x))
								.sum();
		return h * ((f(a) + f(b)) / 2  + sum);
	}
	
	public static double evenSimpsonQ(int N) {
		double h = h(N);
		double firstSum = IntStream.iterate(1, i -> i+=2)
									.takeWhile(i -> i < N)
									.mapToDouble(i -> a + i*h)
									.map(x -> f(x))
									.sum();
		double secondSum = IntStream.iterate(2, i -> i+=2)
									.takeWhile(i -> i < N-1)
									.mapToDouble(i -> a + i*h)
									.map(x -> f(x))
									.sum();
		return h *(f(a) + f(b) + 4*firstSum + 2*secondSum) / 3;
	}
	
	private static void task1(int m, IntToDoubleFunction quadrature) {
		int N = 2;
		double currentQ = quadrature.applyAsDouble(N);
//		System.out.println(currentQ);
		N *= 2;
		double nextQ = quadrature.applyAsDouble(N);
		double R = (nextQ - currentQ) / (1<<m - 1);
//		System.out.println(R);
		while(abs(R) > epsilon) {
			currentQ = nextQ;
//			System.out.println(currentQ);
			N *= 2;
			nextQ = quadrature.applyAsDouble(N);
			R = (nextQ - currentQ) / (1<<m - 1);
//			System.out.println(R);
		}
		System.out.println(nextQ);
	}
	
	private static void task2() {
		double[] x = new double[] {-0.9061798459, -0.5384693101, 0, 0.5384693101, 0.9061798459};
		double[] A = new double[] {0.2369268851, 0.4786286705, 0.5688888899, 0.4786286705, 0.2369268851};
		double answer = IntStream.range(0, k)
								 .mapToDouble(i -> (b-a) * A[i] * f((a+b)/2 + (b-a)*x[i]/2) / 2)
								 .sum();
		System.out.println(answer);						
	}
	
	public static void main(String[] args) {
		task1(4, Solution::evenSimpsonQ);
		task2();
	}
}
