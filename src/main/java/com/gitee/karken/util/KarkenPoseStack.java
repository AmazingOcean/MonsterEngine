package com.gitee.karken.util;

import com.gitee.karken.util.vector.KarkenVector3f;
import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Quaternionf;

public class KarkenPoseStack {

    private PoseStack poseStack;

    public KarkenPoseStack(PoseStack poseStack) {
        this.poseStack = poseStack;
    }

    public KarkenPoseStack pushPose() {
        return thenAccept(() -> poseStack.pushPose());
    }

    public KarkenPoseStack popPose() {
        return thenAccept(() -> poseStack.popPose());
    }

    public KarkenPoseStack translate(KarkenVector3f vector3f) {
        return thenAccept(() -> poseStack.translate(vector3f.getX(), vector3f.getY(), vector3f.getZ()));
    }

    public KarkenPoseStack mulPose(Quaternionf quaternionf) {
        return thenAccept(() -> poseStack.mulPose(quaternionf));
    }

    public KarkenPoseStack scale(KarkenVector3f vector3f) {
        return thenAccept(() -> poseStack.scale(vector3f.getX(), vector3f.getY(), vector3f.getZ()));
    }

    public PoseStack getInstance() {
        return poseStack;
    }


    private KarkenPoseStack thenAccept(Runnable runnable) {
        runnable.run();
        return this;
    }

    public Pose last() {
        return new Pose(poseStack.last());
    }


    public static class Pose {

        private PoseStack.Pose pose;

        public Pose(PoseStack.Pose pose) {
            this.pose = pose;
        }

        public KarkenMatrix3f getNormal() {
            return new KarkenMatrix3f(pose.normal());
        }

        public KarkenMatrix4f getPose() {
            return new KarkenMatrix4f(pose.pose());
        }

    }


}
