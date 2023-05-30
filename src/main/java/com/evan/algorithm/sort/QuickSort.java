package com.evan.algorithm.sort;

import com.evan.algorithm.utils.ArrayUtils;

import java.util.Arrays;

/**
 * @author EvanYu
 * @date 2023/5/28
 * @description 算法：快速排序
 * 复杂度：O(nLogN)
 * 解决案例：适合统计或者计算所有比某个数大或者小的时候
 * 场景：小和计算、求逆序对
 * <p>
 * 例如 2 4 3 1 小和为每个数比它小的数累计 4的小和为1+2+3=6 数组小和为所有小和的累加 1+1+1+2+2+3=10
 * 可以在merge的过程中，每次左边比较较小的数一定会产生右边剩余数量的小和，比如 2，4 一定会产生1个属于4下 2的小和
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = ArrayUtils.GenerateRandomIntArray(12, 50);
        System.out.println(Arrays.toString(quickSort(arr)));

        int[] arr1 = ArrayUtils.GenerateRandomIntArray(12, 50);
        quicksortWithSwap(arr1);
        System.out.println(Arrays.toString(arr1));

    }


    private static int[] quickSort(int[] arr) {
        if (null == arr || arr.length <= 1) {
            return arr;
        }

        int len = arr.length;
        processSort(arr, 0, len - 1);
        return arr;
    }


    /**
     * make arr ordered from index l to index r
     *
     * @param arr
     * @param l
     * @param r
     */
    private static void processSort(int[] arr, int l, int r) {
        int left = l;
        int right = r;
        int temp = 0;
        if (left <= right) {   //待排序的元素至少有两个的情况
            temp = arr[left];  //待排序的第一个元素作为基准元素
            while (left != right) {   //从左右两边交替扫描，直到left = right

                while (right > left && arr[right] >= temp)
                    right--;        //从右往左扫描，找到第一个比基准元素小的元素
                arr[left] = arr[right];  //找到这种元素arr[right]后与arr[left]交换

                while (left < right && arr[left] <= temp)
                    left++;         //从左往右扫描，找到第一个比基准元素大的元素
                arr[right] = arr[left];  //找到这种元素arr[left]后，与arr[right]交换

            }
            arr[right] = temp;    //基准元素归位
            processSort(arr, l, left - 1);  //对基准元素左边的元素进行递归排序
            processSort(arr, right + 1, r);  //对基准元素右边的进行递归排序
        }
    }

    public static void quicksortWithSwap(int n[]) {
        sort(n, 0, n.length - 1);
    }

    public static void sort(int n[], int l, int r) {
        if (l < r) {
            // 一趟快排，并返回交换后基数的下标
            int index = partition(n, l, r);
            // 递归排序基数左边的数组
            sort(n, l, index - 1);
            // 递归排序基数右边的数组
            sort(n, index + 1, r);
        }

    }

    public static int partition(int n[], int l, int r) {
        // p为基数，即待排序数组的第一个数
        int p = n[l];
        int i = l;
        int j = r;
        while (i < j) {
            // 从右往左找第一个小于基数的数
            while (n[j] >= p && i < j) {
                j--;
            }
            // 从左往右找第一个大于基数的数
            while (n[i] <= p && i < j) {
                i++;
            }
            // 找到后交换两个数
            swap(n, i, j);
        }
        // 使划分好的数分布在基数两侧
        swap(n, l, i);
        return i;
    }

    private static void swap(int n[], int i, int j) {
        int temp = n[i];
        n[i] = n[j];
        n[j] = temp;
    }

}
