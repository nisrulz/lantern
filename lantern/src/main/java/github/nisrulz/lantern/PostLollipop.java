package github.nisrulz.lantern;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;

class PostLollipop {
  private final CameraManager mCameraManager;
  private String mCameraId;

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public PostLollipop(Context context) {
    mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
    try {

      mCameraId = mCameraManager.getCameraIdList()[0];
    } catch (CameraAccessException e) {
      e.printStackTrace();
    }
  }

  @TargetApi(Build.VERSION_CODES.M)
  void turnOn() {
    try {
      mCameraManager.setTorchMode(mCameraId, true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @TargetApi(Build.VERSION_CODES.M)
  void turnOff() {
    try {
      mCameraManager.setTorchMode(mCameraId, false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
