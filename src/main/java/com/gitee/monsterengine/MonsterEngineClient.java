package com.gitee.monsterengine;

import com.gitee.karken.animation.loader.DefaultedAnimationLoader;
import com.gitee.karken.serializer.json.JsonAnimationHelper;
import net.fabricmc.api.ClientModInitializer;

public class MonsterEngineClient implements ClientModInitializer {

    public String content = "{\"format_version\":\"1.8.0\",\"animations\":{\"test\":{\"loop\":true,\"animation_length\":1.04,\"bones\":{\"right_arm\":{\"rotation\":{\"0.0\":{\"vector\":[0,0,157.5]},\"0.24\":{\"vector\":[-50,0,88.26923]},\"0.52\":{\"vector\":[0,0,7.5]},\"0.76\":{\"vector\":[30,0,76.73077]},\"1.04\":{\"vector\":[0,0,157.5]}}},\"left_arm\":{\"rotation\":{\"0.0\":{\"vector\":[0,0,-155]},\"0.24\":{\"vector\":[-50.5,0,-86.92308]},\"0.52\":{\"vector\":[0,0,-7.5]},\"0.76\":{\"vector\":[22.5,0,-75.57692]},\"1.04\":{\"vector\":[0,0,-155]}}},\"right_leg\":{\"rotation\":{\"0.0\":{\"vector\":[-45,0,0]},\"0.24\":{\"vector\":[41,0,0]},\"0.52\":{\"vector\":[-45,0,0]},\"0.76\":{\"vector\":[41,0,0]},\"1.04\":{\"vector\":[-45,0,0]}}},\"left_leg\":{\"rotation\":{\"0.0\":{\"vector\":[40,0,0]},\"0.24\":{\"vector\":[-42.5,0,0]},\"0.52\":{\"vector\":[40,0,0]},\"0.76\":{\"vector\":[-42.5,0,0]},\"1.04\":{\"vector\":[40,0,0]}}}}}},\"geckolib_format_version\":2}";

    @Override
    public void onInitializeClient() {
        DefaultedAnimationLoader.INSTANCE.register("test", JsonAnimationHelper.createAnimationStructure(content));
    }


}
