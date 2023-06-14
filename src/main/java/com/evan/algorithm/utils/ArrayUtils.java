package com.evan.algorithm.utils;

import java.util.Random;

/**
 * @author EvanYu
 * @date 2023/5/28
 * @description
 */
public class ArrayUtils {
    static final Random random = new Random();

    private ArrayUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * generate random arr
     *
     * @param num
     * @param bound
     * @return
     */
    public static int[] generateRandomIntArray(int num, int bound) {
        if (num == 0) {
            return new int[]{};
        }
        int[] arr = new int[num];
        for (int i = 0; i < num; i++) {
            arr[i] = random.nextInt(bound);
        }
        return arr;
    }
}
