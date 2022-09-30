package base;

public class Main {	
	public static double phi0(double x, double y) {
		return (y * y + y * x) / (x * x);
	}
	
	public static double dphi0(double x, double y) {
		return (2 * y + x) / (x * x);
	}
	
	public static double phi1(double x, double y, double h) {
		return ((y + h / 2.0 * phi0(x, y)) * (y + h / 2.0 * phi0(x, y)) + (y + h / 2.0 * phi0(x, y)) * (x + h / 2.0))
				/ ((x + h / 2.0) * (x + h / 2.0));
	}
	
	public static double phi2(double x, double y, double h) {
		return ((y + h / 2.0 * phi1(x, y, h)) * (y + h / 2.0 * phi1(x, y, h)) + (y + h / 2.0 * phi1(x, y, h)) * (x + h / 2.0))
				/ ((x + h / 2.0) * (x + h / 2.0));
	}
	
	public static double phi3(double x, double y, double h) {
		return ((y + h * phi2(x, y, h)) * (y + h * phi2(x, y, h)) + (y + h * phi2(x, y, h)) * (x + h))
				/ ((x + h) * (x + h));
	}
	
	public static double u(double x) {
		return x / (2.0 - Math.log(x));
	}
	
	public static double eulerIteration(double y, double h, double prevIter, double x) {
		return prevIter - ((prevIter - y - h * phi0(x, prevIter)) / (1.0 - h * dphi0(x, prevIter)));
	}
	
	public static void main(String[] args) {
		System.out.println("������� ���������������");
		System.out.println("h = 0.1");
		double y = 0.5, x = 1, h = 0.1, norm = 0;
		while (x <= 2) {
			norm = Math.max(norm, Math.abs(y - u(x)));
			System.out.println(x + " " + y + " " + u(x));
			y = y + h * phi1(x, y, h);
			x += h;
		}
		System.out.println(norm);
		System.out.println("������� ���������������");
		System.out.println("h = 0.05");
		y = 0.5; x = 1; h = 0.05; norm = 0;
		while (x <= 2) {
			norm = Math.max(norm, Math.abs(y - u(x)));
			System.out.println(x + " " + y + " " + u(x));
			y = y + h * phi1(x, y, h);
			x += h;
		}
		System.out.println(norm);
		System.out.println("������");
		System.out.println("h = 0.1");
		y = 0.5; x = 1; h = 0.1; norm = 0;
		while (x <= 2) {
			norm = Math.max(norm, Math.abs(y - u(x)));
			System.out.println(x + " " + y + " " + u(x));
			double i, pri = y;
			i = eulerIteration(y, h, pri, x + h);
			while (Math.abs(pri - i) > 0.0000001) {
				pri = i;
				i = eulerIteration(y, h, pri, x + h);
			}
			y = i;
			x += h;
		}
		System.out.println(norm);
		System.out.println("������");
		System.out.println("h = 0.05");
		y = 0.5; x = 1; h = 0.05; norm = 0;
		while (x <= 2) {
			norm = Math.max(norm, Math.abs(y - u(x)));
			System.out.println(x + " " + y + " " + u(x));
			double i, pri = y;
			i = eulerIteration(y, h, pri, x + h);
			while (Math.abs(pri - i) > 0.0000001) {
				pri = i;
				i = eulerIteration(y, h, pri, x + h);
			}
			y = i;
			x += h;
		}
		System.out.println(norm);
		System.out.println("4 �������");
		System.out.println("h = 0.1");
		y = 0.5; x = 1; h = 0.1; norm = 0;
		while (x <= 2) {
			norm = Math.max(norm, Math.abs(y - u(x)));
			System.out.println(x + " " + y + " " + u(x));
			y = y + h / 6.0 * (phi0(x, y) + 2.0 * phi1(x, y, h) + 2.0 * phi2(x, y, h) + phi3(x, y, h));
			x += h;
		}
		System.out.println(norm);
		System.out.println("4 �������");
		System.out.println("h = 0.05");
		y = 0.5; x = 1; h = 0.05; norm = 0;
		while (x <= 2) {
			norm = Math.max(norm, Math.abs(y - u(x)));
			System.out.println(x + " " + y + " " + u(x));
			y = y + h / 6.0 * (phi0(x, y) + 2.0 * phi1(x, y, h) + 2.0 * phi2(x, y, h) + phi3(x, y, h));
			x += h;
		}
		System.out.println(norm);
	}
}
