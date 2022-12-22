package base;

import java.util.List;

public class Solution {
	public static void main(String[] args) {
		double step = 0.1;
		
		System.out.println("Решение явной РС с шагом:\nh = tau = " + step);
		List<Double> y = Solver.solve(step, step);
		System.out.println("Норма: " + Solver.norm1(step, y) + '\n');
		
		System.out.println("Решение явной РС с шагом:\nh = " + step + ", tau = " + step*step / 2);
		y = Solver.solve(step, step*step / 2);
		System.out.println("Норма: " + Solver.norm1(step, y) + '\n');
		
		System.out.println("Решение чисто неявной РС с шагом:\nh = tau = " + step);
		y = Solver.solve(step, step);
		System.out.println("Норма: " + Solver.norm1(step, y) + '\n');
		
		System.out.println("Решение схемы Кранка-Николсона с шагом:\nh = tau = " + step);
		y = Solver.solve(step, step);
		System.out.println("Норма: " + Solver.norm1(step, y) + '\n');
	}
}
