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

    private KarkenVector3d pivot;

    private KarkenVector3d rotation;

    private KarkenVector3d size;

    public KarkenAnimatedCube(EnumMap<Direction, KarkenQuad> karkenQuads, KarkenVector3d pivot, KarkenVector3d rotation, KarkenVector3d size) {
        this.karkenQuads = karkenQuads;
        this.pivot = pivot;
        this.rotation = rotation;
        this.size = size;
    }

    public EnumMap<Direction, KarkenQuad> getKarkenQuads() {
        return karkenQuads;
    }

    public KarkenVector3d getPivot() {
        return pivot;
    }

    public void setPivot(KarkenVector3d pivot) {
        this.pivot = pivot;
    }

    public KarkenVector3d getRotation() {
        return rotation;
    }

    public void setRotation(KarkenVector3d rotation) {
        this.rotation = rotation;
    }

    public KarkenVector3d getSize() {
        return size;
    }

    public void setSize(KarkenVector3d size) {
        this.size = size;
    }
}
