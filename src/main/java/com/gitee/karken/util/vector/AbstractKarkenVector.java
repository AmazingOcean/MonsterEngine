package com.gitee.karken.util.vector;

public abstract class AbstractKarkenVector<T extends Number> implements KarkenVector<T>, Cloneable {

    protected T x;

    protected T y;

    protected T z;

    public AbstractKarkenVector(T x, T y, T z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public T getX() {
        return x;
    }

    @Override
    public void setX(T x) {
        this.x = x;
    }

    @Override
    public T getY() {
        return y;
    }

    @Override
    public void setY(T y) {
        this.y = y;
    }

    @Override
    public T getZ() {
        return z;
    }

    @Override
    public void setZ(T z) {
        this.z = z;
    }

    @Override
    public abstract KarkenVector<T> clone();
}
