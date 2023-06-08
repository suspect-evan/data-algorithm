package com.evan.algorithm.dualpointer;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author EvanYu
 * @date 2023/6/8
 * @description
 *
 * 167.两数求和
 * <p>
 * 在一个增序的整数数组里找到两个数，使它们的和为给定值。已知有且只有一对解。
 * <p>
 * Input: numbers = [2,7,11,15], target = 9
 * Output: [0,1]
 */
@State(Scope.Thread)
public class TwoSum {

    int[] nums = new int[]{2,7,11,15,17,19,21,34,55,56};

    @Test
    public void testTwoSum() {
        System.out.println(Arrays.toString(twoSum(nums, 34)));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
    @Threads(1)
    @Fork(1)
    public void benchmarkSearch() {
        twoSum(nums,34);
    }

    /**
     * find one and only target sum
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        if (nums.length == 2) {
            return new int[]{0, 1};
        }
        int l = 0, r = nums.length - 1;

        while (l < r){
            if(nums[l] + nums[r] == target){
                break;
            }else if(nums[l] + nums[r] < target){
                l++;
            }else if(nums[l] + nums[r] > target){
                r--;
            }
        }
        return new int[]{l, r};
    }
}
