package nisrulz.github.lanternproject;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import nisrulz.github.lantern.Lantern;

public class MainActivity extends AppCompatActivity {
  private boolean isTorchOn;

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Switch toggle = (Switch) findViewById(R.id.switch_flash);
    isTorchOn = false;

    Lantern.getInstance().init(this);

    toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
          // true
          Lantern.getInstance().turnOn();
        }
        else {
          //false
          Lantern.getInstance().turnOff();
        }
      }
    });
  }
}
