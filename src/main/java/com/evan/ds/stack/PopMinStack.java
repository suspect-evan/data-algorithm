package com.evan.ds.stack;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

/**
 * @author EvanYu
 * @date 2023/6/2
 * @description 每次弹出当前栈的最小值
 * 多使用一个栈的空间，记录区间的最小值
 */
@Slf4j
public class PopMinStack {

    Deque<Integer> dataStack = new ArrayDeque<>();
    Deque<Integer> minStack = new ArrayDeque<>();
    IntConsumer pushFunc;
    IntSupplier popFunc;


    public PopMinStack() {
        this.popFunc = this::pop1;
        this.pushFunc = this::push1;
    }

    public void push(Integer num) {
        this.pushFunc.accept(num);
    }

    public Integer popMin() {
        return this.popFunc.getAsInt();
    }


    public void push1(Integer num) {
        this.dataStack.push(num);
        if (this.minStack.isEmpty()) {
            this.minStack.push(num);
            return;
        }
        this.minStack.push(Math.min(minStack.peek(), num));
    }

    public Integer pop1() {
        this.dataStack.pop();
        return this.minStack.pop();
    }


    public void push2(Integer num) {
        this.dataStack.push(num);
        if (this.minStack.isEmpty() || num <= minStack.peek()) {
            this.minStack.push(num);
        }
    }

    public Integer pop2() {
        Integer data = this.dataStack.pop();

        Integer min = minStack.peek();
        if (min != null && min.equals(data)) {
            this.minStack.pop();
        }
        return min;
    }

    public boolean isEmpty() {
        return this.dataStack.isEmpty();
    }


    @Test
    public void testMinStack() {
        PopMinStack stack = new PopMinStack();
        stack.popFunc = stack::pop1;
        stack.pushFunc = stack::push1;
        pushInto(stack);

        while (!minStack.isEmpty()) {
            log.debug("pop min val : {} ", stack.popMin());
        }

        PopMinStack minStack2 = new PopMinStack();
        minStack2.popFunc = minStack2::pop2;
        minStack2.pushFunc = minStack2::push2;
        pushInto(minStack2);

        while (!minStack2.isEmpty()) {
            log.debug("pop min val : {} ", minStack2.popMin());
        }
    }

    private void pushInto(PopMinStack minStack) {
        minStack.push(9);
        minStack.push(11);
        minStack.push(7);
        minStack.push(8);
        minStack.push(14);
        minStack.push(4);
        minStack.push(5);
        minStack.push(1);
    }

}
