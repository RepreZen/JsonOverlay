package com.reprezen.jsonoverlay;

public class Pair<L, R> {
	private L left;
	private R right;

	public Pair(L left, R right) {
		this.left = left;
		this.right = right;
	}

	public static <XL, XR> Pair<XL, XR> of(XL left, XR right) {
		return new Pair<XL, XR>(left, right);
	}

	public L getLeft() {
		return left;
	}

	public R getRight() {
		return right;
	}
}
