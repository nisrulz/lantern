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

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.WindowManager
import java.lang.ref.WeakReference

internal class DisplayLightControllerImpl(activity: Activity) : DisplayLightController {
    private var activityWeakRef: WeakReference<Activity>?
    override fun checkSystemWritePermission(): Boolean {
        var retVal = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            retVal = Settings.System.canWrite(activityRef!!.applicationContext)
        }
        return retVal
    }

    override fun cleanup() {
        activityWeakRef = null
    }

    override fun disableAlwaysOnMode() {
        activityRef!!.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun disableAutoBrightMode() {
        disableFullBrightMode()
    }

    override fun disableFullBrightMode() {
        if (checkSystemWritePermission()) {
            Settings.System.putInt(
                activityRef!!.contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
            )
            val window = activityRef!!.window
            val layoutParams = window.attributes
            layoutParams.screenBrightness = 10 / 100.0f
            window.attributes = layoutParams
        }
    }

    override fun enableAlwaysOnMode() {
        activityRef!!.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun enableAutoBrightMode() {
        if (checkSystemWritePermission()) {
            Settings.System.putInt(
                activityRef!!.contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
            )
        }
    }

    override fun enableFullBrightMode() {
        if (checkSystemWritePermission()) {
            Settings.System.putInt(
                activityRef!!.contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
            )
            val window = activityRef!!.window
            val layoutParams = window.attributes
            layoutParams.screenBrightness = 100 / 100.0f
            window.attributes = layoutParams
        }
    }

    override fun requestSystemWritePermission() {
        // Request for permission if not yet granted
        if (!checkSystemWritePermission()) {
            if (Utils.isMarshmallowAndAbove) {
                val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                intent.data = Uri.parse("package:" + activityRef!!.packageName)
                activityRef!!.startActivity(intent)
            }
        }
    }

    private val activityRef: Activity?
        private get() = activityWeakRef!!.get()

    init {
        activityWeakRef = WeakReference(activity)
    }
}