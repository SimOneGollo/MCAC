package makeritalia.it.mcac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import makeritalia.it.virtualJoystick.*;

public class MainActivity extends AppCompatActivity {

    private JoystickView joystick;
    private TextView txtAngle;
    private TextView txtPower;
    private TextView txtDirec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtAngle = (TextView) findViewById(R.id.txtAngle);
        txtPower = (TextView) findViewById(R.id.txtPower);
        txtDirec = (TextView) findViewById(R.id.txtDirec);
        joystick = (JoystickView) findViewById(R.id.joystick);


        joystick.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {
            @Override
            public void onValueChanged(int angle, int power, int direction) {
                txtAngle.setText(angle);
                txtPower.setText(power);
                switch (direction) {
                    case JoystickView.FRONT:
                        txtDirec.setText(R.string.front);
                        break;
                    case JoystickView.FRONT_RIGHT:
                        txtDirec.setText(R.string.front_right);
                        break;
                    case JoystickView.RIGHT:
                        txtDirec.setText(R.string.right);
                        break;
                    case JoystickView.RIGHT_BOTTOM:
                        txtDirec.setText(R.string.bottom_right);
                        break;
                    case JoystickView.BOTTOM:
                        txtDirec.setText(R.string.bottom);
                        break;
                    case JoystickView.BOTTOM_LEFT:
                        txtDirec.setText(R.string.bottom_left);
                        break;
                    case JoystickView.LEFT:
                        txtDirec.setText(R.string.left);
                        break;
                    case JoystickView.LEFT_FRONT:
                        txtDirec.setText(R.string.front_left);
                        break;
                    default:
                        txtDirec.setText(R.string.center);
                }
            }
        },JoystickView.DEFAULT_LOOP_INTERVAL);
    }



}
