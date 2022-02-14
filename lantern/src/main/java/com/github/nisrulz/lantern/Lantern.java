/*
 * Copyright (C) 2017 Nishant Srivastava
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nisrulz.lantern;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import static com.github.nisrulz.lantern.Utils.isMarshmallowAndAbove;

public class Lantern implements LifecycleObserver {

    @Nullable
    private final Context context;
    private final Handler handler;
    private final Utils utils;
    private WeakReference<Activity> activityWeakRef;
    @Nullable
    private DisplayLightController displayLightController;
    @Nullable
    private FlashController flashController;
    private long pulseTime = 1000;
    private final Runnable pulseRunnable = new Runnable() {
        @Override
        public void run() {
            if (flashController != null) {
                enableTorchMode(!flashController.torchEnabled());
                handler.postDelayed(pulseRunnable, pulseTime);
            }
        }
    };

    public Lantern(@Nullable Context context) {
        this.context = context;
        utils = new Utils();
        handler = new Handler();
    }

    public Lantern setupDisplayController(Activity activity) {
        this.activityWeakRef = new WeakReference<>(activity);
        if (displayLightController == null) {
            displayLightController = new DisplayLightControllerImpl(getActivityRef());
        }
        return this;
    }

    public Lantern alwaysOnDisplay(boolean enabled) {
        if (displayLightController != null) {
            if (enabled) {

                displayLightController.enableAlwaysOnMode();
            } else {
                displayLightController.disableAlwaysOnMode();
            }
        }
        return this;
    }

    public Lantern autoBright(boolean enabled) {
        if (displayLightController != null) {
            if (enabled) {
                displayLightController.enableAutoBrightMode();
            } else {
                displayLightController.disableAutoBrightMode();
            }
        }
        return this;
    }

    public Lantern checkAndRequestSystemPermission() {
        if (displayLightController != null) {
            displayLightController.requestSystemWritePermission();
        }
        return this;
    }

    public boolean isSystemWritePermissionGranted() {
        boolean result = false;

        if (displayLightController != null) {
            result = displayLightController.checkSystemWritePermission();
        }

        return result;
    }

    @OnLifecycleEvent(Event.ON_DESTROY)
    public void cleanup() {
        handler.removeCallbacks(pulseRunnable);
        if (displayLightController != null) {
            displayLightController.cleanup();
        }
        this.activityWeakRef = null;
    }

    public Lantern enableTorchMode(boolean enabled) {
        if (flashController != null) {
            if (context != null) {
                if (enabled) {
                    if (!flashController.torchEnabled()
                            && utils.checkForCameraPermission(context.getApplicationContext())) {
                        flashController.on();
                    }
                } else {
                    if (flashController.torchEnabled()
                            && utils.checkForCameraPermission(context.getApplicationContext())) {
                        flashController.off();
                    }
                }
            } else {
                flashController.off();
            }
        }
        return this;
    }

    public Lantern fullBrightDisplay(boolean enabled) {
        if (displayLightController != null) {
            if (enabled) {
                displayLightController.enableFullBrightMode();
            } else {
                displayLightController.disableFullBrightMode();
            }
        }
        return this;
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    public boolean initTorch() {
        if (context != null) {
            if (utils.checkIfCameraFeatureExists(context)
                    && utils.checkForCameraPermission(context)) {
                if (isMarshmallowAndAbove()) {
                    flashController = new PostMarshmallow(context);
                } else {
                    flashController = new PreMarshmallow();
                }
                return true;
            }
        }
        return false;
    }

    public Lantern observeLifecycle(LifecycleOwner lifecycleOwner) {
        // Subscribe to listening lifecycle
        lifecycleOwner.getLifecycle().addObserver(this);

        return this;
    }

    public Lantern pulse(boolean enabled) {
        if (enabled) {
            handler.postDelayed(pulseRunnable, pulseTime);
        } else {
            handler.removeCallbacks(pulseRunnable);
        }
        return this;
    }

    public Lantern withDelay(long time, final TimeUnit timeUnit) {
        this.pulseTime = TimeUnit.MILLISECONDS.convert(time, timeUnit);
        return this;
    }

    /**
     * Torch Enabled State
     *
     * @return boolean
     */
    @RequiresPermission(Manifest.permission.CAMERA)
    public boolean isTorchEnabled() {
        if (initTorch() && flashController != null) {
            return flashController.torchEnabled();
        }

        return false;
    }

    private Activity getActivityRef() {
        return activityWeakRef.get();
    }
}
