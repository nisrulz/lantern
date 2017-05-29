# Lantern

### Specs
[ ![Download](https://api.bintray.com/packages/nisrulz/maven/com.github.nisrulz%3Alantern/images/download.svg) ](https://bintray.com/nisrulz/maven/com.github.nisrulz%3Alantern/_latestVersion) [![API](https://img.shields.io/badge/API-14%2B-orange.svg?style=flat)](https://android-arsenal.com/api?level=14)

### Show some :heart:
[![GitHub stars](https://img.shields.io/github/stars/nisrulz/lantern.svg?style=social&label=Star)](https://github.com/nisrulz/lantern) [![GitHub forks](https://img.shields.io/github/forks/nisrulz/lantern.svg?style=social&label=Fork)](https://github.com/nisrulz/lantern/fork) [![GitHub watchers](https://img.shields.io/github/watchers/nisrulz/lantern.svg?style=social&label=Watch)](https://github.com/nisrulz/lantern) [![GitHub followers](https://img.shields.io/github/followers/nisrulz.svg?style=social&label=Follow)](https://github.com/nisrulz/lantern)  
[![Twitter Follow](https://img.shields.io/twitter/follow/nisrulz.svg?style=social)](https://twitter.com/nisrulz)

Android library handling flashlight for camera and camera2 api. Added support for handling display/screen light.

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

2. Handle states

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
  1. Match coding style (braces, spacing, etc.) This is best achieved using `CMD`+`Option`+`L` (Reformat code) on Mac (not sure for Windows) with Android Studio defaults.
  2. If its a feature, bugfix, or anything please only change code to what you specify.
  3. Please keep PR titles easy to read and descriptive of changes, this will make them easier to merge :)
  4. Pull requests _must_ be made against `develop` branch. Any other branch (unless specified by the maintainers) will get rejected.
  5. Check for existing [issues](https://github.com/nisrulz/lantern/issues) first, before filing an issue.
  6. Have fun!

### Created & Maintained By
[Nishant Srivastava](https://github.com/nisrulz) ([@nisrulz](https://www.twitter.com/nisrulz))

> If you found this library helpful or you learned something from the source code and want to thank me, consider [buying me a cup of](https://www.paypal.me/nisrulz/5) :coffee:

License
=======

    Copyright 2017 Nishant Srivastava

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
