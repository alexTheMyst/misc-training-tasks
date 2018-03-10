package ru.aleshin.misc.forkjoin;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for SumOfN.
 *
 * @author Alexey Aleshin
 * @version $id$
 * @since 10.03.2018
 */
public class SumOfNTest {

        private SumOfN sumOfN;

        private ForkJoinPool forkJoinPool;

        @Before
        public void setUp() {
            this.sumOfN = new SumOfN();
            this.forkJoinPool = new ForkJoinPool(SumOfN.NUM_OF_THREADS);
        }

        @Test
        public void runForkJoinTask() {
            long computedSum = this.forkJoinPool.invoke(new SumOfN.RecursiveSumOfN(0L, SumOfN.N));
            long testSum = (SumOfN.N * (SumOfN.N + 1)/2);

            System.out.printf("Computed sum = %d == Test sum = %d", computedSum, testSum);
            assertThat(computedSum, is(testSum));
        }
}