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

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build.VERSION_CODES;

/**
 * The type Post marshmallow.
 */
class PostMarshmallow implements FlashController {

    private final CameraManager mCameraManager;

    private String mCameraId;

    /**
     * Instantiates a new Post marshmallow.
     *
     * @param context the context
     */
    @TargetApi(VERSION_CODES.LOLLIPOP)
    public PostMarshmallow(Context context) {
        mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {

            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(VERSION_CODES.M)
    @Override
    public void on() {
        try {
            mCameraManager.setTorchMode(mCameraId, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @TargetApi(VERSION_CODES.M)
    @Override
    public void off() {
        try {
            mCameraManager.setTorchMode(mCameraId, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
