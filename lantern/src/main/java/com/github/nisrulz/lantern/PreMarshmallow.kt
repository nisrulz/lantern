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
package com.github.nisrulz.lantern

import android.hardware.Camera
import android.hardware.Camera.CameraInfo

/**
 * The type Pre marshmallow.
 */
internal class PreMarshmallow : FlashController {
    private var camera: Camera? = null
    private fun initCamera() {
        if (checkCameraId()) {
            try {
                camera = Camera.open(cameraId)
            } catch (ex: RuntimeException) {
                println("Runtime error while opening camera!")
            }
        }
    }

    override fun off() {
        try {
            if (camera != null) {
                val p = camera!!.parameters
                p.flashMode = Camera.Parameters.FLASH_MODE_OFF
                camera!!.parameters = p
                camera!!.stopPreview()
                camera!!.release()
                camera = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun on() {
        try {
            off()
            initCamera()
            if (camera != null) {
                val params = camera!!.parameters
                params.flashMode = Camera.Parameters.FLASH_MODE_TORCH
                camera!!.parameters = params
                camera!!.startPreview()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun torchEnabled(): Boolean {
        return if (camera != null && camera!!.parameters != null) {
            camera!!.parameters.flashMode == Camera.Parameters.FLASH_MODE_TORCH
        } else false
    }

    private val cameraId: Int
        private get() {
            val numberOfCameras = Camera.getNumberOfCameras()
            for (i in 0 until numberOfCameras) {
                val info = CameraInfo()
                Camera.getCameraInfo(i, info)
                if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
                    return i
                }
            }
            return 0
        }

    /**
     * Check if the camera manager returns a camera id
     *
     * @return boolean
     */
    private fun checkCameraId(): Boolean {
        return camera != null && Camera.getNumberOfCameras() > 0
    }

    init {
        initCamera()
    }
}