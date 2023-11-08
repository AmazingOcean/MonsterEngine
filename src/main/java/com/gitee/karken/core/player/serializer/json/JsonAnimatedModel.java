package com.gitee.karken.core.player.serializer.json;

import com.gitee.karken.core.player.serializer.AnimatedBone;
import com.gitee.karken.core.player.serializer.AnimatedDescription;
import com.gitee.karken.core.player.serializer.AnimatedModel;
import com.gitee.karken.util.json.KarkenJsonElement;
import com.gitee.karken.util.json.KarkenJsonObject;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

public class JsonAnimatedModel implements AnimatedModel {

    private String version;

    private AnimatedDescription animatedDescription;

    private List<AnimatedBone> bones = Lists.newArrayList();

    public JsonAnimatedModel(KarkenJsonObject object) {
        this.version = object.getString("format_version");
        // get "minecraft:geometry" index of 0
        object = object.getArray("minecraft:geometry").get(0).getObject();
        this.animatedDescription = new JsonAnimatedDescription(object.getObject("description"));
        for (KarkenJsonElement element : object.getArray("bones").getValues()) {
            this.bones.add(new JsonAnimatedBone(element.getObject()));
        }
    }

    @Override
    public String version() {
        return version;
    }

    @Override
    public List<AnimatedBone> bones() {
        return bones;
    }

    @Override
    public AnimatedDescription description() {
        return animatedDescription;
    }

    @Override
    public String toString() {
        return "JsonAnimatedModel{" +
                "version='" + version + '\'' +
                ", animatedDescription=" + animatedDescription +
                ", bones=" + bones +
                '}';
    }
}
