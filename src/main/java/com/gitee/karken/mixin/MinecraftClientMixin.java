package com.gitee.karken.mixin;

import com.gitee.karken.animation.AnimationController;
import com.gitee.karken.animation.JsonAnimation;
import com.gitee.karken.animation.loader.DefaultedAnimationLoader;
import com.gitee.karken.player.KarkenAnimatedHumanoid;
import com.gitee.karken.serializer.AnimationMetaImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {

    @Inject(method = "startAttack", at = @At("HEAD"), cancellable = true)
    private void ATTACK(CallbackInfoReturnable<Boolean> cir) {
        KarkenAnimatedHumanoid humanoid = getKarkenAnimatedhumanoid(Minecraft.getInstance().player);
        AnimationController controller = humanoid.getAnimationController();
        AnimationMetaImpl animation = Objects.requireNonNull(DefaultedAnimationLoader.INSTANCE.getAnimationStructure("test")).getAnimation("test");
        controller.set(new JsonAnimation(animation));
        assert animation != null;
        System.out.println("MinecraftClientMixin - startAttack " + animation.id());
        cir.setReturnValue(true);
    }


    public KarkenAnimatedHumanoid getKarkenAnimatedhumanoid(LivingEntity entity) {
        return ((KarkenAnimatedHumanoid) entity);
    }
}
