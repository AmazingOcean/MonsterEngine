package com.gitee.karken.animation.loop.synchronizer;

import com.gitee.karken.bone.MinecraftBoneType;
import com.gitee.karken.player.KarkenAnimatedHumanoid;
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
