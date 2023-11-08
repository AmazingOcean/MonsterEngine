package com.gitee.karken.core.player.serializer;

import com.gitee.karken.core.player.serializer.json.JsonAnimatedModel;
import com.gitee.karken.util.KarkenJson;
import com.gitee.monsterengine.MonsterEngine;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class AnimatedSerializer {


    private static Map<ResourceLocation, AnimatedModel> map = new Object2ObjectOpenHashMap<>();

    public static AnimatedModel BIPED;

    public static AnimatedModel loadFromJson(String content) {
        return new JsonAnimatedModel(KarkenJson.parseJson(content).getObject());
    }


    public static void register(ResourceLocation resourceLocation, AnimatedModel model) {
        map.put(resourceLocation, model);
        System.out.println("[AnimatedSerializer] load " + resourceLocation + " model " + model);

    }

    public static AnimatedModel getAnimatedModel(String id) {
        String path = "models/entity" + id + ".geo.json";
        ResourceLocation location = new ResourceLocation(MonsterEngine.MOD_ID, path);
        return map.get(location);
    }


}
