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

    public KarkenVertex withTexture(float u, float v) {
        return new KarkenVertex(position, new AnimatedUV.Box(u, v));
    }

}
