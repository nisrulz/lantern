package nisrulz.github.lantern;

import android.hardware.Camera;

class PreLollipop {

  private Camera camera;

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
      else {
        return;
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
      else {
        return;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
