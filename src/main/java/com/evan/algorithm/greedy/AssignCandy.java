package com.evan.algorithm.greedy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author EvanYu
 * @date 2023/6/8
 * @description 135. 分发糖果
 * <p>
 * n 个孩子站成一排。给你一个整数数组 ratings 表示每个孩子的评分。
 * <p>
 * 你需要按照以下要求，给这些孩子分发糖果：
 * <p>
 * 每个孩子至少分配到 1 个糖果。
 * 相邻两个孩子评分更高的孩子会获得更多的糖果。
 * 请你给每个孩子分发糖果，计算并返回需要准备的 最少糖果数目
 * <p>
 * <p>
 * 输入：ratings = [1,2,2]
 * 输出：4
 * 解释：你可以分别给第一个、第二个、第三个孩子分发 1、2、1 颗糖果。
 * 第三个孩子只得到 1 颗糖果，这满足题面中的两个条件。
 * <p>
 * 解法：贪心，从左发一次，从右再发一次
 */
@State(value = Scope.Thread)
@Slf4j
public class AssignCandy {

    int[] ratings = new int[]{1, 3, 2, 2, 1};

    @Test
    public void testAssign() {
        log.debug("res : {}",candy(ratings));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Warmup(iterations = 1,time = 1,timeUnit = TimeUnit.SECONDS)
    @Threads(1)
    @Fork(1)
    public void benchmarkSearch() {
        candy(ratings);
    }

    /**
     * distribute candy
     *
     * @param ratings
     * @return
     */
    public int candy(int[] ratings) {
        if (ratings.length < 2) {
            return ratings.length;
        }
        int[] res = new int[ratings.length];
        // default 1
        Arrays.fill(res, 1);

        // from left
        for (int i = 1; i < res.length; i++) {
            int l = i - 1;
            // if left < right then right = left + 1
            if (ratings[i - 1] < ratings[i]) {
                res[i] = res[l] + 1;
            }
        }

        // from right
        for (int i = res.length - 1; i > 0; i--) {
            // if left > right and left candy <= right candy then left = right + 1
            if (ratings[i - 1] > ratings[i] && res[i - 1] <= res[i]) {
                res[i - 1] = res[i] + 1;
            }
        }

        return Arrays.stream(res).sum();
    }
}
