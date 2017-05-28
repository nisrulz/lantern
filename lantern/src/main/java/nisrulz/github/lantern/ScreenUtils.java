package nisrulz.github.lantern;

import android.app.Activity;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

public class ScreenUtils {
  public static void clearKeepScreenOn(Activity activity) {
    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
  }

  public static void keepScreenOn(Activity activity) {
    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
  }

  public static void goFullBright(Activity activity) {
    Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

    Window window = activity.getWindow();
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.screenBrightness = 100 / 100.0f;
    window.setAttributes(layoutParams);
  }

  public static void resetToAutoBright(Activity activity) {
    Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
        Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
  }
}
