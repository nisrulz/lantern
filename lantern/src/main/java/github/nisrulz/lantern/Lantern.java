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

package github.nisrulz.lantern;

import static github.nisrulz.lantern.Utils.isMarshmallowAndAbove;

import android.Manifest;
import android.app.Activity;
import android.os.Handler;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class Lantern {

    private WeakReference<Activity> activityWeakRef;

    private final DisplayLightController displayLightController;

    private FlashController flashController;

    private boolean isFlashOn = false;

    private final Utils utils;

    private final Handler handler;

    private long pulseTime = 1000;

    public Lantern(Activity activityWeakRef) {
        this.activityWeakRef = new WeakReference<>(activityWeakRef);
        utils = new Utils();
        handler = new Handler();
        displayLightController = new DisplayLightControllerImpl(activityWeakRef);
    }

    public void requestCameraPermission(final int REQUEST_CODE) {
        ActivityCompat
                .requestPermissions(activityWeakRef.get(), new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);
    }

    public Lantern alwaysOnDisplay(boolean enabled) {
        if (enabled) {
            displayLightController.enableAlwaysOnMode();
        } else {
            displayLightController.disableAlwaysOnMode();
        }
        return this;
    }

    public Lantern autoBright(boolean enabled) {
        if (enabled) {
            displayLightController.enableAutoBrightMode();
        } else {
            displayLightController.disableAutoBrightMode();
        }
        return this;
    }


    private final Runnable pulseRunnable = new Runnable() {
        @Override
        public void run() {
            enableTorchMode(!isFlashOn);
            handler.postDelayed(pulseRunnable, pulseTime);
        }
    };


    public Lantern withDelay(long time, final TimeUnit timeUnit) {
        this.pulseTime = TimeUnit.MILLISECONDS.convert(time, timeUnit);
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

    public Lantern checkAndRequestSystemPermission(boolean enabled) {
        if (enabled) {
            displayLightController.requestSystemWritePermission(activityWeakRef.get());
        }
        return this;
    }

    public void cleanup() {
        displayLightController.cleanup();
        this.activityWeakRef = null;
    }

    public Lantern enableTorchMode(boolean enabled) {
        if (activityWeakRef != null) {
            if (enabled) {
                if (!isFlashOn && utils.checkForCameraPermission(activityWeakRef.get().getApplicationContext())) {
                    flashController.on();
                    isFlashOn = true;
                }
            } else {
                if (isFlashOn && utils.checkForCameraPermission(activityWeakRef.get().getApplicationContext())) {
                    flashController.off();
                    isFlashOn = false;
                }
            }
        } else {
            flashController.off();
            isFlashOn = false;
        }
        return this;
    }

    public Lantern fullBrightDisplay(boolean enabled) {
        if (enabled) {
            displayLightController.enableFullBrightMode();
        } else {
            displayLightController.disableFullBrightMode();
        }
        return this;
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    public boolean init() {
        if (activityWeakRef != null) {
            if (utils.checkIfCameraFeatureExists(activityWeakRef.get()) && utils
                    .checkForCameraPermission(activityWeakRef.get())) {
                if (isMarshmallowAndAbove()) {
                    flashController = new PostMarshmallow(activityWeakRef.get());
                } else {
                    flashController = new PreMarshmallow();
                }
                return true;
            }
        }
        return false;
    }
}
