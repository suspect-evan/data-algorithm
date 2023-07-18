package com.evan.algorithm.search.backtracking;

import com.evan.algorithm.utils.ArrayUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.*;
import org.openjdk.jmh.runner.options.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author EvanYu
 * @date 2023/6/21
 * @description
 */
@State(Scope.Thread)
@Slf4j
public class Permutations {

    int[] nums = {1, 2, 4, 8, 3};

    /**
     * 排列
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        backTracking(nums, 0, res);
        return res;
    }

    private void backTracking(int[] nums, int level, List<List<Integer>> res) {

        if (level == nums.length - 1) {
            List<Integer> l = Arrays.stream(nums)
                    .boxed()
                    .toList();
            res.add(l);
            return;
        }

        for (int i = level; i < nums.length; i++) {
            ArrayUtils.swap(nums, i, level);
            backTracking(nums, level + 1, res);
            ArrayUtils.swap(nums, i, level);
        }
    }

    @Test
    public void testPermutations() {
        log.debug(permute(nums).toString());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
    @Threads(1)
    @Fork(1)
    public void benchmarkPermutations() {
        permute(nums);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Permutations.class.getSimpleName())
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

}
