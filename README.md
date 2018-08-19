<div align="center">
  <img src="img/logo_color.png" height="128" />
</div>

<h1 align="center">Lantern&nbsp;<a href="https://twitter.com/intent/tweet?text=Checkout%20Lantern%3A%20Android%20library%20handling%20flashlight%20for%20camera%20and%20camera2%20api%20%F0%9F%98%8E&via=nisrulz&hashtags=AndroidDev,android,library,OpenSource">
        <img src="https://img.shields.io/twitter/url/http/shields.io.svg?style=social"/>
    </a></h1>

<div align="center">
  <strong>Android library handling flashlight for camera and camera2 api. Added support for handling display/screen light.</strong>
</div>
<br/>
<div align="center">
    <!-- Bintray -->
    <a href="https://bintray.com/nisrulz/maven/com.github.nisrulz%3Alantern/_latestVersion">
        <img src="https://api.bintray.com/packages/nisrulz/maven/com.github.nisrulz%3Alantern/images/download.svg"/>
    </a>
    <!-- API -->
    <a href="https://android-arsenal.com/api?level=14">
        <img src="https://img.shields.io/badge/API-14%2B-orange.svg?style=flat"/>
    </a>
    <!-- Android Arsenal -->
    <a href="https://android-arsenal.com/details/1/5816">
        <img src="https://img.shields.io/badge/Android%20Arsenal-Lantern-brightgreen.svg?style=flat"/>
    </a>
    <!-- GitHub stars -->
    <a href="https://github.com/nisrulz/lantern">
        <img src="https://img.shields.io/github/stars/nisrulz/lantern.svg?style=social&label=Star"/>
    </a>
    <!-- GitHub forks -->
    <a href="https://github.com/nisrulz/lantern/fork">
        <img src="hhttps://img.shields.io/github/forks/nisrulz/lantern.svg?style=social&label=Fork"/>
    </a>
    <!-- GitHub watchers -->
    <a href="https://github.com/nisrulz/lantern">
        <img src="https://img.shields.io/github/watchers/nisrulz/lantern.svg?style=social&label=Watch"/>
    </a>
    <!-- Say Thanks! -->
    <a href="https://saythanks.io/to/nisrulz">
        <img src="https://img.shields.io/badge/Say%20Thanks-!-1EAEDB.svg"/>
    </a>
    <a href="https://www.paypal.me/nisrulz/5usd">
        <img src="https://img.shields.io/badge/$-donate-ff69b4.svg?maxAge=2592000&amp;style=flat">
    </a>
    <br/>
     <!-- GitHub followers -->
    <a href="https://github.com/nisrulz/lantern">
        <img src="https://img.shields.io/github/followers/nisrulz.svg?style=social&label=Follow%20@nisrulz"/>
    </a>
    <!-- Twitter Follow -->
    <a href="https://twitter.com/nisrulz">
        <img src="https://img.shields.io/twitter/follow/nisrulz.svg?style=social"/>
    </a>
</div>

<div align="center">
  <sub>Built with ❤︎ by
  <a href="https://twitter.com/nisrulz">Nishant Srivastava</a> and
  <a href="https://github.com/nisrulz/lantern/graphs/contributors">
    contributors
  </a>
</div>
<br/>
<br/>

# Including in your project
Lantern is available in the Jcenter, so getting it as simple as adding it as a dependency
```gradle
implementation "com.github.nisrulz:lantern:{latest version}"
```
where `{latest version}` corresponds to published version in [ ![Download](https://api.bintray.com/packages/nisrulz/maven/com.github.nisrulz%3Alantern/images/download.svg) ](https://bintray.com/nisrulz/maven/com.github.nisrulz%3Alantern/_latestVersion)

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
    private Lantern lantern = new Lantern(this)
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

# Pull Requests
I welcome and encourage all pull requests. It usually will take me within 24-48 hours to respond to any issue or request. Here are some basic rules to follow to ensure timely addition of your request:
  1. Match coding style (braces, spacing, etc.) This is best achieved using CMD+Option+L (Reformat code) on Mac (not sure for Windows) with Android Studio defaults. This project uses a [modified version of Grandcentrix's code style](https://github.com/nisrulz/AndroidCodeStyle/tree/nishant-config), so please use the same when editing this project.
  2. If its a feature, bugfix, or anything please only change code to what you specify.
  3. Please keep PR titles easy to read and descriptive of changes, this will make them easier to merge :)
  4. Pull requests _must_ be made against `develop` branch. Any other branch (unless specified by the maintainers) will get rejected.
  5. Check for existing [issues](https://github.com/nisrulz/lantern/issues) first, before filing an issue.
  6. Have fun!


## License
Licensed under the Apache License, Version 2.0, [click here for the full license](/LICENSE.txt).

## Author & support
This project was created by [Nishant Srivastava](https://github.com/nisrulz/nisrulz.github.io#nishant-srivastava) but hopefully developed and maintained by many others. See the [the list of contributors here](https://github.com/nisrulz/lantern/graphs/contributors).

> If you appreciate my work, consider buying me a cup of :coffee: to keep me recharged :metal:
>  + [PayPal](https://www.paypal.me/nisrulz/5usd)
>  + Bitcoin Address: 13PjuJcfVW2Ad81fawqwLtku4bZLv1AxCL
>
> Donation to the project is always welcome which helps to maintain and keep [my open source projects](https://github.com/nisrulz/) up to date!

<img src="http://forthebadge.com/images/badges/built-for-android.svg" />