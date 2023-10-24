package com.gitee.karken.mixin;

import com.gitee.karken.animation.loop.AnimationSynchronizer;
import com.gitee.karken.bone.MinecraftBoneType;
import com.gitee.karken.player.KarkenAnimatedHumanoid;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Function;

@Mixin(PlayerModel.class)
public abstract class KarkenPlayerModelMixin<T extends LivingEntity> extends HumanoidModel<T> {

    public KarkenPlayerModelMixin(ModelPart modelPart, Function<ResourceLocation, RenderType> function) {
        super(modelPart, function);
    }

    @Override
    public void setupAnim(T livingEntity, float f, float g, float h, float i, float j) {
        super.setupAnim(livingEntity, f, g, h, i, j);
        KarkenAnimatedHumanoid humanoid = getKarkenAnimatedhumanoid(livingEntity);
        for (MinecraftBoneType type : MinecraftBoneType.values()) {
            AnimationSynchronizer.INSTANCE.update(humanoid,this,type);
        }
    }



    public KarkenAnimatedHumanoid getKarkenAnimatedhumanoid(LivingEntity entity) {
        return ((KarkenAnimatedHumanoid) entity);
    }
}
