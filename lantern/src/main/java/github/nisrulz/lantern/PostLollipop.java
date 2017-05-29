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
import android.os.Build;

class PostLollipop {
  private final CameraManager mCameraManager;
  private String mCameraId;

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public PostLollipop(Context context) {
    mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
    try {

      mCameraId = mCameraManager.getCameraIdList()[0];
    } catch (CameraAccessException e) {
      e.printStackTrace();
    }
  }

  @TargetApi(Build.VERSION_CODES.M)
  void turnOn() {
    try {
      mCameraManager.setTorchMode(mCameraId, true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @TargetApi(Build.VERSION_CODES.M)
  void turnOff() {
    try {
      mCameraManager.setTorchMode(mCameraId, false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
