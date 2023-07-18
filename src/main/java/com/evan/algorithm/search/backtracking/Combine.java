package com.evan.algorithm.search.backtracking;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.*;
import org.openjdk.jmh.runner.options.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * @author EvanYu
 * @date 2023/7/18
 * @description
 */
@State(Scope.Thread)
@Slf4j
public class Combine {

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> combination = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            combination.add(0);
        }

        int count = 0;
        backtrack(n, k, 1, count, combination, result);
        return result;
    }

    private void backtrack(int n, int k, int pos, int count, List<Integer> combination, List<List<Integer>> result) {
        if (count == k) {
            result.add(new ArrayList<>(combination));
            return;
        }

        for (int i = pos; i <= n; i++) {
            // count++
            combination.set(count++, i);
            backtrack(n, k, i + 1, count, combination, result);
            count--;
        }
    }

    @Test
    public void testCombine() {
        log.debug(combine(5, 3).toString());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
    @Threads(1)
    @Fork(1)
    public void benchmarkCombine() {
        combine(10, 3);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Combine.class.getSimpleName())
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
