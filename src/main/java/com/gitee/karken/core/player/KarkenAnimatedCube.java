package com.gitee.karken.core.player;

import com.gitee.karken.util.vector.KarkenVector3d;
import com.gitee.karken.util.vector.KarkenVector3f;
import com.gitee.karken.util.vertex.KarkenQuad;
import com.google.common.collect.Maps;
import net.minecraft.core.Direction;

import java.util.EnumMap;
import java.util.Map;

public class KarkenAnimatedCube {

    private EnumMap<Direction,KarkenQuad> karkenQuads;

    private KarkenVector3f pivot;

    private KarkenVector3f rotation;

    private KarkenVector3f size;

    public KarkenAnimatedCube(EnumMap<Direction, KarkenQuad> karkenQuads, KarkenVector3f pivot, KarkenVector3f rotation, KarkenVector3f size) {
        this.karkenQuads = karkenQuads;
        this.pivot = pivot;
        this.rotation = rotation;
        this.size = size;
    }

    public EnumMap<Direction, KarkenQuad> getKarkenQuads() {
        return karkenQuads;
    }

    public KarkenVector3f getPivot() {
        return pivot;
    }

    public void setPivot(KarkenVector3f pivot) {
        this.pivot = pivot;
    }

    public KarkenVector3f getRotation() {
        return rotation;
    }

    public void setRotation(KarkenVector3f rotation) {
        this.rotation = rotation;
    }

    public KarkenVector3f getSize() {
        return size;
    }

    public void setSize(KarkenVector3f size) {
        this.size = size;
    }
}
