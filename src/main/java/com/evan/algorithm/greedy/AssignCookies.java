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
 * @description 贪心算法
 * <p>
 * 455. 分发饼干
 * 假设你是一位很棒的家长，想要给你的孩子们一些小饼干。但是，每个孩子最多只能给一块饼干。
 * 对每个孩子 i，都有一个胃口值g[i]，这是能让孩子们满足胃口的饼干的最小尺寸；并且每块饼干 j，都有一个尺寸 s[j]。
 * 如果 s[j]>= g[i]，我们可以将这个饼干 j 分配给孩子 i ，这个孩子会得到满足。你的目标是尽可能满足越多数量的孩子，并输出这个最大数值。
 * <p>
 * 解法：贪心，局部最优解
 */
@State(value = Scope.Thread)
@Slf4j
public class AssignCookies {


    private static final int[] g = new int[]{3, 2, 1};
    private static final int[] s = new int[]{2, 1};

    @Test
    public void testAssign() {
        log.debug("assign result : {}", findContentChildren(g, s));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
    @Threads(1)
    @Fork(1)
    public void benchmarkSearch() {
        findContentChildren(g, s);
    }

    /**
     * 分饼干，优先把饼干小的分给胃口小的
     *
     * @param g
     * @param s
     * @return
     */
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int j = 0;
        int max = 0;
        for (int k : g) {
            while (j < s.length) {
                if (s[j] >= k) {
                    max++;
                    j++;
                    break;
                } else {
                    j++;
                }
            }
        }
        return max;
    }
}
