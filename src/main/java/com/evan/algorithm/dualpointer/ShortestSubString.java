package com.evan.algorithm.dualpointer;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author EvanYu
 * @date 2023/6/8
 * @description 76.最小覆盖子串
 * <p>
 * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 ""
 * <p>
 * 输入：s = "ADOBECODEBANC", t = "ABC"
 * 输出："BANC"
 * 解释：最小覆盖子串 "BANC" 包含来自字符串 t 的 'A'、'B' 和 'C'。
 */
@State(Scope.Thread)
public class ShortestSubString {
    String s = "a", t = "b";

    @Test
    public void testSubString() {
        System.out.println(minSubStr(s, t));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
    @Threads(1)
    @Fork(1)
    public void benchmarkSubString() {
        minSubStr(s, t);
    }

    /**
     * find minimum sub string
     *
     * @param s
     * @param t
     * @return
     */
    public String minSubStr(String s, String t) {
        if(s.length() < t.length()){
            return "";
        }

        int[] subMap = new int[128];
        int[] gapMap = new int[128];

        char[] tChars = t.toCharArray();
        char[] sChars = s.toCharArray();
        for (int i = 0; i < tChars.length; i++) {
            subMap[tChars[i] - 0] = 1;
            gapMap[tChars[i] - 0]++;
        }

        int l = 0;
        int cnt = 0;
        int ml = 0, ms = sChars.length + 1;
        for (int r = 0; r < sChars.length; r++) {
            int c = sChars[r] - 0;
            if (subMap[c] == 1) {
                if (--gapMap[c] >= 0) {
                    cnt++;
                }

                // shifting left
                while (cnt == t.length()) {
                    if (r - l + 1 < ms) {
                        ml = l;
                        ms = r - l + 1;
                    }
                    int lc = sChars[l] - 0;
                    if (subMap[lc] == 1 && (++gapMap[lc]) > 0) {
                        cnt--;
                    }
                    l++;
                }
            }
        }
        return ms > s.length() ? "" : s.substring(ml, ml + ms);
    }
}
