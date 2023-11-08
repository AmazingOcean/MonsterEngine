package com.gitee.monsterengine;

import com.gitee.karken.core.player.serializer.AnimatedSerializer;
import com.gitee.karken.core.player.serializer.json.JsonAnimatedSerializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

public class MonsterEngine implements ModInitializer {

    public static String MOD_ID = "monsterengine";
    private static final Logger logger = LoggerFactory.getLogger("monsterengine");

    @Override
    public void onInitialize() {
        logger.info("[MonsterEngine] Hello Fabric world!");
        JsonAnimatedSerializer.init();
    }

    public static Logger getLogger() {
        return logger;
    }


}
