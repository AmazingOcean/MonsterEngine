package com.gitee.karken.util;

import com.gitee.karken.util.json.KarkenJsonElement;
import com.gitee.karken.util.vector.KarkenVector3d;
import com.gitee.karken.util.vector.KarkenVector3f;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class KarkenJson {

    public static Gson GSON = new GsonBuilder().create();

    public static KarkenJsonElement parseJson(String json) {
        return new KarkenJsonElement(GSON.fromJson(json, JsonObject.class));
    }

    public static KarkenVector3f parseKarkenVector3f(JsonArray array) {
        if (array == null)
            return new KarkenVector3f(0f, 0f, 0f);

        return new KarkenVector3f(
                array.get(0).getAsFloat(),
                array.get(1).getAsFloat(),
                array.get(2).getAsFloat()
        );
    }

    public static KarkenVector3d parseKarkenVector3d(JsonArray array) {
        if (array == null)
            return new KarkenVector3d(0.0, 0.0, 0.0);

        return new KarkenVector3d(
                array.get(0).getAsDouble(),
                array.get(1).getAsDouble(),
                array.get(2).getAsDouble()
        );
    }


}
