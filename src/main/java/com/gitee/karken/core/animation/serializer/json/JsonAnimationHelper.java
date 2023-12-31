package com.gitee.karken.core.animation.serializer.json;

import com.gitee.karken.util.KarkenJson;
import com.gitee.karken.util.vector.KarkenMomentumTicks3f;
import com.gitee.karken.util.vector.KarkenVector3f;
import com.google.common.collect.Maps;
import com.google.gson.*;

import java.util.Map;

public class JsonAnimationHelper {

    public static JsonAnimationStructure createAnimationStructure(String content) {
        JsonAnimationStructure structure = new JsonAnimationStructure();
        structure.read(KarkenJson.GSON.fromJson(content, JsonObject.class));
        return structure;
    }

    public static KarkenMomentumTicks3f getKarkenMomentumTick3f(JsonElement element) {
        return new KarkenMomentumTicks3f(getKarkenVector3fMapWithFloat(element));
    }

    public static Map<Float, KarkenVector3f> getKarkenVector3fMapWithFloat(JsonElement element) {
        if (element == null) {
            return Maps.newTreeMap();
        }
        Map<Float, KarkenVector3f> vector3fMap = Maps.newTreeMap();
        getKarkenVector3fMap(element).forEach((s, karkenVector3f) -> vector3fMap.put(Float.parseFloat(s), karkenVector3f));
        return vector3fMap;
    }

    public static Map<String, KarkenVector3f> getKarkenVector3fMap(JsonElement element) {
        Map<String, KarkenVector3f> vector3fMap = Maps.newTreeMap();
        if (element instanceof JsonObject) {
            ((JsonObject) element).entrySet().forEach(entry -> {
                vector3fMap.put(entry.getKey(), getKarkenVector3f(entry.getValue()));
            });
        } else if (element instanceof JsonArray) {
            vector3fMap.put("0.0", getKarkenVector3f(element));
        }
        return vector3fMap;
    }

    /**
     * 根据JsonElement取一个变量
     *
     * @param element element
     * @return vector 3f
     */
    public static KarkenVector3f getKarkenVector3f(JsonElement element) {
        JsonArray array = parseJsonArray(element);
        return new KarkenVector3f(
                array.get(0).getAsFloat(),
                array.get(1).getAsFloat(),
                array.get(2).getAsFloat()
        );
    }

    private static JsonArray parseJsonArray(JsonElement element) {
        if (element.isJsonObject()) {
            return element.getAsJsonObject().get("vector").getAsJsonArray();
        } else {
            return element.getAsJsonArray();
        }
    }

}
