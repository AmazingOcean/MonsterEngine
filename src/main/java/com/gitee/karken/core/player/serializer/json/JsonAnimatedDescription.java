package com.gitee.karken.core.player.serializer.json;

import com.gitee.karken.core.player.serializer.AnimatedDescription;
import com.gitee.karken.util.json.KarkenJsonObject;
import com.google.gson.JsonObject;

public class JsonAnimatedDescription implements AnimatedDescription {

    private String identifier;

    private double textureWeight;

    private double textureHeight;


    public JsonAnimatedDescription(KarkenJsonObject object) {
        this.identifier = object.getString("identifier");
        this.textureWeight = object.getDouble("texture_width");
        this.textureHeight = object.getDouble("texture_height");
    }

    @Override
    public double textureWeight() {
        return textureWeight;
    }

    @Override
    public double textureHeight() {
        return textureHeight;
    }

    @Override
    public String identifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return "JsonAnimatedDescription{" +
                "identifier='" + identifier + '\'' +
                ", textureWeight=" + textureWeight +
                ", textureHeight=" + textureHeight +
                '}';
    }
}
