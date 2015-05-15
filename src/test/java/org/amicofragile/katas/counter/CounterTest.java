package org.amicofragile.katas.counter;

import static org.junit.Assert.*;

import org.junit.Test;

public class CounterTest {
	@Test
	public void callingTwiceIncrementMethodShouldResultInAnIncreaseOfTwo() throws Exception {
		final int initialValue = 11;
		Counter counter = new Counter(initialValue);
		counter.increment();
		counter.increment();
		assertEquals((initialValue + 2), counter.getCount());
	}
}
