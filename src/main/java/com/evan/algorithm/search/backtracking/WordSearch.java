package com.evan.algorithm.search.backtracking;


import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.*;
import org.openjdk.jmh.runner.options.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.extern.slf4j.Slf4j;

/**
 * @author EvanYu
 * @date 2023/7/18
 * @description
 */
@State(Scope.Thread)
@Slf4j
public class WordSearch {

    char[][] chars = {
            {'A', 'B', 'C', 'E'},
            {'S', 'F', 'C', 'S'},
            {'A', 'D', 'E', 'E'},
    };

    @Test
    public void testWordSearch() {
        log.debug("search result : {}", searchWord(chars, "ABFCSE"));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
    @Threads(1)
    @Fork(1)
    public void benchmarkWordSearch() {
        searchWord(chars, "ABFCSE");
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(WordSearch.class.getSimpleName())
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


    public boolean searchWord(char[][] chars, String word) {
        if (null == chars || chars.length == 0) {
            return false;
        }
        int l = chars.length;
        int w = chars[0].length;
        int[][] visited = new int[l][w];
        AtomicBoolean result = new AtomicBoolean(false);

        for (int i = 0; i < l; i++) {
            for (int j = 0; j < w; j++) {
                dfs(chars, i, j, visited, word, 0, result);
            }
        }

        return result.get();
    }

    private void dfs(char[][] chars, int i, int j, int[][] visited, String word, int pos, AtomicBoolean result) {
        if (i < 0 || j < 0 || i >= chars.length || j >= chars[i].length) {
            return;
        }

        if (visited[i][j] == 1 || chars[i][j] != word.charAt(pos) || result.get()) {
            return;
        }

        if (pos == word.length() - 1) {
            result.set(true);
            return;
        }

        visited[i][j] = 1;
        dfs(chars, i + 1, j, visited, word, pos + 1, result);
        dfs(chars, i - 1, j, visited, word, pos + 1, result);
        dfs(chars, i, j + 1, visited, word, pos + 1, result);
        dfs(chars, i, j - 1, visited, word, pos + 1, result);
        visited[i][j] = 0;
    }

}
