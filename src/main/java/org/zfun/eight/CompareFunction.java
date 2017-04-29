package org.zfun.eight;

@FunctionalInterface
public interface CompareFunction <S, T> {
	T compareTo(S a, S b);
}
