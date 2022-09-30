package base;

@FunctionalInterface
public interface ToDoubleThreeFunction<T, U, R> {
	 double applyAsDouble(T t, U u, R r);
}
