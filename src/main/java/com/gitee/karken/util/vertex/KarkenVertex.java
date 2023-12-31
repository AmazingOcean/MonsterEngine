package com.gitee.karken.util.vertex;

import com.gitee.karken.core.player.serializer.AnimatedUV;
import com.gitee.karken.util.vector.KarkenVector3d;
import com.gitee.karken.util.vector.KarkenVector3f;

public class KarkenVertex {

    private KarkenVector3d position;

    private AnimatedUV.Box animatedUV;

    public KarkenVertex(KarkenVector3d position) {
        this.position = position;
    }

    public KarkenVertex(double x, double y, double z, double u, double v) {
        this(new KarkenVector3d(x, y, z), new AnimatedUV.Box(u, v));
    }

    public KarkenVertex(KarkenVector3d position, AnimatedUV.Box animatedUV) {
        this.position = position;
        this.animatedUV = animatedUV;
    }

    public KarkenVector3d getPosition() {
        return position;
    }

    public void setPosition(KarkenVector3d position) {
        this.position = position;
    }

    public AnimatedUV.Box getAnimatedUV() {
        return animatedUV;
    }

    public void setAnimatedUV(AnimatedUV.Box animatedUV) {
        this.animatedUV = animatedUV;
    }

    public KarkenVertex remap(float u,float v) {
        return withTexture(u, v);
    }

    public KarkenVertex remap(double u,double v) {
        return withTexture((float) u, (float) v);
    }

    public KarkenVertex withTexture(float u, float v) {
        return new KarkenVertex(position, new AnimatedUV.Box(u, v));
    }

    @Override
    public String toString() {
        return "KarkenVertex{" +
                "position=" + position +
                ", animatedUV=" + animatedUV +
                '}';
    }
}
