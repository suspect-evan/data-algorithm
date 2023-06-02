package com.evan.ds.stack;

import org.junit.Test;

import java.util.Stack;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

/**
 * @author EvanYu
 * @date 2023/6/2
 * @description 每次弹出当前栈的最小值
 * 多使用一个栈的空间，记录区间的最小值
 */
public class MinStack {

    Stack<Integer> dataStack = new Stack<>();
    Stack<Integer> minStack = new Stack<>();
    IntConsumer pushFunc;
    IntSupplier popFunc;


    public MinStack() {
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
        if (min == data) {
            this.minStack.pop();
        }
        return min;
    }

    public boolean isEmpty() {
        return this.dataStack.isEmpty();
    }


    @Test
    public void testMinStack() {
        MinStack minStack = new MinStack();
        minStack.popFunc = minStack::pop1;
        minStack.pushFunc = minStack::push1;
        pushInto(minStack);

        while (!minStack.isEmpty()) {
            System.out.println(minStack.popMin());
        }

        MinStack minStack2 = new MinStack();
        minStack2.popFunc = minStack2::pop2;
        minStack2.pushFunc = minStack2::push2;
        pushInto(minStack2);

        while (!minStack2.isEmpty()) {
            System.out.println(minStack2.popMin());
        }
    }

    private void pushInto(MinStack minStack) {
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
