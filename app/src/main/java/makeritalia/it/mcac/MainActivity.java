package makeritalia.it.mcac;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import makeritalia.it.virtualJoystick.*;

public class MainActivity extends AppCompatActivity {

    private TextView txtAngle;
    private TextView txtPower;
    private TextView txtDirec;

    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice mDevice;
    BluetoothSocket mSocket;

    OutputStream outStream;

    UUID uuid = UUID.fromString("ad916152-4dfa-4500-a8d5-9be094b918c6");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtAngle = (TextView) findViewById(R.id.txtAngle);
        txtPower = (TextView) findViewById(R.id.txtPower);
        txtDirec = (TextView) findViewById(R.id.txtDirec);
        final JoystickView joystick = (JoystickView) findViewById(R.id.joystick);
        final Switch startStop = (Switch) findViewById(R.id.switchStart);
        final ToggleButton tgb =(ToggleButton) findViewById(R.id.connection);

        joystick.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {
            @Override
            public void onValueChanged(int angle, int power, int direction) {
                txtAngle.setText(String.valueOf(angle));
                txtPower.setText(String.valueOf(power));
                switch (direction) {
                    case JoystickView.FRONT:
                        txtDirec.setText(R.string.front);
                        outMessage(String.valueOf(JoystickView.FRONT));
                        break;
                    case JoystickView.FRONT_RIGHT:
                        txtDirec.setText(R.string.front_right);
                        outMessage(String.valueOf(JoystickView.FRONT_RIGHT));
                        break;
                    case JoystickView.RIGHT:
                        txtDirec.setText(R.string.right);
                        outMessage(String.valueOf(JoystickView.RIGHT));
                        break;
                    case JoystickView.RIGHT_BOTTOM:
                        txtDirec.setText(R.string.bottom_right);
                        outMessage(String.valueOf(JoystickView.RIGHT_BOTTOM));
                        break;
                    case JoystickView.BOTTOM:
                        txtDirec.setText(R.string.bottom);
                        outMessage(String.valueOf(JoystickView.BOTTOM));
                        break;
                    case JoystickView.BOTTOM_LEFT:
                        txtDirec.setText(R.string.bottom_left);
                        outMessage(String.valueOf(JoystickView.BOTTOM_LEFT));
                        break;
                    case JoystickView.LEFT:
                        txtDirec.setText(R.string.left);
                        outMessage(String.valueOf(JoystickView.LEFT));
                        break;
                    case JoystickView.LEFT_FRONT:
                        txtDirec.setText(R.string.front_left);
                        outMessage(String.valueOf(JoystickView.LEFT_FRONT));
                        break;
                    default:
                        txtDirec.setText(R.string.center);
                        outMessage(String.valueOf(0));
                }
            }
        },JoystickView.DEFAULT_LOOP_INTERVAL);

        startStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                /*Funzione per definire start end stop*/
                if (isChecked){
                    /*invio la stringa start*/
                }else{
                    /*invio la stringa stop*/
                }
            }
        });

        tgb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tgb.isChecked()) {
                    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (mBluetoothAdapter != null) {
                        //control that bluetooth is enabled
                        if (mBluetoothAdapter.isEnabled()) {
                            mDevice = mBluetoothAdapter.getRemoteDevice("20:14:12:03:10:44");
                            try {
                                //bluetooth connection
                                mSocket = mDevice.createRfcommSocketToServiceRecord(uuid);
                                mSocket.connect();
                                outStream = mSocket.getOutputStream();
                                Toast.makeText(MainActivity.this, "ON", Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                tgb.setChecked(false);
                                try {
                                    //try to close bluetooth connection
                                    mSocket.close();
                                } catch (IOException ceXC) {
                                }
                                Toast.makeText(MainActivity.this, "bluetooth isn't connect", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "BlueTooth isn't enabled", Toast.LENGTH_LONG).show();
                            tgb.setChecked(false);
                        }
                    }//close mBluetoothAdapter!=null
                } else {
                    try {
                        //try to close socket connections
                        outStream.close();
                        mSocket.close();
                    } catch (IOException ceXC) {}
                }
            }
        });

    }
    //funzione per scrivere nella output del bluetooth
    private void outMessage(String message) {
        if (outStream == null){return;}
        byte[] msgBuffer = message.getBytes();
        try{outStream.write(msgBuffer);}
        catch (IOException e){
            Toast.makeText(MainActivity.this, "Messaggio non Inviato", Toast.LENGTH_SHORT).show();
        }
    }



}
