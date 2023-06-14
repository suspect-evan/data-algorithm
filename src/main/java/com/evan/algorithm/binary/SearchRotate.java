package com.evan.algorithm.binary;

import com.evan.algorithm.utils.ArrayUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author EvanYu
 * @date 2023/6/7
 * @description 二分搜索
 * <p>
 * <p>
 * 搜索旋转排序数组 II
 * 已知存在一个按非降序排列的整数数组 nums ，数组中的值不必互不相同。
 * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转 ，使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。
 * 例如， [0,1,2,4,4,4,5,6,6,7] 在下标 5 处经旋转后可能变为 [4,5,6,6,7,0,1,2,4,4] 。
 * 给你 旋转后 的数组 nums 和一个整数 target ，请你编写一个函数来判断给定的目标值是否存在于数组中。如果 nums 中存在这个目标值 target ，则返回 true ，否则返回 false 。
 * 你必须尽可能减少整个操作步骤。
 * <p>
 * 解法：二分 + 左闭右开
 */
@Slf4j
public class SearchRotate {

    private static final int[] array = new int[]{7, 7, 9, 11, 14, 15, 17, 1, 3, 5, 6, 7};
    private static final Random random = new Random();

    private int[] getSortedRotateArr(int nums) {
        int[] arr = ArrayUtils.generateRandomIntArray(nums, 100);
        Arrays.sort(arr);

        int pivot = random.nextInt(nums - 1);
        int[] copy = new int[nums];
        for (int i = 0; i < arr.length; i++) {
            if (i <= pivot) {
                copy[(arr.length - 1 - pivot) + i] = arr[i];
            } else {
                copy[i - pivot - 1] = arr[i];
            }
        }
        return copy;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
    @Threads(1)
    @Fork(1)
    public void benchmarkSearch() {
        search(getSortedRotateArr(20), 7);
    }

    @Test
    public void testSearch() {
        log.debug("{}", search(array, 10));
        log.debug("{}", Arrays.toString(getSortedRotateArr(10)));
    }

    /**
     * search in rotated sorted array
     *
     * @param nums
     * @param target
     * @return
     */
    public boolean search(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        int mid = (l + r) / 2;

        while (l <= r) {
            if (nums[mid] == target) {
                return true;
            }

            // ignore same
            if (nums[l] == nums[mid]) {
                l++;
            } else if (nums[l] < nums[mid]) {
                // means left is monotonic increasing.

                // in left
                if (nums[l] <= target && target <= nums[mid]) {
                    r = mid - 1;
                } else {
                    // not in left
                    l = mid + 1;
                }
            } else {
                // means right is monotonic increasing.
                // in left
                if (nums[mid] <= target && target <= nums[r]) {
                    l = mid + 1;
                } else {
                    // not in left
                    r = mid - 1;
                }
            }

            mid = (l + r) / 2;
        }
        return false;
    }
}
