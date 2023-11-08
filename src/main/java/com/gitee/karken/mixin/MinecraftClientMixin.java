package com.gitee.karken.mixin;

import com.gitee.karken.core.player.KarkenAnimatedHumanoid;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {

//    @Inject(method = "startAttack", at = @At("HEAD"), cancellable = true)
//    private void ATTACK(CallbackInfoReturnable<Boolean> cir) {
//        KarkenAnimatedHumanoid humanoid = getKarkenAnimatedhumanoid(Minecraft.getInstance().player);
//        AnimationController controller = humanoid.getAnimationController();
//        AnimationMetaImpl animation = Objects.requireNonNull(DefaultedAnimationLoader.INSTANCE.getAnimationStructure("test")).getAnimation("test");
//        controller.set(new JsonAnimation(animation));
//        assert animation != null;
//        System.out.println("MinecraftClientMixin - startAttack " + animation.id());
//        cir.setReturnValue(true);
//    }
//

}
