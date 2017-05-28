package github.nisrulz.lantern;

import android.hardware.Camera;

@SuppressWarnings("deprecation")
class PreLollipop {

  private final Camera camera;

  public PreLollipop() {
    camera = Camera.open();
  }

  void turnOn() {
    try {

      if (camera != null) {
        Camera.Parameters params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  void turnOff() {
    try {
      if (camera != null) {
        Camera.Parameters p = camera.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(p);
        camera.stopPreview();
        camera.release();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
