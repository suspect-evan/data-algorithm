package com.evan.algorithm.sort;

import com.evan.algorithm.utils.ArrayUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
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
 * @date 2023/5/28
 * @description 算法：归并排序
 * 时间复杂度：O(nLogN)
 * 空间复杂度：O(N)
 * 算法稳定：因为不会改变数组
 * 解决案例：适合统计或者计算所有比某个数大或者小的时候
 * 场景：小和计算、求逆序对
 * <p>
 * 例如 2 4 3 1 小和为每个数比它小的数累计 4的小和为1+2+3=6 数组小和为所有小和的累加 1+1+1+2+2+3=10
 * 可以在merge的过程中，每次左边比较较小的数一定会产生右边剩余数量的小和，比如 2，4 一定会产生1个属于4下 2的小和
 */
@Slf4j
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 3, time = 1)
public class MergeSort {
    int[] arr;

    @Setup(value = Level.Invocation)
    public void prepare() {
        arr = ArrayUtils.generateRandomIntArray(20, 100);
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
                .include(MergeSort.class.getSimpleName())
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

    @Test
    public void testMergeSorting() {
        int[] randomArr = ArrayUtils.generateRandomIntArray(12, 50);
        log.debug("raw arr : {}", Arrays.toString(randomArr));
        log.debug("sort result : {}", Arrays.toString(mergeSortWithRecursion(randomArr)));
        randomArr = ArrayUtils.generateRandomIntArray(12, 50);
        log.debug("raw arr : {}", Arrays.toString(randomArr));
        log.debug("sort result : {}", Arrays.toString(mergeSort(randomArr)));
    }

    /**
     * merge sort with Recursion
     *
     * @param arr
     * @return
     */
    public static int[] mergeSortWithRecursion(int[] arr) {

        if (null == arr || arr.length <= 1) {
            return arr;
        }

        processSort(arr, 0, arr.length - 1);
        return arr;
    }

    /**
     * merge sort
     * <p>
     * merge per group (2 > 4 > 8 > 16 until merge group size is gte arr size)
     *
     * @param arr
     * @return
     */
    public static int[] mergeSort(int[] arr) {

        if (null == arr || arr.length <= 1) {
            return arr;
        }
        int len = arr.length;

        // nums per group  2>4>8(size=9)
        int mergeSize = 1;
        while (mergeSize <= len) {
            // merge per group with same size
            int l = 0;
            while (l < len) {
                int middle = l + mergeSize - 1;
                if (middle >= len) {
                    break;
                }
                int r = Math.min(middle + mergeSize, len - 1);
                merge(arr, l, middle, r);
                l = r + 1;
            }

            if (mergeSize > len / 2) {
                break;
            }
            mergeSize <<= 1;

        }
        return arr;
    }

    /**
     * make arr ordered from index l to index r
     *
     * @param arr
     * @param l
     * @param r
     */
    public static void processSort(int[] arr, int l, int r) {
        if (l == r) {
            return;
        }

        // l = 3 r = 8 then middle = int (3 + 5/2) = 5
        int middle = l + ((r - l) >> 1);
        processSort(arr, l, middle);
        processSort(arr, middle + 1, r);
        merge(arr, l, middle, r);
    }


    /**
     * merge sorted arr with [l -> mid] and [mid+1 -> r]
     *
     * @param arr
     * @param l
     * @param mid
     * @param r
     * @return
     */
    public static void merge(int[] arr, int l, int mid, int r) {
        int[] tmpArr = new int[r - l + 1];
        int i = 0;
        int pl = l;
        int pr = mid + 1;

        // smaller val is front of bigger one
        while (pl <= mid && pr <= r) {
            if (arr[pl] <= arr[pr]) {
                tmpArr[i++] = arr[pl++];
            } else {
                tmpArr[i++] = arr[pr++];
            }
        }

        // clear l if there is any number left
        while (pl <= mid) {
            tmpArr[i++] = arr[pl++];
        }

        // clear r if there is any number left
        while (pr <= r) {
            tmpArr[i++] = arr[pr++];
        }

        // rewrite into arr
        for (int j = 0; j < tmpArr.length; j++) {
            arr[l + j] = tmpArr[j];
        }
    }

}
