package com.gitee.karken.core.player.serializer.json;

import com.gitee.karken.core.player.serializer.AnimatedUV;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.Direction;

import java.util.Locale;
import java.util.Map;

public class JsonAnimatedUV {

    static AnimatedUV parse(JsonElement element) {
        if (element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            return new AnimatedUV.Box(array.get(0).getAsDouble(), array.get(1).getAsDouble());
        }
        // face
        else if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            Map<Direction, AnimatedUV.Texture> map = Maps.newEnumMap(Direction.class);
            for (String s : object.keySet()) {
                map.put(Direction.valueOf(s.toUpperCase()), parseTexture(object.getAsJsonObject(s)));
            }
            return new AnimatedUV.Quad(map);
        }
        return null;
    }

    static AnimatedUV.Texture parseTexture(JsonObject object) {
        JsonArray uv = object.getAsJsonArray("uv");
        JsonArray size = object.getAsJsonArray("uv_size");
        return new AnimatedUV.Texture(
                uv.get(0).getAsDouble(),
                uv.get(1).getAsDouble(),
                size.get(0).getAsDouble(),
                size.get(1).getAsDouble()
        );
    }


}
