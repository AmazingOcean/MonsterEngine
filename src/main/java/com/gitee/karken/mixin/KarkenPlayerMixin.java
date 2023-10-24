package com.gitee.karken.mixin;

import com.gitee.karken.animation.AnimationController;
import com.gitee.karken.animation.DefaultAnimationController;
import com.gitee.karken.player.KarkenAnimatedHumanoid;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class KarkenPlayerMixin implements KarkenAnimatedHumanoid {

    private AnimationController controller = new DefaultAnimationController();

    @Override
    public AnimationController getAnimationController() {
        return controller;
    }

    @SuppressWarnings("ConstantConditions")
    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (AbstractClientPlayer.class.isInstance(this)) {
            getAnimationController().onTick();
        }
    }

}
