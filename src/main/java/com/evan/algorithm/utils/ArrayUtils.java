package com.evan.algorithm.utils;

import java.util.Arrays;
import java.util.Random;

/**
 * @author EvanYu
 * @date 2023/5/28
 * @description
 */
public class ArrayUtils {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(GenerateRandomIntArray(15,100)));
    }

    /**
     * generate random arr
     * @param num
     * @param bound
     * @return
     */
    public static int[] GenerateRandomIntArray(int num, int bound) {
        if (num == 0) {
            return null;
        }
        Random random = new Random();
        int[] arr = new int[num];
        for (int i = 0; i < num; i++) {
            arr[i] = random.nextInt(bound);
        }
        return arr;
    }
}
