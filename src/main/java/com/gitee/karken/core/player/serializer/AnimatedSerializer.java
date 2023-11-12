package com.gitee.karken.core.player.serializer;

import com.gitee.karken.core.Cube;
import com.gitee.karken.core.player.KarkenAnimatedModel;
import com.gitee.karken.core.player.serializer.json.JsonAnimatedModel;
import com.gitee.karken.util.KarkenJson;
import com.gitee.monsterengine.MonsterEngine;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import java.util.EnumSet;
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
        try {
            KarkenAnimatedModel animatedModel = new KarkenAnimatedModel(model);
            System.out.println(KarkenJson.GSON.toJson(animatedModel));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

//        MeshDefinition meshDefinition = new MeshDefinition();
//        PartDefinition partDefinition = meshDefinition.getRoot();
//        // {"origin": [-4, 8, -4], "size": [8, 8, 8], "uv": [0, 0]}
//        CubeListBuilder builder = CubeListBuilder
//                .create()
//                .texOffs(0, 0)
//                .addBox(-4f, 8.0f, -4f, 8.0f, 8.0f, 8.0f, CubeDeformation.NONE);
//        partDefinition.addOrReplaceChild("head", builder, PartPose.offset(0f, 0f, 0f));
//        ModelPart modelPart = partDefinition.bake(64, 64);
//        System.out.println(KarkenJson.GSON.toJson(modelPart.getChild("head")));
//
//        Cube cube = new Cube(0, 0, -4, 8, -4, 8, 8, 8, 0, 0, 0, false, 64, 64, EnumSet.allOf(Direction.class));
//        System.out.println(KarkenJson.GSON.toJson(cube));

    }

    public static AnimatedModel getAnimatedModel(String id) {
        String path = "models/entity/" + id + ".geo.json";
        ResourceLocation location = new ResourceLocation(MonsterEngine.MOD_ID, path);
        return map.get(location);
    }


}
