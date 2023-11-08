package com.gitee.karken.util.vector;

import com.gitee.karken.util.MathHelper;

public class KarkenVector3d extends AbstractKarkenVector<Double> {

    public KarkenVector3d(Double x, Double y, Double z) {
        super(x, y, z);
    }

    @Override
    public AbstractKarkenVector<Double> clone() {
        return new KarkenVector3d(x, y, z);
    }

    @Override
    public KarkenVector3d multiply(Double scale) {
        return new KarkenVector3d(getX() * scale, getY() * scale, getZ() * scale);
    }

    public KarkenVector3d multiply(double x, double y, double z) {
        return new KarkenVector3d(getX() * x, getY() * y, getZ() * z);
    }

    public KarkenVector3d withRadians() {
        return new KarkenVector3d(Math.toRadians(getX()), Math.toRadians(getY()), Math.toRadians(getZ()));
    }

    public KarkenVector3d withLerp(KarkenVector3d end, float delta) {
        return new KarkenVector3d(
                MathHelper.lerp(this.getX(), end.getX(), delta),
                MathHelper.lerp(this.getY(), end.getY(), delta),
                MathHelper.lerp(this.getZ(), end.getZ(), delta)
        );
    }


    @Override
    public KarkenVector3d division(Double scale) {
        return new KarkenVector3d(getX() / scale, getY() / scale, getZ() / scale);
    }

    public KarkenVector3d floor() {
        return new KarkenVector3d(Math.floor(this.getX()), Math.floor(this.getY()), Math.floor(this.getZ()));
    }

    public KarkenVector3f getKarkenVector3f() {
        return new KarkenVector3f(getX(), getY(), getZ());
    }


}
