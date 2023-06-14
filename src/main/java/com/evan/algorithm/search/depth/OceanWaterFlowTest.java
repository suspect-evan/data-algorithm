package com.evan.algorithm.search.depth;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.*;
import org.openjdk.jmh.runner.options.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author EvanYu
 * @date 2023/6/13
 * @description 417. 太平洋大西洋水流问题
 * <p>
 * 有一个 m × n 的矩形岛屿，与 太平洋 和 大西洋 相邻。“太平洋”处于大陆的左边界和上边界，而 “大西洋” 处于大陆的右边界和下边界。
 * 这个岛被分割成一个由若干方形单元格组成的网格。给定一个 m x n 的整数矩阵heights，heights[r][c]表示坐标 (r, c) 上单元格 高于海平面的高度 。
 * 岛上雨水较多，如果相邻单元格的高度 小于或等于 当前单元格的高度，雨水可以直接向北、南、东、西流向相邻单元格。水可以从海洋附近的任何单元格流入海洋。
 * 返回网格坐标 result的 2D 列表 ，其中result[i] = [ri, ci]表示雨水从单元格 (ri, ci) 流动 既可流向太平洋也可流向大西洋
 * <p>
 * Input:
 * 太平洋 ~ ~ ~ ~ ~
 * ~ 1 2 2  3 (5) *
 * ~ 3 2 3 (4)(4) *
 * ~ 2 4 (5) 3 1 *
 * ~(6)(7)1 4 5 *
 * ~(5) 1 1 2 4 *
 * * * * * * 大西洋
 * 输入: heights = [[1,2,2,3,5],[3,2,3,4,4],[2,4,5,3,1],[6,7,1,4,5],[5,1,1,2,4]]
 * Output: [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]]
 */
@State(Scope.Thread)
public class OceanWaterFlowTest {

    int[][] heights = {
            {1, 2, 2, 3, 5},
            {3, 2, 3, 4, 4},
            {2, 4, 5, 3, 1},
            {6, 7, 1, 4, 5},
            {5, 1, 1, 2, 4}
    };
    List<List<Integer>> output = Arrays.asList(
            Arrays.asList(0, 4),
            Arrays.asList(1, 3),
            Arrays.asList(1, 4),
            Arrays.asList(2, 2),
            Arrays.asList(3, 0),
            Arrays.asList(3, 1),
            Arrays.asList(4, 0)
    );
    private static final int[] direction = {-1, 0, 1, 0, -1};

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int height = heights.length;
        int width = heights[0].length;
        if (width == 0) {
            return new ArrayList<>();
        }

        List<List<Integer>> res = new ArrayList<>(width);

        boolean[][] pacificFlowable = new boolean[height][width];
        boolean[][] atlanticFlowable = new boolean[height][width];

        for (int i = 0; i < width; i++) {
            dfs(atlanticFlowable, heights, 0, i);
            dfs(pacificFlowable, heights, height - 1, i);
        }

        for (int i = 0; i < height; i++) {
            dfs(atlanticFlowable, heights, i, 0);
            dfs(pacificFlowable, heights, i, width - 1);
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!pacificFlowable[i][j] || !atlanticFlowable[i][j]) {
                    continue;
                }

                List<Integer> subRes = new ArrayList<>();
                subRes.add(i);
                subRes.add(j);
                res.add(subRes);
            }
        }

        return res;
    }

    public void dfs(boolean[][] flowable, int[][] heights, int i, int j) {
        if (flowable[i][j]) {
            return;
        }
        flowable[i][j] = true;
        for (int k = 0; k < 4; k++) {
            int x = i + direction[k];
            int y = j + direction[k + 1];
            if (x >= 0 && y >= 0 && x < heights.length && y < heights[0].length && heights[i][j] <= heights[x][y]) {
                dfs(flowable, heights, x, y);
            }
        }
    }

    @Test
    public void testOceanWaterFlow() {
        pacificAtlantic(heights);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Measurement(iterations = 3, time = 1)
    @Warmup(iterations = 1, time = 1)
    @Threads(1)
    @Fork(1)
    public void benchmarkOceanWaterFlow() {
        pacificAtlantic(heights);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(OceanWaterFlowTest.class.getSimpleName())
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
