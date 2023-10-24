package com.gitee.karken.serializer.json;

import com.gitee.karken.serializer.AbstractAnimationMetaBone;
import com.gitee.karken.vector.KarkenMomentumTicks3f;
import com.google.gson.JsonObject;

public class JsonAnimationBone extends AbstractAnimationMetaBone implements JsonAnimationMeta {

    public String id;

    public KarkenMomentumTicks3f rotation;

    public KarkenMomentumTicks3f position;

    public KarkenMomentumTicks3f scale;

    public JsonAnimationBone(String id) {
        this.id = id;
    }

    @Override
    public void read(JsonObject object) {
        this.rotation = JsonAnimationHelper.getKarkenMomentumTick3f(object.get("rotation"));
        this.position = JsonAnimationHelper.getKarkenMomentumTick3f(object.get("position"));
        this.scale = JsonAnimationHelper.getKarkenMomentumTick3f(object.get("scale"));
    }

    public static JsonAnimationBone create(String id, JsonObject object) {
        JsonAnimationBone bone = new JsonAnimationBone(id);
        bone.read(object);
        return bone;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public KarkenMomentumTicks3f scale() {
        return scale;
    }

    @Override
    public KarkenMomentumTicks3f rotation() {
        return rotation;
    }

    @Override
    public KarkenMomentumTicks3f position() {
        return position;
    }

    @Override
    public String toString() {
        return "JsonAnimationBone{" +
                "id='" + id + '\'' +
                ", rotation=" + rotation +
                ", position=" + position +
                ", scale=" + scale +
                '}';
    }
}
