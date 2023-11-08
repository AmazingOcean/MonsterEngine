package com.gitee.karken.util.vector;

import com.gitee.karken.core.player.KarkenAnimatedCube;
import com.gitee.karken.core.player.serializer.AnimatedCube;
import com.gitee.karken.util.MathHelper;
import com.mojang.math.Axis;
import org.joml.Math;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class KarkenVector3f extends AbstractKarkenVector<Float> {

    public KarkenVector3f(Float x, Float y, Float z) {
        super(x, y, z);
    }

    public KarkenVector3f(double x, double y, double z) {
        super((float) x, (float) y, (float) z);
    }

    public KarkenVector3f(Vector3f vector3f) {
        this(vector3f.x, vector3f.y, vector3f.z);
    }

    @Override
    public KarkenVector3f clone() {
        return new KarkenVector3f(x, y, z);
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

    public KarkenVector3f negation() {
        return new KarkenVector3f(-getX(), -getY(), -getZ());
    }

    public KarkenVector3f negationX() {
        return new KarkenVector3f(Float.valueOf(-getX()), getY(), getZ());
    }

    public KarkenVector3f negationY() {
        return new KarkenVector3f(getX(), Float.valueOf(-getY()), getZ());
    }

    public KarkenVector3f negationZ() {
        return new KarkenVector3f(getX(), getY(), Float.valueOf(-getZ()));
    }

    public KarkenVector3f scale(float scale) {
        return this.multiply(scale);
    }

    public Quaternionf rotationXYZ(float angleX, float angleY, float angleZ) {
        return new Quaternionf().rotationXYZ(angleX, angleY, angleZ);
    }

    public Quaternionf axisRotationX() {
        return Axis.XP.rotation(x);
    }

    public Quaternionf axisRotationY() {
        return Axis.YP.rotation(y);
    }

    public Quaternionf axisRotationZ() {
        return Axis.ZP.rotation(z);
    }

    @Override
    public KarkenVector3f multiply(Float scale) {
        return new KarkenVector3f(getX() * scale, getY() * scale, getZ() * scale);
    }

    public KarkenVector3d getKarkenVector3d() {
        return new KarkenVector3d(
                (double) getX(),
                (double) getY(),
                (double) getZ()
        );
    }

    @Override
    public KarkenVector3f division(Float scale) {
        return new KarkenVector3f(getX() / scale, getY() / scale, getZ() / scale);
    }

    /**
     * 修正光照问题
     *
     * @param cube
     * @param normal
     * @return
     */
    public KarkenVector3f fixInvertedFlatCube(KarkenAnimatedCube cube, KarkenVector3f normal) {
        if (normal.getX() < 0 && (cube.getSize().getY() == 0 || cube.getSize().getZ() == 0))
            normal.negationX();

        if (normal.getY() < 0 && (cube.getSize().getX() == 0 || cube.getSize().getZ() == 0))
            normal.negationY();

        if (normal.getZ() < 0 && (cube.getSize().getX() == 0 || cube.getSize().getY() == 0))
            normal.negationZ();
        return normal;
    }

}
