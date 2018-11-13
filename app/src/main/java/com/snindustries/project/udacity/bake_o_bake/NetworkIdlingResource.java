package com.snindustries.project.udacity.bake_o_bake;

import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Shaaz Noormohammad
 * (c) 11/13/18
 */
public class NetworkIdlingResource implements IdlingResource {

    private ResourceCallback callback;
    private AtomicBoolean isIdleNow = new AtomicBoolean(false);

    @Override
    public String getName() {
        return this.getClass().getName();

    }

    @Override
    public boolean isIdleNow() {
        return isIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }

    public void setIsIdleNow(boolean idleNow) {
        this.isIdleNow.set(idleNow);
        if (idleNow && callback != null) {
            callback.onTransitionToIdle();
        }
    }
}
