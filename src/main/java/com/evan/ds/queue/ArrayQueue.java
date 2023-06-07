package com.evan.ds.queue;

import org.junit.Test;

/**
 * @author EvanYu
 * @date 2023/5/31
 * @description 使用固定数组实现简单的队列
 */
public class ArrayQueue {


    private static class RingBufferQueue {
        private int pushIdx;
        private int popIdx;
        private int size;
        private int limit;
        private int[] arr;

        public RingBufferQueue(int limit) {
            this.pushIdx = 0;
            this.popIdx = 0;
            this.limit = limit;
            this.arr = new int[limit];
        }

        public void push(int ele) {
            if (size >= limit) {
                throw new RuntimeException("queue is full");
            }
            size++;
            arr[pushIdx] = ele;
            pushIdx = nextIdx(pushIdx);
        }

        public int pop() {
            if (size <= 0) {
                throw new RuntimeException("queue is empty");
            }
            size--;
            int res = arr[popIdx];
            popIdx = nextIdx(popIdx);
            return res;
        }

        private boolean isEmpty() {
            return size == 0;
        }

        private int nextIdx(int idx) {
            if (idx < limit - 1) {
                return idx + 1;
            }
            return 0;
        }

    }


    @Test
    public void testQueue() {
        RingBufferQueue queue = new RingBufferQueue(3);
        queue.push(3);
        queue.push(2);
        queue.push(1);

        System.out.println(queue.pop());
        System.out.println(queue.pop());
        queue.push(4);
        queue.push(5);

        while (!queue.isEmpty()){
            System.out.println(queue.pop());
        }
    }

}
