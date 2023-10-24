package com.gitee.karken.serializer.json;

import com.gitee.karken.serializer.AbstractAnimationStructure;
import com.gitee.karken.serializer.AnimationMetaImpl;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class JsonAnimationStructure extends AbstractAnimationStructure implements JsonAnimationMeta {

    public String version;

    public Map<String,AnimationMetaImpl> animations = new HashMap<>();

    @Override
    public void read(JsonObject object) {
        this.version = object.get("format_version").getAsString();
        object.getAsJsonObject("animations").entrySet().forEach(entry -> {
            this.animations.put(entry.getKey(),JsonAnimationMetaImpl.create(entry.getKey(), entry.getValue().getAsJsonObject()));
        });
    }

    @Override
    public String version() {
        return version;
    }

    @Override
    public Map<String, AnimationMetaImpl> animationImpls() {
        return animations;
    }

    @Override
    public String toString() {
        return "JsonAnimationStructure{" +
                "version='" + version + '\'' +
                ", animations=" + animations +
                '}';
    }
}
