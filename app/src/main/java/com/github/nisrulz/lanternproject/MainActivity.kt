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
package com.github.nisrulz.lanternproject

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.app.ActivityCompat
import com.github.nisrulz.lantern.Lantern
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE = 100
    private var lantern: Lantern? = null

    // Runtime Permission is being handled and checked against internally by Lantern
    @SuppressLint("MissingPermission")
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toggle = findViewById<SwitchCompat>(R.id.switch_flash)
        lantern = Lantern(this) // pass a context
            // setup the display controller if you want to use the display as a torch, pass activity reference
            .setupDisplayController(this@MainActivity) // OPTIONAL: Setup Lantern to observe the lifecycle of the activity/fragment, handles auto-calling cleanup() method
            .observeLifecycle(this)

        // Check if torch is already enabled, update state of toggle switch
        toggle.isChecked = lantern.isTorchEnabled()

        // Init Lantern by calling `init()`, which also check if camera permission is granted + camera feature exists
        // In case permission is not granted, request for the permission and retry by calling `init()` method
        // NOTE: In case camera feature is does not exist, `init()` will return `false` and Lantern will not have
        // torch functionality but only screen based features
        if (!lantern.initTorch()) {
            // Request if permission is not granted
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CODE
            )
        }
        toggle.setOnCheckedChangeListener { compoundButton: CompoundButton?, state: Boolean ->
            if (state) {
                // true
                lantern.alwaysOnDisplay(true)
                    .fullBrightDisplay(true)
                    .enableTorchMode(true)
                    .pulse(true).withDelay(1, TimeUnit.SECONDS)
            } else {
                //false
                lantern.alwaysOnDisplay(false)
                    .fullBrightDisplay(false)
                    .enableTorchMode(false).pulse(false)
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int, @NonNull permissions: Array<String>,
        @NonNull grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (!lantern!!.initTorch()) {
                Toast.makeText(this, "Camera Permission Denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}