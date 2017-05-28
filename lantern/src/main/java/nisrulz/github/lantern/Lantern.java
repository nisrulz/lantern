package nisrulz.github.lantern;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

public class Lantern {

  private boolean isFlashOn = false;
  PostLollipop postLollipop;
  PreLollipop preLollipop;
  ScreenUtils screenUtils;

  private Lantern() {
  }

  private static class LazyHolder {
    static final Lantern INSTANCE = new Lantern();
  }

  public static Lantern getInstance() {
    return LazyHolder.INSTANCE;
  }

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

  public void turnOn() {
    if (isLollipopAndAbove()) {
      postLollipop.turnOn();
    }
    else {
      preLollipop.turnOn();
    }
  }

  public void turnOff() {
    if (isLollipopAndAbove()) {
      postLollipop.turnOff();
    }
    else {
      preLollipop.turnOff();
    }
  }

  //---------------------** Misc Utilities **---------------------//

  private boolean isLollipopAndAbove() {
    return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP;
  }

  private boolean checkPermissions(Context context, String permission) {
    return ContextCompat.checkSelfPermission(context, permission)
        == PackageManager.PERMISSION_GRANTED;
  }

  // Method : Check if the device has a Flash as hardware or not
  private boolean checkFeature(Context context, String feature) {
    return context.getPackageManager().hasSystemFeature(feature);
  }
}
