package nisrulz.github.lantern;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresPermission;
import android.view.Window;
import android.view.WindowManager;

public class ScreenUtils {

  @RequiresPermission(Manifest.permission.WAKE_LOCK)
  public static void clearKeepScreenOn(Activity activity) {
    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
  }

  @RequiresPermission(Manifest.permission.WAKE_LOCK)
  public static void keepScreenOn(Activity activity) {
    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
  }

  @RequiresPermission(Manifest.permission.WRITE_SETTINGS)
  public static void goFullBright(Activity activity) {
    Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

    Window window = activity.getWindow();
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.screenBrightness = 100 / 100.0f;
    window.setAttributes(layoutParams);
  }

  @RequiresPermission(Manifest.permission.WRITE_SETTINGS)
  public static void resetToAutoBright(Activity activity) {
    Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
        Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
  }

  public static boolean checkSystemWritePermission(Activity activity) {
    boolean retVal = true;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      retVal = Settings.System.canWrite(activity);
      if (!retVal) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivity(intent);
      }
    }
    return retVal;
  }
}
