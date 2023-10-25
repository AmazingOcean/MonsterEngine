package com.gitee.karken.vector;

import com.gitee.karken.util.MathHelper;

public interface KarkenVector<T extends Number> {

    T getX();

    void setX(T x);

    T getY();

    void setY(T y);

    T getZ();

    void setZ(T z);

    KarkenVector<T> multiply(T scale);

    KarkenVector<T> division(T scale);

}
