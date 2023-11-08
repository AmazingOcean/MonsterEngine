package com.gitee.karken.core.player;

import com.gitee.karken.core.player.serializer.AnimatedBone;
import com.gitee.karken.util.vector.KarkenVector3f;

import java.util.List;

public class KarkenAnimatedBone {

    private String name;

    private KarkenAnimatedBone parent;

    private KarkenVector3f rotation;

    private KarkenVector3f position;

    private KarkenVector3f scale;

    // 子部件
    private List<KarkenAnimatedBone> children;

    private List<KarkenAnimatedCube> cubes;

    private float inflation;

    public KarkenAnimatedBone(String name,KarkenAnimatedBone parent, float inflation) {
        this.name = name;
        this.parent = parent;
        this.inflation = inflation;
    }

    public String getName() {
        return name;
    }

    // 矩形块

    public KarkenVector3f getRotation() {
        return rotation;
    }

    public void setRotation(KarkenVector3f rotation) {
        this.rotation = rotation;
    }

    public KarkenVector3f getPosition() {
        return position;
    }

    public void setPosition(KarkenVector3f position) {
        this.position = position;
    }

    public KarkenVector3f getScale() {
        return scale;
    }

    public void setScale(KarkenVector3f scale) {
        this.scale = scale;
    }

    public List<KarkenAnimatedBone> getChildren() {
        return children;
    }

    public void setChildren(List<KarkenAnimatedBone> children) {
        this.children = children;
    }

    public List<KarkenAnimatedCube> getCubes() {
        return cubes;
    }

    public void setCubes(List<KarkenAnimatedCube> cubes) {
        this.cubes = cubes;
    }

    public KarkenAnimatedBone getParent() {
        return parent;
    }

    public float getInflation() {
        return inflation;
    }

    public void setInflation(float inflation) {
        this.inflation = inflation;
    }
}
