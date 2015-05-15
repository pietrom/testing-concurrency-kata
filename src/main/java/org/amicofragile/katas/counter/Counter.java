package org.amicofragile.katas.counter;

public class Counter {
	private int count;
	public Counter(int initialValue) {
		count = initialValue;
	}
	
	public synchronized void increment() {
		count++;
	}

	public int getCount() {
		return count;
	}
}
