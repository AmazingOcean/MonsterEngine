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
        return new KarkenVector3f(Math.toRadians(getX()), Math.toRadians(getY()), Math.toRadians(getZ()));
    }

    public KarkenVector3f withLerp(KarkenVector3f end, float delta) {
        return new KarkenVector3f(
                MathHelper.lerp(this.getX(), end.getX(), delta),
                MathHelper.lerp(this.getY(), end.getY(), delta),
                MathHelper.lerp(this.getZ(), end.getZ(), delta)
        );
    }

    public KarkenVector3f negation() {
        return multiply(-1,-1,-1);
    }

    public KarkenVector3f negationX() {
        return this.multiply(-1,1,1);
    }

    public KarkenVector3f negationY() {
        return this.multiply(1,-1,1);
    }

    public KarkenVector3f negationZ() {
        return this.multiply(1,1,-1);
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
        return multiply(scale,scale,scale);
    }

    public KarkenVector3f multiply(float x,float y,float z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
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
        return division(scale,scale,scale);
    }
    public KarkenVector3f division(float x,float y,float z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }
    /**
     * 修正光照问题
     *
     * @param cube
     * @return
     */
    public KarkenVector3f fixInvertedFlatCube(KarkenAnimatedCube cube) {
        if (getX() < 0 && (cube.getSize().getY() == 0 || cube.getSize().getZ() == 0))
            this.negationX();

        if (getY() < 0 && (cube.getSize().getX() == 0 || cube.getSize().getZ() == 0))
            this.negationY();

        if (getZ() < 0 && (cube.getSize().getX() == 0 || cube.getSize().getY() == 0))
            this.negationZ();
        return this;
    }

    public boolean isZero() {
        return x == 0f && y == 0f && z == 0f;
    }

}
