package com.gitee.karken.util;
import com.gitee.karken.core.player.KarkenAnimatedBone;
import com.gitee.karken.core.player.KarkenAnimatedCube;
import com.gitee.karken.core.player.bone.KarkenBone;
import com.gitee.karken.util.vector.KarkenVector3f;
import com.gitee.monsterengine.MonsterEngine;
import com.mojang.blaze3d.Blaze3D;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
/**
 * Helper class for various methods and functions useful while rendering
 */
public final class RenderUtils {
    public static void translateMatrixToBone(PoseStack poseStack, KarkenAnimatedBone bone) {
        poseStack.translate(-bone.getPosition().getX()/ 16f, bone.getPosition().getY() / 16f, bone.getPosition().getZ() / 16f);
    }

    public static void rotateMatrixAroundBone(PoseStack poseStack, KarkenAnimatedBone bone) {
        if (bone.getRotation().getZ() != 0)
            poseStack.mulPose(Axis.ZP.rotation(bone.getRotation().getZ()));

        if (bone.getRotation().getY() != 0)
            poseStack.mulPose(Axis.YP.rotation(bone.getRotation().getY()));

        if (bone.getRotation().getX() != 0)
            poseStack.mulPose(Axis.XP.rotation(bone.getRotation().getX()));
    }

    public static void rotateMatrixAroundCube(PoseStack poseStack, KarkenAnimatedCube cube) {
        Vec3 rotation = cube.getRotation().newVec3();

        poseStack.mulPose(new Quaternionf().rotationXYZ(0, 0, (float)rotation.z()));
        poseStack.mulPose(new Quaternionf().rotationXYZ(0, (float)rotation.y(), 0));
        poseStack.mulPose(new Quaternionf().rotationXYZ((float)rotation.x(), 0, 0));
    }

    public static void scaleMatrixForBone(PoseStack poseStack, KarkenAnimatedBone bone) {
        poseStack.scale(bone.getScale().getX(), bone.getScale().getY(), bone.getScale().getZ());
    }

    public static void translateToPivotPoint(PoseStack poseStack, KarkenAnimatedCube cube) {
        Vec3 pivot = cube.getPivot().newVec3();
        poseStack.translate(pivot.x() / 16f, pivot.y() / 16f, pivot.z() / 16f);
    }

    public static void translateToPivotPoint(PoseStack poseStack, KarkenAnimatedBone bone) {
        poseStack.translate(bone.getPivot().getX() / 16f, bone.getPivot().getY() / 16f, bone.getPivot().getZ() / 16f);
    }

    public static void translateAwayFromPivotPoint(PoseStack poseStack, KarkenAnimatedCube cube) {
        Vec3 pivot = cube.getPivot().newVec3();

        poseStack.translate(-pivot.x() / 16f, -pivot.y() / 16f, -pivot.z() / 16f);
    }

    public static void translateAwayFromPivotPoint(PoseStack poseStack, KarkenAnimatedBone bone) {
        poseStack.translate(-bone.getPivot().getX() / 16f, -bone.getPivot().getY() / 16f, -bone.getPivot().getZ() / 16f);
    }

    public static void translateAndRotateMatrixForBone(PoseStack poseStack, KarkenAnimatedBone bone) {
        translateToPivotPoint(poseStack, bone);
        rotateMatrixAroundBone(poseStack, bone);
    }

    public static void prepMatrixForBone(PoseStack poseStack, KarkenAnimatedBone bone) {
        translateMatrixToBone(poseStack, bone);
        translateToPivotPoint(poseStack, bone);
        rotateMatrixAroundBone(poseStack, bone);
        scaleMatrixForBone(poseStack, bone);
        translateAwayFromPivotPoint(poseStack, bone);
    }

    public static Matrix4f invertAndMultiplyMatrices(Matrix4f baseMatrix, Matrix4f inputMatrix) {
        inputMatrix = new Matrix4f(inputMatrix);

        inputMatrix.invert();
        inputMatrix.mul(baseMatrix);

        return inputMatrix;
    }

    /**
     * Translates the provided {@link PoseStack} to face towards the given {@link Entity}'s rotation.<br>
     */
    public static void faceRotation(PoseStack poseStack, Entity animatable, float partialTick) {
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, animatable.yRotO, animatable.getYRot()) - 90));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, animatable.xRotO, animatable.getXRot())));
    }

    /**
     * Add a positional vector to a matrix.
     * This is specifically implemented to act as a translation of an x/y/z coordinate triplet to a render matrix
     */
    public static Matrix4f translateMatrix(Matrix4f matrix, Vector3f vector) {
        return matrix.add(new Matrix4f().m30(vector.x).m31(vector.y).m32(vector.z));
    }

    /**
     * Gets the actual dimensions of a texture resource from a given path.<br>
     * Not performance-efficient, and should not be relied upon
     * @param texture The path of the texture resource to check
     * @return The dimensions (width x height) of the texture, or null if unable to find or read the file
     */
    public static IntIntPair getTextureDimensions(ResourceLocation texture) {
        if (texture == null)
            return null;

        AbstractTexture originalTexture = null;
        Minecraft mc = Minecraft.getInstance();

        try {
            originalTexture = mc.submit(() -> mc.getTextureManager().getTexture(texture)).get();
        }
        catch (Exception e) {
            MonsterEngine.getLogger().warn("Failed to load image for id {}", texture);
            e.printStackTrace();
        }

        if (originalTexture == null)
            return null;

        NativeImage image = null;

        try {
            image = originalTexture instanceof DynamicTexture dynamicTexture ? dynamicTexture.getPixels()
                    : NativeImage.read(mc.getResourceManager().getResource(texture).get().open());
        }
        catch (Exception e) {
            MonsterEngine.getLogger().error("Failed to read image for id {}", texture);
            e.printStackTrace();
        }

        return image == null ? null : IntIntImmutablePair.of(image.getWidth(), image.getHeight());
    }

    public static double getCurrentSystemTick() {
        return System.nanoTime() / 1E6 / 50d;
    }

    /**
     * Returns the current time (in ticks) that the {@link org.lwjgl.glfw.GLFW GLFW} instance has been running.
     * This is effectively a permanent timer that counts up since the game was launched.
     */
    public static double getCurrentTick() {
        return Blaze3D.getTime() * 20d;
    }

    /**
     * Returns a float equivalent of a boolean.<br>
     * Output table:
     * <ul>
     *     <li>true -> 1</li>
     *     <li>false -> 0</li>
     * </ul>
     */
    public static float booleanToFloat(boolean input) {
        return input ? 1f : 0f;
    }

    /**
     * Converts a given double array to its {@link Vec3} equivalent
     */
    public static Vec3 arrayToVec(double[] array) {
        return new Vec3(array[0], array[1], array[2]);
    }

    /**
     * Usually used for items or armor rendering to match the rotations of other non-geo model parts.
     */
    public static void matchModelPartRot(ModelPart from, KarkenAnimatedBone to) {
        to.setRotation(new KarkenVector3f(-from.xRot, -from.yRot, from.zRot));
    }

    /**
     * normal is inverted in an intersecting plane,it can cause issues with shaders and other lighting tasks.<br>
     * This performs a pseudo-ABS function to help resolve some of those issues.
     */
    public static void fixInvertedFlatCube(KarkenAnimatedCube cube, Vector3f normal) {
        if (normal.x() < 0 && (cube.getSize().getY() == 0 || cube.getSize().getZ() == 0))
            normal.mul(-1, 1, 1);

        if (normal.y() < 0 && (cube.getSize().getX() == 0 || cube.getSize().getZ() == 0))
            normal.mul(1, -1, 1);

        if (normal.z() < 0 && (cube.getSize().getX() == 0 || cube.getSize().getY() == 0))
            normal.mul(1, 1, -1);
    }

    /**
     * Converts a {@link Direction} to a rotational float for rotation purposes
     */
    public static float getDirectionAngle(Direction direction) {
        return switch(direction) {
            case SOUTH -> 90f;
            case NORTH -> 270f;
            case EAST -> 180f;
            default -> 0f;
        };
    }

}
