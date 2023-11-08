package com.gitee.karken.core.animation;

import com.gitee.karken.core.player.bone.KarkenBone;

public class DefaultAnimationController implements AnimationController {

    private Animation current;

    @Override
    public void onTick() {

        if (current == null) return;

        // 被关闭
        if (current.getLifecycle() == Animation.Lifecycle.CLOSED) {
            return;
        }
        if (current.getLifecycle() == Animation.Lifecycle.CREATED) {
            current.setLifecycle(Animation.Lifecycle.RUNNING);
        }
        if (!current.onTick()) {
            current.setLifecycle(Animation.Lifecycle.CLOSED);
            // 清除动画
            this.current = null;
        }
    }

    @Override
    public void set(Animation animation) {
        this.current = animation;
        // 重置
    }

    @Override
    public void set(Animation animation, Long delay) {
//        stack.put()
    }

    @Override
    public KarkenBone getBone(String boneId) {
        if (current == null) {
            return null;
        }

        return current.getBone(boneId);
    }

    @Override
    public Animation getAnimation() {
        return current;
    }
}
