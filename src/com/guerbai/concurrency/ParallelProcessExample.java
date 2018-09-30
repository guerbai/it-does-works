package com.guerbai.concurrency;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.System.currentTimeMillis;

class SumTask implements Callable<Long> {

    private int i;
    private long num;

    public SumTask(int i, long num) {
        this.i = i;
        this.num = num;
    }

    @Override
    public Long call() throws Exception {
        long start = i * num;
        long end = start + num;
        long sum = 0;
        for (long i=start; i < end; i++) {
            sum += i;
        }
        return sum;
    }

}

public class ParallelProcessExample {

    private static long measureTimeCost(int threadNum) {
        int nums = Integer.MAX_VALUE;
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);
        List<Future<Long>> sumTasks = new ArrayList<>();
        long numPerThread = ((long)nums+1) / threadNum;
        long startAt = currentTimeMillis();
        for (int i=0; i<threadNum; i++) {
            sumTasks.add(executor.submit(new SumTask(i, numPerThread)));
        }
        long result = 0;
        try {
            for (int i=0; i<threadNum; i++) {
                result += sumTasks.get(i).get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        long endAt = currentTimeMillis();
        executor.shutdown();
        return endAt - startAt;
    }

    public static void main(String[] args) {

        int threadNum1 = 1;
        int threadNum2 = 2;
        int threadNum4 = 4;
        int threadNum8 = 8;
        System.out.println("SumTask with threadNum1 cost time: " + (float) (measureTimeCost(threadNum1)) / 1000 + "s in Java version.");
        System.out.println("SumTask with threadNum2 cost time: " + (float) (measureTimeCost(threadNum2)) / 1000 + "s in Java version.");
        System.out.println("SumTask with threadNum4 cost time: " + (float) (measureTimeCost(threadNum4)) / 1000 + "s in Java version.");
        System.out.println("SumTask with threadNum8 cost time: " + (float) (measureTimeCost(threadNum8)) / 1000 + "s in Java version.");
    }

}
