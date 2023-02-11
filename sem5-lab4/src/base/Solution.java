package base;

import java.util.List;

public class Solution {
	public static void main(String[] args) {
		double h = 0.01;
		System.out.println("Решение с шагом:\nh = " +  h);
		List<Double> yh = Solver.solve(h);
		System.out.println("Норма №1: " + Solver.norm1(h, yh));
		List<Double> y2h = Solver.solve(2 * h);
		System.out.println("Норма №2: " + Solver.norm2(yh, y2h));
	}
}
