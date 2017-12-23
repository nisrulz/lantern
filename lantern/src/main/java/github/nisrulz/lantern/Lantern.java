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
import android.support.annotation.RequiresPermission;

public class Lantern {

    private Activity activity;

    private boolean isFlashOn = false;

    private FlashController flashController;

    private DisplayLightController displayLightController;

    private Utils utils;

    public Lantern(Activity activity) {
        this.activity = activity;
        utils = new Utils();
        displayLightController = new DisplayLightControllerImpl(activity);
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    public boolean init() {
        if (utils.checkIfCameraFeatureExists(activity)) {
            if (isMarshmallowAndAbove()) {
                flashController = new PostMarshmallow(activity);
            } else {
                flashController = new PreMarshmallow();
            }
            return true;
        } else {
            return false;
        }
    }

    public void cleanup() {
        this.activity = null;
        utils = null;
        displayLightController.cleanup();
    }

    // fluent api

    public boolean on() {
        return true;
    }

    public boolean off() {
        return true;
    }

    public Lantern autoBright(boolean enabled) {
        if (enabled) {
            displayLightController.enableAutoBrightMode();
        } else {
            displayLightController.disableAutoBrightMode();
        }
        return this;
    }

    public Lantern fullBright(boolean enabled) {
        if (enabled) {
            displayLightController.enableFullBrightMode();
        } else {
            displayLightController.disableFullBrightMode();
        }
        return this;
    }

    public Lantern torch(boolean enabled) {
        if (enabled) {
            if (!isFlashOn && utils.checkForCameraPermission(activity.getApplicationContext())) {
                flashController.on();
                isFlashOn = true;
            }
        } else {
            if (isFlashOn && utils.checkForCameraPermission(activity.getApplicationContext())) {
                flashController.off();
                isFlashOn = false;
            }
        }
        return this;
    }

    public Lantern alwaysOnDisplay(boolean enabled) {
        if (enabled) {
            displayLightController.enableAlwaysOnMode();
        } else {
            displayLightController.disableAlwaysOnMode();
        }
        return this;
    }

    public Lantern checkAndRequestSystemPermission(boolean enabled) {
        if (enabled) {
            displayLightController.checkAndRequestSystemPermission();
        }
        return this;
    }


}
