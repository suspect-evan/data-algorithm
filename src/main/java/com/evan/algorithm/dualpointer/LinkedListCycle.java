package com.evan.algorithm.dualpointer;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * @author EvanYu
 * @date 2023/6/8
 * @description 167.快慢指针
 * 给定一个链表，如果有环路，找出环路的开始点
 * <p>
 * 证明：
 * r:环的距离大小
 * a:起点到环入口的距离
 * b:环入口到指针相遇距离
 * c:环大小减去b的剩余距离
 * <p>
 * 慢指针走了a+b 快指针走了n圈，即 a+b+n*(b+c) ，由速度2:1 得 2(a+b)= a+b+n*(b+c) 即 a = nb - b + nc
 * 环大小= b+c
 * 所以 a = (n-1)*(b+c) + c
 * 由此可得 新指针走 a 时，慢指针正好走到环的入口
 */

@Slf4j
public class LinkedListCycle {

    @Test
    public void testCycle() {
        log.debug("cycle node is : {} ",listCycle(buildRing()).val);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
    @Threads(1)
    @Fork(1)
    public void benchmarkSearch() {
        listCycle(buildRing());
    }


    /**
     * check list cycle
     *
     * @param head
     * @return
     */
    public ListNode listCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        // has ringBuffer
        do {
            slow = slow.next;
            fast = fast.next.next;
        } while (slow != fast);

        fast = head;
        while (fast != slow) {
            slow = slow.next;
            fast = fast.next;
        }
        return fast;
    }

    protected static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    ListNode buildRing() {
        ListNode e = new ListNode(6);
        ListNode d = new ListNode(5, e);
        ListNode c = new ListNode(4, d);
        ListNode b = new ListNode(3, c);
        ListNode a = new ListNode(2, b);
        ListNode head = new ListNode(1, a);
        e.next = b;
        return head;
    }
}
