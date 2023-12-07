# Usage

Lantern uses a fluent api. You can enable/disable feature by calling the right method on the Lantern object. Use what you need!

1. Declare permissions in your app's `AndroidManifest.xml` file

    ```xml
    <!-- Permissions : Allows access to flashlight -->
    <uses-permission android:name="android.permission.CAMERA"/>
    <uses-permission android:name="android.permission.FLASHLIGHT"/>

    <!-- Permissions : Allows access to change system settings for handling screen states -->
    <uses-permission android:name="android.permission.WRITE_SETTINGS"/>
    ```

1. Init code.

    ```java
    private Lantern lantern = new Lantern(this) // pass a context
                                // setup the display controller if you want to use the display as a torch, pass activity reference
                                .setupDisplayController(MainActivity.this)
                                // Check and request for system permission, used for handling screen states
                                .checkAndRequestSystemPermission()
                                // OPTIONAL: Setup Lantern to observe the lifecycle of the activity/fragment, handles auto-calling cleanup() method
                                .observeLifecycle(this);

    // Init Lantern's torch feature by calling `initTorch()`, which also check if camera permission is granted + camera feature exists
    // In case permission is not granted, request for the permission and retry by calling `initTorch()` method
    // NOTE: In case camera feature/hardware does not exist, `initTorch()` will return `false` and Lantern will not have
    // torch functionality but only screen based features
    if (!lantern.initTorch()) {
        // Request camera permission if it is not granted
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);
    }


    // Handle the runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            // Retry initializing the Lantern's torch feature
            if (!lantern.initTorch()) {
                // Camera Permission Denied! Do something.
            }
        }
    }
    ```
1. Cleanup
   > If you are not observing the lifecyle of the activity/fragment via `observeLifecycle(this)` method, then you
   > need to call `cleanup()` method in `onDestroy()` of the Activity.

    ```java
    @Override
    protected void onDestroy() {
        lantern.cleanup();
        super.onDestroy();
    }
    ```
1. Manage states via

   + Turn On

       ```java
       lantern
           // Enable always on display
           .alwaysOnDisplay(true)
           // Set screen to full bright
           .fullBrightDisplay(true)
           // Or set screen to Auto Bright
           .autoBright(true)
           // Enable torch via flash
           .enableTorchMode(true)
           // Enable pulsating torch
           .pulse(true)
           // Set the delay for between each pulse
           .withDelay(1, TimeUnit.SECONDS);
       ```
   + Turn Off

       ```java
       lantern
           // Disable always on display
           .alwaysOnDisplay(false)
           // Unset full bright screen state
           .fullBrightDisplay(false)
           // Or unset screen from Auto Bright
           .autoBright(false)
           // Disable torch via flash
           .enableTorchMode(false)
           // Disable pulsating torch
           .pulse(false);
       ```
   + Query enabled state of the torch i.e., flashlight

       ```java
       boolean torchEnabled = lantern.isTorchEnabled();
       ```
   + Check if System Write permission has been granted

       ```java
       boolean isPermissionGranted = lantern.isSystemWritePermissionGranted();
       ```