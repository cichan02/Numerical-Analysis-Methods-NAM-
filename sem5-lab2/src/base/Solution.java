package base;

import java.util.List;

public class Solution {
	private static void printMethod(double h, String name) {
		System.out.println(name + " с шагом: \nh = " +  h);
		List<Double> u1 = Solver.solve(h);
		System.out.println("Норма: " + Solver.norm(h, u1));
		List<Double> u2 = Solver.solve(2 * h);
		System.out.println("Погрешность по методу Рунге-Кутта: " + Solver.normRunge(u1, u2));
	}
	
	public static void main(String[] args) {
		printMethod(0.01, "Явный метод средних прямоугольников");
	}
}
