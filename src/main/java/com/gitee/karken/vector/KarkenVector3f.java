package com.gitee.karken.vector;

import com.gitee.karken.util.MathHelper;

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

    public KarkenVector3f withRadians() {
        return new KarkenVector3f((float) Math.toRadians(getX()), (float) Math.toRadians(getY()), (float) Math.toRadians(getZ()));
    }

    public KarkenVector3f withLerp(KarkenVector3f end, float delta) {
        return new KarkenVector3f(
                MathHelper.lerp(this.getX(), end.getX(), delta),
                MathHelper.lerp(this.getY(), end.getY(), delta),
                MathHelper.lerp(this.getZ(), end.getZ(), delta)
        );
    }

    @Override
    public KarkenVector3f multiply(Float scale) {
        return new KarkenVector3f(getX() * scale, getY() * scale, getZ() * scale);
    }

    @Override
    public KarkenVector3f division(Float scale) {
        return new KarkenVector3f(getX() / scale, getY() / scale, getZ() / scale);
    }
}
