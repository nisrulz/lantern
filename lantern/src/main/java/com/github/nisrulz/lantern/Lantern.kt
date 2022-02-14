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
import android.os.Handler
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

class Lantern(@field:Nullable @param:Nullable private val context: Context?) : LifecycleObserver {
    private val handler: Handler
    private val utils: Utils
    private var activityWeakRef: WeakReference<Activity>? = null

    @Nullable
    private var displayLightController: DisplayLightController? = null

    @Nullable
    private var flashController: FlashController? = null
    private var pulseTime: Long = 1000
    private val pulseRunnable: Runnable = object : Runnable {
        override fun run() {
            if (flashController != null) {
                enableTorchMode(!flashController!!.torchEnabled())
                handler.postDelayed(this, pulseTime)
            }
        }
    }

    fun setupDisplayController(activity: Activity): Lantern {
        activityWeakRef = WeakReference<Activity>(activity)
        if (displayLightController == null) {
            displayLightController = DisplayLightControllerImpl(activityRef)
        }
        return this
    }

    fun alwaysOnDisplay(enabled: Boolean): Lantern {
        if (displayLightController != null) {
            if (enabled) {
                displayLightController!!.enableAlwaysOnMode()
            } else {
                displayLightController!!.disableAlwaysOnMode()
            }
        }
        return this
    }

    fun autoBright(enabled: Boolean): Lantern {
        if (displayLightController != null) {
            if (enabled) {
                displayLightController!!.enableAutoBrightMode()
            } else {
                displayLightController!!.disableAutoBrightMode()
            }
        }
        return this
    }

    fun checkAndRequestSystemPermission(): Lantern {
        if (displayLightController != null) {
            displayLightController!!.requestSystemWritePermission()
        }
        return this
    }

    val isSystemWritePermissionGranted: Boolean
        get() {
            var result = false
            if (displayLightController != null) {
                result = displayLightController!!.checkSystemWritePermission()
            }
            return result
        }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun cleanup() {
        handler.removeCallbacks(pulseRunnable)
        if (displayLightController != null) {
            displayLightController!!.cleanup()
        }
        activityWeakRef = null
    }

    fun enableTorchMode(enabled: Boolean): Lantern {
        if (flashController != null) {
            if (context != null) {
                if (enabled) {
                    if (!flashController!!.torchEnabled()
                        && utils.checkForCameraPermission(context.applicationContext)
                    ) {
                        flashController!!.on()
                    }
                } else {
                    if (flashController!!.torchEnabled()
                        && utils.checkForCameraPermission(context.applicationContext)
                    ) {
                        flashController!!.off()
                    }
                }
            } else {
                flashController!!.off()
            }
        }
        return this
    }

    fun fullBrightDisplay(enabled: Boolean): Lantern {
        if (displayLightController != null) {
            if (enabled) {
                displayLightController!!.enableFullBrightMode()
            } else {
                displayLightController!!.disableFullBrightMode()
            }
        }
        return this
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    fun initTorch(): Boolean {
        if (context != null) {
            if (utils.checkIfCameraFeatureExists(context)
                && utils.checkForCameraPermission(context)
            ) {
                flashController = if (Utils.isMarshmallowAndAbove()) {
                    PostMarshmallow(context)
                } else {
                    PreMarshmallow()
                }
                return true
            }
        }
        return false
    }

    fun observeLifecycle(lifecycleOwner: LifecycleOwner): Lantern {
        // Subscribe to listening lifecycle
        lifecycleOwner.lifecycle.addObserver(this)
        return this
    }

    fun pulse(enabled: Boolean): Lantern {
        if (enabled) {
            handler.postDelayed(pulseRunnable, pulseTime)
        } else {
            handler.removeCallbacks(pulseRunnable)
        }
        return this
    }

    fun withDelay(time: Long, timeUnit: TimeUnit?): Lantern {
        pulseTime = TimeUnit.MILLISECONDS.convert(time, timeUnit)
        return this
    }

    /**
     * Torch Enabled State
     *
     * @return boolean
     */
    @get:RequiresPermission(Manifest.permission.CAMERA)
    val isTorchEnabled: Boolean
        get() = if (initTorch() && flashController != null) {
            flashController!!.torchEnabled()
        } else false
    private val activityRef: Activity?
        private get() = activityWeakRef!!.get()

    init {
        utils = Utils()
        handler = Handler()
    }
}