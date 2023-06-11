package com.evan.algorithm.sort;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.*;
import org.openjdk.jmh.runner.options.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author EvanYu
 * @date 2023/6/11
 * @description 347. 前 K 个高频元素
 * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
 * 示例：
 * 输入: nums = [1,1,1,2,2,3], k = 2
 * 输出: [1,2]
 */
public class TopKFrequent {
    @Test
    public void testTopKFrequent() {
        int[] nums = {1,1,1,2,2,3,2,4,2,6,5,5,5,5,5};
        System.out.println(Arrays.toString(topKFrequent(nums,2)));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
    @Threads(1)
    @Fork(1)
    public void benchmarkTopKFrequent() {

    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(TopKFrequent.class.getSimpleName())
                // 预热次数
                .warmupIterations(1)
                // 预热时间
                .warmupTime(TimeValue.valueOf("1s"))
                // 线程数
                .threads(1)
                // fork多少单独的jvm进程进行benchmark
                .forks(1)
                // 等所有线程预热完成 类似于CyclicBarrier
                .syncIterations(true)
                .resultFormat(ResultFormatType.JSON)
                .build();

        new Runner(opt).run();
    }

    /**
     * top k frequent nums
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> frequent = new HashMap<>(nums.length * 2 / 3);
        for (int i = 0; i < nums.length; i++) {
            // anyway plus 1
            frequent.put(nums[i], frequent.getOrDefault(nums[i], 0) + 1);
        }

        // small heap
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(m -> m[1]));

        frequent.forEach((n, f) -> {
            if (queue.size() < k) {
                queue.offer(new int[]{n, f});
                return;
            }

            if (queue.peek()[1] < f) {
                queue.poll();
                queue.offer(new int[]{n, f});
            }
        });

        int[] res = new int[k];
        for (int i = 0; i < res.length; i++) {
            res[i] = queue.poll()[0];
        }
        return res;
    }
}
