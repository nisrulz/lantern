package nisrulz.github.lanternproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import nisrulz.github.lantern.Lantern;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Switch toggle = (Switch) findViewById(R.id.switch_flash);

    Lantern.getInstance().init(this);

    // Check for permission
    boolean hasSystemWritePermission = Lantern.getInstance().checkSystemWritePermission(this);
    // Request for permission if not yet granted
    if (!hasSystemWritePermission) {
      Lantern.getInstance().requestSystemWritePermission(this);
    }

    toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
          // true
          Lantern.getInstance().turnOnFlashlight();
        }
        else {
          //false
          Lantern.getInstance().turnOffFlashlight();
        }
      }
    });
  }
}
