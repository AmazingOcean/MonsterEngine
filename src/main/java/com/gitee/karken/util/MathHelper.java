package com.gitee.karken.util;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class MathHelper {

    // 实现基本的线性插值
    public static float lerp(float start, float end, float delta) {
        return start + delta * (end - start);
    }

    public static double lerp(double start,double end,double delta) {
        return start + delta * (end - start);
    }

    public static void transform(Vector3f vector3f, Matrix3f p_122250_) {
        float f = vector3f.x;
        float f1 = vector3f.y;
        float f2 = vector3f.z;
        vector3f.x = p_122250_.m00 * f + p_122250_.m01 * f1 + p_122250_.m02 * f2;
        vector3f.y = p_122250_.m10 * f + p_122250_.m11 * f1 + p_122250_.m12 * f2;
        vector3f.z = p_122250_.m20 * f + p_122250_.m21 * f1 + p_122250_.m22 * f2;
    }

    public static void transform(Vector4f vector4f,Matrix4f p_123608_) {
        float f = vector4f.x;
        float f1 = vector4f.y;
        float f2 = vector4f.z;
        float f3 = vector4f.w;
        vector4f.x = p_123608_.m00() * f + p_123608_.m01() * f1 + p_123608_.m02() * f2 + p_123608_.m03() * f3;
        vector4f.y = p_123608_.m10() * f + p_123608_.m11() * f1 + p_123608_.m12() * f2 + p_123608_.m13() * f3;
        vector4f.z = p_123608_.m20() * f + p_123608_.m21() * f1 + p_123608_.m22() * f2 + p_123608_.m23() * f3;
        vector4f.w = p_123608_.m30() * f + p_123608_.m31() * f1 + p_123608_.m32() * f2 + p_123608_.m33() * f3;
    }
}
