package com.evan.algorithm.greedy;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * @author EvanYu
 * @date 2023/6/8
 * @description 452. 用最少数量的箭引爆气球
 * 有一些球形气球贴在一堵用 XY 平面表示的墙面上。墙面上的气球记录在整数数组points，其中points[i] = [xstart, xend]表示水平直径在xstart和xend之间的气球。你不知道气球的确切 y 坐标。
 * 一支弓箭可以沿着 x 轴从不同点 完全垂直 地射出。在坐标 x 处射出一支箭，若有一个气球的直径的开始和结束坐标为 xstart，xend，
 * 且满足 xstart≤ x ≤ xend，则该气球会被 引爆。可以射出的弓箭的数量 没有限制 。弓箭一旦被射出之后，可以无限地前进。
 * <p>
 * 给你一个数组 points ，返回引爆所有气球所必须射出的 最小 弓箭数。
 * <p>
 * 输入：points = [[10,16],[2,8],[1,6],[7,12]]
 * 输出：2
 * 解释：气球可以用2支箭来爆破:
 * -在x = 6处射出箭，击破气球[2,8]和[1,6]。
 * -在x = 11处发射箭，击破气球[10,16]和[7,12]。
 */
@State(value = Scope.Thread)
public class InflateBalloon {

    int[][] area = {
            {1, 2},
            {2, 3},
            {3, 4},
            {4, 5},
    };
    @Test
    public void testAssign() {
        System.out.println(findMinArrowShots(area));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Warmup(iterations = 1,time = 1,timeUnit = TimeUnit.SECONDS)
    @Threads(1)
    @Fork(1)
    public void benchmarkSearch() {
        findMinArrowShots(area);
    }

    /**
     * 优先射击区间小的
     *
     * @param points
     * @return
     */
    public int findMinArrowShots(int[][] points) {
        int[][] sorted = Stream.of(points)
                .sorted(Comparator.comparingInt(a -> a[1]))
                .toArray(int[][]::new);

        int max = 1;
        int s = sorted[0][1];
        for (int i = 1; i < sorted.length; i++) {
            // left border > pre
            if (sorted[i][0] > s){
                s = sorted[i][1];
                max++;
            }
        }
        return max;
    }
}
