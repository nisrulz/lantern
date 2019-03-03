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

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;

/**
 * The type Pre marshmallow.
 */
@SuppressWarnings("deprecation")
class PreMarshmallow implements FlashController {

    private Camera camera;

    public PreMarshmallow() {
        initCamera();
    }

    private void initCamera(){
        if (camera == null) {
            try {
                camera = Camera.open(getCameraId());
            } catch (RuntimeException ex) {
                System.out.println("Runtime error while opening camera!");
            }
        }
    }

    @Override
    public void off() {
        try {
            if (camera != null) {
                Camera.Parameters p = camera.getParameters();
                p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(p);
                camera.stopPreview();
                camera.release();
                camera = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void on() {
        try {
            off();
            initCamera();
            if (camera != null) {
                Camera.Parameters params = camera.getParameters();
                params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(params);
                camera.startPreview();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean torchEnabled() {
        if(camera!=null && camera.getParameters()!=null) {
            return camera.getParameters().getFlashMode() == Parameters.FLASH_MODE_TORCH;
        }
        return false;
    }

    private int getCameraId() {
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
                return i;
            }
        }
        return 0;
    }
}
