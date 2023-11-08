package com.gitee.karken.mixin;

import com.gitee.karken.core.animation.loop.synchronizer.AnimationSynchronizerLoader;
import com.gitee.karken.core.player.bone.MinecraftBoneType;
import com.gitee.karken.core.player.KarkenAnimatedHumanoid;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Function;

@Mixin(PlayerModel.class)
public abstract class KarkenPlayerModelMixin<T extends LivingEntity> extends HumanoidModel<T> {



    public KarkenPlayerModelMixin(ModelPart modelPart, Function<ResourceLocation, RenderType> function) {
        super(modelPart, function);
        System.out.println("[KarkenPlayerModelMixin] ===========");
    }

//    @Override
//    public void setupAnim(T livingEntity, float f, float g, float h, float i, float j) {
//        this.setDefaultBone();
//        super.setupAnim(livingEntity, f, g, h, i, j);
//        KarkenAnimatedHumanoid humanoid = getKarkenAnimatedhumanoid(livingEntity);
//        for (MinecraftBoneType type : MinecraftBoneType.values()) {
//            AnimationSynchronizerLoader.render(humanoid,this,type);
//        }
//    }

    @Unique
    public void setDefaultBone(){
        this.leftLeg.setPos(1.9F, 12.0F, 0.0F);
        this.rightLeg.setPos(- 1.9F, 12.0F, 0.0F);
        this.head.setPos(0.0F, 0.0F, 0.0F);
        this.rightArm.z = 0.0F;
        this.rightArm.x = - 5.0F;
        this.leftArm.z = 0.0F;
        this.leftArm.x = 5.0F;
        this.body.xRot = 0.0F;
        this.rightLeg.z = 0.1F;
        this.leftLeg.z = 0.1F;
        this.rightLeg.y = 12.0F;
        this.leftLeg.y = 12.0F;
        this.head.y = 0.0F;
        this.head.zRot = 0f;
        this.body.y = 0.0F;
        this.body.x = 0f;
        this.body.z = 0f;
        this.body.yRot = 0;
        this.body.zRot = 0;
    }



}
