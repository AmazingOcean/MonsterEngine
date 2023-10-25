package com.gitee.karken.animation.loop;

import com.gitee.karken.bone.KarkenBone;
import com.gitee.karken.vector.KarkenVector3f;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class AnimationTransitioned {

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

    public Stack<KarkenVector3f> getStack() {
        return stack;
    }

    public void setStack(Stack<KarkenVector3f> stack) {
        this.stack = stack;
    }
}
