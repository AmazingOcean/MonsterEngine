package com.gitee.karken.core.animation.loop.synchronizer;

import com.gitee.karken.core.player.bone.MinecraftBoneType;
import com.gitee.karken.core.player.KarkenAnimatedHumanoid;
import net.minecraft.client.model.HumanoidModel;

import java.util.Arrays;
import java.util.List;

public class AnimationSynchronizerLoader {


    private static List<AnimationSynchronizer> synchronizers = Arrays.asList(
            new AnimationRotationSync(), new AnimationPositionSync()
    );

    public static void render(KarkenAnimatedHumanoid humanoid, HumanoidModel<?> player, MinecraftBoneType type) {
        synchronizers.forEach(animationSynchronizer -> animationSynchronizer.render(humanoid, player, type));
    }


}
