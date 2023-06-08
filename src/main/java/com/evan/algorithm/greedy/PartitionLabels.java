package com.evan.algorithm.greedy;

import org.junit.Assert;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author EvanYu
 * @date 2023/6/8
 * @description 763. 划分字母区间
 * 给你一个字符串 s 。我们要把这个字符串划分为尽可能多的片段，同一字母最多出现在一个片段中。
 * 注意，划分结果需要满足：将所有划分结果按顺序连接，得到的字符串仍然是 s 。
 * 返回一个表示每个字符串片段的长度的列表。
 * <p>
 * 输入：s = "eccbbbbdec"
 * 输出：[10]
 * <p>
 * 输入：s = "ababcbacadefegdehijhklij"
 * 输出：[9,7,8]
 * 解释：
 * 划分结果为 "ababcbaca"、"defegde"、"hijhklij" 。
 * 每个字母最多出现在一个片段中。
 * 像 "ababcbacadefegde", "hijhklij" 这样的划分是错误的，因为划分的片段数较少。
 */
@State(value = Scope.Thread)
public class PartitionLabels {

    String s = "ababcbacadefegdehijhklij";

    @Test
    public void testAssign() {
        System.out.println(partitionLabels(s).toString());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
    @Threads(1)
    @Fork(1)
    public void benchmarkSearch() {
        partitionLabels(s);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
    @Threads(1)
    @Fork(1)
    public void benchmarkSearchPre() {
        partitionLabelsPreHandle(s);
    }


    public List<Integer> partitionLabels(String s) {
        if (s.length() <= 1) {
            return Arrays.asList(1);
        }

        List<Integer> res = new ArrayList<>();
        char[] chars = s.toCharArray();
        int i = 0, l = 0, r = 0;
        while (i < chars.length) {
            int last = s.lastIndexOf(chars[i]);
            if (last == i && i == r) {
                res.add(r - l + 1);
                l = last + 1;
                r = last + 1;
            } else {
                r = Math.max(r, last);
                i++;
            }
        }
        return res;
    }

    public List<Integer> partitionLabelsPreHandle(String s) {
        int[] maxPos = new int[26];
        int length = s.length();
        // pre handle max Postion
        for (int i = 0; i < length; i++) {
            maxPos[s.charAt(i) - 'a'] = i;
        }
        List<Integer> partition = new ArrayList<Integer>();
        int start = 0, end = 0;
        for (int i = 0; i < length; i++) {
            end = Math.max(end, maxPos[s.charAt(i) - 'a']);
            if (i == end) {
                partition.add(end - start + 1);
                start = end + 1;
            }
        }
        return partition;
    }
}
