package com.gitee.karken.animation.loop;

import com.gitee.karken.vector.KarkenVector3f;

import java.util.Stack;

public class AnimationTransitioned {

    private float tickDelta;

    private Stack<KarkenVector3f> stack = new Stack<>();

    public KarkenVector3f peek() {
        if (stack.isEmpty()) {
            return null;
        }
        return stack.peek();
    }

    public void push(KarkenVector3f vector3f) {
        stack.push(vector3f);
    }

    public float getTickDelta() {
        return tickDelta;
    }

    public void setTickDelta(float tickDelta) {
        this.tickDelta = tickDelta;
    }

    public Stack<KarkenVector3f> getStack() {
        return stack;
    }

    public void setStack(Stack<KarkenVector3f> stack) {
        this.stack = stack;
    }
}
