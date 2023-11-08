package com.gitee.karken.util.vector;

public class KarkenVector3i extends AbstractKarkenVector<Integer> {

    public KarkenVector3i(Integer x, Integer y, Integer z) {
        super(x, y, z);
    }

    @Override
    public KarkenVector3i clone() {
        return new KarkenVector3i(x, y, z);
    }
    @Override
    public KarkenVector3i multiply(Integer scale) {
        return new KarkenVector3i(getX() * scale, getY() * scale, getZ() * scale);
    }

    @Override
    public KarkenVector3i division(Integer scale) {
        return new KarkenVector3i(getX() / scale, getY() / scale, getZ() / scale);
    }
}
