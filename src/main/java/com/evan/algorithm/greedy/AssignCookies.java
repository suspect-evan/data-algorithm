package com.evan.algorithm.greedy;

import org.junit.Assert;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author EvanYu
 * @date 2023/6/8
 * @description 贪心算法
 *
 * 455. 分发饼干
 * 假设你是一位很棒的家长，想要给你的孩子们一些小饼干。但是，每个孩子最多只能给一块饼干。
 * 对每个孩子 i，都有一个胃口值g[i]，这是能让孩子们满足胃口的饼干的最小尺寸；并且每块饼干 j，都有一个尺寸 s[j]。
 * 如果 s[j]>= g[i]，我们可以将这个饼干 j 分配给孩子 i ，这个孩子会得到满足。你的目标是尽可能满足越多数量的孩子，并输出这个最大数值。
 *
 *  解法：贪心，局部最优解
 */
@State(value = Scope.Thread)
public class AssignCookies {


    public int[] g = new int[]{3,2,1};
    public int[] s = new int[]{2,1};
    @Test
    public void testAssign() {
        Assert.assertEquals(2, findContentChildren(g,s));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Warmup(iterations = 1,time = 1,timeUnit = TimeUnit.SECONDS)
    @Threads(1)
    @Fork(1)
    public void benchmarkSearch() {
        Assert.assertEquals(2, findContentChildren(g,s));
    }

    /**
     * 分饼干，优先把饼干小的分给胃口小的
     * @param g
     * @param s
     * @return
     */
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int j = 0,max = 0;
        for (int i = 0; i < g.length; i++) {
            while (j < s.length){
                if(s[j] >= g[i]){
                    max++;
                    j++;
                    break;
                }else {
                    j++;
                }
            }
        }
        return max;
    }
}
