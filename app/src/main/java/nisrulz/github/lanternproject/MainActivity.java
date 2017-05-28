package nisrulz.github.lanternproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import nisrulz.github.lantern.Lantern;
import nisrulz.github.lantern.ScreenUtils;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Switch toggle = (Switch) findViewById(R.id.switch_flash);

    Lantern.getInstance().init(this);

    ScreenUtils.checkSystemWritePermission(this);

    toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
          // true
          Lantern.getInstance().turnOn();
          ScreenUtils.goFullBright(MainActivity.this);
        }
        else {
          //false
          Lantern.getInstance().turnOff();
        }
      }
    });
  }
}
