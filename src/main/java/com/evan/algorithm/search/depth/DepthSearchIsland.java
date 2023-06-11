package com.evan.algorithm.search.depth;

import org.junit.Assert;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.Stack;
import java.util.concurrent.TimeUnit;

/**
 * @author EvanYu
 * @date 2023/6/7
 * @description 深度优先搜索
 * <p>
 * 695. 岛屿的最大面积
 * 给你一个大小为 m x n 的二进制矩阵 grid 。
 * <p>
 * 岛屿是由一些相邻的1(代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在 水平或者竖直的四个方向上 相邻。你可以假设grid 的四个边缘都被 0（代表水）包围着。
 * 岛屿的面积是岛上值为 1 的单元格的数目。
 * 计算并返回 grid 中最大的岛屿面积。如果没有岛屿，则返回面积为 0
 * <p>
 * 解法：二分 + 左闭右开
 */
public class DepthSearchIsland {

    @Test
    public void testSearch() {
        int[][] areaB = {
                {1, 0, 1, 1, 0, 1, 0, 1},
                {1, 0, 1, 1, 0, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 1},
        };
        Assert.assertEquals(6, searchByStack(newArea()));
        Assert.assertEquals(6, searchByRecur(areaB));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(DepthSearchIsland.class.getSimpleName())
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

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
    @Threads(1)
    @Fork(1)
    public void benchmarkSearchByStack() {
        searchByStack(newArea());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
    @Threads(1)
    @Fork(1)
    public void benchmarkSearchByRecur() {
        searchByRecur(newArea());
    }

    private int[][] newArea() {
        int[][] area = {
                {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}
        };
        return area;
    }

    // above (-1,0) right (0,1) below (1,0) left (0,-1)
    private static int[] direction = {-1, 0, 1, 0, -1};

    /**
     * search max area of island
     *
     * @param grid
     * @return
     */
    public int searchByStack(int[][] grid) {
        int maxArea = 0, currentArea = 0, h = grid.length, w = grid[0].length, x = 0, y = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (grid[i][j] == 1) {
                    Stack<int[]> island = new Stack<>();
                    // start searching
                    island.push(new int[]{i, j});
                    currentArea++;
                    // erase tracks
                    grid[i][j] = 0;

                    while (!island.isEmpty()) {
                        int[] c = island.pop();
                        // four directions
                        for (int k = 0; k < 4; k++) {
                            x = c[0] + direction[k];
                            y = c[1] + direction[k + 1];
                            if (x >= 0 && y >= 0 && x < h && y < w && grid[x][y] == 1) {
                                // erase tracks
                                grid[x][y] = 0;
                                island.push(new int[]{x, y});
                                currentArea++;
                            }
                        }
                    }
                    maxArea = Math.max(maxArea, currentArea);
                    currentArea = 0;
                }
            }
        }
        return maxArea;
    }


    /**
     * search max area of island
     *
     * @param grid
     * @return
     */
    public int searchByRecur(int[][] grid) {
        int maxArea = 0, h = grid.length, w = grid[0].length;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (grid[i][j] == 1) {
                    maxArea = Math.max(maxArea, dfs(grid, i, j));
                }
            }
        }
        return maxArea;
    }

    private int dfs(int[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[i].length || grid[i][j] == 0) {
            return 0;
        }
        grid[i][j] = 0;
        int num = 1;
        num += dfs(grid, i + 1, j);
        num += dfs(grid, i - 1, j);
        num += dfs(grid, i, j + 1);
        num += dfs(grid, i, j - 1);
        return num;

        /*if (grid[i][j] == 0) return 0;
        // erase tracks
        grid[i][j] = 0;
        int x, y, area = 1;
        // four directions
        for (int k = 0; k < 4; k++) {
            x = i + direction[k];
            y = j + direction[k + 1];
            if (x >= 0 && y >= 0 && x < grid.length && y < grid[0].length) {
                area += dfs(grid, x, y);
            }
        }
        return area;*/
    }
}
