package com.evan.ds.stack;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Evan
 * @date 2023/6/3
 * @description
 */
@Slf4j
public class ReverseStack {

    @Test
    public void testReverseStack() {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);


        reverse(stack);
        log.debug("{}", stack);
    }

    public int getAndRemoveLast(Deque<Integer> stack) {
        int cur = stack.pop();
        if (stack.isEmpty()) {
            return cur;
        } else {
            int last = getAndRemoveLast(stack);
            stack.push(cur);
            return last;
        }
    }

    public void reverse(Deque<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        int last = getAndRemoveLast(stack);
        reverse(stack);
        stack.push(last);
    }
}
