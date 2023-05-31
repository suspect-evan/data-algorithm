package com.evan.data_structure.list;

import org.junit.Test;

/**
 * @author EvanYu
 * @date 2023/5/28
 * @description 链表反转
 *
 * 1.头插法
 * 2.迭代
 * 3.栈push pop 需要额外的空间
 */
public class ReverseLinkList {

    @Test
    public void testLinkReverse() {
        LinkedNode a = new LinkedNode(1);
        LinkedNode b = new LinkedNode(2);
        LinkedNode c = new LinkedNode(3);
        LinkedNode d = new LinkedNode(4);
        a.next = b;
        b.pre = a;
        b.next = c;
        c.pre = b;
        c.next = d;
        d.pre = c;

        System.out.println(a);
        System.out.println(reverseRecursive(a));
        System.out.println(reverse(d));
    }

    @Test
    public void testLinkErase(){
        LinkedNode a = new LinkedNode(1);
        LinkedNode b = new LinkedNode(2);
        LinkedNode c = new LinkedNode(3);
        LinkedNode d = new LinkedNode(4);
        LinkedNode e = new LinkedNode(3);
        LinkedNode f = new LinkedNode(7);
        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;
        e.next = f;

        System.out.println(eraseNodeInList(a,3));
    }

    /**
     * erase given node in list
     * @param head
     * @param val
     * @return
     */
    private static LinkedNode eraseNodeInList(LinkedNode head,int val){
        if(head == null || head.next == null){
            return head;
        }

        // clear head should be deleted
        LinkedNode newHead = head;
        while (newHead.val == val){
            newHead = newHead.next;
        }
        head = newHead;

        LinkedNode pre = null;
        while (head != null){
            if (head.val == val && pre != null){
                pre.next = head.next;
            }
            pre = head;
            head = head.next;
        }
        return newHead;
    }


    /**
     * head insert
     *
     * @param head
     * @return
     */
    private static LinkedNode reverse(LinkedNode head) {
        LinkedNode pre = null;
        LinkedNode next;
        while (head != null) {
            next = head.next;
            // key
            head.next = pre;
            head.pre = next;
            pre = head;
            head = next;
        }

        return pre;
    }

    private static LinkedNode reverseRecursive(LinkedNode head) {
        if (head == null || head.next == null) {
            return head;
        } else {
            LinkedNode newHead = reverseRecursive(head.next);
            head.next.next = head;
            head.next = null;
            return newHead;
        }

    }


    private static class LinkedNode {
        private LinkedNode pre;
        private LinkedNode next;
        private int val;

        public LinkedNode(int val) {
            this.val = val;
        }

        public LinkedNode() {
        }

        @Override
        public String toString() {
            String link = String.valueOf(this.val);
            LinkedNode current = this;
            while (null != current.next) {
                link = link + "-" + current.next.val;
                current = current.next;
            }
            return link;
        }
    }
}
