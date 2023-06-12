package com.evan.ds.stack;

import org.junit.Test;

import java.util.Stack;

/**
 * @author Evan
 * @date 2023/6/3
 * @description
 *
 */
public class ReverseStack {

    @Test
    public void testReverseStack(){
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);


        reverse(stack);
        System.out.println(stack);
    }

    public int getAndRemoveLast(Stack<Integer> stack){
        int cur = stack.pop();
        if(stack.isEmpty()){
            return cur;
        }else {
            int last = getAndRemoveLast(stack);
            stack.push(cur);
            return last;
        }
    }

    public void reverse(Stack<Integer> stack){
        if(stack.isEmpty()){
            return;
        }
        int last = getAndRemoveLast(stack);
        reverse(stack);
        stack.push(last);
    }
}
