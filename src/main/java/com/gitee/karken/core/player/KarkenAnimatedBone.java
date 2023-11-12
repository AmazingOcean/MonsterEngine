package com.gitee.karken.core.player;

import com.gitee.karken.core.player.serializer.AnimatedBone;
import com.gitee.karken.util.vector.KarkenVector3d;
import com.gitee.karken.util.vector.KarkenVector3f;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.model.geom.ModelPart;

import java.util.List;

public class KarkenAnimatedBone {

    private String name;

    private String parent;

    private KarkenVector3f rotation = new KarkenVector3f(0f,0f,0f);

    private KarkenVector3f pivot = new KarkenVector3f(0f,0f,0f);;

    private KarkenVector3f position = new KarkenVector3f(0f, 0f, 0f);

    private KarkenVector3f scale = new KarkenVector3f(1f, 1f, 1f);

    // 子部件
    private List<KarkenAnimatedBone> children = Lists.newArrayList();

    private List<KarkenAnimatedCube> cubes = Lists.newArrayList();

    private ModelPart minecraftModelPart;

    private float inflation;

    public KarkenAnimatedBone(String name, String parent, float inflation) {
        this.name = name;
        this.parent = parent;
        this.inflation = inflation;
    }

    public String getName() {
        return name;
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

    public String getParent() {
        return parent;
    }

    public float getInflation() {
        return inflation;
    }

    public void setInflation(float inflation) {
        this.inflation = inflation;
    }


    @Override
    public String toString() {
        return "KarkenAnimatedBone{" +
                "name='" + name + '\'' +
                ", parent=" + parent +
                ", rotation=" + rotation +
                ", pivot=" + pivot +
                ", position=" + position +
                ", scale=" + scale +
                ", children=" + children +
                ", cubes=" + cubes +
                ", inflation=" + inflation +
                '}';
    }
}
