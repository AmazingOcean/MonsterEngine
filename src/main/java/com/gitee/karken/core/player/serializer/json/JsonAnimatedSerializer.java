package com.gitee.karken.core.player.serializer.json;

import com.gitee.karken.core.player.serializer.AnimatedSerializer;
import com.gitee.karken.util.KarkenJson;
import com.gitee.monsterengine.MonsterEngine;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class JsonAnimatedSerializer {


    public static JsonAnimatedModel loadFromResource(ResourceManager manager, ResourceLocation location) {
        String content;
        try (InputStream inputStream = manager.getResourceOrThrow(location).open()) {
            content = IOUtils.toString(inputStream, Charset.defaultCharset());
        } catch (Exception e) {
            MonsterEngine.getLogger().error("Couldn't load " + location, e);
            throw new RuntimeException(new FileNotFoundException(location.toString()));
        }
        return loadFromJson(content);
    }

    public static JsonAnimatedModel loadFromJson(String content) {
        return new JsonAnimatedModel(KarkenJson.parseJson(content).getObject());
    }


    public static void init() {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new SimpleResourceReloadable());
    }

    public static class SimpleResourceReloadable implements SimpleSynchronousResourceReloadListener {

        @Override
        public ResourceLocation getFabricId() {
            return new ResourceLocation(MonsterEngine.MOD_ID, "models");
        }


        @Override
        public void onResourceManagerReload(ResourceManager resourceManager) {
            Map<ResourceLocation, Resource> map = resourceManager.listResources("models/entity", r -> r.toString().endsWith("geo.json"));
            for (ResourceLocation resourceLocation : map.keySet()) {
                AnimatedSerializer.register(resourceLocation, loadFromResource(resourceManager, resourceLocation));
            }
        }
    }

}
