package org.amicofragile.katas.counter;

import static org.junit.Assert.*;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

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
	
	@Test
	public void usingTheSameCounterInConcurrentThreadsShouldNotMissUpdates() throws Exception {
		final int threadCount = 100;
		final int incrementsPerThread = 100;
		final int initialValue = 0;
		Counter counter = new Counter(initialValue);
		
		CyclicBarrier startBarrier = new CyclicBarrier(threadCount);
		CyclicBarrier endBarrier = new CyclicBarrier(threadCount + 1);
		for(int i = 0; i < threadCount; i++) {
			Runnable r = new CounterRunner(startBarrier, endBarrier, counter, incrementsPerThread);
			new Thread(r).start();
		}
		endBarrier.await();
		System.out.printf("Final counter value is %d", counter.getCount());
		assertEquals(initialValue + threadCount * incrementsPerThread, counter.getCount());
	}
	
	private static final class CounterRunner implements Runnable {
		private final CyclicBarrier startBarrier;
		private final CyclicBarrier endBarrier;
		private final int increments;
		private final Counter counter;

		public CounterRunner(CyclicBarrier startBarrier, CyclicBarrier endBarrier, Counter counter, int increments) {
			super();
			this.startBarrier = startBarrier;
			this.endBarrier = endBarrier;
			this.counter = counter;
			this.increments = increments;
		}

		public void run() {
			try {
				startBarrier.await();
				for(int i = 0; i < increments; i++) {
					Thread.sleep(2000 * (long)Math.random());
					counter.increment();
				}
				endBarrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
	}
}
