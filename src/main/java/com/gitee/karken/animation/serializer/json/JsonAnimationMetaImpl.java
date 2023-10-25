package com.gitee.karken.animation.serializer.json;

import com.gitee.karken.animation.serializer.AbstractAnimationMetaImpl;
import com.gitee.karken.animation.serializer.AnimationMetaBone;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class JsonAnimationMetaImpl extends AbstractAnimationMetaImpl implements JsonAnimationMeta {

    public String id;

    public double length;

    public Map<String, AnimationMetaBone> bones = new HashMap<>();

    public JsonAnimationMetaImpl(String id) {
        this.id = id;
    }

    @Override
    public void read(JsonObject object) {
        this.length = object.get("animation_length").getAsDouble();
        object.getAsJsonObject("bones").entrySet().forEach(entry -> {
            this.bones.put(entry.getKey(),JsonAnimationBone.create(entry.getKey(),entry.getValue().getAsJsonObject()));
        });
    }

    public static JsonAnimationMetaImpl create(String id, JsonObject object) {
        JsonAnimationMetaImpl animation = new JsonAnimationMetaImpl(id);
        animation.read(object);
        return animation;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public double length() {
        return length;
    }

    @Override
    public Map<String, AnimationMetaBone> bones() {
        return bones;
    }

    @Override
    public String toString() {
        return "JsonAnimationImpl{" +
                "id='" + id + '\'' +
                ", length=" + length +
                ", bones=" + bones +
                '}';
    }
}
