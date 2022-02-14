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

import android.annotation.TargetApi
import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraManager.TorchCallback
import android.os.Build.VERSION_CODES
import androidx.annotation.NonNull

@TargetApi(VERSION_CODES.M)
internal class PostMarshmallow(context: Context) : FlashController {
    private val cameraManager: CameraManager?
    private var cameraId: String? = null
    private var torchEnabledFlag = false
    override fun off() {
        try {
            if (checkCameraId()) {
                cameraManager?.setTorchMode(cameraId, false)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun on() {
        try {
            if (checkCameraId()) {
                cameraManager?.setTorchMode(cameraId?, true)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun torchEnabled(): Boolean {
        return torchEnabledFlag
    }

    /**
     * Check if the camera manager returns a camera id
     *
     * @return boolean
     */
    private fun checkCameraId(): Boolean {
        return try {
            cameraManager != null && cameraManager.cameraIdList.size > 0
        } catch (e: CameraAccessException) {
            false
        }
    }

    init {
        cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            if (checkCameraId()) {
                assert(cameraManager != null)
                cameraId = cameraManager?.cameraIdList[0]
                cameraManager.registerTorchCallback(object : TorchCallback() {
                    override fun onTorchModeUnavailable(@NonNull cameraIdentifier: String) {
                        torchEnabledFlag = false
                        super.onTorchModeUnavailable(cameraId)
                    }

                    override fun onTorchModeChanged(
                        @NonNull cameraIdentifier: String,
                        enabled: Boolean
                    ) {
                        torchEnabledFlag = enabled
                        super.onTorchModeChanged(cameraId, enabled)
                    }
                }, null)
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }
}