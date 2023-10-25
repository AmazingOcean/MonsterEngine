package com.gitee.karken.util;

public class MathHelper {

    // 实现基本的线性插值
    public static float lerp(float start, float end, float delta) {
        return start + delta * (end - start);
    }

}
