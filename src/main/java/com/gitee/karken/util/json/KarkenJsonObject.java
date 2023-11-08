package com.gitee.karken.util.json;

import com.gitee.karken.util.KarkenJson;
import com.gitee.karken.util.vector.KarkenVector3d;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.Optional;

public class KarkenJsonObject {

    public JsonObject object;

    public KarkenJsonObject(JsonObject object) {
        this.object = object;
    }

    public KarkenJsonElement get(String node) {
        if (object.has(node)) {
            return new KarkenJsonElement(object.get(node));
        }
        return new KarkenJsonElement(null);
    }

    public KarkenJsonArray getArray(String node) {
        return get(node).getArray();
    }

    public KarkenJsonObject getObject(String node) {
        return get(node).getObject();
    }

    public double getDouble(String node) {
        return getDoubleElse(node, 0.0);
    }

    public double getDoubleElse(String node, double defaultValue) {
        return get(node).getDoubleElse(defaultValue);
    }

    public float getFloat(String node) {
        return getFloatElse(node, 0f);
    }

    public float getFloatElse(String node, float defaultValue) {
        return get(node).getFloatElse(defaultValue);
    }

    public String getString(String node) {
        return getStringElse(node, null);
    }

    public String getStringElse(String node, String defaultValue) {
        return get(node).getStringOrElse(defaultValue);
    }

}
