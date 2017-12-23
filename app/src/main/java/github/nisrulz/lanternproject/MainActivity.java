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

package github.nisrulz.lanternproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.Toast;
import github.nisrulz.lantern.Lantern;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SwitchCompat toggle = (SwitchCompat) findViewById(R.id.switch_flash);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            Lantern.getInstance().init(this);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CODE);
        }

        // Check for permission
        final boolean hasSystemWritePermission = Lantern.getInstance().checkSystemWritePermission(this);
        // Request for permission if not yet granted
        if (!hasSystemWritePermission) {
            Lantern.getInstance().requestSystemWritePermission(this);
        }

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    // true
                    Lantern.getInstance().turnOnFlashlight(MainActivity.this);
                    Lantern.getInstance().setDisplayToFullBright(MainActivity.this);
                } else {
                    //false
                    Lantern.getInstance().turnOffFlashlight(MainActivity.this);
                    Lantern.getInstance().resetDisplayToAutoBright(MainActivity.this);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Lantern.getInstance().init(this);
            } else {
                Toast.makeText(MainActivity.this, "Camera Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
