package com.evan.ds.queue;

import org.junit.Test;

import java.util.*;

/**
 * @author Evan
 * @date 2023/6/3
 * @description
 * 滑动窗口生成所有窗口的最大值
 *
 * 使用队列记录窗口范围内的从大到小排列的值，当出现大值时把队列尾部的数弹出，直到没有比当前值更小的数
 */
public class SlideWinMax {

    @Test
    public void TestSlideWinMax(){
        int[] arr = new int[]{4,3,8,4,1,5,6,3};
        System.out.println(Arrays.toString(getWinMax(arr,3)));
    }


    public static int[] getWinMax(int[] arr,int w) {
        if(arr == null || w<1||arr.length<w){
            return null;
        }
        //结果为n-w+1长度的数组
        LinkedList<Integer> qMax = new LinkedList<>();
        int[] result = new int[arr.length - w +1];
        int index = 0;
        for(int i=0;i<arr.length;i++){
            // 如果队尾对应的元素比较小，就弹出队尾，直到队尾的位置所代表的值比当前值arr[i]大
            while(!qMax.isEmpty()&&arr[qMax.peekLast()] <= arr[i]) {
                qMax.pollLast();
            }
            qMax.addLast(i);

            // 检查队头下标是否过期，过期就把队头弹出
            if(qMax.peekFirst() == i - w){
                qMax.pollFirst();
            }
            // 如果窗口出现了，就开始记录每个窗口的最大值
            if(i >= w -1) {
                result[index++] = arr[qMax.peekFirst()];
            }
        }
        return result;
    }
}
