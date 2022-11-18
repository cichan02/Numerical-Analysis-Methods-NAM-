package base;

public class Solution {
	private static void printMethod(double h, double norm, String name) {
		System.out.println(name + " с шагом:\nh = " + h);
		System.out.println("Hорма: " + norm + "\n");
	}
	
	public static void main(String[] args) {
		printMethod(0.1, Solver.explicitTrapezoidMethod(0.1), "Явный метод трапеции");
		printMethod(0.05, Solver.explicitTrapezoidMethod(0.05), "Явный метод трапеции");
		printMethod(0.1, Solver.implicitTrapezoidMethod(0.1), "Неявный метод трапеции");
		printMethod(0.05, Solver.implicitTrapezoidMethod(0.05), "Неявный метод трапеции");
		printMethod(0.1, Solver.rungeKuttaMethod4thOrder(0.1), "Метод Рунге-Кутта 4 порядка");
		printMethod(0.05, Solver.rungeKuttaMethod4thOrder(0.05), "Метод Рунге-Кутта 4 порядка");
	}
}
