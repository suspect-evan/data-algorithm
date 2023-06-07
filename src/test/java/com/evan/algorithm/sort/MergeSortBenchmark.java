package com.evan.algorithm.sort;

import com.evan.algorithm.utils.ArrayUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author EvanYu
 * @date 2023/6/7
 * @description
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
public class MergeSortBenchmark {

    int[] arr;

    @Setup(value = Level.Invocation)
    public void prepare() {
        arr = ArrayUtils.GenerateRandomIntArray(20, 100);
    }

    @Benchmark
    public void mergeSort() {
        MergeSort.mergeSort(arr);
    }

    @Benchmark
    public void mergeSortRecur() {
        MergeSort.mergeSortWithRecursion(arr);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MergeSortBenchmark.class.getSimpleName())
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
