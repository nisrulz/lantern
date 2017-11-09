<h1 align="center">Lantern&nbsp;<a href="https://twitter.com/intent/tweet?text=CCheckout%20Lantern%3A%20Android%20library%20handling%20flashlight%20for%20camera%20and%20camera2%20api%20%F0%9F%98%8E&via=nisrulz&hashtags=AndroidDev,android,library,OpenSource">
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
    <a href="https://android-arsenal.com/api?level=9">
        <img src="https://img.shields.io/badge/API-9%2B-orange.svg?style=flat"/>
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
    <a href="https://www.paypal.me/nisrulz/5">
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
compile 'com.github.nisrulz:lantern:{latest version}'
```
where `{latest version}` corresponds to published version in [ ![Download](https://api.bintray.com/packages/nisrulz/maven/com.github.nisrulz%3Alantern/images/download.svg) ](https://bintray.com/nisrulz/maven/com.github.nisrulz%3Alantern/_latestVersion)

# Usage

### Handling Flashlight states

1. Declare permissions in your app's `AndroidManifest.xml` file

    ```xml
    <!-- Permissions : Allows access to flashlight -->
    <uses-permission android:name="android.permission.CAMERA"/>
    <uses-permission android:name="android.permission.FLASHLIGHT"/>
    ```

1. Init code.
   
   > Make sure you have checked for `Manifest.permission.Camera` permission first

    ```java
    Lantern.getInstance().init(context);
    ```
1. Manage states via
  
    + Turn On

        ```java
        Lantern.getInstance().turnOnFlashlight(context);
        ```
    + Turn Off

        ```java
        Lantern.getInstance().turnOffFlashlight(context);
        ```

### Handling Display/Screen states

1. Declare permissions in your app's `AndroidManifest.xml` file

    ```xml
    <!-- Permissions : Allows access to change settings -->
    <uses-permission android:name="android.permission.WRITE_SETTINGS"/>
    ```

1. Keep screen on
  
    + Enable: Keep display on

        ```java
        Lantern.getInstance().keepDisplayOn(activity)
        ```

    + Disable: Keep display on

        ```java
        Lantern.getInstance().clearKeepDisplayOn(activity)
        ```

### Below requires that you have permission to write to system settings.
1. Make use of helper functions

    + Check if the permission to write to system settings is granted or not

        ```java
        Lantern.getInstance().checkSystemWritePermission(activity)
        ```
    + If not granted, request for the same

        ```java
        Lantern.getInstance().requestSystemWritePermission(activity)
        ```

1. Handle states

    + Set display/screen to full bright state

        ```java
        Lantern.getInstance().setDisplayToFullBright(activity)
        ```

    + Reset display/screen to auto-bright state

        ```java
        Lantern.getInstance().resetDisplayToAutoBright(activity)
        ```


# Pull Requests
I welcome and encourage all pull requests. It usually will take me within 24-48 hours to respond to any issue or request. Here are some basic rules to follow to ensure timely addition of your request:
  1. Match coding style (braces, spacing, etc.) This is best achieved using CMD+Option+L (Reformat code) on Mac (not sure for Windows) with Android Studio defaults. The code style used in this project is from [Grandcentrix](https://github.com/grandcentrix/AndroidCodeStyle), so please use the same when editing this project.
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
>  + [PayPal](https://www.paypal.me/nisrulz/5)
>  + Bitcoin Address: 13PjuJcfVW2Ad81fawqwLtku4bZLv1AxCL
>
> I love using my work and I'm available for contract work. Freelancing helps to maintain and keep [my open source projects](https://github.com/nisrulz/) up to date!

<img src="http://forthebadge.com/images/badges/built-for-android.svg" />
