package com.gitee.karken.core.player;

import com.gitee.karken.core.animation.AnimationController;
import com.gitee.karken.core.animation.DefaultAnimationController;
import com.gitee.karken.core.player.serializer.AnimatedModel;

public interface KarkenAnimatedHumanoid {

    AnimatedModel getAnimatedModel();

    AnimationController getAnimationController();

}
