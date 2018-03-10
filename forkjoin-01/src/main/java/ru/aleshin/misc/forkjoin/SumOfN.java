package ru.aleshin.misc.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * Example from Oracle Certified Professional Java SE 8 Programmer Exam 1Z0-809:
 * A Comprehensive OCPJP 8 Certification Guide
 * by by Tushar Sharma, Hari Kiran, S G Ganesh
 *
 * @author Alexey Aleshin
 * @version $id$
 * @since 10.03.2018
 */
public class SumOfN {

    static final long N = 1_000_000;

    static final int NUM_OF_THREADS = 8;

    static class RecursiveSumOfN extends RecursiveTask<Long> {

        long from;

        long to;

        public RecursiveSumOfN(long from, long to) {
            this.from = from;
            this.to = to;
        }

        @Override
        protected Long compute() {

            if ((this.to - this.from) < N/NUM_OF_THREADS) {
                long localSum = 0;
                for (long i = this.from; i <= this.to; i++) {
                    localSum += i;
                }
                System.out.printf("\tSum from %d to %d is %d.\n", this.from, this.to, localSum);
                return localSum;
            } else {
                long mid = (this.from + this.to) / 2;
                System.out.printf("\tInterval %d..%d is to huge for one thread. Split to the following ranges:\n" +
                        "\t\tfirst range: %d to %d\n" +
                        "\t\tsecond range: %d to %d\n", this.from, this.to, this.from, mid, mid + 1, this.to);
                RecursiveSumOfN sumOfNFirst = new RecursiveSumOfN(this.from, mid);
                RecursiveSumOfN sumOfNSecond = new RecursiveSumOfN(mid + 1, this.to);
                sumOfNFirst.fork();
                Long secondResult = sumOfNSecond.compute();
                return sumOfNFirst.join() + secondResult;
            }
        }
    }
}