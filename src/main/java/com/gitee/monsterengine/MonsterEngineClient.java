package com.gitee.monsterengine;

import com.gitee.karken.animation.loader.DefaultedAnimationLoader;
import com.gitee.karken.animation.serializer.json.JsonAnimationHelper;
import net.fabricmc.api.ClientModInitializer;

public class MonsterEngineClient implements ClientModInitializer {

    public String content = "{\"format_version\":\"1.8.0\",\"animations\":{\"test\":{\"loop\":true,\"animation_length\":0.5,\"bones\":{\"right_arm\":{\"rotation\":{\"0.0\":[-22.5,0,0],\"0.25\":[-125.12353,7.24305,-1.95155],\"0.5\":[-22.5,0,0]},\"position\":{\"0.0\":[0,-2,0],\"0.25\":[0,-1,0],\"0.5\":[0,-2,0]}}}}}}";

    @Override
    public void onInitializeClient() {
        DefaultedAnimationLoader.INSTANCE.register("test", JsonAnimationHelper.createAnimationStructure(content));

    }


}
