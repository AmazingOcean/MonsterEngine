package com.gitee.karken.core.animation;

public class DefaultAnimationQuest implements AnimationQuest {

    private Long tick = 0L;

    @Override
    public Long next() {
        return ++tick;
    }

    @Override
    public Long get() {
        return tick;
    }

}
