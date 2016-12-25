package com.test;

import java.util.Stack;

/**
 * <p>
 * Created with IntelliJ IDEA. <br/>
 * User: Ankur jain <br/>
 * Date: 24-Dec-16 <br/>
 * Time: 10:47 AM <br/>
 */
public class MyQueue<E> {
    Stack s1;
    Stack s2;

    public MyQueue() {
        s1 = new Stack();
        s2 = new Stack();
    }

    public boolean isEmpty() {
        if (s2.isEmpty()) {
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
        }
        return s2.isEmpty();
    }

    public void enQueue(Object data) {
        s1.push(data);
    }

    public Object deQueue() {
        if (!s2.isEmpty()) {
            return s2.pop();
        } else {
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
            return s2.pop();
        }
    }
}
