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

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

/**
 * The type Utils.
 */
internal class Utils {
    /**
     * Check for camera permission boolean.
     *
     * @param context the context
     * @return the boolean
     */
    fun checkForCameraPermission(context: Context): Boolean {
        return (context.checkCallingOrSelfPermission(Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)
    }

    /**
     * Check if the device has a Flash as hardware or not
     *
     * @param context the context
     * @return the boolean
     */
    fun checkIfCameraFeatureExists(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    companion object {
        /**
         * Is marshmallow and above boolean.
         *
         * @return the boolean
         */
        val isMarshmallowAndAbove: Boolean
            get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }
}