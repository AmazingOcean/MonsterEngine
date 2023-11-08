package com.gitee.karken.core.player.serializer.json;

import com.gitee.karken.core.player.serializer.AnimatedCube;
import com.gitee.karken.core.player.serializer.AnimatedUV;
import com.gitee.karken.util.KarkenJson;
import com.gitee.karken.util.json.KarkenJsonObject;
import com.gitee.karken.util.vector.KarkenVector3d;
import com.google.gson.JsonObject;

public class JsonAnimatedCube implements AnimatedCube {

    private KarkenVector3d origin;

    private KarkenVector3d size;

    private KarkenVector3d pivot;

    private KarkenVector3d rotation;

    private AnimatedUV animatedUV;

    // 镜像
    private boolean mirror;

    private double inflate;

    public JsonAnimatedCube(KarkenJsonObject object) {
        this.origin = object.getArray("origin").getKarkenVector3d();
        this.size = object.getArray("size").getKarkenVector3d();
        this.pivot = object.getArray("pivot").getKarkenVector3d();
        this.rotation = object.getArray("rotation").getKarkenVector3d();
        this.animatedUV = JsonAnimatedUV.parse(object.get("uv").getJsonElement());
        this.mirror = object.get("mirror").getBoolean();
        this.inflate = object.get("inflate").getDouble();
    }

    @Override
    public double inflate() {
        return inflate;
    }

    @Override
    public boolean mirror() {
        return mirror;
    }

    @Override
    public KarkenVector3d origin() {
        return origin;
    }

    @Override
    public KarkenVector3d size() {
        return size;
    }

    @Override
    public KarkenVector3d pivot() {
        return pivot;
    }

    @Override
    public KarkenVector3d rotation() {
        return rotation;
    }

    @Override
    public AnimatedUV uv() {
        return animatedUV;
    }

    @Override
    public String toString() {
        return "JsonAnimatedCube{" +
                "origin=" + origin +
                ", size=" + size +
                ", pivot=" + pivot +
                ", rotation=" + rotation +
                ", animatedUV=" + animatedUV +
                ", mirror=" + mirror +
                ", inflate=" + inflate +
                '}';
    }
}
