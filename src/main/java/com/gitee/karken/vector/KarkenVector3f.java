package com.gitee.karken.vector;

public class KarkenVector3f extends AbstractKarkenVector<Float> {

    public KarkenVector3f(Float x, Float y, Float z) {
        super(x, y, z);
    }

    @Override
    public String toString() {
        return "KarkenVector3f{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
