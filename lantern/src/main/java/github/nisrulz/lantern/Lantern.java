package github.nisrulz.lantern;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresPermission;
import android.view.Window;
import android.view.WindowManager;

public class Lantern {

  private boolean isFlashOn = false;
  private PostLollipop postLollipop;
  private PreLollipop preLollipop;

  private Lantern() {
  }

  private static class LazyHolder {
    static final Lantern INSTANCE = new Lantern();
  }

  public static Lantern getInstance() {
    return LazyHolder.INSTANCE;
  }

  //---------------------** Flashlight Utilities **---------------------//

  @RequiresPermission(Manifest.permission.CAMERA)
  public void init(Context context) {
    if (isLollipopAndAbove()
        && checkFeature(context, PackageManager.FEATURE_CAMERA_FLASH)
        && checkPermissions(context, Manifest.permission.CAMERA)) {
      postLollipop = new PostLollipop(context);
    }
    else {
      preLollipop = new PreLollipop();
    }
  }

  public void turnOnFlashlight() {
    if (!isFlashOn) {
      if (isLollipopAndAbove()) {
        postLollipop.turnOn();
      }
      else {
        preLollipop.turnOn();
      }
      isFlashOn = true;
    }
  }

  public void turnOffFlashlight() {
    if (isFlashOn) {
      if (isLollipopAndAbove()) {
        postLollipop.turnOff();
      }
      else {
        preLollipop.turnOff();
      }
      isFlashOn = false;
    }
  }

  //---------------------** Display Utilities **---------------------//

  public void clearKeepDisplayOn(Activity activity) {
    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
  }

  public void keepDisplayOn(Activity activity) {
    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
  }

  @RequiresPermission(Manifest.permission.WRITE_SETTINGS)
  public void setDisplayToFullBright(Activity activity) {
    Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

    Window window = activity.getWindow();
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.screenBrightness = 100 / 100.0f;
    window.setAttributes(layoutParams);
  }

  @RequiresPermission(Manifest.permission.WRITE_SETTINGS)
  public void resetDisplayToAutoBright(Activity activity) {
    Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
        Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
  }

  public boolean checkSystemWritePermission(Activity activity) {
    boolean retVal = true;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      retVal = Settings.System.canWrite(activity);
    }
    return retVal;
  }

  public void requestSystemWritePermission(Activity activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
      intent.setData(Uri.parse("package:" + activity.getPackageName()));
      activity.startActivity(intent);
    }
  }

  //---------------------** Misc Utilities **---------------------//

  private boolean isLollipopAndAbove() {
    return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP;
  }

  public boolean checkPermissions(Context context, String permission) {
    return context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
  }

  // Method : Check if the device has a Flash as hardware or not
  public boolean checkFeature(Context context, String feature) {
    return context.getPackageManager().hasSystemFeature(feature);
  }
}
