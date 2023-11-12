package com.gitee.karken.mixin;


import com.gitee.karken.core.animation.AnimationController;
import com.gitee.karken.core.animation.DefaultAnimationController;
import com.gitee.karken.core.player.KarkenAnimatedBone;
import com.gitee.karken.core.player.KarkenAnimatedCube;
import com.gitee.karken.core.player.KarkenAnimatedModel;
import com.gitee.karken.core.player.serializer.*;
import com.gitee.karken.util.KarkenJson;
import com.gitee.karken.util.vector.KarkenVector3f;
import com.gitee.karken.util.vertex.KarkenQuad;
import com.gitee.karken.util.vertex.KarkenVertex;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;

import java.util.EnumSet;

@Mixin(PlayerRenderer.class)
public abstract class KarkenPlayerRenderMixin
        extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    private AnimationController controller = new DefaultAnimationController();

    private KarkenAnimatedModel animatedModel;

    public KarkenPlayerRenderMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> entityModel, float f) throws IllegalAccessException {
        super(context, entityModel, f);
    }

    private void prepareRender() {
        if (animatedModel == null) {
            try {
                this.animatedModel = new KarkenAnimatedModel(AnimatedSerializer.getAnimatedModel("player"));
                System.out.println(KarkenJson.GSON.toJson(animatedModel));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void render(AbstractClientPlayer clientPlayer, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
        Direction direction;
        poseStack.pushPose();
        this.model.attackTime = this.getAttackAnim(clientPlayer, partialTick);
        this.model.riding = clientPlayer.isPassenger();
        this.model.young = clientPlayer.isBaby();
        float lerpBodyRot = Mth.rotLerp(partialTick, clientPlayer.yBodyRotO, clientPlayer.yBodyRot);
        float lerpHeadRot = Mth.rotLerp(partialTick, clientPlayer.yHeadRotO, clientPlayer.yHeadRot);
        float netHeadYaw = lerpHeadRot - lerpBodyRot;
        if (clientPlayer.isPassenger() && clientPlayer.getVehicle() instanceof LivingEntity entity) {
            lerpBodyRot = Mth.rotLerp(partialTick, entity.yBodyRotO, entity.yBodyRot);
            netHeadYaw = lerpHeadRot - lerpBodyRot;
            float clampedHeadYaw = Mth.clamp(Mth.wrapDegrees(netHeadYaw), -85, 85);
            ;
            lerpBodyRot = lerpHeadRot - clampedHeadYaw;

            if (clampedHeadYaw * clampedHeadYaw > 2500.0f) {
                lerpBodyRot += clampedHeadYaw * 0.2f;
            }

            netHeadYaw = lerpHeadRot - lerpBodyRot;
        }

        float headPitch = Mth.lerp(partialTick, clientPlayer.xRotO, clientPlayer.getXRot());
        if (LivingEntityRenderer.isEntityUpsideDown(clientPlayer)) {
            headPitch *= -1.0f;
            netHeadYaw *= -1.0f;
        }
        // 如果是睡觉的状态
        if (clientPlayer.hasPose(Pose.SLEEPING) && (direction = clientPlayer.getBedOrientation()) != null) {
            float eyePosOffset = clientPlayer.getEyeHeight(Pose.STANDING) - 0.1F;
            poseStack.translate((float) (-direction.getStepX()) * eyePosOffset, 0.0f, (float) (-direction.getStepZ()) * eyePosOffset);
        }
        float ageInTicks = this.getBob(clientPlayer, partialTick);
        this.setupRotations(clientPlayer, poseStack, ageInTicks, lerpBodyRot, partialTick);
        poseStack.scale(-1.0f, -1.0f, 1.0f);
        this.scale(clientPlayer, poseStack, partialTick);
        poseStack.translate(0.0f, -1.501f, 0.0f);
        float limbSwingAmount = 0.0f;
        float limbSwing = 0.0f;
        if (!clientPlayer.isPassenger() && clientPlayer.isAlive()) {
            limbSwingAmount = clientPlayer.walkAnimation.speed(partialTick);
            limbSwing = clientPlayer.walkAnimation.position(partialTick);
            if (clientPlayer.isBaby()) {
                limbSwing *= 3.0f;
            }
            if (limbSwingAmount > 1.0f) {
                limbSwingAmount = 1.0f;
            }
        }
        this.model.prepareMobModel(clientPlayer, limbSwing, limbSwingAmount, partialTick);
        this.model.setupAnim(clientPlayer, limbSwing, limbSwingAmount, limbSwingAmount, netHeadYaw, headPitch);
        Minecraft minecraft = Minecraft.getInstance();
        boolean bl = this.isBodyVisible(clientPlayer);
        boolean bl2 = !bl && !clientPlayer.isInvisibleTo(minecraft.player);
        boolean bl3 = minecraft.shouldEntityAppearGlowing(clientPlayer);
        float alpha = bl2 ? 0.15f : 1.0f;
        RenderType renderType = this.getRenderType(clientPlayer, bl, bl2, bl3);
//        if (renderType != null && animatedModel != null) {
//            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(renderType);
//            int p = LivingEntityRenderer.getOverlayCoords(clientPlayer, this.getWhiteOverlayProgress(clientPlayer, partialTick));
//            renderToBuffer(poseStack, vertexConsumer, packedLight, p, 1.0f, 1.0f, 1.0f, bl2 ? 0.15f : 1.0f);
//        }
        prepareRender();
        // 渲染模型
        if (renderType != null && animatedModel != null) {
            int packedOverlay = LivingEntityRenderer.getOverlayCoords(clientPlayer, this.getWhiteOverlayProgress(clientPlayer, partialTick));
            KarkenAnimatedModel.Properties properties = new KarkenAnimatedModel.Properties(renderType, multiBufferSource, entityYaw, packedOverlay, packedLight, partialTick, 1.0f, 1.0f, 1.0f, alpha);
            animatedModel.render(clientPlayer, properties, poseStack);
        }
        if (!((Entity) clientPlayer).isSpectator()) {
            for (RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderLayer : this.layers) {
                renderLayer.render(poseStack, multiBufferSource, packedLight, clientPlayer, limbSwing, limbSwingAmount, partialTick, ageInTicks, netHeadYaw, headPitch);
            }
        }
        poseStack.popPose();
        if (!this.shouldShowName(clientPlayer)) {
            return;
        }
        this.renderNameTag(clientPlayer, clientPlayer.getDisplayName(), poseStack, multiBufferSource, packedLight);
    }


    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float f, float g, float h, float k) {
        model.headParts().forEach(modelPart -> render(modelPart,poseStack, vertexConsumer, packedLight, packedOverlay, f, g, h, k));
        model.bodyParts().forEach(modelPart -> render(modelPart,poseStack, vertexConsumer, packedLight, packedOverlay, f, g, h, k));
    }

    public void render(ModelPart modelPart,PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, float f, float g, float h, float k) {
//        if (!modelPart.visible) {
//            return;
//        }
//        if (modelPart.cubes.isEmpty() && modelPart.children.isEmpty()) {
//            return;
//        }
        poseStack.pushPose();
        modelPart.translateAndRotate(poseStack);
        compile(modelPart,poseStack.last(), vertexConsumer, i, j, f, g, h, k);
        for (ModelPart child : modelPart.children.values()) {
            render(child,poseStack, vertexConsumer, i, j, f, g, h, k);
        }
        poseStack.popPose();
    }

    private void compile(ModelPart modelPart,PoseStack.Pose pose, VertexConsumer vertexConsumer, int i, int j, float f, float g, float h, float k) {
        for (ModelPart.Cube cube : modelPart.cubes) {
            compile(cube,pose, vertexConsumer, i, j, f, g, h, k);
        }
    }

    public void compile(ModelPart.Cube cube,PoseStack.Pose pose, VertexConsumer vertexConsumer, int i, int j, float f, float g, float h, float k) {
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        for (ModelPart.Polygon polygon : cube.polygons) {
            Vector3f vector3f = matrix3f.transform(new Vector3f(polygon.normal));
            float l = vector3f.x();
            float m = vector3f.y();
            float n = vector3f.z();
            for (ModelPart.Vertex vertex : polygon.vertices) {
                float o = vertex.pos.x() / 16.0f;
                float p = vertex.pos.y() / 16.0f;
                float q = vertex.pos.z() / 16.0f;
                Vector4f vector4f = matrix4f.transform(new Vector4f(o, p, q, 1.0f));
                vertexConsumer.vertex(vector4f.x(), vector4f.y(), vector4f.z(), f, g, h, k, vertex.u, vertex.v, j, i, l, m, n);
            }
        }
    }


    //    @Inject(method = "setupRotations(Lnet/minecraft/client/player/AbstractClientPlayer;Lcom/mojang/blaze3d/vertex/PoseStack;FFF)V", at = @At("RETURN"))
//    private void applyBodyTransforms(AbstractClientPlayer abstractClientPlayerEntity, PoseStack matrixStack, float f, float bodyYaw, float tickDelta, CallbackInfo ci) {
//        KarkenAnimatedHumanoid humanoid = (KarkenAnimatedHumanoid) abstractClientPlayerEntity;
//        if (humanoid.getAnimationController().getAnimation() != null) {
//            humanoid.getAnimationController().getAnimation().setTickDelta(tickDelta);
//        }
//
//    }

}
