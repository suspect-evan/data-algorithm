package com.evan.algorithm.search.depth;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.*;
import org.openjdk.jmh.runner.options.*;

import java.util.concurrent.TimeUnit;

/**
 * @author EvanYu
 * @date 2023/6/11
 * @description 547. 省份数量（类似朋友圈问题）
 * 有 n 个城市，其中一些彼此相连，另一些没有相连。如果城市 a 与城市 b 直接相连，且城市 b 与城市 c 直接相连，那么城市 a 与城市 c 间接相连。
 * 省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。
 * 给你一个 n x n 的矩阵 isConnected ，其中 isConnected[i][j] = 1 表示第 i 个城市和第 j 个城市直接相连，而 isConnected[i][j] = 0 表示二者不直接相连。
 * 返回矩阵中 省份 的数量。
 * <p>
 * 输入：isConnected = [[1,1,0],[1,1,0],[0,0,1]]
 * 输出：2
 */
@Slf4j
@State(Scope.Thread)
public class ProvinceCircles {

    public int findCircleNumWithVisit(int[][] isConnected) {
        int cities = isConnected.length;
        boolean[] visited = new boolean[cities];
        int provinces = 0;
        for (int i = 0; i < cities; i ++) {
            if (!visited[i]) {
                dfs(isConnected, visited, cities, i);
                provinces ++;
            }
        }
        return provinces;
    }

    public void dfs(int[][] isConnected, boolean[] visited, int cities, int i) {
        for (int j = 0; j < cities; j ++) {
            if (isConnected[i][j] == 1 && !visited[j]) {
                visited[j] = true;
                dfs(isConnected, visited, cities, j);
            }
        }
    }

    /**
     * find circle nums
     *
     * @param isConnected
     * @return
     */
    public int findCircleNum(int[][] isConnected) {
        int circleNum = 0;
        for (int i = 0; i < isConnected.length; i++) {
            if (dfs(isConnected, i) > 0) circleNum++;
        }
        return circleNum;
    }


    private int dfs(int[][] isConnected, int i) {
        int cNum = 0;
        for (int j = 0; j < isConnected.length; j++) {
            if (isConnected[i][j] == 1) {
                cNum++;
                isConnected[i][j] = 0;
                // mean itself
                if (i != j) {
                    cNum += dfs(isConnected, j);
                }
            }
        }
        return cNum;
    }

    int[][] area = {
            {1, 1, 0},
            {1, 1, 0},
            {0, 0, 1},
    };

    @Test
    public void testProvinceCircles() {
       log.debug("circle number : {} ",findCircleNum(area));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
    @Threads(1)
    @Fork(1)
    public void benchmarkProvinceCircles() {
        findCircleNum(area);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ProvinceCircles.class.getSimpleName())
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
