package com.gitee.karken.vector;

public class KarkenVector3d extends AbstractKarkenVector<Double> {

    public KarkenVector3d(Double x, Double y, Double z) {
        super(x, y, z);
    }

    @Override
    public KarkenVector<Double> multiply(Double scale) {
        return new KarkenVector3d(getX() * scale, getY() * scale, getZ() * scale);
    }

    @Override
    public KarkenVector<Double> division(Double scale) {
        return new KarkenVector3d(getX() / scale, getY() / scale, getZ() / scale);
    }
}
