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

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraManager.TorchCallback;
import android.os.Build.VERSION_CODES;

import androidx.annotation.NonNull;

@TargetApi(VERSION_CODES.M)
class PostMarshmallow implements FlashController {

    private final CameraManager cameraManager;
    private String cameraId;
    private boolean torchEnabledFlag = false;

    PostMarshmallow(Context context) {
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            if (checkCameraId()) {
                assert cameraManager != null;

                cameraId = cameraManager.getCameraIdList()[0];
                cameraManager.registerTorchCallback(new TorchCallback() {
                    @Override
                    public void onTorchModeUnavailable(@NonNull final String cameraIdentifier) {
                        torchEnabledFlag = false;
                        super.onTorchModeUnavailable(cameraId);
                    }

                    @Override
                    public void onTorchModeChanged(@NonNull final String cameraIdentifier, final boolean enabled) {
                        torchEnabledFlag = enabled;
                        super.onTorchModeChanged(cameraId, enabled);
                    }
                }, null);
            }

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void off() {
        try {
            if (checkCameraId()) {
                cameraManager.setTorchMode(cameraId, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void on() {
        try {
            if (checkCameraId()) {
                cameraManager.setTorchMode(cameraId, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean torchEnabled() {
        return torchEnabledFlag;
    }

    /**
     * Check if the camera manager returns a camera id
     *
     * @return boolean
     */
    private boolean checkCameraId() {
        try {
            return (cameraManager != null) && (cameraManager.getCameraIdList().length > 0);
        } catch (CameraAccessException e) {
            return false;
        }
    }
}
