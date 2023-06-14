package com.evan.algorithm.binary;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;

/**
 * @author EvanYu
 * @date 2023/6/7
 * @description 二分搜索
 * <p>
 * 34. 在排序数组中查找元素的第一个和最后一个位置
 * 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
 * 如果数组中不存在目标值 target，返回[-1, -1]。
 * 你必须设计并实现时间复杂度为O(log n)的算法解决此问题。
 * 示例 1：
 * <p>
 * 输入：nums = [5,7,7,8,8,10], target = 8
 * 输出：[3,4]
 * <p>
 * 解法：二分 + 左闭右开
 */
@Slf4j
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
public class SearchRange {

    int[] arr = new int[]{1, 3, 5, 6, 7, 7, 7, 9, 11, 14, 15, 17};

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(SearchRange.class.getSimpleName())
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

    @Benchmark
    public void benchmarkSearch() {
        searchHeadRecur(arr, 7);
        searchTailRecur(arr, 7);
    }

    @Benchmark
    public void benchmarkSearchRecur() {
        searchHead(arr, 0, arr.length - 1, 7);
        searchTail(arr, 0, arr.length - 1, 7);
    }

    @Test
    public void testSearch() {
        log.debug("{}", searchHeadRecur(arr, 7));
        log.debug("{}", searchTailRecur(arr, 7));
        log.debug("{}", searchHead(arr, 0, arr.length - 1, 7));
        log.debug("{}", searchTail(arr, 0, arr.length - 1, 7));
    }

    public static int searchHead(int[] arr, int l, int r, int target) {
        if (l >= r) {
            return l;
        }

        int mid = findMid(l, r);
        int midVal = arr[mid];
        if (midVal >= target) {
            return searchHead(arr, l, mid, target);
        } else {
            return searchHead(arr, mid + 1, r, target);
        }
    }

    public static int searchTail(int[] arr, int l, int r, int target) {
        if (l >= r) {
            return l;
        }

        int mid = findMid(l, r);
        int midVal = arr[mid];
        if (midVal > target) {
            return searchHead(arr, l, mid, target);
        } else {
            return searchHead(arr, mid + 1, r, target);
        }
    }


    public static int searchHeadRecur(int[] arr, int target) {
        int l = 0;
        int r = arr.length - 1;
        int mid = 0;

        while (l < r) {
            mid = (l + r) / 2;
            if (arr[mid] >= target) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    public static int searchTailRecur(int[] arr, int target) {
        int l = 0;
        int r = arr.length - 1;
        int mid = 0;

        while (l < r) {
            mid = (l + r) / 2;
            if (arr[mid] > target) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        // 多减1位
        return l - 1;
    }

    private static int findMid(int l, int r) {
        return (l + r) / 2;
    }
}
