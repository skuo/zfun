package org.zfun.eight;

public interface Formula {
	public double calculate(int a);

    public default double sqrt(int a) {
        return Math.sqrt(a);
    }
}
