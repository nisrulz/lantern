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
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.github.nisrulz.lantern.Lantern
import com.github.nisrulz.lanternproject.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE = 100
    private var isOn = true

    private val lantern by lazy {
        Lantern(this) // pass a context
            // setup the display controller if you want to use the display as a torch,
            // pass activity reference
            .setupDisplayController(this@MainActivity)
            // OPTIONAL: Setup Lantern to observe the lifecycle of the activity/fragment,
            // handles auto-calling cleanup() method
            .observeLifecycle(this)
    }

    private lateinit var binding: ActivityMainBinding

    // Runtime Permission is being handled and checked against internally by Lantern
    @SuppressLint("MissingPermission")
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {
            setContentView(root)
            setupUi(this)
        }
    }

    @SuppressLint("MissingPermission")
    private fun setupUi(binding: ActivityMainBinding) {
        binding.apply {
            // Check if torch is already enabled, update state of button
            setForState(this, isOn)

            // Init Lantern by calling `init()`, which also check if camera permission is granted + camera feature exists
            // In case permission is not granted, request for the permission and retry by calling `init()` method
            // NOTE: In case camera feature is does not exist, `init()` will return `false` and Lantern will not have
            // torch functionality but only screen based features
            if (!lantern.initTorch()) {
                // Request if permission is not granted
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CODE
                )
            }
            imgBtn.setOnClickListener {
                isOn = !isOn
                setForState(this, isOn)

                if (!isOn) {
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
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int, @NonNull permissions: Array<String>,
        @NonNull grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (!lantern.initTorch()) {
                Toast.makeText(this, "Camera Permission Denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setForState(binding: ActivityMainBinding, enabled: Boolean) {
        with(binding) {
            if (enabled) {
                imgBtn.setDrawable(R.drawable.on, applicationContext)
                rootLayout.setBgColor(android.R.color.white, applicationContext)
            } else {
                imgBtn.setDrawable(R.drawable.off, applicationContext)
                rootLayout.setBgColor(R.color.colorAccent, applicationContext)
            }
        }

    }
}